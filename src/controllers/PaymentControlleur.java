package controllers;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class PaymentControlleur implements Initializable {
 

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cvvCodeField;

    @FXML
    private TextField dateField;

    @FXML
    void submitPayment(ActionEvent event) {
String cardNumber = cardNumberField.getText().trim();
String cvvCode = cvvCodeField.getText().trim();
String date = dateField.getText().trim();

if (cardNumber.isEmpty() || !cardNumber.matches("^\\d{16}$")) {
    showAlert("Numéro de carte invalide. Veuillez saisir un numéro de carte valide (16 chiffres).", AlertType.ERROR);
    return;
}

if (cvvCode.isEmpty() || !cvvCode.matches("^\\d{3}$")) {
    showAlert("Code CVC invalide. Veuillez saisir un code CVC valide (3 chiffres).", AlertType.ERROR);
    return;
}

if (date.isEmpty() || !date.matches("^\\d{2}/\\d{2}$")) {
    showAlert("Date d'expiration invalide. Veuillez saisir une date d'expiration valide au format MM/AA.", AlertType.ERROR);
    return;
}

// Vérification de la date d'expiration
String[] dateParts = date.split("/");
int expMonth = Integer.parseInt(dateParts[0]);
int expYear = Integer.parseInt(dateParts[1]);
if (expYear < LocalDate.now().getYear() % 100 || expMonth < 1 || expMonth > 12) {
    showAlert("Date d'expiration invalide. Veuillez saisir une date d'expiration valide au format MM/AA.", AlertType.ERROR);
    return;
}

// Le paiement est valide, effectuez le traitement ici ...
// ...

showAlert("Paiement effectué avec succès !", AlertType.INFORMATION);


}

private void showAlert(String message, AlertType alertType) {
Alert alert = new Alert(alertType);
alert.setHeaderText(null);
alert.setContentText(message);
alert.showAndWait();
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



    }


  

