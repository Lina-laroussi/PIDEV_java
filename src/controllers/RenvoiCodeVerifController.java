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
    private Button button_login;
    @FXML
    private TextField tf_email;
    @FXML
    private Button button_renvoi_code;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }

    @FXML
    private void RenvoyerCode(ActionEvent event) {
        
        String email = tf_email.getText();
        
        try{
           AdminService adminService = new AdminService();
           User user = new User(email);
           User patient = adminService.getUser(user);
           if(!email.equals(patient.getEmail())){
                   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                   alert.setHeaderText(null);
                   alert.setContentText("Email incorrect");
                   alert.showAndWait();
           }else{
               String randomString = UUID.randomUUID().toString();
               String verificationCode = patient.getId() + "-" + randomString;
               try{
                    EmailUtils.sendVerificationCode(email, "Nouveau code de vérification" , verificationCode);

                }catch(Exception e){
                        System.out.println(e.getMessage());
                }
               
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("un code a été envoyé à votre email");
            alert.showAndWait();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/VerifyCode.fxml"));
            Parent parent = loader.load();
            VerifyCodeController controller = (VerifyCodeController) loader.getController();
            System.out.println(verificationCode);
            controller.verifierEmail(verificationCode);
            controller.getUser(patient);
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
