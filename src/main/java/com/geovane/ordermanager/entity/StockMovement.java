package com.geovane.ordermanager.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_movement")
@Builder
public class StockMovement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
	
	private BigInteger quantity;
	
}
