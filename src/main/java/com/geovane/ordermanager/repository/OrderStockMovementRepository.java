package com.geovane.ordermanager.repository;

import com.geovane.ordermanager.entity.Order;
import com.geovane.ordermanager.entity.OrderStockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource
public interface OrderStockMovementRepository extends JpaRepository<OrderStockMovement, BigInteger> {

    List<OrderStockMovement> findByOrderId(BigInteger orderId);

    List<OrderStockMovement> findByStockMovementId(BigInteger stockMovementId);
}
