package com.example.pitica;

public class FoodItem {
    private String title;
    private String chefName;
    private String price;
    private String distance;
    private String imagePath; // Added this field

    // 1. EMPTY CONSTRUCTOR (Required for Firestore serialization)
    public FoodItem() {}

    // 2. Full Constructor
    public FoodItem(String title, String chefName, String price, String distance, String imagePath) {
        this.title = title;
        this.chefName = chefName;
        this.price = price;
        this.distance = distance;
        this.imagePath = imagePath;
    }

    // Getters
    public String getTitle() { return title; }
    public String getChefName() { return chefName; }
    public String getPrice() { return price; }
    public String getDistance() { return distance; }
    public String getImagePath() { return imagePath; }
}