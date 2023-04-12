/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.control.ChoiceBox;

/**
 *
 * @author Asus
 */
public class produit {
     private int Id ;
    private String nom ,description , etat , image;
    private Float prix, quantite; 
     categorie categorie_id;
 
    public produit() {
    }
  public produit(int Id, String nom, String description, String etat, String image, Float prix, Float quantite, categorie categorie_id) {
        this.Id = Id;
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie_id = categorie_id;
    }
  
    public produit(int Id, String nom, String description, String etat, String image, Float prix, Float quantite) {
        this.Id = Id;
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
    }

    public produit(String nom, String description, String etat, String image, Float prix, Float quantite) {
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
    }

    public produit(int Id, String nom, String description, String etat, String image) {
        this.Id = Id;
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.image = image;
    }

    public produit(String nom, String description, String etat, String image, float prix, float quantite, ChoiceBox<String> Categorie_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
   

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public categorie getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(categorie categorie_id) {
        this.categorie_id = categorie_id;
    }

   
    
    
    @Override
    public String toString() {
        return "produit{" + "Id=" + Id + ", nom=" + nom + ", description=" + description + ", etat=" + etat + ", image=" + image + ", prix=" + prix + ", quantite=" + quantite + ", categorie_id=" + categorie_id + '}';
    }

    
    
}
