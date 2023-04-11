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
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AdminService;
import services.UserService;
import utils.Session;


public class ModifierUserController implements Initializable {

    @FXML
    private TextField nom_user;
    @FXML
    private TextField prenom_user;
    @FXML
    private TextField email_user;
    @FXML
    private DatePicker dateNaissance_user;
    @FXML
    private RadioButton sexe_user_femme;
    @FXML
    private RadioButton sexe_user_homme;
    @FXML
    private TextField tel_user;
    @FXML
    private TextField adresse_user;
    
    @FXML
    private Button button_update;

    @FXML
    private Button button_annuler;
    
    @FXML
    private ToggleGroup group; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*User user=new User();
        getUser(user);     */
    }    

   
    @FXML
    private void updateUser(ActionEvent event) throws Exception {

       try{
      
        if(nom_user.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre nom");
            alert.showAndWait();  
        }else if(prenom_user.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("vous devez entrez votre prenom");
            alert.showAndWait();  
        }else if(email_user.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre email");
            alert.showAndWait();  
        }else if(dateNaissance_user.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre date de naissance");
            alert.showAndWait();  
        }else if(sexe_user_homme.getText().equals("") || sexe_user_femme.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre sexe");
            alert.showAndWait();  
        }else if(tel_user.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre téléphone");
            alert.showAndWait();  
        }else if(adresse_user.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre adresse");
            alert.showAndWait();  
        }
        
        else{     
         String nom = nom_user.getText();
         String prenom = prenom_user.getText();
         String email = email_user.getText();
         Date dateNaissance = java.sql.Date.valueOf(dateNaissance_user.getValue());
         String sexe ;
         if(sexe_user_homme.isSelected()){
             sexe=sexe_user_homme.getText();
         }else{
              sexe=sexe_user_femme.getText();
         }
         String tel = tel_user.getText();
         String adresse = adresse_user.getText();
         
        User user= new User(nom,prenom,email,dateNaissance,sexe,tel,adresse); 
        User currentUser = getUser(user);
        int id =currentUser.getId();
        User user1= new User(id,nom,prenom,email,dateNaissance,sexe,tel,adresse); 
        AdminService Adminservice = new AdminService();
        Adminservice.updateUser(user1);
                
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AJOUT AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("user modifié avec succès");
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

    public User getUser(User currentUser) throws SQLException{
            nom_user.setText(currentUser.getNom());
            prenom_user.setText(currentUser.getPrenom());
            email_user.setText(currentUser.getEmail()); 
            
            
            if(currentUser.getDate_de_naissance()== null){
                dateNaissance_user.setValue(LocalDate.now());
           }else{
               java.util.Date utilDate = new java.util.Date(currentUser.getDate_de_naissance().getTime());
                System.out.println(utilDate);
                try{
                    dateNaissance_user.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                }catch(Exception e){
                    System.out.println(e);
                }
           }
           
           if(currentUser.getAdresse()==null){
                adresse_user.setText("Entrez votre adresse");
           }else{
                adresse_user.setText(currentUser.getAdresse());
           }
           
            if(currentUser.getTel()==null){
                tel_user.setText("Entrez votre tel");
           }else{
                tel_user.setText(currentUser.getTel());
           }
       
           
           sexe_user_homme.setToggleGroup(group);
           sexe_user_femme.setToggleGroup(group);
            if(currentUser.getTel()==null){
               sexe_user_homme.setSelected(true);
            }
            
            if(currentUser.getSexe()==null){
                sexe_user_homme.setSelected(true); 
            }else if(currentUser.getSexe().equals("Homme") ){
                sexe_user_homme.setSelected(true);
            }else {
                sexe_user_homme.setSelected(false);
                sexe_user_femme.setSelected(true);
            }
            
           User userUpdate = new User(currentUser.getId(), currentUser.getNom(),  currentUser.getPrenom(), currentUser.getEmail(),  currentUser.getDate_de_naissance(),  currentUser.getSexe(),  currentUser.getTel(),  currentUser.getAdresse());
           AdminService adminService = new AdminService();
           User currentUser1 = adminService.getUser(userUpdate);
           System.out.println(currentUser1);
        return currentUser1;
    }
    
    

    
}
