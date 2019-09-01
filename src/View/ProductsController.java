/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Brand;
import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
}
