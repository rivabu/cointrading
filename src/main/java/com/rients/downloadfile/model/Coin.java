package com.rients.downloadfile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coin {
	private int id;
	private int locationInMaster;
	private String coinName;
	private String coinSymbol;
	private int coinId;

}
