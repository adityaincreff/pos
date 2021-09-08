package com.increff.pos.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderItemPojo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item;
    @Column(nullable = false)
    private int orderPojoId;
    @Column(nullable = false)
    private int productPojoId;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double sellingPrice;

    public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}



    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getOrderPojoId() {
        return orderPojoId;
    }

    public void setOrderPojoId(int order_id) {
        this.orderPojoId = order_id;
    }

    public int getProductPojoId() {
        return productPojoId;
    }

    public void setProductPojoId(int product_id) {
        this.productPojoId = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}


