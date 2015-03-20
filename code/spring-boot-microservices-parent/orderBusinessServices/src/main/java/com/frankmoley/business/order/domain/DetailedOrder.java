package com.frankmoley.business.order.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Frank Moley
 */
public class DetailedOrder {
    private String orderId;
    private String customerName;
    private String customerAddress;
    private String customerEmailAddress;
    private List<DetailedOrderLine> orderLines;
    private double orderTotal;

    public DetailedOrder(){
        super();
    }

    public DetailedOrder(Order order, Person  person, List<DetailedOrderLine> orderLines){
        this.orderId = order.getId();
        this.customerName = person.getFirstName() + " " + person.getLastName();
        this.customerAddress = person.getAddress();
        this.customerEmailAddress = person.getEmailAddress();
        this.orderLines = orderLines;
        double basePrice = 0;
        for(DetailedOrderLine  orderLine: orderLines){
            basePrice += orderLine.getExtendedCost();
        }
        this.orderTotal= basePrice;

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<DetailedOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<DetailedOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
}
