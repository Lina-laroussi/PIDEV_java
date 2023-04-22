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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

public class RegisterUserController implements Initializable {

  
    @FXML
    private AnchorPane pane_image;
     @FXML
    private AnchorPane pane_form;
    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_prenom;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField tf_password;
    @FXML
    private PasswordField tf_confirm;
    @FXML
    private Button button_login;
    @FXML
    private Button button_register;
    
    private static final String EMAIL_REGEX = "^[\\w\\d._%+-]+@[\\w\\d.-]+\\.[A-Za-z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
     @FXML
    private void RegisterPatientAction(ActionEvent event) throws IOException  {
        
         if(tf_nom.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nom");
            alert.showAndWait();
            
         }else if(tf_nom.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Votre nom doit comporter au moins 2 caractères");
             alert.showAndWait(); 
             
         }else if(tf_prenom.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre prenom");
            alert.showAndWait();
            
            
         }else if(tf_prenom.getText().length()<3){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Votre prenom doit comporter au moins 2 caractères");
                alert.showAndWait();
                
         } else if(tf_email.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre adresse email");
             alert.showAndWait();
         }
         
         else if(!tf_email.getText().matches(EMAIL_REGEX)){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Email invalide");
             alert.showAndWait();
         
         }else if(tf_password.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre mot de passe");
             alert.showAndWait();
           
         }
         else if(tf_password.getText().length()<8){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText(" Votre mot de passe doit comporter au moins huit caractères");
             alert.showAndWait();
             
         }else if(tf_confirm.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre confirmation de mot de passe");
             alert.showAndWait();
             
          }else if(!tf_confirm.getText().equals(tf_password.getText())){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("les mots de passe ne correspondent pas");
             alert.showAndWait();
           
         }else{
            String nom = tf_nom.getText();
            String prenom = tf_prenom.getText();
            String email = tf_email.getText();
            String password = tf_password.getText();
          
        try{
            User patient= new User(nom,prenom,email,password);
            UserService us = new UserService();
           
            
            us.registerPatient(patient);  
            AdminService adminService1 = new AdminService();
            
            
            String randomString = UUID.randomUUID().toString();
            String verificationCode = adminService1.getUser(patient).getId() + "-" + randomString;
            try{
                 EmailUtils.sendVerificationCode(email, "votre compte est crée avec succées" , verificationCode);
                 
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
               
            
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("votre compte a été ajouté avec succées, un code a été envoyé à votre email pour verifier votre compte");
            alert.showAndWait();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/VerifyCode.fxml"));
            Parent parent = loader.load();
            VerifyCodeController controller = (VerifyCodeController) loader.getController();
            System.out.println(verificationCode);
            controller.verifierEmail(verificationCode);
            try{
                 User patient1= new User(nom,prenom,email,password);
                 AdminService adminService = new AdminService();
                 controller.getUser( adminService.getUser(patient1));
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            Scene sceneRegister = new Scene(parent);
            Stage stageRegister  = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageRegister.hide();
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
            
        }catch(IOException | SQLException e){
            System.out.println(e.getMessage());
        }
                    
   }     
    }
    
    
    @FXML
     private void RegisterMedecinAction(ActionEvent event) throws IOException  {
        
         if(tf_nom.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nom");
            alert.showAndWait();
            
         }else if(tf_nom.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Votre nom doit comporter au moins 2 caractères");
             alert.showAndWait(); 
             
         }else if(tf_prenom.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre prenom");
            alert.showAndWait();
            
            
         }else if(tf_prenom.getText().length()<3){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Votre prenom doit comporter au moins 2 caractères");
                alert.showAndWait();
                
         } else if(tf_email.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre adresse email");
             alert.showAndWait();
         }
         
         else if(!tf_email.getText().matches(EMAIL_REGEX)){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Email invalide");
             alert.showAndWait();
         
         }else if(tf_password.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre mot de passe");
             alert.showAndWait();
           
         }
         else if(tf_password.getText().length()<8){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText(" Votre mot de passe doit comporter au moins huit caractères");
             alert.showAndWait();
             
         }else if(tf_confirm.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre confirmation de mot de passe");
             alert.showAndWait();
             
          }else if(!tf_confirm.getText().equals(tf_password.getText())){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("les mots de passe ne correspondent pas");
             alert.showAndWait();
           
         }else{
            String nom = tf_nom.getText();
            String prenom = tf_prenom.getText();
            String email = tf_email.getText();
            String password = tf_password.getText();
          
         try{
            User medecin= new User(nom,prenom,email,password);
            UserService us = new UserService();
            
            us.registerMedecin(medecin);    
            
            try{
                 EmailUtils.sendEmail(email, "votre compte est crée avec succées",nom);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
              
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("votre compte a été ajouté avec succées ");
            alert.showAndWait();
            
            Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
        
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister  = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister.hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
         }
         catch(IOException e){
             System.out.println(e.getMessage());
         }
                    
   }     
    }
     
     @FXML
      private void RegisterPharmacienAction(ActionEvent event) throws IOException  {
        
         if(tf_nom.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nom");
            alert.showAndWait();
            
         }else if(tf_nom.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Votre nom doit comporter au moins 2 caractères");
             alert.showAndWait(); 
             
         }else if(tf_prenom.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre prenom");
            alert.showAndWait();
            
            
         }else if(tf_prenom.getText().length()<3){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Votre prenom doit comporter au moins 2 caractères");
                alert.showAndWait();
                
         } else if(tf_email.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre adresse email");
             alert.showAndWait();
         }
         
         else if(!tf_email.getText().matches(EMAIL_REGEX)){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Email invalide");
             alert.showAndWait();
         
         }else if(tf_password.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre mot de passe");
             alert.showAndWait();
           
         }
         else if(tf_password.getText().length()<8){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText(" Votre mot de passe doit comporter au moins huit caractères");
             alert.showAndWait();
             
         }else if(tf_confirm.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre confirmation de mot de passe");
             alert.showAndWait();
             
          }else if(!tf_confirm.getText().equals(tf_password.getText())){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("les mots de passe ne correspondent pas");
             alert.showAndWait();
           
         }else{
            String nom = tf_nom.getText();
            String prenom = tf_prenom.getText();
            String email = tf_email.getText();
            String password = tf_password.getText();
          
     try{
            User pharmacien= new User(nom,prenom,email,password);
            UserService us = new UserService();
            
            us.registerPharmacien(pharmacien);    
            try{
                 EmailUtils.sendEmail(email, "votre compte a été crée avec succées",nom);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
              
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("votre compte a été ajouté avec succées ");
            alert.showAndWait();
            
            Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
        
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister  = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister.hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
            
     }catch(IOException e){
         System.out.println(e.getMessage());
     }    
                    
   }     
    }
      
    @FXML
    private void RegisterAssureurAction(ActionEvent event) throws IOException  {
        
         if(tf_nom.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nom");
            alert.showAndWait();
            
         }else if(tf_nom.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Votre nom doit comporter au moins 2 caractères");
             alert.showAndWait(); 
             
         }else if(tf_prenom.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre prenom");
            alert.showAndWait();
            
            
         }else if(tf_prenom.getText().length()<3){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Votre prenom doit comporter au moins 2 caractères");
                alert.showAndWait();
                
         } else if(tf_email.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre adresse email");
             alert.showAndWait();
         }
         
         else if(!tf_email.getText().matches(EMAIL_REGEX)){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Email invalide");
             alert.showAndWait();
         
         }else if(tf_password.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre mot de passe");
             alert.showAndWait();
           
         }
         else if(tf_password.getText().length()<8){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText(" Votre mot de passe doit comporter au moins huit caractères");
             alert.showAndWait();
             
         }else if(tf_confirm.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre confirmation de mot de passe");
             alert.showAndWait();
             
          }else if(!tf_confirm.getText().equals(tf_password.getText())){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("les mots de passe ne correspondent pas");
             alert.showAndWait();
           
         }else{
            String nom = tf_nom.getText();
            String prenom = tf_prenom.getText();
            String email = tf_email.getText();
            String password = tf_password.getText();
          
         try{
            User assureur= new User(nom,prenom,email,password);
            UserService us = new UserService();
            
            us.registerAssureur(assureur);    
            
            try{
                EmailUtils.sendEmail(email, "votre compte est crée avec succées",nom );
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
              
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("votre compte a été ajouté avec succées ");
            alert.showAndWait();
            
            Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
        
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister  = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister.hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
         }catch(IOException e){
             System.out.println(e.getMessage());
         }    
                    
   }     
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
    
}
