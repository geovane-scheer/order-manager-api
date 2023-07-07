package com.geovane.ordermanager.service;

import com.geovane.ordermanager.dto.ItemDTO;
import com.geovane.ordermanager.dto.OrderDTO;
import com.geovane.ordermanager.entity.Item;
import com.geovane.ordermanager.entity.Order;
import com.geovane.ordermanager.entity.StockMovement;
import com.geovane.ordermanager.entity.User;
import com.geovane.ordermanager.repository.OrderRepository;
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
        if (order.getItem() == null || order.getItem().getId().intValue() < 1) {
            return ResponseEntity.badRequest().body("item.id is mandatory");
            //TO-DO check if item exists
        }
        if (order.getUser() == null || order.getUser().getId().intValue() < 1) {
            return ResponseEntity.badRequest().body("user.id is mandatory");
            //TO-DO check if user exists
        }
        if(order.getQuantity() == null || order.getQuantity().intValue()  < 1){
            return ResponseEntity.badRequest().body("quantity is mandatory");
        }

        Order newOrder = Order.builder()
                .quantity(order.getQuantity())
                .status("OPEN")
                .creationDate(LocalDateTime.now())
                .item(Item.builder()
                        .id(order.getItem().getId())
                        .build())
                .user(User.builder()
                        .id(order.getUser().getId())
                        .build())
                .build();

        removeFromStock(newOrder);
        orderRepository.saveAndFlush(newOrder);
        return ResponseEntity.ok(newOrder);
    }

    private void removeFromStock(Order order) {
        StockMovement itemStock = stockMovementService.getItemStock(order.getItem().getId());
        if (order.getQuantity().intValue() > itemStock.getQuantity().intValue()) {
            itemStock.setQuantity(itemStock.getQuantity().subtract(order.getQuantity()));
            order.setQuantityMissing(order.getQuantity().subtract(itemStock.getQuantity()));
        } else {
            itemStock.setQuantity(itemStock.getQuantity().subtract(order.getQuantity()));
            order.setStatus("COMPLETED");
            sendEmailToCreatorUser(order);
        }
        stockMovementService.update(itemStock);
    }

    private void sendEmailToCreatorUser(Order order) {

    }
}
