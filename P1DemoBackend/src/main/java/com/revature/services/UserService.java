package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Service Classes are used for data processing between controllers and DAOs
//anything we need to do between HTTP and the Database is done here
    //Error handling
    //Data mutations
    //Data validations
    //more!

@Service
public class UserService {

    //autowire the UserDAO
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //register user
    public User registerUser(IncomingUserDTO userDTO) throws IllegalArgumentException{

        //Check the username and password are not empty/null
        if(userDTO.getUsername().isBlank() || userDTO.getUsername() == null){
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if(userDTO.getPassword().isBlank() || userDTO.getPassword() == null){
            throw new IllegalArgumentException("Password cannot be empty");
        }

        //also check that the username isn't vulgar
        if(userDTO.getUsername().equals("JavaScript")){
            throw new IllegalArgumentException("Username cannot be JavaScript");
        }

        //we could definitely check if the DTO is null too,
        //but we're going to assume we've written good code
        if(userDTO == null){
            throw new IllegalArgumentException("This probably won't get thrown if we've written good code");
        }

        //TODO: We could have made an exception handling service to clean all this up, which is typical

        //if all checks pass, we can create a new User based off the DTO and send it to the DAO
        User newUser = new User(userDTO.getUsername(), userDTO.getPassword());

        try {
            //save the user to the database and return that user at the same time
            return userDAO.save(newUser);
        } catch (DataIntegrityViolationException e){
            throw new IllegalArgumentException("Username already exists!");
        }

    }

    //This service will facilitate login - get a user from the DAO (or null)
    public Optional<User> loginUser(IncomingUserDTO userDTO) throws IllegalArgumentException {

        //TODO: validity checks

        //if all checks pass, return a User OR null, and send it to the controller
        return userDAO.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());

    }

    //this method will delete a user by id
    public void deleteUser(int userId){

        //TODO: validity checks
            //make sure the deleter is logged in and is a manager
            //make sure the user to delete actually exists
            //make sure the deleter is not trying to delete themselves

        userDAO.deleteById(userId);
    }

}
