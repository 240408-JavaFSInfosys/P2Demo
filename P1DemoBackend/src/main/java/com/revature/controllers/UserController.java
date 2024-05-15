package com.revature.controllers;

import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
//approving our frontend to talk to this controller
//we're ALSO saying that we're going to allow session data to be passed back and forth
@CrossOrigin(origins = "http://localhost:3000, http://44.220.158.169", allowCredentials = "true")
public class UserController {

    //autowire user service, JwtTokenUtil, AuthenticationManager, and PasswordEncoder
    private UserService userService;
    private AuthenticationManager authManager;
    private JwtTokenUtil jwtUtil;
    private PasswordEncoder passwordEncoder; //SPRING SECURITY - lets us encode our passwords

    @Autowired
    public UserController(UserService userService, AuthenticationManager authManager, JwtTokenUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody IncomingUserDTO userDTO){

        //try to register the user
        try{
            //ENCRYPT THE PASSWORD USING BCRYPT!!
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userService.registerUser(userDTO);
            return ResponseEntity.status(201).body(userDTO.getUsername() + " was created!");
            //If this all works, send back a 201 CREATED, plus a confirmation message
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
            //If something goes wrong, send back a 400 BAD REQUEST, plus the error message
        }

        //TODO: We'll have checks for DB issues here as well

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody IncomingUserDTO userDTO){

        //attempt to login (notice no direct calls of the service/DAO)
        try{
            //this is what authenticates the incoming username/password
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );

            //build up the user based on the validation
            User user = (User) auth.getPrincipal();

            //if the user is found/valid, generate a JWT!
            String accessToken = jwtUtil.generateAccessToken(user);

            //create our OutgoingUserDTO to send back (which will include, ID, Username, and JWT)
            OutgoingUserDTO userOut = new OutgoingUserDTO(user.getUserId(), user.getUsername(), accessToken);

            //finally we sent the OutgoingUserDTO back to the client
            return ResponseEntity.ok(userOut);

        } catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }

    //delete use by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId){

        //TODO: take in HttpSession to do the necessary checks

        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok("User was deleted!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
