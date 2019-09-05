/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Brand;
import Model.DBConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    
}
