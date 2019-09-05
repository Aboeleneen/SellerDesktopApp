/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import javafx.collections.ObservableList;

/**
 *
 * @author Lenovo
 */
public class Order {
    
    private int order_id ;
    private int total_price;
    private String payment_type;
    private ObservableList<Product> products;
    private Date order_date;
    private Boolean status;
    private int productsNum;
    public Order(int order_id , int total_price, String payment_type, Date order_date, Boolean status) {
        this.order_id = order_id;
        this.total_price = total_price;
        this.payment_type = payment_type;
        this.order_date = order_date;
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    
    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getProductsNum() {
        return productsNum;
    }

    public void setProductsNum(int productsNum) {
        this.productsNum = productsNum;
    }
    
    
}
