package controllers;

import entities.Consultation;
import entities.RendezVous;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import services.ConsultationService;
import utils.MyConnection;

public class AjouterConsultationController implements Initializable {

    @FXML
    private Label lb_consltId;
    @FXML
    private TextField txt_cin, txt_nom, txt_tel, txt_dateRDV, txt_hRDV, txt_poid, txt_taill, txt_imc, txt_temp, txt_press, txt_freq, txt_tx, txt_mal, txt_trait, txt_px;
    @FXML
    private TextArea txt_obsv;
    @FXML
    private Button btn_valider;
  
    private final Connection cnx;
    private PreparedStatement pstmt;   
    public AjouterConsultationController() {
        MyConnection bd = MyConnection.getInstance();
        cnx=bd.getCnx();
    }
//my AjouterConsultationController()  
 @Override
public void initialize(URL url, ResourceBundle rb) {   
    try {
        // Retrieve the last consult_id from the database
        String query = "SELECT MAX(consult_id) FROM consultation";
        PreparedStatement preparedStatement = cnx.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        int lastConsultId = 0;
        if (resultSet.next()) {
            lastConsultId = resultSet.getInt(1);
        }
        int nextConsultId = lastConsultId + 1;
        lb_consltId.setText(String.valueOf(nextConsultId));
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
// event when writing the cin of the patient it will show the patient name 
    txt_cin.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
            String cin = txt_cin.getText();
            try {
                // Retrieve patient information from the database using the CIN entered
                String query = "SELECT u.nom, u.num_tel, r.date, r.heure_debut " +
               "FROM user u " +
               "INNER JOIN rendez_vous r ON r.patient_id = u.id " +
               "WHERE u.roles LIKE '[\"ROLE_PATIENT\"]' AND u.cin = ?";
                PreparedStatement preparedStatement = cnx.prepareStatement(query);
                preparedStatement.setString(1, cin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String tel = resultSet.getString("num_tel");
                    String date = resultSet.getString("date");
                    String heur = resultSet.getString("heure_debut");

                    txt_nom.setText(nom);
                    txt_tel.setText(tel);
                    txt_dateRDV.setText(date);
                    txt_hRDV.setText(heur);
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Patient not found");
                    alert.setContentText("Patient not found in the database.");
                    alert.showAndWait();
                    txt_nom.clear();
                    txt_tel.clear();
                    txt_dateRDV.clear();
                    txt_hRDV.clear();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });    
    //event to calculate the imc after entering the values of poid and taille
        txt_poid.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.isEmpty() && !txt_taill.getText().isEmpty()) {
            calculateIMC();
        }});
        txt_taill.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.isEmpty() && !txt_poid.getText().isEmpty()) {
            calculateIMC();
        } });
}    
private void calculateIMC() {
    try {
        float poid = Float.parseFloat(txt_poid.getText());
        float tail = Float.parseFloat(txt_taill.getText());
        if (poid <= 0 || tail <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Les valeurs de poids et taille doivent être positives et non nulles.");
            alert.showAndWait();
            return;
            }
            double imc = poid / (tail * tail);
            String imcString = String.format("%.2f", imc);
            imcString = imcString.replace(',', '.'); // replace comma with period
            txt_imc.setText(imcString);
    }catch (NumberFormatException e) {
        // Show an error message if the entered values are not numeric
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Les valeurs de poids et taille doivent être des nombres.");
        alert.showAndWait();
    }
}
@FXML
    private void Valider(ActionEvent event) throws Exception {
       try{
        if(txt_poid.getText().equals("") && (txt_taill.getText().equals("")) && (txt_imc.getText().equals(""))) {   
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
        }else{    
        Consultation c = new Consultation (Integer.parseInt(lb_consltId.getText()), 
            Float.parseFloat(txt_poid.getText()), Float.parseFloat(txt_taill.getText()),
            Float.parseFloat(txt_imc.getText()), Float.parseFloat(txt_temp.getText()),
            Float.parseFloat(txt_px.getText()), Float.parseFloat(txt_press.getText()),
            Float.parseFloat(txt_freq.getText()), Float.parseFloat(txt_tx.getText()),
            txt_mal.getText(), txt_trait.getText(),txt_obsv.getText());
        
        RendezVous r = new RendezVous();
        ConsultationService consultService = new ConsultationService();
        consultService.ajouterConsultation(c,r);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AJOUT AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Consultation ajouté avec succès");
        alert.showAndWait();

        // close the current window
        Stage currentStage = (Stage) btn_valider.getScene().getWindow();
        currentStage.close();

        // open the "afficherConsultation" page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficherConsultation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setScene(scene);
        stage2.show();
    }
   } catch(Exception ex){
       ex.printStackTrace();
       Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes, Veuillez les verifiers ",ButtonType.CLOSE);
       alert.showAndWait();
       
    }
}
    
//************************* buton reset *********************
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


