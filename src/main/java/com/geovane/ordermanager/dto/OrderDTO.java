package com.geovane.ordermanager.dto;

import com.geovane.ordermanager.entity.Item;
import com.geovane.ordermanager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class OrderDTO {

	private BigInteger id;
	private Item item;
	private User user;
	private BigInteger quantity;
	private String status;
	
}
