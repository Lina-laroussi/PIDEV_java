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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.AdminService;


public class MedecinControllerController implements Initializable {

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
    
    
    private ObservableList<User> listMedecins =  FXCollections.observableArrayList();
    public List<User> medecins;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        System.out.println(listMedecins);
        int column = 0;
        int row=1;
     try{
        for(int i=0;i<listMedecins.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("../gui/CardMedecins.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardMedecinsController cardController = fxmlLoader.getController();
                 cardController.setData(listMedecins.get(i));
                 if(column ==3){
                     column=0;
                     ++row;
                   }
                UserContainer.add(cardBox, column++, row);
                scrollp.setContent(UserContainer);
                bigContainer.getChildren().clear();
                bigContainer.getChildren().add(hbox);
                bigContainer.getChildren().add(scrollp);
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
                 fxmlLoader.setLocation(getClass().getResource("../gui/CardMedecins.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardUserController cardController = fxmlLoader.getController();
                 cardController.setData(medecins.get(i));
                 UserContainer.add(cardBox, column++, row);
                 if(column == 3){
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
        
    
}
