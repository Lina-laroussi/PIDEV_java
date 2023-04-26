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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../gui/Card.fxml"));
            VBox cardBox = fxmlLoader.load();
       
            CardController cardController = fxmlLoader.getController();
     
            rdvContainer.getChildren().add(cardBox);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
          afficherRdvs();
            

    }  
    
   private ObservableList<RendezVous> showCards(){
        List<RendezVous> rdvList = new ArrayList<>();
        ObservableList<RendezVous> listRendezVous =  FXCollections.observableArrayList();
        RendezVousService rendezVousService = new RendezVousService();
        try {
            listRendezVous = rendezVousService.showRendezVous();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             return listRendezVous;
    }
   @FXML
    void showPlannings(ActionEvent event) {
    try {
         bigContainer.getChildren().clear();
         bigContainer.getChildren().add(FXMLLoader.load(getClass().getResource("../gui/AfficherPlanning.fxml")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void afficherRdvs(){
           rendezVousList = FXCollections.observableArrayList(showCards());
        int column = 0;
        int row=1;

         try {

        for(int i=0;i<rendezVousList.size();i++){
        RendezVousService rdvService = new RendezVousService();
            try {
             rendezVousList.get(i).setFullName(rdvService.userName(rendezVousList.get(i).getIdPlanning()));   
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());

            }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../gui/Card.fxml"));
        VBox cardBox = fxmlLoader.load();
        CardController cardController = fxmlLoader.getController();
        cardController.setData(rendezVousList.get(i));
        if(column ==6){
            column=0;
            ++row;
        }
        rdvContainer.add(cardBox, column++, row);
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
          //Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Parent root = FXMLLoader.load(getClass().getResource("./gui/MedecinDashbord.fxml"));
            //Scene scene = new Scene(root);
            //AfficherPlanningController controller = loader.getController();

            //primaryStage.setScene(scene);
            ///primaryStage.setScene(new Scene(new FullCalendarView(LocalDate.now()).getView()));
            //primaryStage.setTitle("Medcare");
           // primaryStage.show();
           bigContainer.getChildren().clear();
           bigContainer.getChildren().add(new FullCalendarView(LocalDate.now()).getView());
            


    }

}
