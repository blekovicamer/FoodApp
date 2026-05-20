package com.example.pitica;

public class Order {
    private String foodName, buyerName, status;

    public Order(String foodName, String buyerName, String status) {
        this.foodName = foodName;
        this.buyerName = buyerName;
        this.status = status;
    }

    public String getFoodName() { return foodName; }
    public String getBuyerName() { return buyerName; }
    public String getStatus() { return status; }
}