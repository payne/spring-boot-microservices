package com.frankmoley.web.order.domain;

import java.util.List;
import java.util.UUID;

/**
 * @author Frank Moley
 */
public class Order {
    private String id = UUID.randomUUID().toString();
    private String customerId;
    private List<OrderLine> orderLines;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
