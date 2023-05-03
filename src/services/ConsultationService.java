package services;

import entities.Consultation;
import entities.RendezVous;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utils.MyConnection;

public class ConsultationService {
    
    private final Connection cnx;
    private PreparedStatement  pStmt;
    
    public ConsultationService() {
           cnx = MyConnection.getInstance().getCnx();
    }
// --------------------------------afficher---------------------------------------------------
public List<Consultation> showConsultation() throws SQLException {

    List<Consultation> consultations = new ArrayList<>();
    String request = "SELECT c.*, u.prenom "
            + "FROM consultation c "
            + "JOIN rendez_vous rv ON c.consult_id = rv.consultation_id "
            + " JOIN user u ON rv.patient_id = u.id "
            + "AND u.roles LIKE '[\"patient\"]'";
    try {
        pStmt = cnx.prepareStatement(request);
        ResultSet rs = pStmt.executeQuery();        
        while (rs.next()) {
            Consultation c = new Consultation();
            c.setIdConslt(rs.getInt("consult_id"));
            c.setPoids(rs.getFloat("poids"));
            c.setTaille(rs.getFloat("taille"));
            c.setImc(rs.getFloat("imc"));
            c.setTemperature(rs.getFloat("temperature"));
            c.setPrix(rs.getFloat("prix"));
            c.setPression_arterielle(rs.getFloat("pression_arterielle"));
            c.setFrequence_cardiaque(rs.getFloat("frequence_cardiaque"));
            c.setTaux_glycemie(rs.getFloat("taux_glycemie"));
            c.setMaladie(rs.getString("maladie"));
            c.setTraitement(rs.getString("traitement"));
            c.setObservation(rs.getString("observation"));
            c.setPatientName(rs.getString("prenom"));
            consultations.add(c);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }  
        return consultations;
}
// --------------------------------ADD consultation ---------------------------------------------------
public void ajouterConsultation(Consultation c, RendezVous r) throws SQLException {
    String request = "INSERT INTO consultation(consult_id, poids, taille, imc, temperature, prix, pression_arterielle, frequence_cardiaque, taux_glycemie, maladie, traitement, observation) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";
    String request2 = "UPDATE rendez_vous SET consultation_id = consult_id WHERE patient_id = ?";
    try {
        cnx.setAutoCommit(false); // start transaction
        PreparedStatement preparedStatement = cnx.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, c.getIdConslt());
        preparedStatement.setFloat(2, c.getPoids());
        preparedStatement.setFloat(3, c.getTaille());
        preparedStatement.setFloat(4, c.getImc());
        preparedStatement.setFloat(5, c.getTemperature());
        preparedStatement.setFloat(6, c.getPrix());
        preparedStatement.setFloat(7, c.getPression_arterielle());
        preparedStatement.setFloat(8, c.getFrequence_cardiaque());
        preparedStatement.setFloat(9, c.getTaux_glycemie());
        preparedStatement.setString(10, c.getMaladie());
        preparedStatement.setString(11, c.getTraitement());
        preparedStatement.setString(12, c.getObservation());
        preparedStatement.executeUpdate();
        // Get the ID of the inserted consultation
        ResultSet rs = preparedStatement.getGeneratedKeys();
        int generatedId = -1;
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }
        System.out.println("Generated consultation ID: " + generatedId);
        PreparedStatement preparedStatement2 = cnx.prepareStatement(request2);
        preparedStatement2.setInt(1, generatedId);
        preparedStatement2.setInt(2, r.getIdPatient());
        int rowsAffected = preparedStatement2.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected by update query");
        cnx.commit();
        cnx.setAutoCommit(true); 
    } catch (SQLException ex) {
        cnx.rollback(); 
        cnx.setAutoCommit(true);
        ex.printStackTrace();
    }
}
// --------------------------------editer---------------------------------------------------
    public void editerConsultation(Consultation c){
        
        //System.out.println(p.getDescription());
         String request = "UPDATE consultation SET poids=?, taille=?, imc=?, temperature=?, prix=?,"
                 + " pression_arterielle=?, frequence_cardiaque=?, taux_glycemie=?, maladie=?, traitement=?,"
                 + " observation=? WHERE consult_id=?";    
    try {
        pStmt = cnx.prepareStatement(request);
        pStmt.setFloat(1, c.getPoids());
        pStmt.setFloat(2, c.getTaille());
        pStmt.setFloat(3, c.getImc());
        pStmt.setFloat(4, c.getTemperature());
        pStmt.setFloat(5, c.getPrix());
        pStmt.setFloat(6, c.getPression_arterielle()); 
        pStmt.setFloat(7, c.getFrequence_cardiaque());
        pStmt.setFloat(8, c.getTaux_glycemie());
        pStmt.setString(9, c.getMaladie());
        pStmt.setString(10, c.getTraitement());        
        pStmt.setString(11, c.getObservation()); 
        pStmt.executeUpdate();
        System.out.println("Consultation modifiée avec succès");
    } catch (SQLException ex) {
        System.out.println(ex);
        System.out.println ("Erreur lors de la modification de la consultation");
    }
}   
// --------------------------------supprimer consultation---------------------------------------------------
public  void deleteConsultation(int idConsult) {  

    String request = "DELETE FROM consultation WHERE consult_id =" + idConsult;
    try {
        pStmt = cnx.prepareStatement(request);
        pStmt.executeUpdate();
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Supprimer consultation");
        alert1.setContentText("La consultation " + idConsult + " a été supprimée avec succès");
        Optional<ButtonType> answer1 = alert1.showAndWait();
        System.out.println("Delete Done");
    } catch (SQLException ex) {
        System.out.println(ex);
        System.out.println("Erreur lors de la suppression de la consultation");
    }
}
private void updateRendezVous(RendezVous rdv, int consultId) {
    String request = "INSERT INTO rendez_vous(date,heure_debut,heure_fin,symptomes,etat,date_de_creation,planning_id,consultation_id) "
            + "VALUES(?,?,?,?,?,?,?,?) ";
    try (PreparedStatement pStmt = cnx.prepareStatement(request)) {
        pStmt.setDate(1, new java.sql.Date(rdv.getDate().getTime()));
        pStmt.setTime(2, new java.sql.Time(rdv.getHeureDebut().getTime()));
        pStmt.setTime(3, new java.sql.Time(rdv.getHeureFin().getTime()));
        pStmt.setString(4, rdv.getSymptomes());
        pStmt.setString(5, "en attente");
        pStmt.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
        pStmt.setInt(7, rdv.getIdPlanning());
        pStmt.setInt(8, consultId); // set the consultation_id column with the provided consultId
        pStmt.executeUpdate();
        System.out.println("success");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}



    
    
}
