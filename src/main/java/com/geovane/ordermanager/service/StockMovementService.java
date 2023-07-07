package com.geovane.ordermanager.service;

import com.geovane.ordermanager.dto.StockMovementDTO;
import com.geovane.ordermanager.dto.UserDTO;
import com.geovane.ordermanager.entity.*;
import com.geovane.ordermanager.repository.ItemRepository;
import com.geovane.ordermanager.repository.OrderRepository;
import com.geovane.ordermanager.repository.OrderStockMovementRepository;
import com.geovane.ordermanager.repository.StockMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StockMovementService {

    private  StockMovementRepository stockMovementRepository;
    private ModelMapper modelMapper;
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private OrderStockMovementRepository orderStockMovementRepository;
    public List<StockMovement> getItemStock(BigInteger id, BigInteger quantity) {
        return stockMovementRepository.findByItemIdAndQuantityGreaterThan(id, quantity);
    }

    public void update(StockMovement itemStock) {
        stockMovementRepository.saveAndFlush(itemStock);
    }

    public List<StockMovementDTO> getAllStockMovement() {
        List<StockMovement> stockMovements = stockMovementRepository.findAll();
        return stockMovements.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private StockMovementDTO convertToDto(StockMovement stockMovement) {
        return modelMapper.map(stockMovement, StockMovementDTO.class);
    }

    public ResponseEntity<?> save(StockMovementDTO stockMovement) {
        if (stockMovement.getItem() == null || stockMovement.getItem().getId() == null || stockMovement.getItem().getId().intValue() < 1) {
            log.error("item.id is mandatory");
            return ResponseEntity.badRequest().body("item.id is mandatory");
            //TO-DO check if item exists
        }
        if(stockMovement.getQuantity() == null || stockMovement.getQuantity().intValue()  < 1){
            log.error("quantity is mandatory");
            return ResponseEntity.badRequest().body("quantity is mandatory");
        }

        StockMovement newStockMovement = StockMovement.builder()
                .quantity(stockMovement.getQuantity())
                .creationDate(LocalDateTime.now())
                .item(Item.builder()
                        .id(stockMovement.getItem().getId())
                        .build())
                .build();

        stockMovementRepository.saveAndFlush(newStockMovement);
        checkOpenOrdersToFill(newStockMovement);
        log.info("stock movement {} created", newStockMovement.getId());
        return ResponseEntity.ok(newStockMovement);
    }

    public ResponseEntity<?> delete(BigInteger id) {
        StockMovement stockMovementToDelete = stockMovementRepository.findById(id).orElse(null);
        if (stockMovementToDelete != null) {
            log.info("deleting stock movement = {}", stockMovementToDelete.getId());
            stockMovementRepository.deleteById(id);
            return ResponseEntity.ok(stockMovementToDelete);
        } else {
            log.error("stockMovement not found. id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> update(BigInteger id, StockMovementDTO stockMovement) {
        StockMovement stockMovementToUpdate = stockMovementRepository.findById(id).orElse(null);
        if (stockMovementToUpdate != null) {
            log.info("updating stock movement. id = {}", stockMovementToUpdate.getId());
            stockMovementToUpdate.setQuantity(stockMovement.getQuantity());
            stockMovementToUpdate.setItem(itemRepository.findById(stockMovement.getItem().getId()).orElse(null));
            stockMovementRepository.saveAndFlush(stockMovementToUpdate);
            return ResponseEntity.ok(stockMovementToUpdate);
        } else {
            log.error("stock movement not found. id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    private void checkOpenOrdersToFill(StockMovement stockMovement) {
        List<Order> openOrders = orderRepository.findByStatusAndItemIdAndQuantityMissingGreaterThan("OPEN", stockMovement.getItem().getId(), BigInteger.valueOf(0));
        if (!openOrders.isEmpty()) {
            log.info("there are open orders of this item!");
            for (Order order : openOrders) {
                OrderStockMovement orderStockMovement = OrderStockMovement.builder()
                        .order(order)
                        .stockMovement(stockMovement)
                        .build();
                orderStockMovementRepository.saveAndFlush(orderStockMovement);
                if (order.getQuantityMissing().intValue() > stockMovement.getQuantity().intValue()) {
                    order.setQuantityMissing(order.getQuantityMissing().subtract(stockMovement.getQuantity()));
                    stockMovement.setQuantity(BigInteger.valueOf(0));
                } else {
                    stockMovement.setQuantity(stockMovement.getQuantity().subtract(order.getQuantityMissing()));
                    order.setQuantityMissing(BigInteger.valueOf(0));
                    order.setStatus("COMPLETED");
                    break;
                }
                orderRepository.saveAndFlush(order);
            }
            stockMovementRepository.saveAndFlush(stockMovement);
        }
    }

    public void update(List<StockMovement> itemStock) {
        stockMovementRepository.saveAllAndFlush(itemStock);
    }
}
