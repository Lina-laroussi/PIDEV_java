/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author sbent
 */
public class studentData {
                    //studentNum
    private Integer Numadherent;
  //  private String year;
                    //course
    private String id_ordonnance;
 //   private String firstName;
                    //lastName 
    private String description ;

   // private String gender;
                //birth
    private Date date_creation;
                    //status
    private String etat;
                    //image
    private String image_facture ;
  //  private Double firstSem;
   // private Double secondSem;
  //  private Double finals;


    public studentData(Integer Numadherent, String id_ordonnance, String description, Date date_creation, String etat, String image_facture) {
        this.Numadherent = Numadherent;
        this.id_ordonnance = id_ordonnance;
        this.description = description;
        this.date_creation = date_creation;
        this.etat = etat;
        this.image_facture = image_facture;
    }
 

    public Integer getNumadherent() {
        return Numadherent;
    }

    public void setNumadherent(Integer Numadherent) {
        this.Numadherent = Numadherent;
    }

    public String getId_ordonnance() {
        return id_ordonnance;
    }

    public void setId_ordonnance(String id_ordonnance) {
        this.id_ordonnance = id_ordonnance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getImage_facture() {
        return image_facture;
    }

    public void setImage_facture(String image_facture) {
        this.image_facture = image_facture;
    }
}
