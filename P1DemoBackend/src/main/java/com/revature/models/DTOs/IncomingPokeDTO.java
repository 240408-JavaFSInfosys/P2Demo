package com.revature.models.DTOs;

//This DTO will take in a Pokemon with just name, image, and an int for userId
//saves us the hassle of trying to send an entire user from the front end
public class IncomingPokeDTO {

    private String name;
    private String image;
    private int userId;

    public IncomingPokeDTO() {
    }

    public IncomingPokeDTO(String name, String image, int userId) {
        this.name = name;
        this.image = image;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "IncomingPokeDTO{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", userId=" + userId +
                '}';
    }
}
