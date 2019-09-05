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
public class AddProductController implements Initializable {

    // controller
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
     */
    public void initData(Brand selectedBrand){
        this.selectedBrand=selectedBrand;
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
     * add new product and prepare to add new one 
     * @param event
     */
    @FXML
    public void addNewProduct(ActionEvent event) throws SQLException, IOException{
        selectedBrand.addNewProduct(getProduct());
        Back(event);
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
        
        // add image
      //  DBConnection db  = DBConnection.getInstance();
      //  product.setImage_id(db.InsertImage(imageview.getImage().));
      
        product.setProduct_id(this.insertProduct(product));
        return product;
    }
    /**
     * insert new product into database
     */
    private int insertProduct(Product product) throws SQLException{
        DBConnection db = DBConnection.getInstance();
        return db.InsertProduct(product);
    }
    
    /**
     * return to products scene
     */
    @FXML
    public void Back(ActionEvent event) throws IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Products.fxml"));
            Parent homeView = loader.load();
           
            // pass data to home page scene
            ProductsController controller = loader.getController();
            controller.initData(this.selectedBrand);
            
            Scene scene = new Scene(homeView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
    
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
}
