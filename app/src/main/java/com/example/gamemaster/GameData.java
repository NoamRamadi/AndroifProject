package com.example.gamemaster;

public class GameData {
    public String name;
    public String description;
    public String genre;
    public String rate;


    public GameData(String name, String description, String genre, String rate) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GameData(){}
}
