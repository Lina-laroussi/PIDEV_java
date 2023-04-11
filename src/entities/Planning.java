/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author rouai
 */
public class Planning {
    private int idPlanning;
    private String description;
    private String etat;
    private Date dateDebut;
    private Date DateFin;
    private Date heureDebut;
    private Date heureFin;

    public Planning() {
    }

    public Planning(int idPlanning, String description, Date dateDebut, Date DateFin, Date heureDebut, Date heureFin) {
        this.idPlanning = idPlanning;
        this.description = description;
        this.dateDebut = dateDebut;
        this.DateFin = DateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }
    public Planning(int idPlanning, String description,String etat, Date dateDebut, Date DateFin, Date heureDebut, Date heureFin) {
        this.idPlanning = idPlanning;
        this.description = description;
        this.etat = etat;
        this.dateDebut = dateDebut;
        this.DateFin = DateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Planning(String description, Date dateDebut, Date DateFin, Date heureDebut, Date heureFin) {
        this.description = description;
        this.dateDebut = dateDebut;
        this.DateFin = DateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Planning(String description, Date dateDebut, Date DateFin) {
        this.description = description;
        this.dateDebut = dateDebut;
        this.DateFin = DateFin;
    }
   

    public int getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(int idPlanning) {
        this.idPlanning = idPlanning;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date DateFin) {
        this.DateFin = DateFin;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    @Override
    public String toString() {
        return "Planning{" + "idPlanning=" + idPlanning + ", description=" + description + ", etat=" + etat + ", dateDebut=" + dateDebut + ", DateFin=" + DateFin + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + '}';
    }

    
}
