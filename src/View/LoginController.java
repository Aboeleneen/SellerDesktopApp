/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class LoginController implements Initializable {

    
    // scene builder
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label errorMessage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    /**
     * check login validation after click login button
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    public void login(ActionEvent event) throws IOException{
        if(check()){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("HomePage.fxml"));
            Parent homeView = loader.load();
            Scene scene = new Scene(homeView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }else{
            this.errorMessage.setText("wrong username or password");
        }
    }
    
    /**
     * call checkLogin function in database class
     */
    private Boolean check(){
        DBConnect db = DBConnect.getInstance();
        String user = this.username.getText();
        String pass = this.password.getText();
        return db.checkLogin(user, pass);
    }
    
}
