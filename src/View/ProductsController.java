/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Brand;
import Model.DBConnection;
import Model.Product;
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
public class ProductsController implements Initializable {

    // controller
    private Brand selectedBrand;
    
    // scene builder 
    @FXML private TableView<Product> tableview;
    @FXML private TableColumn<Product,Integer> column_id;
    @FXML private TableColumn<Product,String> column_name;
    @FXML private TableColumn<Product,String> column_color;
    @FXML private TableColumn<Product,Integer> column_size;
    @FXML private TableColumn<Product,String> column_category;
    @FXML private TableColumn<Product,String> column_subcategory;
    @FXML private TableColumn<Product,String> column_material;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        column_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        column_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        column_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        column_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        column_subcategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        column_material.setCellValueFactory(new PropertyValueFactory<>("material"));
    }    
    
    /**
     * get data from home page scene
     * @param selectedBrand
     */
    public void initData(Brand selectedBrand){
        this.selectedBrand = selectedBrand;
        this.tableview.setItems(this.selectedBrand.getProducts());
    }
    
    /**
     * return home page
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
    
    /**
     * logout event
     * @param event
     * @throws java.io.IOException
     */
     @FXML
    public void logout(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        Parent loginView = loader.load();
        Scene scene = new Scene(loginView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    /**
     * go to add new product scene
     */
    @FXML
    public void addNewProduct(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddProduct.fxml"));
        Parent loginView = loader.load();
        
        // pass data to addNewProduct scene
        AddProductController controller = loader.getController();
        controller.initData(selectedBrand);
        
        Scene scene = new Scene(loginView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    // display order page contatin all information about selected order 
    @FXML
    public  void dislay(ActionEvent event) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProductView.fxml"));
        Parent root = loader.load();
        
         // pass information to orderPage scene
        ProductViewController controller = loader.getController();
        controller.initData(this.tableview.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root);
        Stage orderWindow = new Stage();
        orderWindow.setScene(scene);
        orderWindow.show();
    }
    
    /**
     * delete the selected product from table
     */
    @FXML
    public void deleteProduct() throws SQLException{
        Product product = this.tableview.getSelectionModel().getSelectedItem();
        selectedBrand.getProducts().remove(product);
        DBConnection db = DBConnection.getInstance();
        db.deleteProduct(product.getProduct_id());
        this.tableview.setItems(selectedBrand.getProducts());
    }
    
    /**
     * edit product
     */
    @FXML
    public void editProduct(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditProduct.fxml"));
        Parent editProductView = loader.load();
        
        // pass data to EditProduct scene
        Product product = this.tableview.getSelectionModel().getSelectedItem();
        selectedBrand.setEditedProdcut(product);
        EditProductController controller = loader.getController();
        controller.initData(selectedBrand);
        
        Scene scene = new Scene(editProductView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
   
}
