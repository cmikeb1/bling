package com.cmikeb.models.domain;

import java.util.Date;

/**
 * Created by cmbylund on 10/25/15.
 */
public class Transaction {
    private String id;
    private Date createdDate;
    private Date transactionDate;
    private String categoryId;
    private Category category;
    private String name;
    private Double amount;

    public Transaction(String id, Date createdDate, Date transactionDate, String categoryId, Category category, String name, Double amount) {
        this.id = id;
        this.createdDate = createdDate;
        this.transactionDate = transactionDate;
        this.categoryId = categoryId;
        this.category = category;
        this.name = name;
        this.amount = amount;
    }

    public Transaction() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
