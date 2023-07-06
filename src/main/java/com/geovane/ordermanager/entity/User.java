package com.geovane.ordermanager.entity;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User {
	
	@Id
	@GeneratedValue
	private BigInteger id;
	
	private String name;
	
	private String email;

}
