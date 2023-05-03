package controllers;

import entities.Consultation;
import entities.RendezVous;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ConsultationService;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author MBM info
 */
public class ModifierConsultationController implements Initializable {

    @FXML
    private TextField NumConslt, txt_poid, txt_taill, txt_imc, txt_temp, txt_press, txt_freq, txt_tx, txt_mal, txt_trait, txt_px;
    @FXML
    private TextArea txt_obsv;
    @FXML
    private Button btn_valider;
    

private final Connection cnx;
private PreparedStatement ste;    
public ModifierConsultationController() {
    MyConnection bd = MyConnection.getInstance();
    cnx=bd.getCnx();
}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void inflateUI(Consultation consultation) {
        NumConslt.setText(Integer.toString(consultation.getIdConslt()));
        txt_poid.setText(Float.toString(consultation.getPoids()));
        txt_taill.setText(Float.toString(consultation.getTaille()));
        txt_imc.setText(Float.toString(consultation.getImc()));
        txt_temp.setText(Float.toString(consultation.getTemperature()));
        txt_px.setText(Float.toString(consultation.getPrix()));
        txt_press.setText(Float.toString(consultation.getPression_arterielle()));
        txt_freq.setText(Float.toString(consultation.getFrequence_cardiaque()));
        txt_tx.setText(Float.toString(consultation.getTaux_glycemie()));
        txt_mal.setText(consultation.getMaladie());
        txt_trait.setText(consultation.getTraitement());
        txt_obsv.setText(consultation.getObservation());
    }
    // ************************** buton valider *********************
    //control de saisie
    @FXML
    private void Valider(ActionEvent event) throws Exception {
       try{
        if(txt_poid.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le poids du patient");
            alert.showAndWait(); 
        }else if(txt_taill.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la taille du patient");
            alert.showAndWait(); 
        }else if(txt_imc.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez l'imc du patient");
            alert.showAndWait(); 
        }else if(txt_temp.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la température du patient");
            alert.showAndWait();  
        }else if(txt_press.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la pression arterielle du patient");
            alert.showAndWait();  
        }else if(txt_freq.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la fréquence cardiaque du patient");
            alert.showAndWait();  
        }else if(txt_tx.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le taux de glycemie du patient");
            alert.showAndWait();  
        }else if(txt_mal.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la maladie du patient");
            alert.showAndWait();    
        }else if(txt_trait.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le traitement du maladie de patient");
            alert.showAndWait();   
        }else if(txt_px.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le prix de consultation");
            alert.showAndWait();     
        }else if(txt_obsv.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez votre observation sur maladie et/ou patient");
            alert.showAndWait();      
        }
        
        else{     
            Consultation c = new Consultation(Float.parseFloat(txt_poid.getText()),Float.parseFloat(txt_taill.getText()),
                    Float.parseFloat(txt_imc.getText()), Float.parseFloat(txt_temp.getText()),
                    Float.parseFloat(txt_px.getText()), Float.parseFloat(txt_press.getText()),
                    Float.parseFloat(txt_freq.getText()), Float.parseFloat(txt_tx.getText()),
                    txt_mal.getText(), txt_trait.getText(),txt_obsv.getText());
            
            ConsultationService consultService = new ConsultationService();
            RendezVous r = new RendezVous();
            consultService.ajouterConsultation(c,r);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("AJOUT AVEC SUCCES");
            alert.setHeaderText(null);
            alert.setContentText("Consultation ajouté avec succès");
            alert.showAndWait();
            
            // close the current window
            Stage stage = (Stage) btn_valider.getScene().getWindow();
            stage.close();
             
        }
       }
        catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
       
        String title = "succes ";
        String message = "Consultation ajouté avec succes";
        
        // open the "afficherConsultation" page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficherConsultation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setScene(scene);
            stage2.show();
    }
    // ************************** buton reset *********************
    @FXML
    private void Reset (ActionEvent event)  {
    
        txt_poid.setText("");
        txt_taill.setText("");
        txt_imc.setText("");
        txt_temp.setText("");
        txt_press.setText("");
        txt_freq.setText("");
        txt_tx.setText("");
        txt_mal.setText("");
        txt_trait.setText("");
        txt_px.setText("");
        txt_obsv.setText("");
    }
    
}
