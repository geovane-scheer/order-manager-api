package com.geovane.ordermanager.controller;

import com.geovane.ordermanager.dto.UserDTO;
import com.geovane.ordermanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		log.info("getAllUsers() - start");
		List<UserDTO> users = userService.getAllUsers();
		log.info("getAllUsers() - end");
		return ResponseEntity.ok(users);
	}

	@PostMapping
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
		return userService.save(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable BigInteger id, @RequestBody UserDTO user) throws Exception {
		return userService.update(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable BigInteger id) throws Exception {
		return userService.delete(id);
	}

}
