/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Asus
 */
public class produit {
     private int Id ;
    private String nom ,description , etat , image;
    private Float prix, quantite; 
private categorie categorieId;
    public produit() {
    }

    public produit(int Id, String nom, String description, String etat, String image, Float prix, Float quantite, categorie categorieId) {
        this.Id = Id;
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
        this.categorieId = categorieId;
    }

    public produit(String nom, String description, String etat, String image, Float prix, Float quantite, categorie categorieId) {
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.image = image;
        this.prix = prix;
        this.quantite = quantite;
        this.categorieId = categorieId;
    }
    

    public produit(int Id, String nom, String image, Float prix) {
        this.Id = Id;
        this.nom = nom;
        this.image = image;
        this.prix = prix;
    }
public produit(int Id, String nom, String description, float quantite, String etat, float prix, String image) {
    this.Id = Id;
    this.nom = nom;
    this.description = description;
    this.quantite = quantite;
    this.etat = etat;
    this.prix = prix;
    this.image = image;
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
    

    public produit(String nom, String description, String etat, String image, float prix, float quantite, categorie categorieId) {
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.image = image;
        this.prix=prix;
        this.quantite=quantite;
        this.categorieId=categorieId;
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

    public categorie getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(categorie categorieId) {
        this.categorieId = categorieId;
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

    @Override
    public String toString() {
        return "Produit{" + "Id=" + Id + ", nom=" + nom + ", description=" + description + ", etat=" + etat + ", image=" + image + ", prix=" + prix + ", quantite=" + quantite + '}';
    }
    
    
    
}
