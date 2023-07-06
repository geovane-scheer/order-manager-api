package com.geovane.ordermanager.repository;

import com.geovane.ordermanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, BigInteger> {

}
