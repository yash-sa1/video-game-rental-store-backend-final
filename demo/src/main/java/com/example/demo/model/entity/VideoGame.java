package com.example.demo.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoGame {
    private String title;
    private String genre;
    private double price;
    private int current_stock;
    private final int gameID;
    private String imageUrl;

    public VideoGame(String title,int gameID, String genre, double price, int current_stock,  String imageUrl) {
        this.title = title;
        this.gameID = gameID;
        this.price = price;
        this.current_stock = current_stock;
        this.genre = genre;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public int getCurrent_stock() {
        return current_stock;
    }

    public int getGameID() {
        return gameID;
    }

    public void setCurrent_stock(int current_stock) {
        this.current_stock = current_stock;
    }

    public static VideoGame fromString (final String videoGameString) {
        String[] parts = videoGameString.split(",");
        String videogameTitle = parts[0];
        int videogameID = Integer.parseInt(parts[1]);
        String videogamegenre = parts[2];
        double videogameprice = Double.parseDouble(parts[3]);
        int videogamestock = Integer.parseInt(parts[4]);
        String videogameImageUrl = parts[5];
        return new VideoGame(videogameTitle, videogameID, videogamegenre, videogameprice, videogamestock, videogameImageUrl);

    }

    @Override
    public String toString() {
        return "\n{" + title + "," + gameID + "," + genre + "," + price + "," + current_stock + "," + imageUrl + "}\n";
    }
}

