package com.geovane.ordermanager.service;

import com.geovane.ordermanager.dto.UserDTO;
import com.geovane.ordermanager.entity.User;
import com.geovane.ordermanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
	
	private UserRepository userRepository;
	
	private ModelMapper modelMapper;

	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	private UserDTO convertToDto(User user) {
	    return modelMapper.map(user, UserDTO.class);
	}

	public ResponseEntity<?> save(UserDTO user) {
		if (user.getName() == null || user.getName().isEmpty()) {
			ResponseEntity.badRequest().body("Name is mandatory");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			ResponseEntity.badRequest().body("Name is mandatory");
		}
		User newUser = userRepository.saveAndFlush(User.builder()
				.name(user.getName())
				.email(user.getEmail())
				.build());
		return ResponseEntity.ok(newUser);
	}

	public ResponseEntity<?> update(BigInteger id, UserDTO entity) {
		User userToUpdate = userRepository.findById(id).orElse(null);
		if (userToUpdate != null) {
			log.info("updating user. id = {}", userToUpdate.getId());
			userToUpdate.setName(entity.getName());
			userToUpdate.setEmail(entity.getEmail());
			userRepository.saveAndFlush(userToUpdate);
			return ResponseEntity.ok(userToUpdate);
		} else {
			log.error("user not found. id: {}", id);
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<?> delete(BigInteger id) {
		User userToDelete = userRepository.findById(id).orElse(null);
		if (userToDelete != null) {
			log.info("deleting user = {}", userToDelete.getId());
			userRepository.deleteById(id);
			return ResponseEntity.ok(userToDelete);
		} else {
			log.error("user not found. id: {}", id);
			return ResponseEntity.notFound().build();
		}
	}
}
