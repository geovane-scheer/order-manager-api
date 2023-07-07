package com.geovane.ordermanager.dto;

import com.geovane.ordermanager.entity.Item;
import com.geovane.ordermanager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class StockMovementDTO {
	private BigInteger id;
	private Item item;
	private BigInteger quantity;
	private LocalDateTime creationDate;
	private String quantityUsed;
}
