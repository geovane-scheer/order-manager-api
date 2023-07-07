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
@Table(name = "orders")
@Builder
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
	
	private BigInteger quantity;
	
	private String status;

	@Column(name = "quantity_missing")
	private BigInteger quantityMissing;

}
