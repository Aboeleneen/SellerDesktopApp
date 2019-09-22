/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Brand;
import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ProfileController implements Initializable {

    // controller
    private Brand selectedBrand;
    // scene builder
    @FXML private Label brand_id;
    @FXML private Label email;
    @FXML private Label phone;
    @FXML private Label location;
    @FXML private Label numOfProducts;
    @FXML private Label brand_rate;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
   /**
    * get data from home page scene
    */
    public void initData(Brand selectedBrand) throws SQLException{
        this.selectedBrand = selectedBrand;
        this.brand_id.setText(Integer.toString(selectedBrand.getBrand_id()));
        this.phone.setText(getPhone());
        this.email.setText(selectedBrand.getEmail());
        this.location.setText(selectedBrand.getLocation());
        // numOfProducts
        // brand_rate
        
    }
    
    /**
     * get phone number from database
     */
    private String getPhone() throws SQLException{
        DBConnect db = DBConnect.getInstance();
        return db.getPhone(this.selectedBrand.getPhone_id());
    }
    /**
     * return home
     * @param event
     * @throws IOException 
     */
    @FXML
    public void goHome(ActionEvent event) throws IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("HomePage.fxml"));
            Parent homeView = loader.load();
           
            // pass data to home page scene
            HomePageController controller = loader.getController();
            controller.initData(this.selectedBrand);
            
            Scene scene = new Scene(homeView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
    
    /**
     * edit profile 
     */
    @FXML
    public void editProfile(ActionEvent event) throws IOException, SQLException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditProfile.fxml"));
            Parent editProfileView = loader.load();
            
            // pass data to edit profile scene
            EditProfileController controller = loader.getController();
            controller.initData(selectedBrand);
            
            Scene scene = new Scene(editProfileView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
    
}
