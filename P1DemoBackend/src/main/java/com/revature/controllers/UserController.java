package com.revature.controllers;

import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
//approving our frontend to talk to this controller
//we're ALSO saying that we're going to allow session data to be passed back and forth
@CrossOrigin(origins = "http://localhost:3000, http://44.220.158.169", allowCredentials = "true")
public class UserController {

    //autowire user service
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody IncomingUserDTO userDTO){

        //try to register the user
        try{
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
    public ResponseEntity<?> loginUser(@RequestBody IncomingUserDTO userDTO, HttpSession session){

        //Get the User object from the service (which talks to the DB)
        Optional<User> optionalUser = userService.loginUser(userDTO);

        //If login fails (which will return an empty optional), tell the user they failed
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(401).body("Login Failed!");
        }

        //If login succeeds store the user info in our session
        User u = optionalUser.get();

        //Storing the user info in our session
        session.setAttribute("userId", u.getUserId());
        session.setAttribute("username", u.getUsername()); //probably won't use this

        //Hypothetical role save to session
        //session.setAttribute("role", u.getRole());

        //Finally, send back a 200 (OK) as well as a OutgoingUserDTO
        return ResponseEntity.ok(new OutgoingUserDTO(u.getUserId(), u.getUsername()));

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
