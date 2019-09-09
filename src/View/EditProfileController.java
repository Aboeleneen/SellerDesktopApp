/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Brand;
import Model.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class EditProfileController implements Initializable {
    // controller
    private Brand selectedBrand;
    // scene builder
    @FXML private TextField brand_name;
    @FXML private TextField brand_email;
    @FXML private TextField brand_location;
    @FXML private TextField brand_phone;
    @FXML private TextField brand_username;
    @FXML private TextField brand_password;
    @FXML private ImageView imageView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initData(Brand selectedBrand) throws SQLException {
        this.selectedBrand=selectedBrand;
        this.brand_name.setText(selectedBrand.getBrand_name());
        this.brand_email.setText(selectedBrand.getEmail());
        this.brand_location.setText(selectedBrand.getLocation());
        this.brand_phone.setText(getPhone());
        this.brand_username.setText(selectedBrand.getUsername());
        this.brand_password.setText(selectedBrand.getPassword());
    }
    
    /**
     * get phone given phone_id
     */
    public String getPhone() throws SQLException{
        DBConnection db = DBConnection.getInstance();
        return db.getPhone(selectedBrand.getPhone_id());
    }
    
    /**
     * save changes and return to profile scene
     */
    @FXML
    public void saveEdit(ActionEvent event) throws SQLException, IOException{
        DBConnection db = DBConnection.getInstance();
        selectedBrand = this.getBrand();
        db.updateProfile(selectedBrand);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile.fxml"));
        Parent homeView = loader.load();
           
        // pass data to home page scene
        ProfileController controller = loader.getController();
        controller.initData(selectedBrand);
            
            
        Scene scene = new Scene(homeView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    /**
     * return brand object from given information
     */
    public Brand getBrand(){
        Brand brand = new Brand(selectedBrand.getBrand_id(),
                                brand_username.getText(), 
                                brand_password.getText(), 
                                selectedBrand.getPhone_id(), 
                                brand_location.getText(), 
                                selectedBrand.getImage_id(), 
                                brand_name.getText(),
                                brand_email.getText());
        brand.setOrders(selectedBrand.getOrders());
        brand.setProducts(selectedBrand.getProducts());
        return brand;
    }
    
    /**
     * back to profile scene
     */
    @FXML
    public void back(ActionEvent event) throws SQLException, IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile.fxml"));
        Parent homeView = loader.load();
           
        // pass data to home page scene
        ProfileController controller = loader.getController();
        controller.initData(selectedBrand);
            
            
        Scene scene = new Scene(homeView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
}
