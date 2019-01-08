package com.example.pavel.bookkeeping.Model;

import com.example.pavel.bookkeeping.Adapters.Constants;

public class Account implements BaseModel {
    String name;
    String description;
    Double balance;

    public Account(String name, String description, Double balance) {
        this.name = name;
        this.description = description;
        this.balance = balance;
    }

    public Account() {
        name = "";
        description = "";
        balance = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public int getViewType() {
        return Constants.ViewType.ACCOUNT_TYPE;
    }
}
