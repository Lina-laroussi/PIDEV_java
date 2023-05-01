/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Planning;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utils.MyConnection;

/**
 *
 * @author rouai
 */
public class PlanningService {
    private final Connection cnx;
    private PreparedStatement  preparedStatement;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
    public PlanningService() {
           cnx = MyConnection.getInstance().getCnx();
    }
    public void ajouterPlanning(Planning p){
        //String request = "INSERT INTO Planning SET date_debut=?,date_fin=?,heure_debut=?,heure_fin=?,description=?,etat=?,date_de_creation=?,date_de_modification" ;
        String request="INSERT INTO planning(date_debut,date_fin,heure_debut,heure_fin,description,etat,date_de_creation,date_de_modification,medecin_id)"+"VALUES(?,?,?,?,?,?,?,?,?) ";

       try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.setDate(1,new java.sql.Date(p.getDateDebut().getTime()));
            preparedStatement.setDate(2,new java.sql.Date(p.getDateFin().getTime()));
            preparedStatement.setTime(3,new java.sql.Time(p.getHeureDebut().getTime()));
            preparedStatement.setTime(4,new java.sql.Time(p.getHeureFin().getTime()));
            preparedStatement.setString(5,p.getDescription());
            preparedStatement.setString(6,"en cours");
            preparedStatement.setDate(7,new java.sql.Date(new java.util.Date().getTime()));
            preparedStatement.setDate(8,new java.sql.Date(new java.util.Date().getTime()));
            preparedStatement.setInt(9,p.getIdMedecin());

            preparedStatement.executeUpdate();
            System.out.println ("succes"); 
       
       }catch (SQLException e) {
            System.out.println (e);
        }
        

    }
    
    public void editerPlanning (Planning p){
        System.out.println(p.getDescription());
        String request = "UPDATE Planning SET date_debut =\""+new java.sql.Date(p.getDateDebut().getTime())+"\",date_fin=\""+new java.sql.Date(p.getDateFin().getTime())+"\",heure_debut=\""+new java.sql.Time(p.getHeureDebut().getTime())+"\",heure_fin=\""+new java.sql.Time(p.getHeureFin().getTime())+"\",description=\""+p.getDescription()+"\"where id="+p.getIdPlanning()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("planning modifié");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service edit erreur");
    }}
    
    public ObservableList<Planning> showPlanning(int medecin_id) throws SQLException{
        String request = "SELECT * FROM planning where medecin_id="+medecin_id;
        ObservableList<Planning> planningList =  FXCollections.observableArrayList();
        try {
            preparedStatement = cnx.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Test: " + resultSet.toString());
                int idPlanning = resultSet.getInt("id");
                Date dateDebut = resultSet.getDate("date_debut");
                Date dateFin = resultSet.getDate("date_fin");
                Time heureDebut = resultSet.getTime("heure_debut");
                Time heureFin = resultSet.getTime("heure_fin");
                String description = resultSet.getString("description");
                String etat = resultSet.getString("etat");
                Planning planning = new Planning(idPlanning, description,etat,dateDebut,dateFin,heureDebut,heureFin);
                planningList.add(planning);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return planningList;

    }
        
    public void supprimerPlanning (int idPlanning){
          
        String request = "DELETE FROM Planning where id="+idPlanning;  
        try {
            System.out.println(idPlanning);
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Supprimer planning");
            alert1.setContentText("le planning  " + idPlanning + " a été supprimer avec planning");
            Optional<ButtonType> answer1 = alert1.showAndWait();
            System.out.println("Delete Done");
               
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println ("service supression");
               
        }
       
    }
     public void archiverP(Planning p){
        String request = "UPDATE planning SET etat =\""+"archivé"+"\"where id="+p.getIdPlanning()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("planning archivé");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service archive erreur");
        }
    }
    public List<Planning> search(String critere,int medecin_id) throws SQLException {
      return showPlanning(medecin_id).stream()
                .filter(a ->{
                        boolean match = (a.getDateDebut().toString().contains(critere))|| 
                        (a.getDateFin().toString().contains(critere))||
                        (a.getHeureDebut().toString().contains(critere))||
                        (a.getHeureFin().toString().contains(critere))||
                        (a.getDescription().contains(critere));
                        return match;
                })
                       
                .collect(Collectors.toList());
        
}
        public Planning findByDate(LocalDate date,int medecin_id) {
        String request = "SELECT * FROM planning WHERE date_debut <= ? AND date_fin >= ? AND medecin_id = ?";
        Planning planning = new Planning();
        try {
        preparedStatement = cnx.prepareStatement(request);
        preparedStatement.setDate(1, java.sql.Date.valueOf(date));
        preparedStatement.setDate(2, java.sql.Date.valueOf(date));
        preparedStatement.setInt(3, medecin_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            
            planning = new Planning(resultSet.getInt("id"),resultSet.getDate("date_debut"),resultSet.getDate("date_fin"),resultSet.getTime("heure_debut"),resultSet.getTime("heure_fin"),resultSet.getString("etat"));

        }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return planning;

    }
        public int findById(int planning_id) {
        String request = "SELECT medecin_id FROM planning WHERE id="+planning_id;
        int medecin_id=-1;
        try {
        preparedStatement = cnx.prepareStatement(request);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            medecin_id=resultSet.getInt("medecin_id");
        }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return medecin_id;

    }     
}
