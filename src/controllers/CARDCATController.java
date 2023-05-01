/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entities.categorie;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CARDCATController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private AnchorPane card_form;

    @FXML
    private Label prod_Des;

    @FXML
    private Button prod_addBtn;

    @FXML
    private Label prod_marque;

    @FXML
    private Label prod_name;
     @FXML
    private Label prod_id;

    
    private categorie categorie;

  

     public void setData(categorie categorie) {
    this.categorie = categorie;
    prod_name.setText(categorie.getNom());
    prod_marque.setText(categorie.getMarque());
    prod_Des.setText(categorie.getDescription());
prod_id.setText(Integer.toString(categorie.getId()));
}

       
 @FXML
private void handleConsulterButtonAction(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/FrontProduit.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    FrontProduitController frontProduitController = fxmlLoader.getController();
    frontProduitController.setCategorie(categorie);
        frontProduitController.menuDisplayCard();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
    System.out.println("ID de la catégorie sélectionnée : " + categorie.getId());

}



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
