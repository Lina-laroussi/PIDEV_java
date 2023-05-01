/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.mysql.cj.xdevapi.Client;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.AdminService;
import services.UserService;
import utils.EmailUtils;
import utils.Session;


public class LoginController implements Initializable {

    
    @FXML
    private AnchorPane pane_image;
     @FXML
    private AnchorPane pane_form;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField tf_password;
    @FXML
    private Button button_login;
    
    @FXML
    private Button button_register;
    
    @FXML
    private Button button_oublié;
    
   
    private static final String EMAIL_REGEX = "^[\\w\\d._%+-]+@[\\w\\d.-]+\\.[A-Za-z]{2,}$";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
     private void LoginUserAction(ActionEvent event) throws IOException {
         
         
        if (tf_email.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre email");
            alert.showAndWait();
        }
         else if(!tf_email.getText().matches(EMAIL_REGEX)){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Email invalide");
             alert.showAndWait();
            
        }else if(tf_password.getText().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre Mot de Passe");
            alert.showAndWait();
         
        }else {
            String email = tf_email.getText();
            String password = tf_password.getText();
            UserService us = new UserService();
            User user = us.LoginUser(email, password);
            System.out.println(user);
            
            
            if(user!=null){
                     Session.getInstance().setUser(user);
                     System.out.println(Session.getInstance().getUser());
                    
                    if(user.getRoles().equals("[\"ROLE_ADMIN\"]")){
                        
                        Parent parentDashboard = FXMLLoader.load(getClass().getResource("../gui/DashboardAdmin.fxml"));

                        Scene sceneDashboard  = new Scene(parentDashboard );
                        Stage stageDashboard   = (Stage)((Node)event.getSource()).getScene().getWindow();

                        stageDashboard  .hide();

                        stageDashboard  .setScene(sceneDashboard );
                        stageDashboard  .show();
                    
                    }else if(user.getEtat().equals("non vérifié") ){
                         Alert alert = new Alert(Alert.AlertType.ERROR);
                         alert.setHeaderText(null);
                         alert.setContentText("votre compte n'est pas vérifié,"
                                    + "vous devez entrer un code de vérification pour vérifier votre email");
                         alert.showAndWait();
                         
                          FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/VerifyCode.fxml"));
                          Parent parent = loader.load();      
                          Scene sceneRegister = new Scene(parent);
                          Stage stageRegister  = (Stage)((Node)event.getSource()).getScene().getWindow();
                          stageRegister.hide();
                          stageRegister.setScene(sceneRegister);
                          stageRegister.show();    
                            
                    }else if(user.getEtat().equals("non valide") ){
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setContentText("votre compte n'est pas validé par l'administrateur");
                            alert.showAndWait();
                            
                    }else if(user.getEtat().equals("deleted") ){
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setContentText("votre compte n'existe pas");
                            alert.showAndWait();       
                            
                    }else if(user.isIs_blocked()==true ){
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setContentText("votre compte est bloqué , veuillez contactez l'admnistrateur pour plus d'informations ");
                            alert.showAndWait();        
                            
                    }else if (user.getRoles().equals("[\"ROLE_MEDECIN\"]")){
                        Parent parentProfile = FXMLLoader.load(getClass().getResource("../gui/ProfileMedecin.fxml"));

                        Scene sceneProfile= new Scene(parentProfile);
                        Stage stageProfile  = (Stage)((Node)event.getSource()).getScene().getWindow();

                        stageProfile.hide();

                        stageProfile.setScene(sceneProfile);
                        stageProfile.show();
                        
                     }else if(user.getRoles().equals("[\"ROLE_ASSUREUR\"]")){
                        Parent parentProfile = FXMLLoader.load(getClass().getResource("../gui/dashboard.fxml"));
                        Scene sceneProfile= new Scene(parentProfile);
                        Stage stageProfile  = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stageProfile.hide();
                        stageProfile.setScene(sceneProfile);
                        stageProfile.show();
                     }
                    else{
                      Parent parentProfile= FXMLLoader.load(getClass().getResource("../gui/ProfileUser.fxml"));
                      Scene sceneProfile = new Scene(parentProfile);
                      Stage stageProfile  = (Stage)((Node)event.getSource()).getScene().getWindow();
       
                      stageProfile.hide();
          
                      stageProfile.setScene(sceneProfile );
                      stageProfile.show();
                   } 
               }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Email ou mot de passe incorrect ");
                alert.showAndWait();   
        }
       
        }
     }   
    
     
    
    
    
    @FXML
    private void RegisterAction(ActionEvent event) throws IOException {
        
            Parent parentRegister= FXMLLoader.load(getClass().getResource("../gui/choose_profile.fxml"));
            Scene sceneLogin = new Scene(parentRegister);
            Stage stageLogin = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageLogin .hide();
        
            stageLogin.setScene(sceneLogin);
            stageLogin .show();
    }
    
    
     
    
    
    @FXML
    private void MotdePasseOublié(ActionEvent event) throws IOException {
        
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/ForgotPassword.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
    

}
