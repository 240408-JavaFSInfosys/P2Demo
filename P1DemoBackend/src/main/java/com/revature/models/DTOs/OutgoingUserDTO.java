package com.revature.models.DTOs;

//This DTO will only send user id and username to the front end,
//so we don't have to risk sending the user's password over HTTP
public class OutgoingUserDTO {

    private int userId;
    private String username;

    public OutgoingUserDTO() {
    }

    public OutgoingUserDTO(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
