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
    private JwtTokenUtil jwtUtil;

    @Autowired
    public PokeController(PokemonService pokemonService, JwtTokenUtil jwtTokenUtil) {
        this.pokemonService = pokemonService;
        this.jwtUtil = jwtTokenUtil;
    }

    //post mapping for inserting new pokemon
    @PostMapping()
    public ResponseEntity<String> addPokemon(@RequestBody IncomingPokeDTO pokeDTO, @RequestHeader("Authorization") String token){

        //Extract and validate the User information from the JWT sent in the Authorization header
        String jwt = token.substring(7); //remove "Bearer " from the token
        int userId = jwtUtil.extractUserId(jwt); //send the jwt to the util to extract userId
        System.out.println("User ID: " + userId);

        //TODO: use the hypothetical role extractor method to check role here

        //we need to attach the userId to the pokeDTO
        pokeDTO.setUserId(userId);

        //TODO: we never put the service in a try/catch, would be nice
        Pokemon p = pokemonService.addPokemon(pokeDTO);

        return ResponseEntity.ok(p.getUser().getUsername() + " caught a " + p.getName() + "!");

    }

    @GetMapping
    public ResponseEntity<?> getAllPokemon(@RequestHeader("Authorization") String token){

        //Extract and validate the User information from the JWT sent in the Authorization header
        String jwt = token.substring(7); //remove "Bearer " from the token
        int userId = jwtUtil.extractUserId(jwt); //send the jwt to the util to extract userId
        System.out.println("User ID: " + userId);

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
