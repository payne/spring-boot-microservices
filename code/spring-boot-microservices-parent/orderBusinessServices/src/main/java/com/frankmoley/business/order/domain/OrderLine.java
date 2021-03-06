package com.frankmoley.business.order.domain;

/**
 * @author Frank Moley
 */
public class OrderLine {

    private String productId;
    private long quantity;

    public OrderLine(){
        super();
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
