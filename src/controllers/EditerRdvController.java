/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import entities.RendezVous;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import services.RendezVousService;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class EditerRdvController implements Initializable {

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
    
    private int rdvId;
    
    private int planningId;
    
    private String etat;
    
    private final Connection cnx;
    
    private PreparedStatement ste;
    
    SimpleDateFormat heureFormatPicker = new SimpleDateFormat("HH:mm");
    
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy"); // Change the output format to match the format accepted by JFoenix DatePicker

    public EditerRdvController() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    
    ObservableList<RendezVous> ListPlanning=FXCollections.observableArrayList();
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inflateUI(RendezVous rdv) {
       rdvId= rdv.getIdRdv();
       planningId = rdv.getIdPlanning();
       etat = rdv.getEtat();
       tf_date.setValue(LocalDate.parse(outputFormat.format(rdv.getDate()), DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Set the DatePicker value using the formatted date
       tf_heure_debut.setValue(LocalTime.parse(heureFormatPicker.format(rdv.getHeureDebut()), DateTimeFormatter.ofPattern("HH:mm")));
       tf_heure_fin.setValue(LocalTime.parse(heureFormatPicker.format(rdv.getHeureFin()), DateTimeFormatter.ofPattern("HH:mm")));
       tf_symptomes.setText(rdv.getSymptomes());
        
    }   

    @FXML
    private void Valider(ActionEvent event) {
        
try{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
        if(tf_date.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une date");
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
        }else if(tf_symptomes.getText().equals("")){
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

                 RendezVous rdv = new RendezVous(rdvId,tf_symptomes.getText(), etat,date,heureDebut,heureFin,planningId);
                RendezVousService rendezVousService = new RendezVousService();
                rendezVousService.editerRdv(rdv);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification rendez vous");
                alert.setHeaderText(null);
                alert.setContentText("rendezVous modifié avec succès");
                alert.showAndWait();
                }catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        
        }}

        catch(RuntimeException ex)
        {
            System.out.println(ex);
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "rendezVous modifié avec succes";
     
     }
    
        }


    

