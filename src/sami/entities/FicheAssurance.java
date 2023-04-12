/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sami.entities;

/**
 *
 * @author sbent
 */
public class FicheAssurance {
    
    private int id;
    private int num_adherant;
    private String image;
    private String description;
    private String etat;

    public FicheAssurance() {
    }

    public FicheAssurance(int id, int num_adherant, String image, String description, String etat) {
        this.id = id;
        this.num_adherant = num_adherant;
        this.image = image;
        this.description = description;
        this.etat = etat;
    }

    public FicheAssurance(int num_adherant, String image, String description, String etat) {
        this.num_adherant = num_adherant;
        this.image = image;
        this.description = description;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getNum_adherant() {
        return num_adherant;
    }

    public void setNum_adherant(int num_adherant) {
        this.num_adherant = num_adherant;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @Override
    public String toString() {
        return "FicheAssurance{" + "num_adherant=" + num_adherant + ", image=" + image + ", description=" + description + ", etat=" + etat + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FicheAssurance other = (FicheAssurance) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
   
}
