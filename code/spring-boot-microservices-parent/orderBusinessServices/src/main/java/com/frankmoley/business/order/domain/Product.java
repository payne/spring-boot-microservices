package com.frankmoley.business.order.domain;

/**
 * @author Frank Moley
 */
public class Product {


    private String id;
    private String name;
    private String description;
    private String catalogId;
    private long quantityOnHand;
    private double cost;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public long getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(long quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
