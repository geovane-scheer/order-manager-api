package com.geovane.ordermanager.entity;

import java.math.BigInteger;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	private String name;
	
}
