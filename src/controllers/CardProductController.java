/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import services.ProduitService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import entities.produit;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CardProductController implements Initializable {

    @FXML
    private AnchorPane card_form;

    
    
     @FXML
    private Label menu_total;
     
    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private ImageView prod_imageView;
 @FXML
    private TableView<produit> menu_tableView;
   
@FXML
    private TableColumn<produit, Float> menu_col_price;

    @FXML
    private TableColumn<produit, String> menu_col_productName;

    @FXML
    private TableColumn<produit, Float> menu_col_quantity;
    @FXML
    private Button prod_addBtn;

    private produit produit;
    private Image image;

    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;



    public void setData(produit produit) {
        this.produit = produit;
        prod_image = produit.getImage();
        prod_name.setText(produit.getNom());
        prod_price.setText("$" + String.valueOf(produit.getPrix()));
        String path = "File:" + produit.getImage();
        image = new Image(path, 190, 94, false, true);
        prod_imageView.setImage(image);

    }
    
    private TableView<produit> menuTableView;

public void setMenuTableView(TableView<produit> menu_tableView) {
    this.menu_tableView = menu_tableView;
}

private boolean incrementQuantityIfAlreadyExists(produit newProduit) {
    for (produit p : menu_tableView.getItems()) {
        if (p.getId() == newProduit.getId()) {
            p.setQuantite(p.getQuantite()+ 1);
            return true;
        }
    }
    return false;
}

@FXML
private void handleAddButton(ActionEvent event) {
    produit selectedProduct = menu_tableView.getSelectionModel().getSelectedItem();
    if (selectedProduct != null) {
        selectedProduct.setQuantite(selectedProduct.getQuantite()+ 1);
        menu_tableView.refresh();
    } else {
        produit newProduit = this.produit;
        boolean productExists = incrementQuantityIfAlreadyExists(newProduit);
        if (!productExists) {
            newProduit.setQuantite(1f);
            menu_tableView.getItems().add(newProduit);
        }
    }

    // Calculer le prix total
    float total = 0;
    for (produit p : menu_tableView.getItems()) {
        total += p.getPrix() * p.getQuantite();
    }

    // Afficher le prix total avec 2 décimales
    menu_total.setText("$" + String.format("%.2f", total));
}

public void setMenuTotal(Label menu_total) {
    this.menu_total = menu_total;
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}