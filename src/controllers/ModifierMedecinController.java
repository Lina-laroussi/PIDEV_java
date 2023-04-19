/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.AdminService;


public class ModifierMedecinController implements Initializable {

    @FXML
    private TextField nom_medecin;

    @FXML
    private TextField prenom_medecin;

    @FXML
    private TextField email_medecin;

    @FXML
    private DatePicker dateNaissance_medecin;

    @FXML
    private RadioButton sexe_medecin_femme;

    @FXML
    private RadioButton sexe_medecin_homme;

    @FXML
    private TextField tel_medecin;

    @FXML
    private TextField adresse_medecin;

    @FXML
    private TextField specialite_medecin;

    @FXML
    private Button button_update;
    
    @FXML 
    private ImageView image_medecin;
   
     
    private static final String EMAIL_REGEX = "^[\\w\\d._%+-]+@[\\w\\d.-]+\\.[A-Za-z]{2,}$";
    private static final String TEL_REGEX="\\d{8}";


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   
    public void inflateUI(User u) {
       nom_medecin.setText(u.getNom()); // Set the DatePicker value using the formatted date
       prenom_medecin.setText(u.getPrenom());
       email_medecin.setText(u.getEmail());
       if(u.getDate_de_naissance()== null){
                dateNaissance_medecin.setValue(LocalDate.now());
       }else{
       java.util.Date utilDate = new java.util.Date(u.getDate_de_naissance().getTime());
                System.out.println(utilDate);
                try{
                    dateNaissance_medecin.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                }catch(Exception e){
                    System.out.println(e);
        }
       }
           ToggleGroup toggleGroup = new ToggleGroup();
           sexe_medecin_homme.setToggleGroup(toggleGroup);
           sexe_medecin_femme.setToggleGroup(toggleGroup);
       if(u.getSexe()==null){
                sexe_medecin_homme.setSelected(true);
            }
            else if(u.getSexe().equals("Homme")){
                sexe_medecin_homme.setSelected(true);
            }else{
                sexe_medecin_homme.setSelected(false);
                sexe_medecin_femme.setSelected(true);
        }   
        if(u.getAdresse()==null){
                adresse_medecin.setText("Entrez votre adresse");
           }else{
                adresse_medecin.setText(u.getAdresse());
           }
           
         if(u.getTel()==null){
                tel_medecin.setText("Entrez votre tel");
           }else{
                tel_medecin.setText(u.getTel());
         }
         if(u.getSpecialité()==null){
                specialite_medecin.setText("Entrez votre specialité");
        }else{
                specialite_medecin.setText(u.getSpecialité());
        }
    }
    
    
    @FXML
    public void updateUser(ActionEvent event) throws SQLException {
         try{
      
        if(nom_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez un nom");
            alert.showAndWait();  
        }
        else if(nom_medecin.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("le nom doit comporter au moins 2 caractères");
             alert.showAndWait();   
             
        }else if(prenom_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("vous devez entrez un prenom");
            alert.showAndWait(); 
            
        }else if(prenom_medecin.getText().length()<3){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("le prénom doit comporter au moins 2 caractères");
             alert.showAndWait();   
        }else if(email_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez un email");
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
            alert.setContentText("Vous devez entrez une date de naissance");
            alert.showAndWait();  
        }else if(tel_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le téléphone");
            alert.showAndWait();   
        }else if(!tel_medecin.getText().matches(TEL_REGEX)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous numéro de téléphone n'est pas valide");
            alert.showAndWait();    
        }else if(adresse_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez l'adresse");
            alert.showAndWait();  
        }else if(specialite_medecin.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la specialite");
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
         String specialité = specialite_medecin.getText();
         
         
        User user= new User(nom,prenom,email,dateNaissance,sexe,tel,adresse,specialité); 
        User currentUser = getUser(user);
        int id =currentUser.getId();
        String image = currentUser.getImage();
        
        User user1= new User(id,nom,prenom,email,dateNaissance,sexe,tel,adresse,specialité,image); 
        AdminService Adminservice = new AdminService();
        User userUpdated = Adminservice.updateMedecin(user1);
        System.out.println(user1);        
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
        String message = "utilisateur modifié avec succes";
    }
    
      public User getUser(User currentUser) throws SQLException{
            nom_medecin.setText(currentUser.getNom());
            prenom_medecin.setText(currentUser.getPrenom());
            email_medecin.setText(currentUser.getEmail()); 
            
            
            if(currentUser.getDate_de_naissance()== null){
                dateNaissance_medecin.setValue(LocalDate.now());
           }else{
               java.util.Date utilDate = new java.util.Date(currentUser.getDate_de_naissance().getTime());
                System.out.println(utilDate);
                try{
                    dateNaissance_medecin.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                }catch(Exception e){
                    System.out.println(e);
                }
           }
           
           if(currentUser.getAdresse()==null){
                adresse_medecin.setText("Entrez votre adresse");
           }else{
                adresse_medecin.setText(currentUser.getAdresse());
           }
           
            if(currentUser.getTel()==null){
                tel_medecin.setText("Entrez votre tel");
           }else{
                tel_medecin.setText(currentUser.getTel());
           }
       
            if(currentUser.getSpecialité()==null){
                specialite_medecin.setText("Entrez votre specialité");
            }else{
                specialite_medecin.setText(currentUser.getSpecialité());
            }
           ToggleGroup toggleGroup = new ToggleGroup();
           sexe_medecin_homme.setToggleGroup(toggleGroup);
           sexe_medecin_femme.setToggleGroup(toggleGroup);
            if(currentUser.getTel()==null){
               sexe_medecin_homme.setSelected(true);
            }
            
            if(currentUser.getSexe()==null){
                sexe_medecin_homme.setSelected(true); 
            }else if(currentUser.getSexe().equals("Homme") ){
                sexe_medecin_homme.setSelected(true);
            }else {
                sexe_medecin_homme.setSelected(false);
                sexe_medecin_femme.setSelected(true);
            }
            
           User userUpdate = new User(currentUser.getId(), currentUser.getNom(),  currentUser.getPrenom(), currentUser.getEmail(),  currentUser.getDate_de_naissance(),  currentUser.getSexe(),  currentUser.getTel(),  currentUser.getAdresse(),currentUser.getSpecialité(),currentUser.getImage());
           AdminService adminService = new AdminService();
           User currentUser1 = adminService.getUser(userUpdate);
        return currentUser1;
    }
}
