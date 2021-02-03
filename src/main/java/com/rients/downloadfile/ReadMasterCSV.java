package com.rients.downloadfile;

import com.rients.downloadfile.model.Coin;
import com.rients.downloadfile.model.Transaction;
import com.rients.downloadfile.model.Tupel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.rients.downloadfile.StaticData.RESULTPATH;
import static com.rients.downloadfile.StaticData.SEP;
import static com.rients.downloadfile.StaticData.TRANSACTIONSPATH;

public class ReadMasterCSV {

	public static void main(String[] args) throws IOException {
		List<Coin> coins = Arrays.asList(
				new Coin(1, 1, "Bitcoin", "BTC", 1),
				new Coin(2, 2, "Ethereum", "ETH", 1027),
				new Coin(3, 3, "Tether", "USDT", 825),
				new Coin(4, 5, "XRP", "XRP", 52));
		new ReadMasterCSV().readMasterCSV(coins);
	}

	public String format(Double d) {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		String firstNumberAsString = decimalFormat.format(d);
		return firstNumberAsString;
	}

	private void readMasterCSV(List<Coin> coins) {

		int daysBack = 17;

//		Map<String, Integer> coinIndex = new TreeMap<>();
//		coins.forEach((coin) -> coinIndex.put(coin.getCoinSymbol(), coin.getId() - 1));

		Map<String, List<Double>> masterCSV = TradingFileUtils.readMasterCSV();
		List<Tupel> tupels = TradingUtils.generateTupes(coins);

		Map<String, List<List<Double>>> merged = new TreeMap<>();
		masterCSV.forEach((date, rates) -> {
			List<List<Double>> totalList = new ArrayList<>();
			List<Double> requestedQuotes = IntStream
					.range(0, rates.size())
					.filter(i -> StaticData.contains(coins, i))
					.mapToDouble(i -> rates.get(i - 1))
					.boxed()
					.collect(Collectors.toList());
			List<Double> calculatedResults = calculateRelativeStrongValues(requestedQuotes, tupels);
			totalList.add(requestedQuotes);
			totalList.add(calculatedResults);
			merged.put(date, totalList);
		});

		String header = calculateHeader(coins, tupels);
		List<String> lines = generateOutputLines(merged);
		lines.set(0, header);
		TradingFileUtils.writeToFile(RESULTPATH, lines);

		Intermediate intermediate = new Intermediate(tupels.size());
		merged.forEach((date, values) ->
		{
			List<Double> calculatedValues = values.get(1);
			IntStream.range(0, calculatedValues.size()).forEach(index -> {
				Element element = new Element(date, calculatedValues.get(index), 0d, 0);
				intermediate.getData().get(index).add(element);
			});
		});
		intermediate.getData().forEach(column -> {
			SMA sma = new SMA(daysBack);
			column.forEach(cell -> {
				Double oldValue = sma.currentAverage();
				Double value = sma.compute(cell.getData());
				cell.setSma(value);
				cell.setScore((value < oldValue) ? 0 : 1);
			});
		});
		// calculate points
		int[] idx = {0};
		Map<String, List<Integer>> pointsList = new TreeMap<>();
		intermediate.getData().forEach(column -> {
			Tupel tupel = tupels.get(idx[0]);
			int first = tupel.getFirstCoin().getId();
			int second = tupel.getSecondCoin().getId();
			column.forEach(element -> {
				if (idx[0] == 0) {
					List<Integer> points = new ArrayList<>();
					IntStream is = IntStream.range(0, coins.size());
					is.forEach(index -> points.add(0));
					pointsList.put(element.getDate(), points);
				}
				int winningIdx = element.getScore() == 1 ? first : second;
				int newValue = pointsList.get(element.getDate()).get(winningIdx - 1) + 1;
				List<Integer> currentList = pointsList.get(element.getDate());
				currentList.set(winningIdx - 1, newValue);
			});
			idx[0]++;
		});
		Map<String, String> coinsInStock = new TreeMap<>();
		pointsList.forEach((date, score) -> {
			Integer maxVal = Collections.max(score); // should return 7
			Integer maxIdx = score.indexOf(maxVal);
			coinsInStock.put(date, coins.get(maxIdx).getCoinSymbol());
		});
		List<Transaction> transactions = new ArrayList<>();
		String coinInStock = "";
		boolean firstTransaction = true;
		Transaction transaction = null;
		Double portfolioAmount = 1000d;
		Map<String, Double> totalAmountList = new TreeMap<>();

		int index = 0;
		for (Map.Entry<String, String> entry : coinsInStock.entrySet()) {
			if (index >= daysBack) {
				String currentDate = entry.getKey();
				String currentCoinSymbol = entry.getValue();

				if (!coinInStock.equals(currentCoinSymbol)) {
					if (!firstTransaction) {
						// close
						transaction.setSellRate(masterCSV.get(currentDate).get(getCoinIndexInMaster(coinInStock, coins) -1));
						transaction.setSellDate(currentDate);
						transactions.add(transaction);
						portfolioAmount = transaction.getPieces() * transaction.getSellRate();
					}
					transaction = new Transaction();
					transaction.setBuyDate(currentDate);
					transaction.setCoinSymbol(currentCoinSymbol);
					Double rate = masterCSV.get(currentDate).get(getCoinIndexInMaster(currentCoinSymbol, coins) - 1);
					transaction.setBuyRate(rate);
					transaction.setPieces(portfolioAmount / rate);
					coinInStock = currentCoinSymbol;
					firstTransaction = false;
				}
				if (index == coinsInStock.size() - 1) {
					transaction.setSellRate(masterCSV.get(currentDate).get(getCoinIndexInMaster(coinInStock, coins) - 1));
					transaction.setSellDate(currentDate);
					transactions.add(transaction);
					portfolioAmount = transaction.getPieces() * transaction.getSellRate();
					System.out.println(transaction);

				}
			}
			index++;
		}

		List<String> transactionLines = transactions.stream().map(Transaction::toString).collect(Collectors.toList());
		transactionLines.add(0, Transaction.getHeader());
		TradingFileUtils.writeToFile(TRANSACTIONSPATH, transactionLines);
		System.out.println("portfolioAmount: " + format(portfolioAmount));
		System.out.println("number of tranactions: " + transactions.size());

	}

	private int getCoinIndexInMaster(String coinSymbol, List<Coin> coins) {
		return coins.stream().filter(coin -> coin.getCoinSymbol().equals(coinSymbol)).findFirst().get().getLocationInMaster();

	}

	public List<Double> calculateRelativeStrongValues(List<Double> input, List<Tupel> tupels) {
		List<Double> result = new ArrayList<>();
		tupels.forEach(tupel -> {
			double relativeValue = input.get(tupel.getFirstCoin().getId() - 1) / input.get(tupel.getSecondCoin().getId() - 1);
			result.add(relativeValue);
		});
		return result;
	}

	public String calculateHeader(List<Coin> coins, List<Tupel> tupels) {
		StringBuffer result = new StringBuffer("Date" + SEP);
		coins.forEach(coin -> {
			result.append(coin.getCoinSymbol() + SEP);
		});
		result.append(SEP);
		tupels.forEach(tupel -> {
			result.append(tupel.getFirstCoin().getCoinSymbol() + "/" + tupel.getSecondCoin().getCoinSymbol() + SEP);
		});
		return result.toString();
	}

	public List<String> generateOutputLines(Map<String, List<List<Double>>> merged) {
		List<String> lines = new ArrayList<>();
		merged.forEach((date, values) ->
		{
			StringBuffer lineAsString = new StringBuffer();
			lineAsString.append(date + SEP);
			List<Double> originalValues = values.get(0);
			List<Double> calculatedValues = values.get(1);
			originalValues.forEach(value -> {
				lineAsString.append(value.toString() + SEP);
			});
			lineAsString.append(SEP);
			calculatedValues.forEach(value -> {
				lineAsString.append(value.toString() + SEP);
			});
			lines.add(lineAsString.toString());
		});

		return lines;
	}

	@Data
	public class Intermediate {
		private List<List<Element>> data = new ArrayList<>();

		public Intermediate(int size) {
			IntStream.range(0, size).forEach(index -> {
				List<Element> column = new ArrayList<>();
				this.getData().add(column);
			});
		}
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class Element {
		private String date;
		private Double data;
		private Double sma;
		private Integer score;
	}

}
