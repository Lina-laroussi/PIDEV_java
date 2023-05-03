
package controllers;

import entities.Consultation;
import entities.Ordonnance;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.OrdonnanceService;
import utils.MyConnection;

public class AjouterOrdonnanceController implements Initializable {

    @FXML
    private TextField txt_id, txt_mal, txt_trait, dateRDV, hDebut, txt_codeOrd, txt_desc, txt_med, txt_dos, txt_nbr;
    @FXML
    private Tab tabOrd;
    @FXML
    private Button btn_valider;
   
private Consultation selectedConsultation;
private int consultId;
private final Connection cnx;
private PreparedStatement ste;

public AjouterOrdonnanceController() {
    MyConnection bd = MyConnection.getInstance();
    cnx=bd.getCnx();
}  
@Override
public void initialize(URL url, ResourceBundle rb) {
    // generates a unique identifier,substring is used to take only the first 10 characters
    String codeOrdonnance = UUID.randomUUID().toString().substring(0, 10);    
    txt_codeOrd.setText(codeOrdonnance);    
    if (selectedConsultation != null) {
        txt_id.setText(String.valueOf(selectedConsultation.getPatientName()));
        txt_mal.setText(selectedConsultation.getMaladie());
        txt_trait.setText(selectedConsultation.getTraitement());
       Date date = selectedConsultation.getRendezVous().getDate(); // get appointment date
       Date time = selectedConsultation.getRendezVous().getHeureDebut(); // get appointment time
    }   
}
public void setConsultationId(Consultation c){
       this.consultId = c.getIdConslt();
   }
    @FXML
    private void Valider(ActionEvent event) throws Exception {
       try{
        if(txt_desc.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("you need to check the inserted value, it must be caracters only");
            alert.showAndWait(); 
        }else if(txt_med.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le medicament poue ce patient, you need to check the inserted value, it must be caracters only");
            alert.showAndWait(); 
        }else if(txt_dos.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le dosage du medicament, you need to check the inserted value it must be numbers only");
            alert.showAndWait(); 
        }else if(txt_nbr.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le nombre de jours de medicament a prendre par le patient, you need to check the inserted value it must be numbers only");
            alert.showAndWait();  
        }else{     
        Ordonnance ord = new Ordonnance(txt_codeOrd.getText(), txt_desc.getText(),
        txt_med.getText(), Integer.parseInt(txt_dos.getText()), Integer.parseInt(txt_nbr.getText()));
        OrdonnanceService ordService = new OrdonnanceService();
        ordService.ajouterOrdonnance(ord, selectedConsultation.getIdConslt());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AJOUT ORDONNANCE AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("ordonnance ajouté au consultation avec succès");
        alert.showAndWait();
        Stage stage = (Stage) btn_valider.getScene().getWindow();
        stage.close();         
        }
       }catch (NullPointerException e) {
        // handle the null pointer exception
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERREUR");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une consultation avant de continuer.");
        alert.showAndWait();
    } catch (Exception e) {
        // handle other exceptions
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERREUR");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'ajout de l'ordonnance.");
        alert.showAndWait();
    }
        String title = "succes ";
        String message = "Ordonnance ajouté avec succes";
    }
    
// ************************** buton reset *********************
@FXML
private void Reset (ActionEvent event)  {

    txt_desc.setText("");
    txt_med.setText("");
    txt_dos.setText("");
    txt_nbr.setText("");
}     

   public void setSelectedConsultation(Consultation consultation) {
    
       /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // define the date format
       String formattedDate = dateFormat.format(rdv.getDate()); // format the date
       dateRDV.setText(formattedDate);
       
       SimpleDateFormat heurFormat = new SimpleDateFormat("hh:mm:ss");
       String formattedHeur = heurFormat.format(rdv.getHeureDebut()); 
       hDebut.setText(formattedHeur);
      
       //selectedConsultation = consultation;
        txt_id.setText(Integer.toString(consultation.getIdConslt()));
        txt_mal.setText(consultation.getMaladie());
        txt_trait.setText(consultation.getTraitement());
         */
       
       
   }
}
