/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;



import entities.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.BCrypt;
import utils.MyConnection;


public class UserService {

    private final Connection cnx;
    
    public UserService() {
        cnx = MyConnection.getInstance().getCnx();
    }
    
    
     public void registerPatient(User patient) {
       String request = "INSERT INTO User SET nom=?,prenom=?,email=?,password=?,etat=?,date_de_creation=?,roles=?,is_blocked=?" ;
       try{
        String password = BCrypt.hashpw(patient.getPassword(),BCrypt.gensalt(13));
        PreparedStatement st = cnx.prepareStatement(request);
        st.setString(1,patient.getNom());
        st.setString(2,patient.getPrenom());
        st.setString(3,patient.getEmail());
        st.setString(4,password);
        st.setString(5,"non vérifié");
        st.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
        st.setString(7,"[\"ROLE_PATIENT\"]");
        st.setBoolean(8, false);
        st.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error in method add " +ex);
        }        
    }
     
     
     
    public void VerifyPatient(User user) throws SQLException   {
       String request = "UPDATE user SET etat='valide' WHERE email= ?" ;
        try{
           PreparedStatement st = cnx.prepareStatement(request); 
            st.setString(1, user.getEmail());
            st.executeUpdate();
            System.out.println("email patient verified");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }     
    }
     
    
     public void updateUserPassword(User user ,String password) throws SQLException   {
       String request = "UPDATE user SET password=\"" + password + "\"where email= ?" ;
        try{
           PreparedStatement st = cnx.prepareStatement(request); 
            st.setString(1, user.getEmail());
            st.executeUpdate();
            System.out.println("user password updated");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }     
    }
     
     public void registerMedecin(User medecin) {
       String request = "INSERT INTO User SET nom=?,prenom=?,email=?,password=?,etat=?,date_de_creation=?,roles=?,is_blocked=?" ;
       try{
        String password = BCrypt.hashpw(medecin.getPassword(),BCrypt.gensalt(13));
        PreparedStatement st = cnx.prepareStatement(request);
        st.setString(1,medecin.getNom());
        st.setString(2,medecin.getPrenom());
        st.setString(3,medecin.getEmail());
        st.setString(4,password);
        st.setString(5,"non valide");
        st.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
        st.setString(7,"[\"ROLE_MEDECIN\"]");
        st.setBoolean(8, false);
        st.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error in method add " +ex);
        }        
    }
     
    public void registerPharmacien(User pharmacien) {
       String request = "INSERT INTO User SET nom=?,prenom=?,email=?,password=?,etat=?,date_de_creation=?,roles=?,is_blocked=?" ;
       try{
        String password = BCrypt.hashpw(pharmacien.getPassword(),BCrypt.gensalt(13));
        PreparedStatement st = cnx.prepareStatement(request);
        st.setString(1,pharmacien.getNom());
        st.setString(2,pharmacien.getPrenom());
        st.setString(3,pharmacien.getEmail());
        st.setString(4,password);
        st.setString(5,"non valide");
        st.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
        st.setString(7,"[\"ROLE_PHARMACIEN\"]");
        st.setBoolean(8, false);
        st.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error in method add " +ex);
        }        
    }
    
    
     public void registerAssureur(User assureur) {
       String request = "INSERT INTO User SET nom=?,prenom=?,email=?,password=?,etat=?,date_de_creation=?,roles=?,is_blocked=?" ;
       try{
        String password = BCrypt.hashpw(assureur.getPassword(),BCrypt.gensalt(13));
        PreparedStatement st = cnx.prepareStatement(request);
        st.setString(1,assureur.getNom());
        st.setString(2,assureur.getPrenom());
        st.setString(3,assureur.getEmail());
        st.setString(4,password);
        st.setString(5,"non valide");
        st.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
        st.setString(7,"[\"ROLE_ASSUREUR\"]");
        st.setBoolean(8, false);
        st.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error in method add " +ex);
        }        
    }
     
     
     
     
    public User LoginUser(String email, String password) {
        String request = "SELECT * FROM User where email=?";
        User user = null;
        try{
           PreparedStatement st;
           st = cnx.prepareStatement(request);
           st.setString(1,email);
           ResultSet rs = st.executeQuery();
         
        while(rs.next()){
             if(BCrypt.checkpw(password,rs.getString(4))== true){
                
                           user = new User(
                                rs.getInt(1),
                                rs.getString(5),  
                                rs.getString(6),
                                rs.getString(2),
                                rs.getString(4),  
                                rs.getString(19),   
                                rs.getDate(11),
                                rs.getString(3),
                                rs.getString(8), //adresse
                                rs.getString(12),///sexe
                                rs.getString(9),//tel
                                rs.getString(13),//specialite
                                rs.getDate(14),
                                rs.getString(17),
                                rs.getBoolean(21),
                                rs.getString(20)
                                );
                           
                
        }
        }
        }catch(SQLException e){
            System.out.println("erreur authentification " + e);
        }
        return user ;
    }  
    
    
     public void updateMedecin(User medecin) {
              String request = "UPDATE user SET nom =\""+ medecin.getNom()+ "\",prenom=\""+ medecin.getPrenom()+ "\",email=\""+ medecin.getEmail()+ "\",date_de_naissance=\"" + new java.sql.Date(medecin.getDate_de_naissance().getTime())+ "\",sexe=\""+ medecin.getSexe()+ "\",num_tel=\""+ medecin.getTel()+ "\",adresse=\""+ medecin.getAdresse()+ "\",specialite=\""+ medecin.getSpecialité()+ "\"where id="+ medecin.getId()+"";
       System.out.println(medecin.getNom());
        try{
           PreparedStatement st = cnx.prepareStatement(request);
            st.executeUpdate();
            System.out.println("medecin updated");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }
        
            }
     
     
     
    
      public void updateUser(User user) {
              String request = "UPDATE user SET nom =\""+ user.getNom()+ "\",prenom=\""+ user.getPrenom()+ "\",email=\""+ user.getEmail()+ "\",date_de_naissance=\"" + new java.sql.Date(user.getDate_de_naissance().getTime())+ "\",sexe=\""+ user.getSexe()+ "\",num_tel=\""+ user.getTel()+ "\",adresse=\""+ user.getAdresse()+ "\"where id="+ user.getId()+"";
       System.out.println(user.getNom());
        try{
           PreparedStatement st = cnx.prepareStatement(request);  
            st.executeUpdate();
            System.out.println("user updated");
        }
        catch(SQLException ex){
            System.out.println("erreur update "+ex);
        }
      }
        
        
      public void updateUserImage(User user) {
         String request = "UPDATE user SET image =\"" + user.getImage()+ "\"where id="+ user.getId()+"" ;
          try{
            PreparedStatement st = cnx.prepareStatement(request);  
            st.executeUpdate();
            System.out.println("user image updated");
          }
          catch(SQLException ex){
             System.out.println("erreur update "+ex);
          }
    }
    
    
    
    
    
}
