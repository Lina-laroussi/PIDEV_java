/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.RendezVous;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnection;

/**
 *
 * @author rouai
 */
public class RendezVousService {

    private final Connection cnx;
    private PreparedStatement  preparedStatement;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
    public RendezVousService() {
           cnx = MyConnection.getInstance().getCnx();
    }
    public void ajouterRendezVous(RendezVous rdv){
       String request="INSERT INTO rendez_vous(date,heure_debut,heure_fin,symptomes,etat,date_de_creation,planning_id,patient_id)"+"VALUES(?,?,?,?,?,?,?,?) ";
       try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.setDate(1,new java.sql.Date(rdv.getDate().getTime()));
            preparedStatement.setTime(2,new java.sql.Time(rdv.getHeureDebut().getTime()));
            preparedStatement.setTime(3,new java.sql.Time(rdv.getHeureFin().getTime()));
            preparedStatement.setString(4,rdv.getSymptomes());
            preparedStatement.setString(5,"en attente");
            preparedStatement.setDate(6,new java.sql.Date(new java.util.Date().getTime()));
            preparedStatement.setInt(7,rdv.getIdPlanning());
            preparedStatement.setInt(8,rdv.getIdPatient());
            preparedStatement.executeUpdate();
            System.out.println ("succes"); 
       
       }catch (SQLException e) {
            System.out.println (e.getMessage());
        }
        

    }
    
    public void editerRdv(RendezVous rdv){
        String request = "UPDATE rendez_vous SET date =\""+new java.sql.Date(rdv.getDate().getTime())+"\",heure_debut=\""+new java.sql.Time(rdv.getHeureDebut().getTime())+"\",heure_fin=\""+new java.sql.Time(rdv.getHeureFin().getTime())+"\",symptomes=\""+rdv.getSymptomes()+"\"where id="+rdv.getIdRdv()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("rendez Vous modifié");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service edit erreur");
        }
    }
    
    public ObservableList<RendezVous> showRendezVous(int user_id,String user_role) throws SQLException{
        String request = "SELECT * FROM rendez_vous";
        if(user_role.equals("[\"ROLE_MEDECIN\"]")){
            request = "SELECT rv.* " +
                       "FROM rendez_vous rv " +
                       "JOIN planning p ON rv.planning_id = p.id " +
                       "WHERE p.medecin_id ="+user_id;
        }
        if(user_role.equals("[\"ROLE_PATIENT\"]")){
            request = "SELECT * FROM rendez_vous where patient_id="+user_id;
        }
        ObservableList<RendezVous> rendezVousList =  FXCollections.observableArrayList();
        try {
            preparedStatement = cnx.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                System.out.println("Test: " + resultSet.toString());
                int id = resultSet.getInt("id");
                int idPlanning = resultSet.getInt("planning_id");
               // String request2 = "SELECT * FROM user where id="+idPlanning ;
                String etat = resultSet.getString("etat");
              
                Date date = resultSet.getDate("date");
                Time heureDebut = resultSet.getTime("heure_debut");
                Time heureFin = resultSet.getTime("heure_fin");
                String symptomes = resultSet.getString("symptomes");
                int patient_id = resultSet.getInt("patient_id");
                RendezVous rendez_vous = new RendezVous(id, symptomes,etat,date,heureDebut,heureFin,idPlanning);
                rendez_vous.setIdPatient(patient_id);
                rendezVousList.add(rendez_vous);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return rendezVousList;

    }    
         public String userName(int user_id)throws SQLException{
             
             String request = "SELECT nom,prenom FROM user where id="+user_id;
             String fullName="";
        try {
            preparedStatement = cnx.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                System.out.println("Test: " + resultSet.toString());
                fullName = resultSet.getString("nom")+" "+resultSet.getString("prenom");

            }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return fullName;
    }
         
    public void confirmerRdv (RendezVous rdv){
        String request = "UPDATE rendez_vous SET etat =\""+"confirmé"+"\"where id="+rdv.getIdRdv()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("rendez Vous confirmé");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service confim erreur");
        }
    }
    
        public void AnnulerRdv (RendezVous rdv){
        String request = "UPDATE rendez_vous SET etat =\""+"annulé"+"\"where id="+rdv.getIdRdv()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("rendez Vous annulé");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service annule erreur");
        }
    }
    public void archiverRdv (RendezVous rdv){
        String request = "UPDATE rendez_vous SET etat =\""+"archivé"+"\"where id="+rdv.getIdRdv()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("rendez Vous archivé");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service archive erreur");
        }
    }
    public RendezVous findRdvById(int rdvId) throws SQLException{
        String request = "SELECT * FROM rendez_vous where id="+rdvId+"";
        RendezVous rdv = null;
         try {
         preparedStatement = cnx.prepareStatement(request);
         ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            
            System.out.println("resultSet"+resultSet.getInt("id"));
            rdv = new RendezVous(rdvId,resultSet.getString("symptomes"),resultSet.getString("etat"),resultSet.getDate("date"),resultSet.getTime("heure_debut"),resultSet.getTime("heure_fin"),resultSet.getInt("planning_id"));

        }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
         return rdv;
    }
    
    public List<RendezVous> findByDate(LocalDate date) throws SQLException{
        String request = "SELECT * FROM rendez_vous WHERE date='" + java.sql.Date.valueOf(date) + "'ORDER BY heure_debut";
        List<RendezVous> rendezVousList =new ArrayList<>();
        try {
            preparedStatement = cnx.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();                    
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idPlanning = resultSet.getInt("planning_id");
                String etat = resultSet.getString("etat");
                Date dateRdv = resultSet.getDate("date");
                Time heureDebut = resultSet.getTime("heure_debut");
                Time heureFin = resultSet.getTime("heure_fin");
                String symptomes = resultSet.getString("symptomes");
                RendezVous rendez_vous = new RendezVous(id, symptomes,etat,dateRdv,heureDebut,heureFin,idPlanning);
                rendezVousList.add(rendez_vous);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return rendezVousList;

    }

}
