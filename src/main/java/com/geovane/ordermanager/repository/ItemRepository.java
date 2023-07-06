package com.geovane.ordermanager.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.geovane.ordermanager.entity.Item;

@RepositoryRestResource
public interface ItemRepository extends JpaRepository<Item, BigInteger> {

}
