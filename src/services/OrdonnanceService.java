package services;

import entities.Ordonnance;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utils.MyConnection;

public class OrdonnanceService {
private final Connection cnx;
private PreparedStatement  pStmt;
public OrdonnanceService() {
    cnx = MyConnection.getInstance().getCnx();
}    
// --------------------------------afficher---------------------------------------------------
public List<Ordonnance> showOrdonnance() {
    List<Ordonnance> ordonnances = new ArrayList<>();
    String request = "SELECT * FROM ordonnance";
    try {
        pStmt = cnx.prepareStatement(request);
        try (ResultSet rs = pStmt.executeQuery()) {
            while (rs.next()) {
                Ordonnance ord = new Ordonnance(rs.getString("description"),rs.getString("code_ordonnance"),rs.getString("medicaments"), rs.getInt("dosage"), rs.getInt("nombre_jours"), rs.getDate("date_de_creation"));
                ord.setId(rs.getInt("ordonnance_id"));
                ordonnances.add(ord);}
        }
        pStmt.close();
    }catch (SQLException e) {
        e.printStackTrace();
    }
    return ordonnances;
}

// --------------------------------ADD ordonnance ---------------------------------------------------
public void ajouterOrdonnance(Ordonnance ord, int consultationId){
String request="INSERT INTO ordonnance ( description, code_ordonnance, medicaments, dosage, nombre_jours, date_de_creation, consultation_id)"
                +"VALUES(?,?,?,?,?,?,?) ";
    //Date dateCreation = new Date(ord.getDate_de_creation().getTime());
    //Date dateModification = new Date(ord.getDate_de_modification().getTime());
    //System.out.println(c.getPoids());
   try {
        pStmt = cnx.prepareStatement(request);
        pStmt.setString(1, ord.getDescription());
        pStmt.setString(2, ord.getCode_ordonnance());
        pStmt.setString(3, ord.getMedicaments());
        pStmt.setInt(4, ord.getDosage());
        pStmt.setInt(5, ord.getNombre_jours());
        pStmt.setDate(6, (Date) ord.getDate_de_creation());
        pStmt.setInt(7, consultationId);
        //pStmt.setDate(7, (Date) ord.getDate_de_modification());
        //pStmt.setString(8, ord.getQrCodeFilename());
        pStmt.executeUpdate();
        pStmt.close();
        System.out.println("Ordonnance ajoutée avec succès à la consultation " + consultationId);
    } catch (SQLException e) {
        System.out.println(e);
    }
        
    }
    
// --------------------------------editer---------------------------------------------------
    public void editerOrdonnance(Ordonnance ord){
        
         String request = "UPDATE ordonnance SET nombre_jours=?, medicaments=?, code_ordonnance=?,"
                 + " dosage=?,  description=?, date_de_creation=?, qrCodeFilename=? WHERE ordonnance_id=?";
        Date dateCreation = new Date(ord.getDate_de_creation().getTime());
        
    
    try {
        pStmt = cnx.prepareStatement(request);
        pStmt.setInt(1, ord.getNombre_jours());
        pStmt.setString(2, ord.getMedicaments());
        pStmt.setString(3, ord.getCode_ordonnance());
        pStmt.setInt(4, ord.getDosage());        
        pStmt.setString(5, ord.getDescription());
        pStmt.setDate(6, dateCreation);
        //pStmt.setString(8, ord.getQrCodeFilename());
        pStmt.executeUpdate();
        System.out.println("ordonnance modifiée avec succès");
    } catch (SQLException ex) {
        System.out.println(ex);
        System.out.println ("Erreur lors de la modification de la ordonnance");
    }
        
    }


// --------------------------------supprimer consultation---------------------------------------------------
    public void deleteOrdonnance(int idOrd) {  

        String request = "DELETE FROM ordonnance WHERE ordonnance_id=" + idOrd;

        try {
            pStmt = cnx.prepareStatement(request);
            pStmt.executeUpdate();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Supprimer ordonnance");
            alert1.setContentText("L'ordonnance " + idOrd + " a été supprimée avec succès");
            Optional<ButtonType> answer1 = alert1.showAndWait();
            System.out.println("Delete Done");

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Erreur lors de la suppression de l'ordonnance");
        }
}
    





}

