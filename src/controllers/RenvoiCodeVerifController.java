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
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import services.AdminService;
import utils.EmailUtils;

public class RenvoiCodeVerifController implements Initializable {

    @FXML
    private TextField tf_email;
    @FXML
    private Button button_renvoi_code;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RenvoyerCode(ActionEvent event) {
        
        String email = tf_email.getText();
        
        try{
           AdminService adminService = new AdminService();
           User user = new User(email);
           User patient = adminService.getUser(user);
           if(!email.equals(patient.getEmail())){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setHeaderText(null);
                   alert.setContentText("Email incorrect");
                   alert.showAndWait();
           }else{
               Random random = new Random();
               int randomNumber = random.nextInt(800001) + 100000;
               String verificationCode = Integer.toString(randomNumber);
               User patient1 = new User(patient.getNom(),email,verificationCode);
            
               adminService.updateCodeUser(patient1);
               try{
                    EmailUtils.resendVerificationCode(email, "Nouveau code de vérification" , verificationCode,patient1);

                }catch(Exception e){
                        System.out.println(e.getMessage());
                }
               
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("un code a été envoyé à votre email");
            alert.showAndWait();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/VerifyCode.fxml"));
            Parent parent = loader.load();
            Scene sceneRegister = new Scene(parent);
            Stage stageRegister  = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageRegister.hide();
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
        }
        
        }catch(SQLException | IOException e ){
            System.out.println(e.getMessage());   
        }   
    }
    
}
