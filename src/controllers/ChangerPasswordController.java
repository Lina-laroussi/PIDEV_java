/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;
import utils.BCrypt;
import utils.Session;


public class ChangerPasswordController implements Initializable {

    @FXML
    private PasswordField tf_password;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private Button button_update;
    


 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }            

    @FXML
    private void updatePasswordUser(ActionEvent event) throws Exception {

       try{
      
        if(tf_password.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nouveau mot de passe");
            alert.showAndWait();  
        }
        else if(tf_password.getText().length()<8){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText(" Votre mot de passe doit comporter au moins huit caractères");
             alert.showAndWait();   
             
        }else if(confirm_password.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Vous devez entrez votre confirmation de mot de passe");
             alert.showAndWait();
             
          }else if(!confirm_password.getText().equals(tf_password.getText())){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("les mots de passe ne correspondent pas");
             alert.showAndWait();
             
        }
        
        else{     
        String password = tf_password.getText();
       
        User currentUser = Session.getInstance().getUser();
        String email =currentUser.getEmail();
        
        User user= new User(email,password);
        UserService userService = new UserService();
        String NewPassword = BCrypt.hashpw(password,BCrypt.gensalt(13));   

        userService.updateUserPassword(user, NewPassword);
                
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AJOUT AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Mot de Passe modifié avec succès");
        alert.showAndWait();
        
        }}

        catch(RuntimeException ex)
        {
            System.out.println(ex);
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "password user modifié avec succes";
     
    }
    
}
