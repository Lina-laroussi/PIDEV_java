/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Asus
 */
public class categorie {
    private int id ;
    public String nom , description , etat , marque , groupe_age;

    public categorie() {
    }

    public categorie(String nom, String description, String etat, String marque, String groupe_age) {
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.marque = marque;
        this.groupe_age = groupe_age;
    }

    public categorie(int id, String nom, String description, String etat, String marque, String groupe_age) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.marque = marque;
        this.groupe_age = groupe_age;
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

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getGroupe_age() {
        return groupe_age;
    }

    public void setGroupe_age(String groupe_age) {
        this.groupe_age = groupe_age;
    }
      public categorie(String name) {
        this.nom = name;
    }

    @Override
    public String toString() {
        return "categorie{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", etat=" + etat + ", marque=" + marque + ", groupe_age=" + groupe_age + '}';
    }
    
    
    
}
