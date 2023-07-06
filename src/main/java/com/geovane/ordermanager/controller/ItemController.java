package com.geovane.ordermanager.controller;

import com.geovane.ordermanager.dto.ItemDTO;
import com.geovane.ordermanager.entity.Item;
import com.geovane.ordermanager.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


@RestController
@RequestMapping(value = "/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
	
	private final ItemService itemService;
	
	@GetMapping
	public ResponseEntity<List<ItemDTO>> getAllItens(){
		log.info("getAllItens() - start");
		List<ItemDTO> itens = itemService.getAllItens();
		log.info("getAllItens() - end");
		return ResponseEntity.ok(itens);
	}

	@PostMapping
	public ResponseEntity<?> saveItem(@RequestBody ItemDTO item) {
		if (item.getName() != null && !item.getName().isEmpty()){
			log.info("saveItem() - start: item = {}", item.getName());
			Item newItem = itemService.save(item.getName());
			log.info("saveItem() - end: newItem = {}", newItem.getId());
			return ResponseEntity.ok(newItem);
		} else {
			return ResponseEntity.badRequest().body("Name is mandatory");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateItem(@PathVariable BigInteger id, @RequestBody ItemDTO item) throws Exception {
		return itemService.update(id, item);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable BigInteger id) throws Exception {
		return itemService.delete(id);
	}

}
