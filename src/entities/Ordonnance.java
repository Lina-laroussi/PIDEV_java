package entities;

import java.util.Date;

public class Ordonnance {    
    private int id, nombre_jours, dosage;
    private String medicaments, code_ordonnance, description ;
    private Date date_de_creation, date_de_modification;

    public Ordonnance() {
    }

    public Ordonnance(int id, int nombre_jours, int dosage, String medicaments, String code_ordonnance, String description, Date date_de_creation, Date date_de_modification) {
        this.id = id;
        this.nombre_jours = nombre_jours;
        this.dosage = dosage;
        this.medicaments = medicaments;
        this.code_ordonnance = code_ordonnance;
        this.description = description;
        this.date_de_creation = date_de_creation;
        this.date_de_modification = date_de_modification;
    }

    public Ordonnance(int nombre_jours, int dosage, String medicaments, String description, Date date_de_modification) {
        this.nombre_jours = nombre_jours;
        this.dosage = dosage;
        this.medicaments = medicaments;
        this.description = description;
        this.date_de_modification = date_de_modification;
    }

    public Ordonnance(String code_ordonnance, String description, String medicaments, int dosage, int nombre_jours, Date date_de_creation) {
        this.code_ordonnance = code_ordonnance;
        this.description = description;
        this.medicaments = medicaments;
        this.dosage = dosage;
        this.nombre_jours = nombre_jours;
       this.date_de_modification = date_de_creation;
}
    public Ordonnance(String code_ordonnance, String description, String medicaments, int dosage, int nombre_jours) {
        this.code_ordonnance = code_ordonnance;
        this.description = description;
        this.medicaments = medicaments;
        this.dosage = dosage;
        this.nombre_jours = nombre_jours;
}
    public Ordonnance(String code_ordonnance, String medicaments, String description, Date date_de_modification) {
        this.code_ordonnance = code_ordonnance;
        this.medicaments = medicaments;
        this.description = description;
        this.date_de_modification = date_de_modification;
    }   



    public int getId() {
        return id;
    }
    public int getConsult_id() {
        return 0;
        
    }
    
    public int getNombre_jours() {
        return nombre_jours;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public String getCode_ordonnance() {
        return code_ordonnance;
    }

    public int getDosage() {
        return dosage;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate_de_creation() {
        return date_de_creation;
    }

    public Date getDate_de_modification() {
        return date_de_modification;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre_jours(int nombre_jours) {
        this.nombre_jours = nombre_jours;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public void setCode_ordonnance(String code_ordonnance) {
        this.code_ordonnance = code_ordonnance;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate_de_creation(Date date_de_creation) {
        this.date_de_creation = date_de_creation;
    }

    public void setDate_de_modification(Date date_de_modification) {
        this.date_de_modification = date_de_modification;
    }

    @Override
    public String toString() {
        return "Ordonnance{" + "id=" + id + 
                ", nombre_jours=" + nombre_jours + 
                ", medicaments=" + medicaments + ", code_ordonnance=" 
                + code_ordonnance + ", dosage=" + dosage + ", description="
                + description + ", date_de_creation=" + date_de_creation +
                ", date_de_modification=" + date_de_modification + '}';
    }

    

    
    
    
    
    
}
