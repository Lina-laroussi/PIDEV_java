package controllers;

import entities.FicheMedicale;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.FicheMedicaleService;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author wissal
 */
public class ModifierFichMedController implements Initializable {
    @FXML
    private Label lb_num;
    @FXML
    private TextField txt_dateCrea,txt_desc, txt_dateModif, txt_nom,txt_prenom, txt_sexe, txt_adre, txt_cin, txt_tel, txt_mail, txt_naiss, txt_ss, txt_tail, txt_poi, ch_allerg, ch_path;
   @FXML
    private Button btn_valider;
   
private final Connection cnx;    
private PreparedStatement ste;
public ModifierFichMedController() {
    MyConnection bd=MyConnection.getInstance();
    cnx=bd.getCnx();
}
ObservableList<FicheMedicale> listFich = FXCollections.observableArrayList();
@Override
public void initialize(URL url, ResourceBundle rb) {

}      

public void inflateUI(FicheMedicale fichMed) throws SQLException {
   
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // fich table
        lb_num.setText(Integer.toString(fichMed.getId())); // instead of the id id i neef to get the nam        
        txt_desc.setText(fichMed.getDescription());
        ch_allerg.setText(fichMed.getAllergies());
        ch_path.setText(fichMed.getPathologie());
        txt_dateCrea.setText(dateFormat.format(fichMed.getDate_de_creation()));
        txt_dateModif.setText(dateFormat.format(fichMed.getDate_de_modification()));
        // fiche user
       
}
 
@FXML
void Valider(ActionEvent event) throws IOException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
        if(txt_desc.getText().isEmpty() || ch_allerg.getText().isEmpty() || ch_path.getText().isEmpty() || txt_dateModif.getText().isEmpty()) { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir les champs vides");
            alert.showAndWait();  
        } else {
            // create a new FicheMedicale object with the updated values
            FicheMedicale fich = new FicheMedicale(
                    Integer.parseInt(lb_num.getText()),
                    txt_desc.getText(),
                    ch_allerg.getText(),
                    ch_path.getText(),
                    dateFormat.parse(txt_dateCrea.getText())
                    //new Date(),
                    //fichMed.getPatientId()
            );
            
            // update the database with the new values
            FicheMedicaleService fichService = new FicheMedicaleService();
            fichService.editerFichMedical(fich);            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MODIFICATION AVEC SUCCES");
            alert.setHeaderText(null);
            alert.setContentText("Fiche medicale modifiée avec succès");
            alert.showAndWait();

            // close the current window
            Stage stage = (Stage) btn_valider.getScene().getWindow();
            stage.close(); 
        }
    } catch(Exception ex){
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes, Veuillez les verifiers ",ButtonType.CLOSE);
        alert.showAndWait();
        // open the "afficherConsultation" page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficherConsultation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setScene(scene);
        stage2.show();
    }
}

@FXML
void Reset(ActionEvent event) {    
    txt_desc.setText("");
    ch_allerg.setText("");
    ch_path.setText("");
    txt_dateModif.setText("");
}
}
