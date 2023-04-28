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


public class ForgotPasswordController implements Initializable {

   
    @FXML
    private TextField tf_email;
    @FXML
    private Button button_renvoi_password;
    
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

 

    
    
    @FXML
    private void SendPassword(ActionEvent event) {
         String email = tf_email.getText();
        
        try{
           AdminService adminService = new AdminService();
           User user = new User(email);
           User user1 = adminService.getUser(user);
           if(!email.equals(user1.getEmail())){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setHeaderText(null);
                   alert.setContentText("Email incorrect");
                   alert.showAndWait();
           }else{
                Random random = new Random();
                int randomNumber = random.nextInt(800001) + 100000;
                String verificationCode = Integer.toString(randomNumber);
                User user2 = new User(user1.getNom(),email,verificationCode);

                adminService.updateCodeUser(user2);

               
               try{
                    EmailUtils.sendPassword(email, "Nouveau Mot de Passe" , verificationCode,user2);

                }catch(Exception e){
                        System.out.println(e.getMessage());
                }
               
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("un code a été envoyé à votre email");
            alert.showAndWait();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/VerifyCodePassword.fxml"));
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
