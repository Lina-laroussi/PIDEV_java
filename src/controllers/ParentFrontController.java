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
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;
import utils.Session;


public class ParentFrontController implements Initializable {

    @FXML
    private Button button_deconnect;
    @FXML
    private Circle circleImg;
    @FXML
    private Button button_image;
    @FXML
    private Label username;
    @FXML
    private Button button_password;
    @FXML
    private Button button_deconnect1;
    @FXML
    private Button button_profile;
    
    @FXML
    private Button button_planning;

    @FXML
    private Button button_rendezVous;

    @FXML
    private Button button_cal;

    @FXML
    private Button button_fiche;

    @FXML
    private Button button_consul;

    @FXML
    private Button butt_ord;
    
    @FXML
    private ImageView icon_deconnect;
       
    @FXML
    private ImageView icon_password;   
    
    @FXML
    private Button button_accueil;
    
    @FXML
    private Button button_medecins;
    
    @FXML
    private ImageView icon_profile;
    @FXML
    private ImageView icon_planning;
    @FXML
    private ImageView icon_rendez;
    @FXML
    private ImageView icon_cal;
    @FXML
    private ImageView icon_fiche;
    @FXML
    private ImageView icon_consul;
    @FXML
    private ImageView icon_ord;
    
    @FXML
    private MenuButton btn_menu;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User currentUser = Session.getInstance().getUser();
        System.out.println(currentUser);
        btn_menu.setText(currentUser.getNom().substring(0, 1).toUpperCase() +currentUser.getNom().substring(1) +" "+ currentUser.getPrenom().substring(0, 1).toUpperCase() +currentUser.getPrenom().substring(1));
        
           MenuItem deconnect = btn_menu.getItems().get(1);
         
           deconnect.setOnAction(event->{
                  Session.getInstance().clear();
                  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                  alert.setHeaderText(null);
                  alert.setContentText("deconnexion réussie ");
                  alert.showAndWait();   
                  Parent parentLogin;
            try {
                parentLogin = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
                Scene sceneRegister = new Scene(parentLogin);
                  Stage stageRegister = (Stage) btn_menu.getScene().getWindow();

                  stageRegister .hide();

                  stageRegister .setScene(sceneRegister);
                  stageRegister .show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
                  
           });
           
            MenuItem profile = btn_menu.getItems().get(0);
             profile.setOnAction(event->{
             if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
                 try {
                     Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/ProfileMedecin.fxml"));
                     Scene sceneRegister = new Scene(parentLogin);
                     Stage stageRegister = (Stage) btn_menu.getScene().getWindow();

                     stageRegister .hide();

                     stageRegister .setScene(sceneRegister);
                     stageRegister .show();
                 } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                 }
                        
        }else{
                 try {
                     Parent parentLogin = FXMLLoader.load(getClass().getResource("../gui/ProfileUser.fxml"));
                     Scene sceneRegister = new Scene(parentLogin);
                    Stage stageRegister = (Stage) btn_menu.getScene().getWindow();

                    stageRegister .hide();

                    stageRegister .setScene(sceneRegister);
                    stageRegister .show();
                 } catch (IOException ex) {
                     System.out.println(ex.getMessage()); 
                 }       
        }    
                  
           });
           
       
        username.setText(currentUser.getNom().substring(0, 1).toUpperCase() +currentUser.getNom().substring(1) +" "+ currentUser.getPrenom().substring(0, 1).toUpperCase() +currentUser.getPrenom().substring(1));
           
         Image image = new Image(getClass().getResourceAsStream("../gui/images/pngegg.png")); // Replace with the path to your actual image file
            if(currentUser.getImage()== null){
                circleImg.setFill(new ImagePattern(image));
                circleImg.setStroke(Color.TRANSPARENT);

            }else{
                String imagePath = "C:/Users/larou/Desktop/MedCare/PIDEV/public/uploads/utilisateurs/" + currentUser.getImage();
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
            
        Image Profile = new Image(getClass().getResourceAsStream("../gui/images/iconUser.png")); // Replace with the path to your actual image file
        Image Planning = new Image(getClass().getResourceAsStream("../gui/images/plan.png")); // Replace with the path to your actual image file
        Image RendezVous = new Image(getClass().getResourceAsStream("../gui/images/rendezVous.png")); // Replace with the path to your actual image file
        Image Cal = new Image(getClass().getResourceAsStream("../gui/images/calendrier.png")); // Replace with the path to your actual image file
        Image Consul = new Image(getClass().getResourceAsStream("../gui/images/consultation.png")); // Replace with the path to your actual image file
        Image Fiche = new Image(getClass().getResourceAsStream("../gui/images/fiche-de-taches.png")); // Replace with the path to your actual image file
        Image Ord = new Image(getClass().getResourceAsStream("../gui/images/ordonnance.png")); // Replace with the path to your actual image file
        Image deconnect1 = new Image(getClass().getResourceAsStream("../gui/images/se-deconnecter.png")); // Replace with the path to your actual image file    
        Image pharmacie = new Image(getClass().getResourceAsStream("../gui/images/pharmacie1.png")); // Replace with the path to your actual image file       
        Image Facture = new Image(getClass().getResourceAsStream("../gui/images/bill.png")); // Replace with the path to your actual image file       
        Image Stat = new Image(getClass().getResourceAsStream("../gui/images/stats.png")); // Replace with the path to your actual image file       
        Image Rembour = new Image(getClass().getResourceAsStream("../gui/images/refund.png")); // Replace with the path to your actual image file       

    
      if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){   
          icon_profile.setImage(Profile);
          icon_planning.setImage(Planning);
          icon_rendez.setImage(RendezVous);
          icon_cal.setImage(Cal);
          button_fiche.setText("Statistiques");
          icon_fiche.setImage(Stat);
          icon_consul.setImage(Consul);
          butt_ord.setText("Se déconnecter");
          icon_ord.setImage(deconnect1);
          icon_deconnect.setVisible(false);
          button_deconnect1.setVisible(false);
          
       }else  if(currentUser.getRoles().equals("[\"ROLE_PATIENT\"]")){
        Image imageRv = new Image(getClass().getResourceAsStream("../gui/images/rendezVous.png")); // Replace with the path to your actual image file

          icon_profile.setImage(Profile);
          button_planning.setText("Rendez-vous");
          icon_planning.setImage(RendezVous);
          button_rendezVous.setText("Fiche médicale");
          icon_rendez.setImage(Fiche);
          button_cal.setText("Consultations");
          icon_cal.setImage(Consul);
          button_fiche.setText("Ordonnances");
          icon_fiche.setImage(Ord);
          button_consul.setText("Se déconnecter");
          icon_consul.setImage(deconnect1);
          butt_ord.setVisible(false);
          button_deconnect1.setVisible(false);  
          icon_deconnect.setVisible(false);
          icon_ord.setVisible(false);
          
       }else if(currentUser.getRoles().equals("[\"ROLE_ASSUREUR\"]")){
           icon_profile.setImage(Profile);       
           button_planning.setText("Fiche Assurance");
           icon_planning.setImage(Fiche);
           button_rendezVous.setText("Remboursement");
           icon_rendez.setImage(Rembour);
           button_cal.setText("Statistique");
           icon_cal.setImage(Stat);
           button_fiche.setText("Se déconnecter");
           icon_fiche.setImage(deconnect1);
           button_consul.setVisible(false);
           butt_ord.setVisible(false);
           button_password.setVisible(false);
           button_deconnect1.setVisible(false);  
           icon_password.setVisible(false);
           icon_deconnect.setVisible(false);
           icon_ord.setVisible(false);
           
       }else if(currentUser.getRoles().equals("[\"ROLE_PHARMACIEN\"]")){
           icon_profile.setImage(Profile);       
           button_planning.setText("Pharmacies");
           icon_planning.setImage(pharmacie);
           button_rendezVous.setText("Factures");
           icon_rendez.setImage(Facture);
           button_cal.setText("Statistique");
           icon_cal.setImage(Stat);
           button_fiche.setText("Se déconnecter");
           icon_fiche.setImage(deconnect1);
           button_consul.setVisible(false);
           butt_ord.setVisible(false);
           button_deconnect1.setVisible(false);  
           icon_deconnect.setVisible(false);
           icon_ord.setVisible(false);
           icon_consul.setVisible(false) ;
       }  
    }    

    
    private FileChooser fileChooser;
   
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
                String destinationDirectoryPath = "C:/Users/larou/Desktop/MedCare/PIDEV/public/uploads/utilisateurs/"; 
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
    private void updatePassword(ActionEvent event) throws IOException {
        
  
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/ChangerPassword.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
     @FXML
    private void Profile(ActionEvent event) throws IOException {
        User currentUser = Session.getInstance().getUser();    
        
        if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/ProfileMedecin.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
        }else{
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/ProfileUser.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
        }   
        
    }
    
    
     @FXML
    private void Planning(ActionEvent event) {
        User currentUser = Session.getInstance().getUser();    
      try{ 
        if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/AfficherPlanning.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
        }else if(currentUser.getRoles().equals("[\"ROLE_PATIENT\"]")){
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/MedecinDashbord.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
        }else if(currentUser.getRoles().equals("[\"ROLE_PHARMACIEN\"]")){
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/AfficherPharmacie.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister.hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
        }   
      }catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    
    
     @FXML
    private void RendezVous(ActionEvent event) throws IOException {
        User currentUser = Session.getInstance().getUser();    
        
        if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/MedecinDashbord.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
            
        }else if(currentUser.getRoles().equals("[\"ROLE_PHARMACIEN\"]")){
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/AfficherFacture.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister.hide();
        
            stageRegister.setScene(sceneRegister);
            stageRegister.show();
    }
    }
    
    
     @FXML
    private void Calendrier(ActionEvent event) throws IOException {
        User currentUser = Session.getInstance().getUser();    
        
        if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/MedecinDashbord.fxml"));
            Parent parentLogin = loader.load();
 
            MedecinDashbordController controller = (MedecinDashbordController) loader.getController();
            controller.showCalendar(event);
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
        } 
    }
    
    
    
     @FXML
    private void Fiche(ActionEvent event) throws IOException {
        User currentUser = Session.getInstance().getUser();    
        
        if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/DashbordMedecin.fxml"));
            Parent parentLogin = loader.load();
 
            DashbordMedecinController controller = (DashbordMedecinController) loader.getController();
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageRegister .hide();
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
        }else if(currentUser.getRoles().equals("[\"ROLE_ASSUREUR\"]") && currentUser.getRoles().equals("[\"ROLE_PHARMACIEN\"]") ){
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
    }
    
      @FXML
    private void Consultations(ActionEvent event) throws IOException {
        User currentUser = Session.getInstance().getUser();    
        
        if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AfficherConsultation.fxml"));
            Parent parentLogin = loader.load();
 
            AfficherConsultationController controller = (AfficherConsultationController) loader.getController();
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageRegister .hide();
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
        } else if(currentUser.getRoles().equals("[\"ROLE_PATIENT\"]")){
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
    }
    
    
       @FXML
    private void Ordonnances(ActionEvent event) throws IOException {
        User currentUser = Session.getInstance().getUser();    
        
        if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
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
    void medecinsAction(ActionEvent event) throws IOException {
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/MedecinsConnect.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }

    @FXML
    void PharmaciesAction(ActionEvent event) throws IOException {
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/PharmacieConnect.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
    
        @FXML
    private void DoctoshopAction(ActionEvent event) throws IOException {
            Parent parentLogin= FXMLLoader.load(getClass().getResource("../gui/DoctoshopConnect.fxml"));
            Scene sceneRegister = new Scene(parentLogin);
            Stage stageRegister = (Stage)((Node)event.getSource()).getScene().getWindow();
       
            stageRegister .hide();
        
            stageRegister .setScene(sceneRegister);
            stageRegister .show();
    }
}