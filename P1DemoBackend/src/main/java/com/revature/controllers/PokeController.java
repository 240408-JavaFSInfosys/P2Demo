package com.revature.controllers;

import com.revature.models.DTOs.IncomingPokeDTO;
import com.revature.models.Pokemon;
import com.revature.services.PokemonService;
import com.revature.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = "http://localhost:3000, http://44.220.158.169", allowCredentials = "true")
public class PokeController {

    private PokemonService pokemonService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public PokeController(PokemonService pokemonService, JwtTokenUtil jwtTokenUtil) {
        this.pokemonService = pokemonService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    //post mapping for inserting new pokemon
    @PostMapping()
    public ResponseEntity<String> addPokemon(@RequestBody IncomingPokeDTO pokeDTO, @RequestHeader("Authorization") String token){

        String jwt = token.substring(7); // Remove "Bearer " from the token
        int userId = jwtTokenUtil.extractUserId(jwt);
        System.out.println("User ID from subject: " + userId);

        //we can attach the stored user ID from the JWT to the pokeDTO
        pokeDTO.setUserId(userId);

        //TODO: try/catch once we decide to do some error handling
        Pokemon p = pokemonService.addPokemon(pokeDTO);

        return ResponseEntity.status(201).body(
                p.getUser().getUsername() + " caught " + p.getName());

    }

    @GetMapping
    public ResponseEntity<?> getAllPokemon(@RequestHeader("Authorization") String token){

        String jwt = token.substring(7); // Remove "Bearer " from the token
        int userId = jwtTokenUtil.extractUserId(jwt);
        System.out.println("User ID from subject: " + userId);

        //just for fun, showing we can get the username from our extractor method
        //you'll extract user Role in a similar way
        String username = jwtTokenUtil.extractUsername(jwt);
        System.out.println("Username from claims: " + username);

        //Why return in many line when one line do trick?
        return ResponseEntity.ok(pokemonService.getAllPokemon(userId));

    }

    //delete a pokemon by ID
    @DeleteMapping("/{pokeId}")
    public ResponseEntity<String> releasePokemon(@PathVariable int pokeId, HttpSession session){

        //Login check
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("You must be logged in to release Pokemon!");
        }

        //Get the userId from the session
        int userId = (int) session.getAttribute("userId");

        //try/catch the service method, either returning a confirmation or error message
        try {
            return ResponseEntity.ok(pokemonService.releasePokemon(pokeId, userId));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
