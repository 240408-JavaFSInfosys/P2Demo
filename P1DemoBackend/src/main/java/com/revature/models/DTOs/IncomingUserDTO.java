package com.revature.models.DTOs;

//What's a DTO? A Data Transfer Object!
//We don't want to have to specify User Id or a List of Pokemon for Login/Registration
//DTOs let us store ONLY the relevant information for a given operation

//Big Picture: This will let a user just submit username/password for login/registration

public class IncomingUserDTO {

    private String username;
    private String password;

    //private String email (IF THIS EXISTED)

    //private String someOtherRelevant Field

    //then from here it's just boilerplate code-------------


    public IncomingUserDTO() {
    }

    public IncomingUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "IncomingUserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
