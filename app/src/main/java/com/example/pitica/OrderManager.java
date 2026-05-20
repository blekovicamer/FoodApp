package com.example.pitica;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    // List for food I bought (History)
    private static List<Order> userHistoryList = new ArrayList<>();
    // List for food people bought from me (Sales)
    private static List<Order> chefReceivedList = new ArrayList<>();

    public static List<Order> getUserHistory() { return userHistoryList; }
    public static List<Order> getChefReceived() { return chefReceivedList; }
}