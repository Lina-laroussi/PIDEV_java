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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Session;


public class ParentBackController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
     @FXML
    private Button button_prod;

    @FXML
    private Button button_categ;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

      @FXML
    private void deconnectAction(ActionEvent event) throws IOException {
        
            
            Session.getInstance().clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("deconnexion réussie ");
            alert.showAndWait();   
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
    
         @FXML
    private void ProductAction(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/produit.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
    
         @FXML
    private void CategorieAction(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Categorie.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
          @FXML
    private void userAction(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/DashboardAdmin.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
}