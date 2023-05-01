/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Planning;
import entities.RendezVous;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.PlanningService;
import services.RendezVousService;
import controllers.FullCalendarView;
import entities.User;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class MedecinDashbordController implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Label username;

    @FXML
    private GridPane rdvContainer;
    @FXML
    private VBox bigContainer;
    @FXML
    private ScrollPane scrollp;
    @FXML
    private TextField tf_recherche;
    @FXML
    private HBox hbox;
    ObservableList<RendezVous> rendezVousList;
    User currentUser;
    PlanningService p;
    FullCalendarView fv;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        p =new PlanningService();
        currentUser = Session.getInstance().getUser();

         afficherRdvs();
            

    }  
    
   private ObservableList<RendezVous> showCards(){
        List<RendezVous> rdvList = new ArrayList<>();
        ObservableList<RendezVous> listRendezVous =  FXCollections.observableArrayList();
        RendezVousService rendezVousService = new RendezVousService();
        try {
            listRendezVous = rendezVousService.showRendezVous(currentUser.getId(),currentUser.getRoles());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             return listRendezVous;
    }
  /* @FXML
    void showPlannings(ActionEvent event) {
    try {
         bigContainer.getChildren().clear();
         bigContainer.getChildren().add(FXMLLoader.load(getClass().getResource("../gui/AfficherPlanning.fxml")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
    
    public void afficherRdvs(){
        rendezVousList = FXCollections.observableArrayList(showCards());
        int column = 1;
        int row=0;

         try {

        for(int i=0;i<rendezVousList.size();i++){
        RendezVousService rdvService = new RendezVousService();
            try {
                if(currentUser.getRoles().equals("[\"ROLE_MEDECIN\"]")){
                    rendezVousList.get(i).setFullName(rdvService.userName(rendezVousList.get(i).getIdPatient()));
                }else  if(currentUser.getRoles().equals("[\"ROLE_PATIENT\"]")){
                    int medecin_id = p.findById(rendezVousList.get(i).getIdPlanning());
                    rendezVousList.get(i).setFullName(rdvService.userName(medecin_id));   
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());

            }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../gui/Card.fxml"));
        VBox cardBox = fxmlLoader.load();
        CardController cardController = fxmlLoader.getController();
        cardController.setData(rendezVousList.get(i));
        rdvContainer.add(cardBox, column++, row);

       
        if(column ==4){
            column=1;
            ++row;
        }
        scrollp.setContent(rdvContainer);
        bigContainer.getChildren().clear();
        bigContainer.getChildren().add(hbox);
        bigContainer.getChildren().add(scrollp);
        
        //rdvContainer.add(cardBox, column++, row);
        //rdvContainer.getChildren().add(cardBox,column row);
        GridPane.setMargin(cardBox,new Insets(10));
          }
        }

        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
   @FXML
   void showRendezVouses(ActionEvent event) {
     
        afficherRdvs();
    }
    @FXML
    void showCalendar(ActionEvent event) {

           bigContainer.getChildren().clear();
           this.fv= new FullCalendarView(LocalDate.now());
           bigContainer.getChildren().add(fv.getView());

    }

}
