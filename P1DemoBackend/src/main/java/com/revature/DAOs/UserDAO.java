package com.revature.DAOs;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    //We need to add a custom method to find a user by username and password
    //This is what we'll use to check for valid login credentials
    public Optional<User> findByUsernameAndPassword(String username, String password);

    //We need to add a custom method to find a user by username for JWTs
    public Optional<User> findByUsername(String username);

}
