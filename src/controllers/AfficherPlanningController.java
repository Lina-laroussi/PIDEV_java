/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Planning;
import entities.User;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.PlanningService;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class AfficherPlanningController implements Initializable {
    @FXML
    private TableView<Planning> table_planning;
    
  
    @FXML
    private TableColumn<Planning, Date> date_debut;    
    @FXML
    private TableColumn<Planning, Date> date_fin;    
    @FXML
    private TableColumn<Planning, Date> heure_debut;    
    @FXML
    private TableColumn<Planning, Date> heure_fin;    
    @FXML
    private TableColumn<Planning, String> etat;   
    @FXML
    private TableColumn<Planning, String> description;    
    @FXML
    private TextField tf_rechercher_planning;

    User currentUser;
        /**
     * Initializes the controller class.
     */
    
    ObservableList<Planning> listPlanning =  FXCollections.observableArrayList();
    PlanningService planningService = new PlanningService();
    Planning p = new Planning();
    public List<Planning> plannings;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       currentUser = Session.getInstance().getUser();

        try {
            
            
        listPlanning = planningService.showPlanning(currentUser.getId());
        date_debut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        heure_debut.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        heure_fin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        table_planning.getItems().setAll(listPlanning);
//           
    }   catch (SQLException ex) { 
            System.out.println(ex);
        } 
    
    }    
    
       @FXML
    private void editerPlanning(ActionEvent event) throws IOException {
        Planning selectedForEdit = table_planning.getSelectionModel().getSelectedItem();
        
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EditerPlanning.fxml"));
            Parent parent = loader.load();

            EditerPlanningController controller = (EditerPlanningController) loader.getController();
            controller.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Editer plannning");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((e) -> {
                try {
                    handleRefresh(new ActionEvent());
                    loadData();
                   
                    
                }catch (SQLException ex) {
                    System.out.println(ex);
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            });
            
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    } 
    @FXML
     private void supprimerPlanning(ActionEvent event) throws IOException, SQLException {
         System.out.println("btn clicked");
        Planning selectedForDelete = table_planning.getSelectionModel().getSelectedItem();
        PlanningService planningService= new PlanningService();
        System.out.println("selected planning id"+selectedForDelete.getIdPlanning());
        planningService.supprimerPlanning(selectedForDelete.getIdPlanning());
        loadData();
        handleRefresh(new ActionEvent());
        loadData();
        String title = "succes delet ";
        String message = "planning supprimé avec succes";
        //TrayNotification tray = new TrayNotification();
        //tray.setTitle(title);
       // tray.setMessage(message);
        //tray.setNotificationType(NotificationType.SUCCESS);
       // tray.showAndWait();
    }


    @FXML
    private void rechercherPlanning() {
        System.out.println("");
        try {
            plannings = new PlanningService().search(tf_rechercher_planning.getText(),currentUser.getId());
            listPlanning.clear();
            table_planning.getItems().setAll(plannings);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    //load list
    
       private void loadData() throws SQLException {
        listPlanning.clear();
         try {
            
        listPlanning = planningService.showPlanning(currentUser.getId());
        date_debut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        heure_debut.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        heure_fin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
            
        table_planning.getItems().setAll(listPlanning);
//           
    }   catch (SQLException ex) { 
             System.out.println(ex);
        }
    }
         private void handleRefresh(ActionEvent event) throws SQLException {
        loadData();
        
    }
         
    @FXML
    private void ajouterPlanningAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterPlanning.fxml"));
        Parent parent = loader.load();
        AjouterPlanningController controller = (AjouterPlanningController) loader.getController();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Ajouter plannning");
        stage.setScene(new Scene(parent));
        stage.show();
                 stage.setOnHiding((e) -> {
                try {
                    handleRefresh(new ActionEvent());
                    loadData();
                   
                    
                }catch (SQLException ex) {
                    System.out.println(ex);
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            });

    }

    @FXML
    void ajouterRdv(ActionEvent event) throws IOException {
        Planning selectedP = table_planning.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterRdv.fxml"));
        Parent parent = loader.load();
        AjouterRdvController controller = (AjouterRdvController) loader.getController();
        controller.setPlanningId(selectedP);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Ajouter rendez vous");
        stage.setScene(new Scene(parent));
        stage.show();
                 stage.setOnHiding((e) -> {
                try {
                    handleRefresh(new ActionEvent());
                    loadData();
                   
                    
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            });
    }
    @FXML
    void archiverP(ActionEvent event) {
        PlanningService pService = new PlanningService(); 
        Planning selectedForArchive = table_planning.getSelectionModel().getSelectedItem();
        try{
        planningService.archiverP(selectedForArchive);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("archive planning AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("archive planning avec succès");
        alert.showAndWait();
        }catch(RuntimeException ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,"erreur",ButtonType.CLOSE);
            alert.showAndWait();
     }
        
    }
}
