package com.geovane.ordermanager.controller;

import com.geovane.ordermanager.dto.OrderDTO;
import com.geovane.ordermanager.entity.OrderStockMovement;
import com.geovane.ordermanager.repository.OrderStockMovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/orderStockMovement")
@RequiredArgsConstructor
@Slf4j
public class OrderStockMovementController {

    private final OrderStockMovementRepository orderStockMovementRepository;

    @GetMapping
    public ResponseEntity<List<OrderStockMovement>> getAll(){
        log.info("getAll() - start");
        List<OrderStockMovement> orders = orderStockMovementRepository.findAll();
        log.info("getAll() - end");
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/byOrder/{orderId}")
    public ResponseEntity<List<OrderStockMovement>> getByOrder(@PathVariable BigInteger orderId){
        log.info("getByOrder() - start");
        List<OrderStockMovement> orders = orderStockMovementRepository.findByOrderId(orderId);
        log.info("getByOrder() - end");
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/byStockMovement/{stockMovementId}")
    public ResponseEntity<List<OrderStockMovement>> getByStockMovement(@PathVariable BigInteger stockMovementId){
        log.info("getByStockMovement() - start");
        List<OrderStockMovement> orders = orderStockMovementRepository.findByStockMovementId(stockMovementId);
        log.info("getByStockMovement() - end");
        return ResponseEntity.ok(orders);
    }

}
