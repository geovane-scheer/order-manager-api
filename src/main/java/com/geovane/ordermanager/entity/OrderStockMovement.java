package com.geovane.ordermanager.entity;

import com.geovane.ordermanager.repository.StockMovementRepository;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders_stock_movement")
@Builder
public class OrderStockMovement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "stock_movement_id", referencedColumnName = "id")
    private StockMovement stockMovement;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;
	
}
