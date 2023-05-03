package services;

import entities.FicheMedicale;
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


public class FicheMedicaleService {
    
private final Connection cnx;
private PreparedStatement  pStmt;
public FicheMedicaleService() {
       cnx = MyConnection.getInstance().getCnx();
}
//--------------------------------afficher fiche medicale service---------------------------------------------------
public List<FicheMedicale> showFicheMedicale() {
        
    List<FicheMedicale> FicheMedicales = new ArrayList<>();
    String request = "SELECT * FROM fiche_medicale";
    try {
        pStmt = cnx.prepareStatement(request);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()) {
            FicheMedicale fich = new FicheMedicale();
            fich.setId(rs.getInt("fichMed_id"));
            fich.setDescription(rs.getString("description"));
            fich.setAllergies(rs.getString("allergies"));
            fich.setPathologie(rs.getString("pathologie"));
            fich.setDate_de_creation(rs.getDate("date_de_creation"));
            fich.setDate_de_modification(rs.getDate("date_de_modification"));
            FicheMedicales.add(fich);
        }
        pStmt.close();
        rs.close();
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return FicheMedicales;
}
   
// --------------------------------ADD Fiche Medicale  ---------------------------------------------------
public void ajouterFichMedicale(FicheMedicale fich){

    String request="INSERT INTO fiche_medicale(description, allergies, pathologie, date_de_creation, date_de_modification)"
            +"VALUES(?,?,?,?,?) ";
    Date dateCreation = new Date(fich.getDate_de_creation().getTime());
    Date dateModification = new Date(fich.getDate_de_modification().getTime());
   try {
        pStmt = cnx.prepareStatement(request);
        pStmt.setString(1, fich.getDescription());
        pStmt.setString(2, fich.getAllergies());
        pStmt.setString(3, fich.getPathologie());
        pStmt.setDate(7, dateCreation);
        pStmt.setDate(8, dateModification);
        pStmt.executeUpdate();
        pStmt.close();
        System.out.println ("succes"); 

   }catch (SQLException e) {
        System.out.println (e);
    }
}    
// --------------------------------editer---------------------------------------------------
public void editerFichMedical(FicheMedicale fich){

    String request = "UPDATE fiche_medicale SET description=?, allergies=?, pathologie=?, date_de_creation=?, date_de_modification=? WHERE fichMed_id =?";
    Date dateCreation = new Date(fich.getDate_de_creation().getTime());
    Date dateModification = new Date(fich.getDate_de_modification().getTime());
    try {
        pStmt = cnx.prepareStatement(request);
        pStmt.setString(1, fich.getDescription());
        pStmt.setString(2, fich.getAllergies());
        pStmt.setString(3, fich.getPathologie());
        pStmt.setDate(7, dateCreation);
        pStmt.setDate(8, dateModification);
        pStmt.setInt(9, fich.getId());
        pStmt.executeUpdate();
        System.out.println("Fiche Medicale modifiée avec succès");
    } catch (SQLException ex) {
    System.out.println(ex);
    System.out.println ("Erreur lors de la modification de la Fiche Medicale");
}        
}
//--------------------------------supprimer fiche_medicale---------------------------------------------------
public void deleteFicheMedicale(int idFich) {  
    String request = "DELETE FROM fiche_medicale WHERE fichMed_id= " + idFich;
    try {
        pStmt = cnx.prepareStatement(request);
        pStmt.executeUpdate();
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Supprimer Fiche Medicale ");
        alert1.setContentText("Le Fiche Medicale " + idFich + " a été supprimée avec succès, its not gonna show when you open the consultation again");
        Optional<ButtonType> answer1 = alert1.showAndWait();
        System.out.println("Delete Done");
    } catch (SQLException ex) {
        System.out.println(ex);
        System.out.println("Erreur lors de la suppression du Fiche Medicale");
    }
}    
 //--------------------------------find fich medicale by id ---------------------------------------------------    
public FicheMedicale findfichById(int idFich) throws SQLException{   
    String request = "SELECT * FROM fiche_medicale where fichMed_id=" +idFich+"";
    FicheMedicale fich = null;
     try {
       PreparedStatement preparedStatement = cnx.prepareStatement(request);
     ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
        System.out.println("resultSet"+resultSet.getInt("fichMed_id"));
        fich = new FicheMedicale(idFich,resultSet.getString("allergies"),resultSet.getString("pathologie"),resultSet.getDate("date_de_creation"));
       }
    }catch (SQLException ex) {
        System.out.println(ex);
    }
     return fich;
}   
    
    
}
