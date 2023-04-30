/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.BCrypt;
import utils.MyConnection;


public class AdminService {
     private final Connection cnx;
    
    public AdminService() {
        cnx = MyConnection.getInstance().getCnx();
    }
    
    public ObservableList<User> ListUsers() throws SQLException {
        String request = "SELECT id,nom,prenom,email,password,date_de_naissance,sexe,adresse,num_tel,specialite,etat,roles,date_de_creation,image,is_blocked,reset_token FROM user WHERE (NOT(roles LIKE '%ROLE_ADMIN%') AND etat != 'deleted')";
        ObservableList<User> UserList =  FXCollections.observableArrayList();
        try {
            PreparedStatement st2 = cnx.prepareStatement(request);
            ResultSet resultSet = st2.executeQuery();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Date dateNaissance = resultSet.getDate("date_de_naissance");
                String sexe = resultSet.getString("sexe");
                String adresse = resultSet.getString("adresse");
                String tel = resultSet.getString("num_tel");
                String specialite = resultSet.getString("specialite");
                String etat = resultSet.getString("etat");
                String roles = resultSet.getString("roles");
                Date date_de_creation = resultSet.getDate("date_de_creation");  
                String image= resultSet.getString("image");
                Boolean is_blocked=resultSet.getBoolean("is_blocked");
                String reset_token = resultSet.getString("reset_token");
                User user = new User(id,  nom,  prenom,  email,  password,  etat,  dateNaissance,  roles, adresse, sexe, tel, specialite, date_de_creation,image,is_blocked,reset_token);               
                UserList.add(user);
            }
             
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return UserList;

    }
    
    
      public ObservableList<User> ListMedecins() throws SQLException {
        String request = "SELECT id,nom,prenom,email,password,date_de_naissance,sexe,adresse,num_tel,specialite,etat,roles,date_de_creation,image,is_blocked,reset_token FROM user WHERE roles LIKE '%ROLE_MEDECIN%' AND etat != 'deleted' AND etat!='non valide' AND is_blocked != TRUE";
        ObservableList<User> MedecinList =  FXCollections.observableArrayList();
        try {
            PreparedStatement st2 = cnx.prepareStatement(request);
            ResultSet resultSet = st2.executeQuery();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Date dateNaissance = resultSet.getDate("date_de_naissance");
                String sexe = resultSet.getString("sexe");
                String adresse = resultSet.getString("adresse");
                String tel = resultSet.getString("num_tel");
                String specialite = resultSet.getString("specialite");
                String etat = resultSet.getString("etat");
                String roles = resultSet.getString("roles");
                Date date_de_creation = resultSet.getDate("date_de_creation");  
                String image= resultSet.getString("image");
                Boolean is_blocked=resultSet.getBoolean("is_blocked");
                String reset_token = resultSet.getString("reset_token");
                User user = new User(id,  nom,  prenom,  email,  password,  etat,  dateNaissance,  roles, adresse, sexe, tel, specialite, date_de_creation,image,is_blocked,reset_token);               
                MedecinList.add(user);
            }
             
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return MedecinList;

    }
    
    
    public void deleteUser(User user) throws SQLException   {
       String request = "UPDATE user SET etat='deleted' WHERE id=" + user.getId()+"" ;
        try{
           PreparedStatement st = cnx.prepareStatement(request);    
            st.executeUpdate();
            System.out.println("user deleted");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }     
    }
    
    
   
    
     public void ValidateUser(User user) throws SQLException   {
       String request = "UPDATE user SET etat='valide' WHERE id=" + user.getId()+"" ;
        try{
           PreparedStatement st = cnx.prepareStatement(request);    
            st.executeUpdate();
            System.out.println("user validated");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }     
    }
     
     
    public void updateCodeUser(User user) throws SQLException {
        String request = "UPDATE user SET reset_token =\"" + user.getReset_token() + "\" WHERE email='" + user.getEmail() + "'";
        try{
           PreparedStatement st = cnx.prepareStatement(request);  
            st.executeUpdate();
            System.out.println("user updated");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }
    }
    
     public User getUserByCode(String userCode) throws SQLException {
        String request = "SELECT id,nom,prenom,email,password,date_de_naissance,sexe,adresse,num_tel,specialite,etat,roles,date_de_creation,image,is_blocked,reset_token FROM user WHERE reset_token= ?" ;
        User  user1 =  new User();
        try {
            PreparedStatement st2 = cnx.prepareStatement(request);
            st2.setString(1,userCode);
            ResultSet resultSet = st2.executeQuery();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Date dateNaissance = resultSet.getDate("date_de_naissance");
                String sexe = resultSet.getString("sexe");
                String adresse = resultSet.getString("adresse");
                String tel = resultSet.getString("num_tel");
                String specialite = resultSet.getString("specialite");
                String etat = resultSet.getString("etat");
                String roles = resultSet.getString("roles");
                Date date_de_creation = resultSet.getDate("date_de_creation");  
                String image= resultSet.getString("image");
                Boolean is_blocked=resultSet.getBoolean("is_blocked");
                String reset_token = resultSet.getString("reset_token");
                user1 = new User(id,  nom,  prenom,  email,  password,  etat,  dateNaissance,  roles, adresse, sexe, tel, specialite, date_de_creation,image,is_blocked,reset_token);
            }
             
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return user1;

    }
    
        
     public void BlockUser(User user) throws SQLException   {
       String request = "UPDATE user SET is_blocked=? WHERE id=" + user.getId()+"" ;
        try{
           PreparedStatement st = cnx.prepareStatement(request);  
            st.setBoolean(1, true);
            st.executeUpdate();
            System.out.println("user blocked");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }     
    } 
    
     
     public void UnblockUser(User user) throws SQLException   {
       String request = "UPDATE user SET is_blocked=? WHERE id=" + user.getId()+"" ;
        try{
           PreparedStatement st = cnx.prepareStatement(request);  
            st.setBoolean(1, false);
            st.executeUpdate();
            System.out.println("user unblocked");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }     
    } 
     
    
    public User updateUser(User user) throws SQLException {
        String request = "UPDATE user SET nom =\""+ user.getNom()+ "\",prenom=\""+ user.getPrenom()+ "\",email=\""+ user.getEmail()+ "\",date_de_naissance=\"" + new java.sql.Date(user.getDate_de_naissance().getTime())+ "\",sexe=\""+ user.getSexe()+ "\",num_tel=\""+ user.getTel()+ "\",adresse=\""+ user.getAdresse()+ "\"where id="+ user.getId()+"";
        try{
           PreparedStatement st = cnx.prepareStatement(request);  
            st.executeUpdate();
            System.out.println("user updated");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }
        return user;
    }
     
    public User updateMedecin(User medecin) {
        String request = "UPDATE user SET nom =\""+ medecin.getNom()+ "\",prenom=\""+ medecin.getPrenom()+ "\",email=\""+ medecin.getEmail()+ "\",date_de_naissance=\"" + new java.sql.Date(medecin.getDate_de_naissance().getTime())+ "\",sexe=\""+ medecin.getSexe()+ "\",num_tel=\""+ medecin.getTel()+ "\",adresse=\""+ medecin.getAdresse()+ "\",specialite=\""+ medecin.getSpecialité()+ "\"where id="+ medecin.getId()+"";
        try{
           PreparedStatement st = cnx.prepareStatement(request);
            st.executeUpdate();
            System.out.println("medecin updated");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }
        return medecin;
    }
    
    
     public User getUser(User user) throws SQLException {
        String request = "SELECT id,nom,prenom,email,password,date_de_naissance,sexe,adresse,num_tel,specialite,etat,roles,date_de_creation,image,is_blocked,reset_token FROM user WHERE email= ?" ;
        User  user1 =  new User();
        try {
            PreparedStatement st2 = cnx.prepareStatement(request);
            st2.setString(1, user.getEmail());
            ResultSet resultSet = st2.executeQuery();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Date dateNaissance = resultSet.getDate("date_de_naissance");
                String sexe = resultSet.getString("sexe");
                String adresse = resultSet.getString("adresse");
                String tel = resultSet.getString("num_tel");
                String specialite = resultSet.getString("specialite");
                String etat = resultSet.getString("etat");
                String roles = resultSet.getString("roles");
                Date date_de_creation = resultSet.getDate("date_de_creation");  
                String image= resultSet.getString("image");
                Boolean is_blocked=resultSet.getBoolean("is_blocked");
                String reset_token = resultSet.getString("reset_token");
                user1 = new User(id,  nom,  prenom,  email,  password,  etat,  dateNaissance,  roles, adresse, sexe, tel, specialite, date_de_creation,image,is_blocked,reset_token);
            }
             
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
        return user1;

    }
     
     public List<User> search(String critere) throws SQLException {
      return ListUsers().stream()
           .filter(a ->{
               boolean match = (a.getNom().contains(critere))|| 
               (a.getPrenom().contains(critere))||
               (a.getEmail().contains(critere))||
               (a.getEtat().contains(critere))||
               (a.getRoles().contains(critere));
           return match;
        })
              
   .collect(Collectors.toList());
        
} 
     
     
    
    
}
