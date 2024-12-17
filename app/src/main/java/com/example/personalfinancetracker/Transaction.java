package com.example.personalfinancetracker;

import java.util.Date;

public class Transaction {
    private long id;
    private double amount;
    private String category;
    private String description;
    private Date date;
    private boolean isIncome;


    public Transaction() {}

    public Transaction(double amount, String category, String description, Date date, boolean isIncome) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
        this.isIncome = isIncome;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public boolean isIncome() { return isIncome; }
    public void setIncome(boolean income) { isIncome = income; }
}