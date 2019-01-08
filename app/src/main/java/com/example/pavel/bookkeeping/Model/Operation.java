package com.example.pavel.bookkeeping.Model;

import com.example.pavel.bookkeeping.Adapters.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Operation  implements BaseModel {
    LocalDateTime timestamp;
    OperationType type;
    Double value;
    Account accountFrom;
    Account accountTo;
    Category category;
    String description;

    public Operation(LocalDateTime timestamp, OperationType type, Double value, Account accountFrom, Account accountTo, Category category, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.value = value >= 0 ? value : 0;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.category = category;
        this.description = description;
    }

    public Operation() {
        timestamp = LocalDateTime.now();
        type = OperationType.arrival;
        value = 0.0;
        accountFrom = null;
        accountTo = null;
        category = Category.arrival;
        description = "";
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getViewType() {
        return Constants.ViewType.OPERATION_TYPE;
    }
}
