/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Brand;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class HomePageController implements Initializable {

    
    // scene builder
    @FXML private Text brandname;
    
    // controller
    private Brand selectedBrand;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * @param  selectedBrand
     * get information from login scene 
     */
    public void initData(Brand selectedBrand){
        this.selectedBrand = selectedBrand;
        this.brandname.setText(selectedBrand.getBrand_name());
    }
    
    /**
     * change the scene to show products of current brand
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    public void showProducts(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Products.fxml"));
        Parent productsView = loader.load();
        
        // pass data to products scene
        ProductsController controller = loader.getController();
        controller.initData(this.selectedBrand);
        
        Scene scene = new Scene(productsView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    /**
     * change the scene to show orders of current brand
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    public void showOrders(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Orders.fxml"));
        Parent ordersView = loader.load();
        Scene scene = new Scene(ordersView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    /**
     * change scene to show profile of current brand
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    public void showProfile(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile.fxml"));
        Parent ordersView = loader.load();
        Scene scene = new Scene(ordersView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    /**
     * change scene to start a chat with customer service
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    public void contactCustomerService(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Chat.fxml"));
        Parent ordersView = loader.load();
        Scene scene = new Scene(ordersView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
}
