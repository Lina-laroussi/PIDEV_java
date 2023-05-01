/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
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
import javafx.stage.Stage;
import utils.Session;


public class MenuController implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Button btn_accueil;
   
    @FXML
    private Button button_medecins;
    User currentUser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       currentUser = Session.getInstance().getUser();
        System.out.println(currentUser);
        button_login.setOnAction(event->{
            if(currentUser == null){
                try {
                    Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
                    Scene sceneRegister = new Scene(parentLogin);
                    Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
                     stageRegister .hide();
        
                      stageRegister .setScene(sceneRegister);
                       stageRegister .show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            
        }else if(currentUser != null){
                button_login.setText("se déconnecter");
                try {
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
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
        });
    }    



   /*  @FXML
    private void loginAction(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/login.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }*/
    
    
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
    
    @FXML
    void DoctoshopAction(ActionEvent event) throws IOException {
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/FrontCategorie.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
}