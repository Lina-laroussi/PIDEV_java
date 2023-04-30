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
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MenuController implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Button btn_accueil;
   
    @FXML
    private Button button_medecins;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    



     @FXML
    private void loginAction(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/login.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
    
      @FXML
    private void AccueilAction(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Accueil.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
    
    @FXML
    void medecinsAction(ActionEvent event) throws IOException {
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Medecins.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
}
