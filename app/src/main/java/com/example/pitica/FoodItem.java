package com.example.pitica;

public class FoodItem {
    private String title;
    private String chefName;
    private String price;
    private String distance;

    // Constructor
    public FoodItem(String title, String chefName, String price, String distance) {
        this.title = title;
        this.chefName = chefName;
        this.price = price;
        this.distance = distance;
    }

    // Getters
    public String getTitle() { return title; }
    public String getChefName() { return chefName; }
    public String getPrice() { return price; }
    public String getDistance() { return distance; }
}