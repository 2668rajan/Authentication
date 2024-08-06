package com.sportseventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportseventmanagement.model.User;
import com.sportseventmanagement.repository.UserRepository;
import com.sportseventmanagement.request.LoginRequest;
import com.sportseventmanagement.response.JWTResponse;
import com.sportseventmanagement.response.MessageResponse;
import com.sportseventmanagement.security.jwt.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;


	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping(value = "/validate")
	public boolean getvalidation(@RequestHeader(value = "Authorization", required = true) String token1) {
		String jwtToken = null;
		String userName = null;
		
		if (token1 != null && token1.startsWith("Bearer ")) {
			jwtToken = token1.substring(7);
			try {
				userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
			} catch (IllegalArgumentException | ExpiredJwtException e) {
				return false;
			}
		}
		return userName != null;

	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
	
		return ResponseEntity.ok(new JWTResponse(jwt));
	}
	
	@PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@RequestBody User signUpRequest) {
	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageResponse("Error: Username is already taken!"));
	    }

	    // Create new user's account
	    User user = new User(signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));
	    
	    userRepository.save(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }

}
