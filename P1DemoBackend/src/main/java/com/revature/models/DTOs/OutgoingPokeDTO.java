package com.revature.models.DTOs;

//This DTO will send pokemon with only pokeId, name, image, and userId
//We DON'T want to send entire entire user object
//In order to keep the password safe and avoid ugly recursive issues in the data
//(Pokemon has User which has List<Pokemon> which each have User... etc.)
public class OutgoingPokeDTO {

    private int pokeId;
    private String name;
    private String image;
    private int userId;

    public OutgoingPokeDTO() {
    }

    public OutgoingPokeDTO(int pokeId, String name, String image, int userId) {
        this.pokeId = pokeId;
        this.name = name;
        this.image = image;
        this.userId = userId;
    }

    public int getPokeId() {
        return pokeId;
    }

    public void setPokeId(int pokeId) {
        this.pokeId = pokeId;
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
        return "OutgoingPokeDTO{" +
                "pokeId=" + pokeId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", userId=" + userId +
                '}';
    }
}
