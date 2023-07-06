package com.geovane.ordermanager.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ItemDTO {

	private BigInteger id;
	private String name;
	
}
