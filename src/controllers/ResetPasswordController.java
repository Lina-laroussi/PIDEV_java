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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.UserService;
import utils.BCrypt;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author LINA
 */
public class ResetPasswordController implements Initializable {

    @FXML
    private TextField tf_password;
    @FXML
    private Button button_envoyer;
    @FXML
    private TextField tf_confirm;
    
    
    User user =null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
      
    public User getUser(User utilisateur){
       user = utilisateur;
       return user;
    }
    
    
    @FXML
    private void SendPassword(ActionEvent event) {
        
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
             
        }
        
        else{     
        String password = tf_password.getText();
       
        User currentUser = getUser(user);
        System.out.println(currentUser);
        String email =currentUser.getEmail();
        
        User user1= new User(email,password);
        UserService userService = new UserService();
        String NewPassword = BCrypt.hashpw(password,BCrypt.gensalt(13));   

        userService.updateUserPassword(user1, NewPassword);
                
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AJOUT AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Mot de Passe modifié avec succès");
        alert.showAndWait();
        Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
        Scene sceneRegister = new Scene(parentLogin);
        Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
        stageRegister .hide();
        
        stageRegister .setScene(sceneRegister);
        stageRegister .show();
        
        }}

        catch(RuntimeException ex)
        {
            System.out.println(ex);
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        } catch (SQLException ex) {
              System.out.println(ex.getMessage());
        } catch (IOException ex) {
              System.out.println(ex.getMessage());
        }
        String title = "succes ";
        String message = "password user modifié avec succes";
     
    }
    
    
}
