/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Lenovo
 */
public class DBConnect {
    
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private static DBConnect DBConnectInstance;
    
    private DBConnect(){
    try{
            Class.forName("com.mysql.jdbc.Driver");
            con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/closet", "root", "");
            st = con.createStatement();
            
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Done");
        }
    }
    
    /**
     * 
     * @return DBConnectInstance 
     */
    public static DBConnect getInstance(){
        if(DBConnectInstance == null){
            DBConnectInstance = new DBConnect();
        }
        return DBConnectInstance;
    }
    
    /**
     * @param username password
     * @return true if there is an record in database with this information 
     *         false otherwise
     */
    public Boolean checkLogin(String username , String password) throws SQLException{
        String query = "select * from brand where username = ? and password = ?" ;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2,password);
        rs = ps.executeQuery();
        return rs.last();
    }
    
    /**
     * @param username , password
     * @return brand object with this record
     * @throws java.sql.SQLException
     */
    public Brand getBrand(String username ,String password) throws SQLException{
        String query = "select * from brand where username = ? and password = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        rs=ps.executeQuery();
        rs.next();
        Brand brand = new Brand(rs.getInt("brand_id"),
                                rs.getString("username"), 
                                rs.getString("password"), 
                                rs.getInt("phone_id"), 
                                rs.getString("location"), 
                                rs.getInt("image_id"), 
                                rs.getString("name"));
        brand.setProducts(this.getProducts(rs.getInt("brand_id")));
        brand.setOrders(this.getOrders(rs.getInt("brand_id")));
        return brand;
    }
    
    /**
     * get all products belong to selected brand
     * @param brand_id
     * @return observabeList<Product>
     */
    public ObservableList<Product> getProducts(int brand_id) throws SQLException{
        ObservableList<Product> products = FXCollections.observableArrayList();
        String query = "select product.product_id , product.product_name  "+
                        "product.discount , product.image_id , description.material , sub_category.subCategory_name, " +
                        "description.color , description.size , description.price , category.category_name "+
                        "from sub_category join product join description join category " +
                        "ON sub_category.subcategory_id = description.sub_category_id " +
                        "and description.product_id = product.product_id  " +
                        "and description.category_id = category.category_id where product.brand_id = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, brand_id);
        rs = ps.executeQuery();
        while(rs.next()){
            products.add(new Product(rs.getInt("product_id"),
                                     rs.getString("product_name"), 
                                     rs.getDouble("price"),
                                     rs.getDouble("discount"),
                                     rs.getInt("image_id"),
                                     rs.getString("material"),
                                     rs.getString("category_name"),
                                     rs.getString("subCategory_name"),
                                     rs.getInt("size"), 
                                     rs.getString("color")));
        }
        return products;
    }
    
    /**
     * get all orders related to selected brand
     * @param brand_id
     * @return ObservableList<Order> 
     */
    public ObservableList<Order> getOrders(int brand_id) throws SQLException{
        ObservableList<Order>  orders = FXCollections.observableArrayList();
        String query = "Select * from orders where brand_id = ? " ;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, brand_id);
        rs = ps.executeQuery();
        while(rs.next()){
            Order newOrder = new Order(rs.getInt("order_id"), 
                                       rs.getInt("total_price"), 
                                       rs.getInt("payment_id"), 
                                       rs.getDate("date"),
                                       rs.getBoolean("placed"));
            newOrder.setProducts(this.getProductsOfOrder(rs.getInt("order_id")));
            orders.add(newOrder);
        }
        return orders;
    }
    
    /**
     * get all product belong to selected order
     * @param order_id
     * @return ObservableList<Product> 
     * @throws java.sql.SQLException 
     */
    public ObservableList<Product> getProductsOfOrder(int order_id) throws SQLException{
        ObservableList<Product> products = FXCollections.observableArrayList();
        String query = "select product.product_id , product.product_name , brand.name , product.image_id "+
                        "description.color , description.size , description.price , category.category_name , "+
                        "description.material , sub_category.subCategory_name , " +
                        "from orders join prodorder join product join brand join description join category join sub_category" +
                        "ON prodorder.order_id = orders.order_id and product.product_id = prodorder.product_id " +
                        "and product.brand_id = brand.brand_id and description.product_id = product.product_id  " +
                        "and description.category_id = category.category_id and description.sub_category_id = " +
                        "sub_category.subcategory_id where prodorder.order_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, order_id);
        rs = ps.executeQuery();
        while(rs.next()){
             products.add(new Product(rs.getInt("product_id"),
                                     rs.getString("product_name"), 
                                     rs.getDouble("price"),
                                     rs.getDouble("discount"),
                                     rs.getInt("image_id"),
                                     rs.getString("material"),
                                     rs.getString("category_name"),
                                     rs.getString("subCategory_name"),
                                     rs.getInt("size"), 
                                     rs.getString("color")));
        }
        return products;
    }
    
}
