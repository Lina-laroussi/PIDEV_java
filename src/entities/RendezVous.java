/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author rouai
 */
public class RendezVous implements Serializable{
    private int idRdv;
    private String symptomes;
    private String etat;
    private Date date;
    private Date heureDebut;
    private Date heureFin;
    private int idPlanning;
    private String fullName;
    private int idPatient;
    public RendezVous() {
    } 

    public RendezVous(int idRdv, String symptomes, String etat, Date date, Date heureDebut, Date heureFin, int idPlanning) {
        this.idRdv = idRdv;
        this.symptomes = symptomes;
        this.etat = etat;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.idPlanning = idPlanning;
    }
    
    public RendezVous(int idRdv, String symptomes, String etat, Date date, Date heureDebut, Date heureFin) {
        this.idRdv = idRdv;
        this.symptomes = symptomes;
        this.etat = etat;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public RendezVous(String symptomes, String etat, Date date, Date heureDebut, Date heureFin, int idPlanning) {
        this.symptomes = symptomes;
        this.etat = etat;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.idPlanning = idPlanning;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(int idPlanning) {
        this.idPlanning = idPlanning;
    }

    public int getIdRdv() {
        return idRdv;
    }

    public void setIdRdv(int idRdv) {
        this.idRdv = idRdv;
    }

    public String getSymptomes() {
        return symptomes;
    }

    public void setSymptomes(String symptomes) {
        this.symptomes = symptomes;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return "RendezVous{" + "idRdv=" + idRdv + ", symptomes=" + symptomes + ", etat=" + etat + ", date=" + date + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + '}';
    }
    
    
}
