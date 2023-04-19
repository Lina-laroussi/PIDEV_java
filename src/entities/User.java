/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;



public class User {
    private int id;
    private String email;
    private String nom;
    private String prenom;
    private String password;
    private Date date_de_naissance;
    private String sexe;
    private String Adresse;
    private String tel;
    private String specialité;
    private String etat;
    private Date date_de_creation;
    private String roles;
    private String Image;

  

    public User() {
    }

    public User(int id,String nom, String prenom) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    
     public User(int id,String image) {
        this.id=id;
        this.Image=image;
    }
     
    public User(int id,String nom, String prenom,String email, Date date_de_naissance, String sexe, String tel, String Adresse, String specialité,String image) {
       
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.tel = tel;
        this.Adresse = Adresse;
        this.specialité = specialité;
        this.Image=image;
    
    }
    
      public User(String nom, String prenom,String email, Date date_de_naissance, String sexe, String tel, String Adresse) {
       
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.tel = tel;
        this.Adresse = Adresse;    
    }
      
       public User(String nom, String prenom,String email, Date date_de_naissance, String sexe, String tel, String Adresse,String specialite) {
       
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.tel = tel;
        this.Adresse = Adresse;  
        this.specialité=specialite;
    }
    
    
    
        public User(int id,String nom, String prenom,String email, Date date_de_naissance, String sexe, String tel, String Adresse,String image) {
       
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.tel = tel;
        this.Adresse = Adresse;
        this.Image=image;
     
    }
    
    
    

    public User(int id, String nom, String prenom, String email, String password, String etat, Date date_de_naissance, String roles,String Adresse,String sexe,String tel,String specialite,Date date_de_creation,String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.etat = etat;
        this.date_de_naissance = date_de_naissance;
        this.roles = roles;
        this.Adresse = Adresse;
        this.sexe = sexe;
        this.tel = tel;
        this.specialité = specialite;
        this.date_de_creation=date_de_creation;
        this.Image=image;
        
    }

    public User(String nom, String prenom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public String getSpecialité() {
        return specialité;
    }

    public void setSpecialité(String specialité) {
        this.specialité = specialité;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(Date date_de_creation) {
        this.date_de_creation = date_de_creation;
    }
    
    
    
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }  
    
    
    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", date_de_naissance=" + date_de_naissance + ", sexe=" + sexe + ", Adresse=" + Adresse + ", tel=" + tel + ", specialit\u00e9=" + specialité + ", etat=" + etat + ", date_de_creation=" + date_de_creation + ", roles=" + roles + ", Image=" + Image + '}';
    }

  
    
    
}
