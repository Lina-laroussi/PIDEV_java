/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagementsystem;

import java.sql.Date;

/**
 *
 * @author sbent
 */
public class Remboursement {
    private int id;
    private float montant_a_rembourser,montant_maximale,taux_remboursement;
    private String etat,fiche_assurance_id;
    private Date date_remboursement;

    public Remboursement() {
    }

    public Remboursement(float montant_a_rembourser, float montant_maximale, float taux_remboursement, String etat, String fiche_assurance_id, Date date_remboursement) {
        this.montant_a_rembourser = montant_a_rembourser;
        this.montant_maximale = montant_maximale;
        this.taux_remboursement = taux_remboursement;
        this.etat = etat;
        this.fiche_assurance_id = fiche_assurance_id;
        this.date_remboursement = date_remboursement;
    }

    public Remboursement(int id, float montant_a_rembourser, float montant_maximale, float taux_remboursement, String etat, String fiche_assurance_id, Date date_remboursement) {
        this.id = id;
        this.montant_a_rembourser = montant_a_rembourser;
        this.montant_maximale = montant_maximale;
        this.taux_remboursement = taux_remboursement;
        this.etat = etat;
        this.fiche_assurance_id = fiche_assurance_id;
        this.date_remboursement = date_remboursement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant_a_rembourser() {
        return montant_a_rembourser;
    }

    public void setMontant_a_rembourser(float montant_a_rembourser) {
        this.montant_a_rembourser = montant_a_rembourser;
    }

    public float getMontant_maximale() {
        return montant_maximale;
    }

    public void setMontant_maximale(float montant_maximale) {
        this.montant_maximale = montant_maximale;
    }

    public float getTaux_remboursement() {
        return taux_remboursement;
    }

    public void setTaux_remboursement(float taux_remboursement) {
        this.taux_remboursement = taux_remboursement;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getFiche_assurance_id() {
        return fiche_assurance_id;
    }

    public void setFiche_assurance_id(String fiche_assurance_id) {
        this.fiche_assurance_id = fiche_assurance_id;
    }

    public Date getDate_remboursement() {
        return date_remboursement;
    }

    public void setDate_remboursement(Date date_remboursement) {
        this.date_remboursement = date_remboursement;
    }
    
    
}
