package entities;

import java.util.Date;

public class FicheMedicale {    
    private int id;
    private String description, allergies, pathologie;
    private Date date_de_creation, date_de_modification;

    public FicheMedicale() {
    }

    public FicheMedicale(int id, String description, String allergies, String pathologie, Date date_de_creation, Date date_de_modification) {
        this.id = id;
        this.description = description;
        this.allergies = allergies;
        this.pathologie = pathologie;
        this.date_de_creation = date_de_creation;
        this.date_de_modification = date_de_modification;
    }

    public FicheMedicale(int id, String description, Date date_de_creation) {
        this.id = id;
        this.description = description;
        this.date_de_creation = date_de_creation;
    }

    public FicheMedicale(String description, String allergies, String pathologie, Date date_de_modification) {
        this.description = description;
        this.allergies = allergies;
        this.pathologie = pathologie;
        this.date_de_modification = date_de_modification;
    }

    public FicheMedicale(int parseInt, String text, String text0, String text1, Date parse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public FicheMedicale(int idFich, String string, String string0, java.sql.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getPathologie() {
        return pathologie;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setPathologie(String pathologie) {
        this.pathologie = pathologie;
    }

    public void setDate_de_creation(Date date_de_creation) {
        this.date_de_creation = date_de_creation;
    }

    public void setDate_de_modification(Date date_de_modification) {
        this.date_de_modification = date_de_modification;
    }

    @Override
    public String toString() {
        return "FicheMedicale{" + "id=" + id
                + ", description=" + description 
                + ", allergies=" + allergies + ", pathologie=" + pathologie 
                + ", date_de_creation=" + date_de_creation + ", date_de_modification=" 
                + date_de_modification + '}';
    }

    public String getImgPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addConsultation(Consultation consult) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
}
