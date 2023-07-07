package com.geovane.ordermanager.service;

import com.geovane.ordermanager.dto.ItemDTO;
import com.geovane.ordermanager.dto.OrderDTO;
import com.geovane.ordermanager.entity.*;
import com.geovane.ordermanager.repository.ItemRepository;
import com.geovane.ordermanager.repository.OrderRepository;
import com.geovane.ordermanager.repository.OrderStockMovementRepository;
import com.geovane.ordermanager.repository.UserRepository;
import com.geovane.ordermanager.utils.EmailService;
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
public class OrderService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private StockMovementService stockMovementService;
    private EmailService emailService;
    private OrderStockMovementRepository orderStockMovementRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public ResponseEntity<?> update(BigInteger id, OrderDTO order) {
        return null;
    }

    public ResponseEntity<?> delete(BigInteger id) {
        return null;
    }

    public ResponseEntity<?> save(OrderDTO order) {
        if (order.getItem() == null || order.getItem().getId() == null|| order.getItem().getId().intValue() < 1) {
            return ResponseEntity.badRequest().body("item.id is mandatory");
            //TO-DO check if item exists
        }
        if (order.getUser() == null || order.getUser().getId() == null|| order.getUser().getId().intValue() < 1) {
            return ResponseEntity.badRequest().body("user.id is mandatory");
            //TO-DO check if user exists
        }
        if(order.getQuantity() == null || order.getQuantity().intValue()  < 1){
            return ResponseEntity.badRequest().body("quantity is mandatory");
        }

        Order newOrder = Order.builder()
                .quantity(order.getQuantity())
                .quantityMissing(order.getQuantity())
                .status("OPEN")
                .creationDate(LocalDateTime.now())
                .item(itemRepository.findById(order.getItem().getId()).orElse(null))
                .user(userRepository.findById(order.getUser().getId()).orElse(null))
                .build();

        orderRepository.saveAndFlush(newOrder);
        removeFromStock(newOrder);
        log.info("order {} created", newOrder.getId());
        return ResponseEntity.ok(newOrder);
    }

    private void removeFromStock(Order order) {
        List<StockMovement> itemStock = stockMovementService.getItemStock(order.getItem().getId(), BigInteger.valueOf(0));
        if (!itemStock.isEmpty()) {
            //the current stock does not satisfy the order quantity
            int currentStock = itemStock.stream().mapToInt(s -> s.getQuantity().intValue()).sum();
            if (order.getQuantity().intValue() > currentStock) {
                itemStock.forEach(i -> {
                    i.setQuantity(new BigInteger(String.valueOf(0)));
                    OrderStockMovement orderStockMovement = OrderStockMovement.builder()
                            .order(order)
                            .stockMovement(i)
                            .build();
                    orderStockMovementRepository.saveAndFlush(orderStockMovement);
                });
                order.setQuantityMissing(order.getQuantity().subtract(BigInteger.valueOf(currentStock)));
            } else {
                //the current stock satisfies the order quantity
                for (StockMovement stockMovement : itemStock) {
                    if (order.getQuantityMissing().intValue() >= stockMovement.getQuantity().intValue()) {
                        order.setQuantityMissing(order.getQuantityMissing().subtract(stockMovement.getQuantity()));
                        stockMovement.setQuantity(new BigInteger(String.valueOf(0)));
                    } else {
                        stockMovement.setQuantity(stockMovement.getQuantity().subtract(order.getQuantityMissing()));
                        order.setQuantityMissing(BigInteger.valueOf(0));
                    }
                    OrderStockMovement orderStockMovement = OrderStockMovement.builder()
                            .order(order)
                            .stockMovement(stockMovement)
                            .build();
                    orderStockMovementRepository.saveAndFlush(orderStockMovement);
                }
                order.setStatus("COMPLETED");
                try {
                    sendEmailToCreatorUser(order);
                } catch (Exception e) {
                    log.error("error sending email to user: {}", e.getMessage());
                }

            }
            stockMovementService.update(itemStock);
            orderRepository.saveAndFlush(order);
        } else {
            log.info("there isnt stock for this item");
        }

    }

    private void sendEmailToCreatorUser(Order order) {
        String subject = "Order" + order.getId() + "COMPLETED";
        String text = "Hello " + order.getUser().getName() +", your order is COMPLETED! Kind regards";
        emailService.sendSimpleMessage(order.getUser().getEmail(), subject, text);
    }

}
