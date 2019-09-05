/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ProductViewController implements Initializable {

    // controller 
    private Product selectedProduct;
    
    // scene builder
    @FXML private Label productName;
    @FXML private Label color;
    @FXML private Label size;
    @FXML private Label material;
    @FXML private Label category;
    @FXML private Label subCategory;
    @FXML private Label price;
    @FXML private Label discount;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * get data from products scene
     */
    public void initData(Product selectedProduct) {
       this.selectedProduct=selectedProduct;
       this.category.setText(selectedProduct.getCategory());
       this.subCategory.setText(selectedProduct.getSubCategory());
       this.color.setText(selectedProduct.getColor());
       this.discount.setText(Double.toString(selectedProduct.getDiscount()));
       this.price.setText(Double.toString(selectedProduct.getPrice()));
       this.material.setText(selectedProduct.getMaterial());
       this.productName.setText(selectedProduct.getProduct_name());
       this.size.setText(Integer.toString(selectedProduct.getSize()));
       // image view here
    }
    
}
