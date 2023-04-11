package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import entities.Planning;
import entities.RendezVous;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import services.RendezVousService;
import utils.MyConnection;

public class AjouterRdvController implements Initializable{

    @FXML
    private Button valider;

    @FXML
    private JFXDatePicker tf_date;

    @FXML
    private JFXTimePicker tf_heure_debut;

    @FXML
    private JFXTimePicker tf_heure_fin;

    @FXML
    private JFXTextArea tf_symptomes;
    private final Connection cnx;
    private PreparedStatement ste;
    
    private int planningId;
    
    public AjouterRdvController() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_date.setValue(LocalDate.now());
        tf_heure_debut.setValue(LocalTime.now());
        tf_heure_fin.setValue(LocalTime.now());
    }  
    public void setPlanningId(Planning p){
        this.planningId = p.getIdPlanning();
    }

    @FXML
    void Valider(ActionEvent event)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
        

     try{
         if(tf_date.getValue().toString() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une date de debut");
            alert.showAndWait();  
        }else if(tf_heure_debut.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une heure de debut");
            alert.showAndWait();  
        }else if(tf_heure_fin.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une heure de fin");
            alert.showAndWait();  
        } else if(tf_symptomes.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez les symptomes");
            alert.showAndWait();  
        }else if(tf_symptomes.getText().length()<5){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("le champs symptomes doit comporter au moins 5 caractères");
            alert.showAndWait();  
        }
        
        else{  
             try {
                 Date date = dateFormat.parse(tf_date.getValue().toString());
                 Date heureDebut = heureFormat.parse(tf_heure_debut.getValue().toString());
                 Date heureFin = heureFormat.parse(tf_heure_fin.getValue().toString());
                 System.out.println("date controller"+date);
                 
                RendezVous rdv = new RendezVous(tf_symptomes.getText(), "en attente",date,heureDebut,heureFin,planningId);
                RendezVousService rendezVousService = new RendezVousService();
                rendezVousService.ajouterRendezVous(rdv);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("AJOUT AVEC SUCCES");
                alert.setHeaderText(null);
                alert.setContentText("Rendez Vous ajouté avec succès");
                alert.showAndWait();

             } catch (ParseException ex) {
                 System.out.println(ex.getMessage());
             }
   

        }}

        catch(RuntimeException ex)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "planning ajouté avec succes";
    }
    @FXML
    private void AfficherPlanningAction(ActionEvent event) throws IOException {

      // Parent parentAfficherPlanning= FXMLLoader.load(getClass().getResource("AfficherPlanning.fxml"));
    //Scene scene = new Scene(parentAfficherPlanning);
    Stage stageEdit = (Stage)((Node)event.getSource()).getScene().getWindow();

    stageEdit.hide();
    }

}
