package com.geovane.ordermanager.controller;

import com.geovane.ordermanager.dto.ItemDTO;
import com.geovane.ordermanager.dto.OrderDTO;
import com.geovane.ordermanager.entity.Item;
import com.geovane.ordermanager.service.ItemService;
import com.geovane.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	
	private final OrderService orderService;
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAllOrders(){
		log.info("getAllOrders() - start");
		List<OrderDTO> orders = orderService.getAllOrders();
		log.info("getAllOrders() - end");
		return ResponseEntity.ok(orders);
	}

	@PostMapping
	public ResponseEntity<?> saveOrder(@RequestBody OrderDTO order) {
		return orderService.save(order);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrder(@PathVariable BigInteger id, @RequestBody OrderDTO order) throws Exception {
		return orderService.update(id, order);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteOrder(@PathVariable BigInteger id) throws Exception {
		return orderService.delete(id);
	}

}
