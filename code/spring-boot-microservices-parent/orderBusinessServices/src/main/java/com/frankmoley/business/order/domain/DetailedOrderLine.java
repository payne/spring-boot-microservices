package com.frankmoley.business.order.domain;

/**
 * @author Frank Moley
 */
public class DetailedOrderLine {

    private String productId;
    private String productName;
    private long quantity;
    private double unitCost;
    private double extendedCost;

    public DetailedOrderLine(){
        super();
    }

    public DetailedOrderLine(Product product, long quantity){
        this.productId = product.getId();
        this.productName = product.getName();
        this.quantity = quantity;
        this.unitCost = product.getCost();
        this.extendedCost = this.quantity * this.unitCost;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getExtendedCost() {
        return extendedCost;
    }

    public void setExtendedCost(double extendedCost) {
        this.extendedCost = extendedCost;
    }
}
