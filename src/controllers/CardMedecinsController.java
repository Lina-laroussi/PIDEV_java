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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import services.AdminService;
import utils.Session;


public class CardMedecinsController implements Initializable {

    @FXML
    private Circle circleImg;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label adresse;
    @FXML
    private Label spécialité;
    @FXML
    private Button button_rendezVous;

    User user;
    
    User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User currentUser = Session.getInstance().getUser();
        button_rendezVous.setOnAction(event->{
            if(currentUser == null){
                try {
                    Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
                    Scene sceneRegister = new Scene(parentLogin);
                    Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
                     stageRegister .hide();
        
                      stageRegister .setScene(sceneRegister);
                       stageRegister .show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            
        }else{
                try {
                    Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/ChangerPassword.fxml"));
                     Scene sceneRegister = new Scene(parentLogin);
                     Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();

                     stageRegister .hide();

                     stageRegister .setScene(sceneRegister);
                     stageRegister .show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
        });

    }    
    
    
    
     public void setData(User user){
        this.user =user ;
        Image image = new Image(getClass().getResourceAsStream("../gui/images/pngegg.png")); // Replace with the path to your actual image file
        if(user.getImage()== null){
                circleImg.setFill(new ImagePattern(image));
                circleImg.setStroke(Color.TRANSPARENT);
        }else{
            String imagePath = "C:/Users/larou/Desktop/MedCare/PIDEV/public/uploads/utilisateurs/" + user.getImage();
                try {
                    File imageFile = new File(imagePath);
                    FileInputStream fileInputStream = new FileInputStream(imageFile);
                    Image imageUser = new Image(fileInputStream);
                    circleImg.setFill(new ImagePattern(imageUser));
                    circleImg.setStroke(Color.TRANSPARENT);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        }
        username.setText(user.getNom().substring(0, 1).toUpperCase() +user.getNom().substring(1) +" "+ user.getPrenom().substring(0, 1).toUpperCase() +user.getPrenom().substring(1));
        email.setText(user.getEmail());
        if(user.getAdresse()==null){
            adresse.setText("");
        }else{
            adresse.setText(user.getAdresse().substring(0, 1).toUpperCase()+user.getAdresse().substring(1));

        }
        
        if(user.getSpecialité()==null){
            spécialité.setText("");
        }else{
            spécialité.setText(user.getSpecialité().substring(0, 1).toUpperCase()+user.getSpecialité().substring(1));

        }
        
     }
     
     

  
}
