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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.AdminService;
import utils.Session;


public class MedecinsConnectController implements Initializable {

    @FXML
    private VBox bigContainer;
    @FXML
    private HBox hbox;
    @FXML
    private TextField rechercher;
    @FXML
    private ScrollPane scrollp;
    @FXML
    private GridPane UserContainer;
    
    @FXML
    private MenuButton btn_menu;
    
    private ObservableList<User> listMedecins =  FXCollections.observableArrayList();
    public List<User> medecins;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User currentUser = Session.getInstance().getUser();
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

        afficherMedecins();
    }    
    
    
    
     private ObservableList<User> showMedecins(){
        AdminService adminService = new AdminService();
        try {
            listMedecins = adminService.ListMedecins();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             return listMedecins;
    }

    
    public void afficherMedecins(){
        listMedecins = FXCollections.observableArrayList(showMedecins());
        int column = 1;
        int row=0;
     try{
        for(int i=0;i<listMedecins.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/CardMedecin.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardMedecinController cardController = fxmlLoader.getController();
                 cardController.setData(listMedecins.get(i));
                 UserContainer.add(cardBox, column++, row);
                 if(column ==5){
                    column=1;
                     ++row;
                   }
                scrollp.setContent(UserContainer);
                bigContainer.getChildren().clear();
                bigContainer.getChildren().addAll(hbox);
                bigContainer.getChildren().addAll(scrollp);
                GridPane.setMargin(cardBox,new Insets(10));  
          }
        
        } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
   @FXML
    private void rechercherMedecins() {
    try{   
        medecins = new AdminService().search(rechercher.getText());
        System.out.println(medecins);
        int column = 0;
        int row=1;
        UserContainer.getChildren().clear();
        for(int i=0;i<medecins.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("../gui/CardMedecin.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardUserController cardController = fxmlLoader.getController();
                 cardController.setData(medecins.get(i));
                 UserContainer.add(cardBox, column++, row);
                 if(column == 5){
                     column=0;
                     ++row;
                   }
                scrollp.setContent(UserContainer);
                bigContainer.getChildren().clear();
                bigContainer.getChildren().add(hbox);
                bigContainer.getChildren().add(scrollp);
                GridPane.setMargin(cardBox,new Insets(10));
                
          }
     }  catch(SQLException | IOException e){
         System.out.println(e.getMessage());
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
    private void PharmaciesAction(ActionEvent event) throws IOException {
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
