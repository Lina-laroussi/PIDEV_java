/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import javafx.geometry.Insets;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.AdminService;
import utils.Session;


public class DashboardAdminController implements Initializable {
    
     @FXML
    private VBox bigContainer;

    @FXML
    private HBox hbox;

    @FXML
    private ScrollPane scrollp;

    @FXML
    private GridPane UserContainer;
    
    @FXML
    private TextField rechercher;

    User userAction =null;
    private ObservableList<User> listUsers =  FXCollections.observableArrayList();
    public List<User> users;
     
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        afficherUsers();
    }
    
    
    private ObservableList<User> showUsers(){
        AdminService adminService = new AdminService();
        try {
            listUsers = adminService.ListUsers();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             return listUsers;
    }

    
    public void afficherUsers(){
        listUsers = FXCollections.observableArrayList(showUsers());
        System.out.println(listUsers);
        int column = 0;
        int row=1;
     try{
        for(int i=0;i<listUsers.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("../gui/Card.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardUserController cardController = fxmlLoader.getController();
                 cardController.setData(listUsers.get(i));
                 if(column ==8){
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
    private void rechercherUsers() {
    try{   
        users = new AdminService().search(rechercher.getText());
        System.out.println(users);
        int column = 0;
        int row=1;
        UserContainer.getChildren().clear();
        for(int i=0;i<users.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("../gui/Card.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardUserController cardController = fxmlLoader.getController();
                 cardController.setData(users.get(i));
                 UserContainer.add(cardBox, column++, row);
                 if(column ==8){
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
    
   
    
    


