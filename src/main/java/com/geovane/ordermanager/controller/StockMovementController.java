package com.geovane.ordermanager.controller;

import com.geovane.ordermanager.dto.StockMovementDTO;
import com.geovane.ordermanager.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


@RestController
@RequestMapping(value = "/stockMovement")
@RequiredArgsConstructor
@Slf4j
public class StockMovementController {
	
	private final StockMovementService stockMovementService;
	
	@GetMapping
	public ResponseEntity<List<StockMovementDTO>> getAllStockMovement(){
		log.info("getAllStockMovement() - start");
		List<StockMovementDTO> stockMovements = stockMovementService.getAllStockMovement();
		log.info("getAllStockMovement() - end");
		return ResponseEntity.ok(stockMovements);
	}

	@PostMapping
	public ResponseEntity<?> saveStockMovement(@RequestBody StockMovementDTO stockMovement) {
		return stockMovementService.save(stockMovement);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStockMovement(@PathVariable BigInteger id, @RequestBody StockMovementDTO stockMovement) throws Exception {
		return stockMovementService.update(id, stockMovement);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteStockMovement(@PathVariable BigInteger id) throws Exception {
		return stockMovementService.delete(id);
	}

}
