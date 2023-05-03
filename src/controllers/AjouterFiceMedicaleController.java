/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author MBM info
 */
public class AjouterFiceMedicaleController implements Initializable {
    @FXML
    private TextField txt_desc,txt_ss, txt_tel, txt_sexe, txt_prenom, txt_nom, txt_naiss, txt_mail, txt_dateCrea, txt_cin, txt_adre;
    @FXML
    private ChoiceBox<String> ch_allerg, ch_path;
    @FXML
    private Button BT_reset, btn_valider;
    
ObservableList<String> allergiesList = FXCollections.observableArrayList("Pollen","Dust","Peanuts","Shellfish","Mold");
ObservableList<String> pathologiesList = FXCollections.observableArrayList("Asthma", "Diabetes", "Hypertension", "Cancer", "Arthritis");

private final Connection cnx;
private PreparedStatement pstmt;   
public AjouterFiceMedicaleController() {
    MyConnection bd = MyConnection.getInstance();
    cnx=bd.getCnx();
}
@Override
public void initialize(URL url, ResourceBundle rb) {
txt_dateCrea.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
ch_allerg.setItems(allergiesList);
ch_path.setItems(pathologiesList);
// event when writing the cin of the patient
txt_cin.setOnKeyPressed(event -> {
    if (event.getCode() == KeyCode.ENTER) {
        String cin = txt_cin.getText();
        try {
            // Retrieve patient information from the database using the CIN entered
            String query = "SELECT email , nom, prenom, adresse, num_tel, date_de_naissance, sexe, num_securite_sociale " +
               "FROM user " +
               "WHERE roles LIKE '[\"ROLE_PATIENT\"]' AND cin=?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, cin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String mail = resultSet.getString("email");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adres = resultSet.getString("adresse");
                String tel = resultSet.getString("num_tel");
                String naiss = resultSet.getString("date_de_naissance");
                String sexe = resultSet.getString("sexe");
                String nss = resultSet.getString("num_securite_sociale");
                    txt_nom.setText(nom);
                    txt_prenom.setText(prenom);
                    txt_tel.setText(tel);
                    txt_sexe.setText(sexe);
                    txt_ss.setText(nss);  
                    txt_naiss.setText(naiss);
                    txt_mail.setText(mail);  
                    txt_adre.setText(adres);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Patient not found");
                alert.setContentText("Patient not found in the database.");
                alert.showAndWait();
                txt_nom.clear();
                txt_prenom.clear();
                txt_tel.clear();
                txt_sexe.clear();
                txt_ss.clear();  
                txt_naiss.clear();
                txt_mail.clear();  
                txt_adre.clear();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }}});    
}       

@FXML
private void Valider(ActionEvent event) throws Exception {
    // Get the selected values from the ChoiceBoxes
    String allergie = ch_allerg.getSelectionModel().getSelectedItem();
    String pathologie = ch_path.getSelectionModel().getSelectedItem();
    String desc = txt_desc.getText();
    // Get the current date and time in the local time zone and format it as a string
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);   
    // Add the selected values and the current date to your database
    String query = "INSERT INTO fiche_medicale (description, allergies, pathologie, date_de_creation, date_de_modification) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement preparedStatement = cnx.prepareStatement(query);
    preparedStatement.setString(1, desc);
    preparedStatement.setString(2, allergie);
    preparedStatement.setString(3, pathologie);
    preparedStatement.setString(4, formattedDate);
    preparedStatement.setString(5, formattedDate); // provide the current date for the date_de_modification column
    preparedStatement.executeUpdate();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setContentText("The selected values have been added to the database.");
    alert.showAndWait();
    // close the current stage
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();
    // open the "afficherConsultation" page
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/afficherConsultation.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage2 = new Stage();
    stage2.setScene(scene);
    stage2.show();
    
}

 @FXML
private void Reset (ActionEvent event)  {    
        txt_ss.setText("");
        txt_tel.setText("");
        txt_sexe.setText("");
        txt_prenom.setText("");
        txt_nom.setText("");
        txt_naiss.setText("");
        txt_mail.setText("");
        txt_dateCrea.setText("");
        txt_cin.setText("");
        txt_adre.setText("");
        ch_allerg.setValue(null);
        ch_path.setValue(null);           
    }

}
