package com.example.gamemaster;

public class GameData {
    public String name;
    public String description;
    public String genre;
    public String rate;
    public String company;
    public int image;


    public GameData(String name,String company, String description, String genre, String rate,int image) {
        this.name = name;
        this.company=company;
        this.description = description;
        this.genre = genre;
        this.rate = rate;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GameData(){}

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public String getCompany() {
        return company;
    }
}
