package com.geovane.ordermanager.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geovane.ordermanager.dto.ItemDTO;
import com.geovane.ordermanager.entity.Item;
import com.geovane.ordermanager.repository.ItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class ItemService {
	
	private ItemRepository itemRepository;
	
	private ModelMapper modelMapper;

	public List<ItemDTO> getAllItens() {
		List<Item> itens = itemRepository.findAll();
		return itens.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	private ItemDTO convertToDto(Item item) {
	    return modelMapper.map(item, ItemDTO.class);
	}

	public Item save(String name) {
		return itemRepository.saveAndFlush(Item.builder()
				.name(name)
				.build());
	}

	public ResponseEntity<?> update(BigInteger id, ItemDTO entity) {
		Item itemToUpdate = itemRepository.findById(id).orElse(null);
		if (itemToUpdate != null) {
			log.info("updating item. id = {}", itemToUpdate.getId());
			itemToUpdate.setName(entity.getName());
			itemRepository.saveAndFlush(itemToUpdate);
			return ResponseEntity.ok(itemToUpdate);
		} else {
			log.error("item not found. id: {}", id);
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<?> delete(BigInteger id) {
		Item itemToDelete = itemRepository.findById(id).orElse(null);
		if (itemToDelete != null) {
			log.info("deleting item = {}", itemToDelete.getId());
			itemRepository.deleteById(id);
			return ResponseEntity.ok(itemToDelete);
		} else {
			log.error("item not found. id: {}", id);
			return ResponseEntity.notFound().build();
		}
	}
}
