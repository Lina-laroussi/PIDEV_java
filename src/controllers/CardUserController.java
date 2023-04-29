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
import java.util.List;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import services.AdminService;


public class CardUserController implements Initializable {

    @FXML
    private Circle circleImg;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label etat;
    @FXML
    private Label role;
    @FXML
    private Button btn_edit;
    @FXML
    private Button btn_valider;
    @FXML
    private Button btn_delete;
    @FXML
    private ImageView img_valider;
    

    User user;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
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
        etat.setText(user.getEtat());
        if(user.getEtat().equals("valide") && user.getRoles().equals("[\"ROLE_PATIENT\"]") && user.isIs_blocked()==false){
            btn_valider.setStyle(
                 "-fx-background-color: #f86d6d;"   
            );
            Image imagebtn = new Image(getClass().getResourceAsStream("../gui/images/lock1.png"));
            img_valider.setImage(imagebtn);
            btn_valider.setGraphic(img_valider);            
            etat.setText("Vérifié");
            etat.setTextFill(Color.GREEN);     
        }
        
        else if(user.getEtat().equals("valide") && user.isIs_blocked()==false){
            btn_valider.setStyle(
                 "-fx-background-color: #f86d6d;"   
            );
            Image imagebtn = new Image(getClass().getResourceAsStream("../gui/images/lock1.png"));
            img_valider.setImage(imagebtn);
            btn_valider.setGraphic(img_valider);   
            etat.setText("Valide");
            etat.setTextFill(Color.GREEN);    
            
        }else if(user.getEtat().equals("valide") && user.isIs_blocked()==true){
            
            btn_valider.setStyle(
                 "-fx-background-color: #08d02d;"   
            );
            Image imagebtn = new Image(getClass().getResourceAsStream("../gui/images/unlock.png"));
            img_valider.setImage(imagebtn);
            btn_valider.setGraphic(img_valider);   
            etat.setText("Bloqué");
            etat.setTextFill(Color.RED);  
        
        }else if(user.getEtat().equals("non vérifié"))  {
             etat.setText("Non vérifié");
             etat.setTextFill(Color.RED);
             btn_valider.setVisible(false);
        }else{
             etat.setText("Non valide");
             etat.setTextFill(Color.RED);
        }
        
        if(user.getRoles().equals("[\"ROLE_MEDECIN\"]")){
            role.setText("Médecin");
            role.setTextFill(Color.DARKSLATEBLUE);
        }else if(user.getRoles().equals("[\"ROLE_PATIENT\"]")){
            role.setText("Patient");
            role.setTextFill(Color.GOLD);
        }else if(user.getRoles().equals("[\"ROLE_ASSUREUR\"]")){
            role.setText("Assureur");
            role.setTextFill(Color.RED);
        }else{
            role.setText("Pharmacien");
            role.setTextFill(Color.CORNFLOWERBLUE);
        }
     }
     
     
    @FXML
    void deleteUser(ActionEvent event) throws SQLException, IOException {
        AdminService adminService = new AdminService();
       try{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression ? ");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cet utilisateur");
        alert.showAndWait();
        adminService.deleteUser(user);
        setData(user);
         
       }catch(RuntimeException ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,"erreur",ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    
     @FXML
    void validerUser(ActionEvent event) throws SQLException {
        AdminService adminService = new AdminService();
       try{
        if(user.getEtat().equals("non valide")){  
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation utilisateur");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment valider cette utilisateur ?");
            alert.showAndWait();
            adminService.ValidateUser(user);
            setData(user);
        }else if(user.getEtat().equals("valide") && user.isIs_blocked()==false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bloquer utilisateur");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment bloquer cette utilisateur ?");
            alert.showAndWait();
            adminService.BlockUser(user);
            setData(user);
          
        }else if(user.getEtat().equals("valide") && user.isIs_blocked()==true){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Débloquer utilisateur");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment débloquer cette utilisateur ?");
            alert.showAndWait();
            adminService.UnblockUser(user);
            setData(user);  
        }    
       }catch(RuntimeException ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,"erreur",ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    
     
    @FXML
    void modifierUser(ActionEvent event) throws SQLException, Exception  {
          try {
            if(user.getRoles().equals("[\"ROLE_MEDECIN\"]")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierMedecin.fxml"));
                Parent parent = loader.load();

                ModifierMedecinController controller = (ModifierMedecinController) loader.getController();
                //controller.inflateUI(this.user);
                User medecinUpdate = new User(user.getId(), user.getNom(),  user.getPrenom(), user.getEmail(),  user.getDate_de_naissance(),  user.getSexe(),  user.getTel(),  user.getAdresse(),user.getSpecialité(),user.getImage());
                controller.getUser(medecinUpdate);
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                
             
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierUser.fxml"));
                Parent parent = loader.load();

                ModifierUserController controller = (ModifierUserController) loader.getController();
                //controller.inflateUI(this.user);
                 User userUpdate = new User(user.getId(), user.getNom(),  user.getPrenom(), user.getEmail(),  user.getDate_de_naissance(),  user.getSexe(),  user.getTel(),  user.getAdresse(),user.getImage());
                 controller.getUser(userUpdate);
                  Scene scene = new Scene(parent);
                 Stage stage = new Stage();
                 stage.setScene(scene);
                 stage.show();
                
            }    
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    
    
   
}
