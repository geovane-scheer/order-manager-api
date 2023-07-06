package com.geovane.ordermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

	private BigInteger id;
	private String name;
	private String email;
	
}
