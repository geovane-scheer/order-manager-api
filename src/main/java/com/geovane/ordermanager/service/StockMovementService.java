package com.geovane.ordermanager.service;

import com.geovane.ordermanager.entity.StockMovement;
import com.geovane.ordermanager.repository.StockMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@AllArgsConstructor
@Slf4j
public class StockMovementService {

    private  StockMovementRepository stockMovementRepository;
    public StockMovement getItemStock(BigInteger id) {
        return stockMovementRepository.findByItemId(id);
    }

    public void update(StockMovement itemStock) {
    }
}
