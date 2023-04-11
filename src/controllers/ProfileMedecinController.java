/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;
import utils.Session;


public class ProfileMedecinController implements Initializable {

 
    
    @FXML
    private Label username;
    
    
    @FXML
    private TextField nom_medecin;
     
    @FXML
    private TextField prenom_medecin;
    
    @FXML
    private TextField email_medecin;
    
    @FXML
    private DatePicker dateNaissance_medecin;
    
    @FXML
    private TextField adresse_medecin;
    
    @FXML
    private TextField tel_medecin;
    
    @FXML
    private TextField specialite_medecin;
    
    @FXML 
    private ImageView image_medecin;
    
    @FXML
    private RadioButton sexe_medecin_homme;   
    
    @FXML
    private RadioButton sexe_medecin_femme;    

    @FXML
    private ToggleGroup group; 
    
    @FXML
    private Button add_photo; 
    
    @FXML
    private Circle circleImg;
    
        
    
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
    // Change the output format to match the format accepted by JFoenix DatePicker
    
     

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        User currentMedecin = Session.getInstance().getUser();
        System.out.println(currentMedecin);
            username.setText(currentMedecin.getNom().substring(0, 1).toUpperCase() +currentMedecin.getNom().substring(1) +" "+ currentMedecin.getPrenom().substring(0, 1).toUpperCase() +currentMedecin.getPrenom().substring(1));
            nom_medecin.setText(currentMedecin.getNom());
            prenom_medecin.setText(currentMedecin.getPrenom());
            email_medecin.setText(currentMedecin.getEmail()); 
            
            
            if(currentMedecin.getDate_de_naissance()== null){
                dateNaissance_medecin.setValue(LocalDate.now());
           }else{
               java.util.Date utilDate = new java.util.Date(currentMedecin.getDate_de_naissance().getTime());
                System.out.println(utilDate);
                try{
                    dateNaissance_medecin.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                }catch(Exception e){
                    System.out.println(e);
                }
           }
           
           if(currentMedecin.getAdresse()==null){
                adresse_medecin.setText("Entrez votre adresse");
           }else{
                adresse_medecin.setText(currentMedecin.getAdresse());
           }
           
            if(currentMedecin.getTel()==null){
                tel_medecin.setText("Entrez votre tel");
           }else{
                tel_medecin.setText(currentMedecin.getTel());
           }
            
            if(currentMedecin.getSpecialité()==null){
                specialite_medecin.setText("Entrez votre specialité");
            }else{
                specialite_medecin.setText(currentMedecin.getSpecialité());
           }
           
            sexe_medecin_homme.setToggleGroup(group);
            sexe_medecin_femme.setToggleGroup(group);
            
            if(currentMedecin.getSexe()==null){
                sexe_medecin_homme.setSelected(true);
            }
            else if(currentMedecin.getSexe().equals("Homme")){
                sexe_medecin_homme.setSelected(true);
            }else{
                sexe_medecin_homme.setSelected(false);
                sexe_medecin_femme.setSelected(true);
            }   
            
            Image image = new Image(getClass().getResourceAsStream("../gui/user1.png")); // Replace with the path to your actual image file
            if(currentMedecin.getImage()== null){
                //image_medecin.setImage(image);
                circleImg.setFill(new ImagePattern(image));

            }else{
                byte[] imageData = currentMedecin.getImage().getBytes(); // Replace "image" with the actual column name that stores the image data

                // Convert the image data to an InputStream
                InputStream inputStream = new ByteArrayInputStream(imageData);
                // Create an Image object from the InputStream
                Image imageMedecin = new Image(inputStream);  
                System.out.println(imageMedecin);
                image_medecin.setImage(imageMedecin);
            }
            
          
    }    
    
    
      
      @FXML
    private void deconnectAction(ActionEvent event) throws IOException {
        
            
            Session.getInstance().clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("deconnexion réussie ");
            alert.showAndWait();   
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
    
    
    
   
    @FXML
    private void updateMedecin(ActionEvent event) throws Exception {

       try{
      
        if(nom_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nom");
            alert.showAndWait();  
        }else if(prenom_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("vous devez entrez votre prenom");
            alert.showAndWait();  
        }else if(email_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre email");
            alert.showAndWait();  
        }else if(dateNaissance_medecin.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre date de naissance");
            alert.showAndWait();  
        }else if(sexe_medecin_homme.getText().equals("") || sexe_medecin_femme.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre sexe");
            alert.showAndWait();  
        }else if(tel_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre téléphone");
            alert.showAndWait();  
        }else if(adresse_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre adresse");
            alert.showAndWait();  
        }else if(specialite_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre spécialité");
            alert.showAndWait();  
        }
        
        else{    
            
            
         String nom = nom_medecin.getText();
         String prenom = prenom_medecin.getText();
         String email = email_medecin.getText();
         Date dateNaissance = java.sql.Date.valueOf(dateNaissance_medecin.getValue());
         
         String sexe ;
         if(sexe_medecin_homme.isSelected()){
             sexe=sexe_medecin_homme.getText();
         }else{
              sexe=sexe_medecin_femme.getText();
         }
         String tel = tel_medecin.getText();
         String adresse = adresse_medecin.getText();
         String specialite=specialite_medecin.getText();
         String image =image_medecin.getImage().toString();
        
        User currentMedecin = Session.getInstance().getUser();
        int id =currentMedecin.getId();
      
        User user= new User(id,nom,prenom,email,dateNaissance,sexe,tel,adresse,specialite);
        UserService userService = new UserService();
        userService.updateMedecin(user);
        
        System.out.println(user);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AJOUT AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("medecin modifié avec succès");
        alert.showAndWait();
        
        }}

        catch(RuntimeException ex)
        {
            System.out.println(ex);
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "planning modifié avec succes";
     
    }
    
   
     
    
}
