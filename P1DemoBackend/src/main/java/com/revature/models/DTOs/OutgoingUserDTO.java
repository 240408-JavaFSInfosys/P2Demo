package com.revature.models.DTOs;

//This DTO will only send user id and username to the front end,
//so we don't have to risk sending the user's password over HTTP
public class OutgoingUserDTO {

    private int userId;
    private String username;

    private String JWT;

    public OutgoingUserDTO() {
    }

    public OutgoingUserDTO(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public OutgoingUserDTO(int userId, String username, String JWT) {
        this.userId = userId;
        this.username = username;
        this.JWT = JWT;
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

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", JWT='" + JWT + '\'' +
                '}';
    }
}
