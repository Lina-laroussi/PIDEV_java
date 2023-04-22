/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
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
    private Button add_photo; 
    
    @FXML
    private Button button_image;
    
    @FXML
    private Circle circleImg;
    
    
    
    
        
    private static final String EMAIL_REGEX = "^[\\w\\d._%+-]+@[\\w\\d.-]+\\.[A-Za-z]{2,}$";
    private static final String TEL_REGEX="\\d{8}";

    private FileChooser fileChooser;
    
     

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
           
            ToggleGroup toggleGroup = new ToggleGroup();
            sexe_medecin_homme.setToggleGroup(toggleGroup);
            sexe_medecin_femme.setToggleGroup(toggleGroup);
            
            if(currentMedecin.getSexe()==null){
                sexe_medecin_homme.setSelected(true);
            }
            else if(currentMedecin.getSexe().equals("Homme")){
                sexe_medecin_homme.setSelected(true);
            }else{
                sexe_medecin_homme.setSelected(false);
                sexe_medecin_femme.setSelected(true);
            }   
            
            Image image = new Image(getClass().getResourceAsStream("../gui/images/pngegg.png")); // Replace with the path to your actual image file
            if(currentMedecin.getImage()== null){
                  circleImg.setFill(new ImagePattern(image));
                  circleImg.setStroke(Color.TRANSPARENT);

            }else{
                String imagePath = "c:/xampp/htdocs/uploads/" + currentMedecin.getImage();
                try {
                    File imageFile = new File(imagePath);
                    FileInputStream fileInputStream = new FileInputStream(imageFile);
                    Image imageMedecin = new Image(fileInputStream);
                    circleImg.setFill(new ImagePattern(imageMedecin));
                    circleImg.setStroke(Color.TRANSPARENT);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                
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
    private void AjouterPhoto() throws IOException {
        
        Stage primaryStage = null;
       // Use FileChooser to choose an image file
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        }
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null) {
            try {
                // Display the chosen image in the ImageView
                Image image = new Image(new FileInputStream(selectedFile));
                circleImg.setFill(new ImagePattern(image));
                 // Copy the image to the destination directory
                String destinationDirectoryPath = "c:/xampp/htdocs/uploads/"; 
                String imageName = selectedFile.getName(); 
                Path source = selectedFile.toPath();
                Path destination = new File(destinationDirectoryPath + imageName).toPath();
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image copied to: " + destinationDirectoryPath + imageName);
                User currentMedecin = Session.getInstance().getUser();
                int id =currentMedecin.getId();
       
                User user= new User(id,imageName);
                UserService userService = new UserService();
                userService.updateUserImage(user);
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("AJOUT AVEC SUCCES");
                 alert.setHeaderText(null);
                 alert.setContentText("image ajoutés avec succées");
                 alert.showAndWait();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    }
    
    
    
    
   
    @FXML
    private void updateMedecin(ActionEvent event) throws Exception {

       try{
      
        if(nom_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nom");
            alert.showAndWait();  
        }else if(nom_medecin.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Votre prenom doit comporter au moins 2 caractères");
             alert.showAndWait();
        }else if(prenom_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("vous devez entrez votre prenom");
            alert.showAndWait();  
        }else if(prenom_medecin.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Votre nom doit comporter au moins 2 caractères");
             alert.showAndWait();
        }
        else if(email_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre email");
            alert.showAndWait();  
            
        }
         else if(!email_medecin.getText().matches(EMAIL_REGEX)){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Email invalide");
             alert.showAndWait();
        }else if(dateNaissance_medecin.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre date de naissance");
            alert.showAndWait();  
        }else if(tel_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre téléphone");
            alert.showAndWait();   
        }else if(!tel_medecin.getText().matches(TEL_REGEX)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous numéro de téléphone n'est pas valide");
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
         
         
        User currentMedecin = Session.getInstance().getUser();
        int id =currentMedecin.getId();
        String image = currentMedecin.getImage();
       
        User user= new User(id,nom,prenom,email,dateNaissance,sexe,tel,adresse,specialite,image);
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
