/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Brand;
import Model.Order;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class OrdersController implements Initializable {

    // controller
    private Brand selectedBrand;
    // scene builder 
    @FXML private TableView<Order> tableView;
    @FXML private TableColumn<Order,Integer> column_id;
    @FXML private TableColumn<Order,Integer> column_productNum;
    @FXML private TableColumn<Order,Integer> column_status;
    @FXML private TableColumn<Order,Integer> column_totalPrice;
    @FXML private TableColumn<Order,String> column_paymentType;
    @FXML private TableColumn<Order,String> column_date;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        column_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        column_productNum.setCellValueFactory(new PropertyValueFactory<>("productsNum"));
        column_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        column_totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        column_paymentType.setCellValueFactory(new PropertyValueFactory<>("payment_type"));
        column_date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }    
    
    /**
     * get data from home page scene
     */
    public void initDate(Brand selectedBrand){
        this.selectedBrand=selectedBrand;
        this.tableView.setItems(this.selectedBrand.getOrders());
    }
    
    /**
     * return home
     * @param event
     * @throws java.io.IOException
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
    
    // display order page contatin all information about selected order 
    @FXML
    public  void dislay(ActionEvent event) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrderPage.fxml"));
        Parent root = loader.load();
        
         // pass information to orderPage scene
        OrderPageController controller = loader.getController();
        controller.initData(this.tableView.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root);
        Stage orderWindow = new Stage();
        orderWindow.setScene(scene);
        orderWindow.show();
    }
    
}
