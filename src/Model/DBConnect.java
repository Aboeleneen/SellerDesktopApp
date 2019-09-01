/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public Boolean checkLogin(String username , String passwrod){
        return true;
    }
    
}
