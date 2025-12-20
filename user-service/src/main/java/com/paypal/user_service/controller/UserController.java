package com.paypal.user_service.controller;

import com.paypal.user_service.dto.ApiResponse;
import com.paypal.user_service.dto.UserResponseDTO;
import com.paypal.user_service.entity.User;
import com.paypal.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
      this.userService=userService;
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Received user: " + user); // This will print
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /*@PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
    	System.out.println("Received user: " + user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

	
	 * @GetMapping("/{id}") public ResponseEntity<ApiResponse<UserResponseDTO>>
	 * getUserById(@PathVariable Long id){ return
	 * userService.getUserById(id).map(us->ResponseEntity.ok(new
	 * ApiResponse<>("",us)))
	 * .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body( new
	 * ApiResponse<>("User not found with ID: "+id,null))); }
	 * 
	 * @GetMapping("/all") public ResponseEntity<List<UserResponseDTO>>
	 * getallUsers(){ return ResponseEntity.ok(userService.getAllUsers()); }
	 * 
	 * @GetMapping("/test") public String test() {
	 * System.out.println("TEST endpoint hit!"); return "test OK"; }
	 */
}