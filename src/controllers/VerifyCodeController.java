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
import javafx.stage.Stage;
import services.UserService;
import utils.EmailUtils;


public class VerifyCodeController implements Initializable {

  
    @FXML
    private TextField tf_code;
    @FXML
    private Button button_code;
    @FXML
    private Button button_renvoi;

    private String Code ;
    private User user;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

 
    
    
    public String verifierEmail(String verificationCode){
        Code = verificationCode;
        return Code;
    }
    
    public User getUser(User patient){
       user = patient;
       return user;
    }
    
    @FXML
    private void EnvoyerCode(ActionEvent event) throws IOException{
        
        User patient = getUser(user);
        String userCode =tf_code.getText();
        
        String code = verifierEmail(Code);
        String[] parts = Code.split("-");
        String userId = parts[0]; 
        
        if (userCode.equals(code) && patient.getId() == Integer.parseInt(userId)) {
            System.out.println("Email address is verified.");
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
            
        } else {
            System.out.println("Invalid verification code.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("il semble que vous avez entré le mauvais code , vous pouvez renvoyer un autre code de vérification ");
            alert.showAndWait();
            
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/RenvoiCodeVerif.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageRegister .hide();
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
            
        }
           
    }
}
