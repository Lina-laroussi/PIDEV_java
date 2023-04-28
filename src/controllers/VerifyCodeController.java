/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AdminService;
import services.UserService;
import utils.EmailUtils;
import utils.Session;


public class VerifyCodeController implements Initializable {

  
    @FXML
    private TextField tf_code;
    @FXML
    private Button button_code;
    
    @FXML
    private Button button_renvoyer;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

 
    
    
    @FXML
    private void EnvoyerCode(ActionEvent event) throws IOException{
        
        
        AdminService adminservice = new AdminService();
        String userCode =tf_code.getText();
        try{
            User patient = adminservice.getUserByCode(userCode);

            if (patient.getEmail() == null)  {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("il semble que vous avez entré le mauvais code , vous pouvez renvoyer un autre code de vérification ");
                alert.showAndWait();

            } else {
              
                try{
                    UserService userService = new UserService();
                    userService.VerifyPatient(patient);
                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("votre compte a été vérifié ");
                alert.showAndWait();


                Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
                Scene sceneRegister = new Scene(parentLogin);
                Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
                stageRegister .hide();
                stageRegister .setScene(sceneRegister);
                stageRegister .show();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }   
    }
    
    
     @FXML
    private void RenvoyerAction(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/RenvoiCodeVerif.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }

  
}
