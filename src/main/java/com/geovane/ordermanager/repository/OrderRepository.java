package com.geovane.ordermanager.repository;

import com.geovane.ordermanager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, BigInteger> {

}
