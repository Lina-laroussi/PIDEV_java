package controllers;

import entities.Ordonnance;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.OrdonnanceService;
import utils.MyConnection;
import entities.Consultation;
import javafx.scene.image.ImageView;

public class ModifierOrdonnanceController implements Initializable {

    @FXML
    private TextField txt_prenom, txt_tel, txt_cin, codeOrd, txt_date, txt_desc, txt_dos, txt_med, txt_nbrJ;
    @FXML
    private ImageView qrCode;
    @FXML
    private Button btn_valider;
    
    
private Consultation selectedConsultation;
private final Connection cnx;
private PreparedStatement ste;    
public ModifierOrdonnanceController() {
    MyConnection bd = MyConnection.getInstance();
    cnx=bd.getCnx();
}

@Override
public void initialize(URL url, ResourceBundle rb) {


}
public void inflateUI(Ordonnance ordonnance) {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // define the date format
    String formattedDate = dateFormat.format(ordonnance.getDate_de_modification()); // format the date
    //txt_prenom.setText(Integer.toString(ordonnance.getIdConslt()));
    //txt_tel.setText(Float.toString(ordonnance.getPoids()));
    //txt_cin.setText(Float.toString(ordonnance.getTaille()));
    codeOrd.setText(ordonnance.getCode_ordonnance());
    txt_med.setText(ordonnance.getMedicaments());
    txt_dos.setText(Integer.toString(ordonnance.getDosage()));
    txt_nbrJ.setText(Integer.toString(ordonnance.getNombre_jours()));
    txt_desc.setText(ordonnance.getDescription());
    txt_date.setText(formattedDate);
}
    // ************************** buton valider *********************
    //control de saisie
    @FXML
    private void Valider(ActionEvent event) throws Exception {
 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            int dosage = Integer.parseInt(txt_dos.getText());
            int nbrJours = Integer.parseInt(txt_nbrJ.getText());

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Le dosage ou le nombre de jours doit être un nombre entier");
            alert.showAndWait();
        }

       try{            
        if(txt_med.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez re-entrez le nouveau medicament");
            alert.showAndWait(); 
        }else if(txt_dos.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez re-entrez le dosage de medicament a apprendre");
            alert.showAndWait(); 
        }else if(txt_nbrJ.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez re-entrez le nombre de jours d'apprendre le medicament");
            alert.showAndWait(); 
        }else if(txt_desc.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez re-entrez la description du medicament");
            alert.showAndWait();  
        }else if(txt_date.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez re-entrez la date de modification de l'ordonnance");
            alert.showAndWait();
        }else{     
            Ordonnance ord = new Ordonnance(codeOrd.getText(),txt_med.getText(), txt_desc.getText(), dateFormat.parse(txt_date.getText()));
            
            OrdonnanceService ordService = new OrdonnanceService();
            ordService.ajouterOrdonnance(ord, selectedConsultation.getIdConslt());
            
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
    
        txt_med.setText("");
        txt_dos.setText("");
        txt_nbrJ.setText("");
        txt_desc.setText("");
        txt_date.setText("");
    }
       
    
}
