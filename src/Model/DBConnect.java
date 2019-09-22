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
       if(DBConnectInstance == null ){
            DBConnectInstance = new DBConnect();
            return DBConnectInstance;
        }else{
            return DBConnectInstance;
        }
    }
    
    /**
     * @param username 
     * @param password
     * @return true if there is an record in database with this information 
     *         false otherwise
     * @throws java.sql.SQLException
     */
    public Boolean checkLogin(String username , String password) throws SQLException{
        String query = "select * from brand where username = ? and password = ?" ;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2,password);
        System.out.println(username + password);
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
        int brand_id = rs.getInt("brand_id");
        Brand brand = new Brand(rs.getInt("brand_id"),
                                rs.getString("username"), 
                                rs.getString("password"), 
                                rs.getInt("phone_id"), 
                                rs.getString("location"), 
                                rs.getInt("image_id"), 
                                rs.getString("name"),
                                rs.getString("email"));
        brand.setOrders(this.getOrders(brand_id));
        brand.setProducts(this.getProducts(brand_id));
        return brand;
    }
    
    /**
     * get all products belong to selected brand
     * @param brand_id
     * @return observabeList<Product>
     */
    public ObservableList<Product> getProducts(int brand_id) throws SQLException{
        ObservableList<Product> products = FXCollections.observableArrayList();
        String query = "select product.product_id , product.product_name , "+
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
            Product product = new Product(rs.getInt("product_id"),
                                     rs.getString("product_name"), 
                                     rs.getDouble("price"),
                                     rs.getDouble("discount"),
                                     rs.getInt("image_id"),
                                     rs.getString("material"),
                                     rs.getString("category_name"),
                                     rs.getString("subCategory_name"),
                                     rs.getInt("size"), 
                                     rs.getString("color"));
            product.setBrand_id(brand_id);
            products.add(product);
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
        String query = "Select orders.order_id , orders.total_price , orders.date , orders.placed ," + 
                       "payment.payment_name  from orders join payment " + 
                       "ON orders.payment_id = payment.payment_id where orders.brand_id = ? " ;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, brand_id);
        rs = ps.executeQuery();
        while(rs.next()){
            Order newOrder = new Order(rs.getInt("order_id"), 
                                       rs.getInt("total_price"), 
                                       rs.getString("payment_name"), 
                                       rs.getDate("date"),
                                       rs.getBoolean("placed"));
            ObservableList<Product> products = this.getProductsOfOrder(rs.getInt("order_id"));
            newOrder.setProducts(products);
            newOrder.setProductsNum(products.size());
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
        String query =  "select product.product_id , product.product_name , brand.name , product.image_id , brand.brand_id , "+
                        "description.color , description.size , description.price , category.category_name , product.discount ,  "+
                        "description.material , sub_category.subCategory_name from orders join prodorder join "+ 
                        "product join brand join description join category join sub_category "+
                        "ON prodorder.order_id = orders.order_id and product.product_id = prodorder.product_id "+
                        "and product.brand_id = brand.brand_id and description.product_id = product.product_id "+
                        "and description.category_id = category.category_id and description.sub_category_id = "+
                        "sub_category.subcategory_id where prodorder.order_id = ? ; " ;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, order_id);
        rs = ps.executeQuery();
        while(rs.next()){
             Product product = new Product(rs.getInt("product_id"),
                                     rs.getString("product_name"), 
                                     rs.getDouble("price"),
                                     rs.getDouble("discount"),
                                     rs.getInt("image_id"),
                                     rs.getString("material"),
                                     rs.getString("category_name"),
                                     rs.getString("subCategory_name"),
                                     rs.getInt("size"), 
                                     rs.getString("color"));
             product.setBrand_name(rs.getString("name"));
             product.setBrand_id(rs.getInt("brand_id"));
             products.add(product);
             
        }
        return products;
    }
    
    /**
     * get phone number given phone_id
     */
    public String getPhone(int phone_id) throws SQLException{
        String query = "Select  * from phones where phone_id = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, phone_id);
        rs = ps.executeQuery();
        rs.next();
        return rs.getString("phone");
    }
    
    /**
     * add image to images tables and return its id
     * @param image
     * @return 
     * @throws java.sql.SQLException
     */
    public int InsertImage(String image) throws SQLException{
        String query = "insert into images(image) values(?) " ;
        PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, image);
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        rs.next();
        return (int) rs.getLong(1);
    }
    
    /**
     * add new product to products table and return its id
     */
    public int InsertProduct(Product product) throws SQLException{
        String query = "INSERT INTO product (product_name, available, discount, image_id, brand_id) VALUES (?, '1', ?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, product.getProduct_name());
        ps.setDouble(2, product.getDiscount());
        // update here 
        ps.setInt(3,1);
        ps.setInt(4, product.getBrand_id());
        ps.executeUpdate();
        rs=ps.getGeneratedKeys();
        rs.next();
        int product_id = (int) rs.getLong(1);
        product.setProduct_id(product_id);
        this.InsertDescription(product);
        return product_id;
    }
    
    /**
     * add description for the product into database
     */
    private void InsertDescription(Product product) throws SQLException{
        String query =  "INSERT INTO description (color, size, material, hot_deals, price, category_id, sub_category_id, product_id, brand_name) VALUES (?, ?, ?, '0', ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, product.getColor());
        ps.setInt(2,product.getSize());
        ps.setString(3, product.getMaterial());
        ps.setDouble(4, product.getPrice());
        ps.setInt(5, getCategoryID(product.getCategory()));
        ps.setInt(6, getSubCategoryID(product.getSubCategory()));
        ps.setInt(7, product.getProduct_id());
        ps.setString(8, product.getBrand_name());
        ps.execute();
    }
    
    /**
     * get category_id given category_name
     */
    private int getCategoryID(String category_name) throws SQLException{
        String query = "select * from category where category_name = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, category_name.toLowerCase());
        rs = ps.executeQuery();
        rs.next();
        return rs.getInt("category_id");
    }
    
    /**
     * get subCategory_id given subCategory_id
     */
    private int getSubCategoryID(String subCategory_name) throws SQLException{
        String query = "select * from sub_category where subCategory_name = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, subCategory_name.toLowerCase());
        rs = ps.executeQuery();
        rs.next();
        return rs.getInt("subcategory_id");
    }
    
    /**
     * delete selected product from database given productID
     */
    public void deleteProduct(int product_id) throws SQLException{
        String query = "DELETE FROM product WHERE product_id = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, product_id);
        ps.execute();
        query = "DELETE FROM description WHERE product_id = ?" ;
        ps=con.prepareStatement(query);
        ps.setInt(1, product_id);
        ps.execute();
    }
    
    /**
     * update product 
     */
    public void updateProduct(Product selectedProduct) throws SQLException {
        String query = "UPDATE product SET product_name =  ? , discount = ?  WHERE product_id = ? " ;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, selectedProduct.getProduct_name());
        ps.setDouble(2, selectedProduct.getDiscount());
        ps.setInt(3, selectedProduct.getProduct_id());
        ps.executeUpdate();
        query = "UPDATE description SET size = ? , material = ? , price = ? , category_id = ? , sub_category_id = ? WHERE description.product_id = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1, selectedProduct.getSize());
        ps.setString(2, selectedProduct.getMaterial());
        ps.setDouble(3, selectedProduct.getPrice());
        ps.setInt(4,getCategoryID(selectedProduct.getCategory()));
        ps.setInt(5, getSubCategoryID(selectedProduct.getSubCategory()));
        ps.setInt(6,selectedProduct.getProduct_id());
        ps.executeUpdate();
    }
    
    /**
     * update profile
     */
    public void updateProfile(Brand brand) throws SQLException{
        String query = "UPDATE brand SET name= ? , username = ?, password = ? , email = ? , location = ? WHERE brand.brand_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, brand.getBrand_name());
        ps.setString(2, brand.getUsername());
        ps.setString(3,brand.getPassword());
        ps.setString(4, brand.getEmail());
        ps.setString(5, brand.getLocation());
        // phone problem
        // image problem
        ps.setInt(6, brand.getBrand_id());
        ps.executeUpdate();
    }
    
    public int getPhoneID(String phone) throws SQLException{
        String query = "Select * from phones where phone = ? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, phone);
        rs = ps.executeQuery();
        if(rs.last()){
            rs.next();
            return rs.getInt("phone_id");
        }else{
            query = "insert into phones(phone) values(?)";
            ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, phone);
            rs=ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }
     
}
