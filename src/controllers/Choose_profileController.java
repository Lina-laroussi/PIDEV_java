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
import javafx.stage.Stage;


public class Choose_profileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    
    @FXML
    private void RegisterPatientAction(ActionEvent event) throws IOException {
        
            Parent parentRegister= FXMLLoader.load(getClass().getResource("../gui/RegisterPatient.fxml"));
            Scene sceneRegister = new Scene(parentRegister);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister .show();
    }
    
    @FXML
    private void RegisterMedecinAction(ActionEvent event) throws IOException {
        
            Parent parentRegister= FXMLLoader.load(getClass().getResource("../gui/RegisterMedecin.fxml"));
            Scene sceneRegister = new Scene(parentRegister);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister .show();
    }
    
    
    @FXML
    private void RegisterPharmacienAction(ActionEvent event) throws IOException {
        
            Parent parentRegister= FXMLLoader.load(getClass().getResource("../gui/RegisterPharmacien.fxml"));
            Scene sceneRegister = new Scene(parentRegister);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister.hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
    }
    
    @FXML
    private void RegisterAssureurAction(ActionEvent event) throws IOException {
        
            Parent parentRegister= FXMLLoader.load(getClass().getResource("../gui/RegisterAssureur.fxml"));
            Scene sceneRegister = new Scene(parentRegister);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister .show();
    }
    
    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        
            Parent parentRegister= FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
            Scene sceneRegister = new Scene(parentRegister);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister .show();
    }
    
    
    
    
    
}
