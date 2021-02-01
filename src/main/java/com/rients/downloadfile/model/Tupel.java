package com.rients.downloadfile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Tupel {
	private Coin firstCoin;
	private Coin secondCoin;

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Tupel) {
			Tupel that = (Tupel) o;
			if (this.firstCoin.equals(that.firstCoin) && this.secondCoin.equals(that.secondCoin)) {
				return true;
			} else if (this.firstCoin.equals(that.secondCoin) && this.secondCoin.equals(that.firstCoin)) {
				return true;
			}

		}
		return false;
	}

	public boolean isValid() {
		return Objects.nonNull(firstCoin) && Objects.nonNull(secondCoin) && !firstCoin.equals(secondCoin);
	}

	public String toString() {
		return this.firstCoin.getCoinSymbol() + "," + this.secondCoin.getCoinSymbol();
	}

}
