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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class EditProductController implements Initializable {

    // controller
    private Product selectedProduct;
    private Product EditedProduct;
    private Brand selectedBrand;
    // scene builder
    @FXML private TextField productName;
    @FXML private ColorPicker color;
    @FXML private TextField size;
    @FXML private TextField material;
    @FXML private ChoiceBox category;
    @FXML private ChoiceBox subCategory;
    @FXML private TextField price;
    @FXML private TextField discount;
    @FXML private ImageView imageview;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initCategory();
        this.initSubCategory();
    }    
    
    /**
     * get data from products scene
     * @param selectedProduct
     */
    public void initData(Brand selectedBrand){
        this.selectedBrand=selectedBrand;
        this.selectedProduct = selectedBrand.getEditedProdcut();
        this.category.setValue(selectedProduct.getCategory());
        this.subCategory.setValue(selectedProduct.getSubCategory());
       // this.color.setValue(selectedProduct.getColor());
        this.discount.setText(Double.toString(selectedProduct.getDiscount()));
        this.price.setText(Double.toString(selectedProduct.getPrice()));
        this.material.setText(selectedProduct.getMaterial());
        this.productName.setText(selectedProduct.getProduct_name());
        this.size.setText(Integer.toString(selectedProduct.getSize()));
    }
    
    /**
     * add values to category choiceBox
     */
    private void initCategory(){
        this.category.getItems().add("men");
        this.category.getItems().add("women");
        this.category.getItems().add("kids");
        this.category.getItems().add("accessories");
        this.category.getItems().add("cosmitics");
    }
    
    /**
     * add values to subCategory choiceBox
     */
    private void initSubCategory(){
        this.subCategory.getItems().add("t-shirt");
        this.subCategory.getItems().add("paints");
        this.subCategory.getItems().add("shoes");
    }
    
    /**
     * back t products scene
     */
    @FXML
    public void Back(ActionEvent event) throws IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Products.fxml"));
            Parent ProductsView = loader.load();
           
            // pass data to home page scene
            ProductsController controller = loader.getController();
            controller.initData(this.selectedBrand);
            
            Scene scene = new Scene(ProductsView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
    
    /**
     * save the edit to the product
     */
    @FXML
    public void save(ActionEvent event) throws SQLException, IOException{
            this.EditedProduct = getProduct();
            this.selectedBrand.getProducts().remove(selectedProduct);
            DBConnection db = DBConnection.getInstance();
            db.updateProduct(EditedProduct);
            this.selectedBrand.addNewProduct(EditedProduct);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Products.fxml"));
            Parent productsView = loader.load();
           
            // pass data to home page scene
            ProductsController controller = loader.getController();
            controller.initData(this.selectedBrand);
            
            Scene scene = new Scene(productsView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
    
    /**
     * get product object from current information
     */
    private Product getProduct() throws SQLException{
        Product product = new Product();
        product.setMaterial(material.getText());
        product.setPrice(Double.parseDouble(price.getText()));
        product.setProduct_name(productName.getText());
        product.setDiscount(Double.parseDouble(discount.getText()));
        product.setSize(Integer.parseInt(size.getText()));
        product.setColor(color.getValue().toString());
        product.setCategory(category.getValue().toString());
        product.setSubCategory(subCategory.getValue().toString());
        product.setBrand_id(this.selectedBrand.getBrand_id());
        product.setBrand_name(this.selectedBrand.getBrand_name());
        product.setProduct_id(selectedProduct.getProduct_id());
        // add image
      //  DBConnection db  = DBConnection.getInstance();
      //  product.setImage_id(db.InsertImage(imageview.getImage().));
      
        
        return product;
    }
}
