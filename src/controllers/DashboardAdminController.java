/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.AdminService;
import utils.Session;


public class DashboardAdminController implements Initializable {

  
    @FXML
    private TableView<User> UsersTable;
    @FXML
    private TableColumn<User, String> nomCol;
    @FXML
    private TableColumn<User, String> prenomCol;
    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> dateCol;
    
    @FXML
    private TableColumn<User, String> creationCol;

    @FXML
    private TableColumn<User, String> adresseCol;

    @FXML
    private TableColumn<User, String> telCol;

    @FXML
    private TableColumn<User, String> sexeCol;

    @FXML
    private TableColumn<User, String> specialiteCol;
    
    @FXML
    private TableColumn<User, String> roleCol;
    
    @FXML
    private TableColumn<User, Button> editCol;

    @FXML
    private TableColumn<User, Button> deleteCol; 
    
     @FXML
    private TableColumn<User, String> etatCol;

    @FXML
    private TableColumn<User, Button> validateCol;
  
    User userAction =null;
    private ObservableList<User> listUsers =  FXCollections.observableArrayList();
     private ObservableList<String> roles = FXCollections.observableArrayList(
                "Médecin",
                "Patient",
                "Assureur",
                "Pharmacien"
        );
     
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            AdminService adminService = new AdminService();
            listUsers = adminService.ListUsers();   
            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
            creationCol.setCellValueFactory(new PropertyValueFactory<>("date_de_creation"));
            adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
            sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));
            specialiteCol.setCellValueFactory(new PropertyValueFactory<>("specialité"));
            roleCol.setCellValueFactory(new PropertyValueFactory<>("roles"));
            etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
            
          
            
         //valider utilisateur
            validateCol.setCellValueFactory(cellData -> {
            Button Validerbtn = new Button("Valider");
            Validerbtn.setVisible(cellData.getValue().getEtat().equals("non valide"));
            Validerbtn.setStyle(
                    " -fx-border-radius:25px ;"
                    + "-fx-background-color:#08d02d;"
                    + "-fx-text-fill: white; "
                    + "-fx-font-weight: bold;" 
                    
            );
            
            User userAction = cellData.getValue();
            // Action du bouton
            Validerbtn.setOnAction(event -> {
                  try {
                    int id = userAction.getId();
                    String nom = userAction.getNom();
                    String prenom=userAction.getPrenom();
                    User userValidate= new User(id,nom,prenom);
                    adminService.ValidateUser(userValidate); 
                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("utilisateur validé ");
                    alert.showAndWait();   
                    UsersTable.refresh();

                } catch (SQLException ex) {
                     System.out.println(ex);
                }
               
            });

            return new SimpleObjectProperty<>(Validerbtn);
        });
            
            
         //modifier utilisateur
            editCol.setCellValueFactory(cellData -> {
            Button editbtn = new Button("Modifier");
             editbtn.setStyle(
                    " -fx-border-radius:25px ;"
                    + "-fx-background-color:#ffb433;"
                    + "-fx-text-fill: white; "
                    + "-fx-font-weight: bold;" 
                    
            );
            

             
            User userAction = cellData.getValue();
         // Action du bouton
            editbtn.setOnAction(event -> {
                
                try {
                    int id = userAction.getId();
                    String nom = userAction.getNom();
                    String prenom=userAction.getPrenom();
                    String email = userAction.getEmail();
                    String adresse = userAction.getAdresse();
                    Date dateNaissance = userAction.getDate_de_naissance();
                    String tel =userAction.getTel();
                    String sexe = userAction.getSexe();
                    String specialite = userAction.getSpecialité();
                    
                    if(userAction.getRoles().equals("['ROLE_MEDECIN']")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierMedecin.fxml"));
                        Parent parent  = loader.load();
                        ModifierMedecinController controller = loader.getController();
                        User medecinUpdate = new User(id, nom,  prenom, email,  dateNaissance,  sexe,  tel,  adresse,specialite);
                        controller.getUser(medecinUpdate);
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }else{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ModifierUser.fxml"));
                        Parent parent  = loader.load();
                        ModifierUserController controller = loader.getController();
                        User userUpdate = new User(id, nom,  prenom, email,  dateNaissance,  sexe,  tel,  adresse);
                        controller.getUser(userUpdate);
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                    
                                          
                } catch (IOException ex) {
                    System.out.println(ex);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                
              
            });

            return new SimpleObjectProperty<>(editbtn);
        });
            
            
         //supprimer utilisateur
            deleteCol.setCellValueFactory(cellData -> {
            Button deletebtn = new Button("Supprimer");
            deletebtn.setStyle(
                    " -fx-border-radius:25px ;"
                    + "-fx-background-color:#f1422e;"
                    + "-fx-text-fill: white; "
                    + "-fx-font-weight: bold;"
                    
            );
            User userAction = cellData.getValue();
            // Action du bouton
            deletebtn.setOnAction(event -> {
                 try {
                     
                    int id = userAction.getId();
                    String nom = userAction.getNom();
                    String prenom=userAction.getPrenom();
                    User userdelete= new User(id,nom,prenom);
                    adminService.deleteUser(userdelete); 
                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("utilisateur supprimé ");
                    alert.showAndWait();   
                    UsersTable.refresh();

                } catch (SQLException ex) {
                     System.out.println(ex);
                }
                            
            });

            return new SimpleObjectProperty<>(deletebtn);
        });
            
            UsersTable.getItems().setAll(listUsers);
            
        } catch (SQLException ex) {
             System.out.println(ex);
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
    
}    
    
   
    
    


