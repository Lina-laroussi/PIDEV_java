
package controllers;

import entities.FicheMedicale;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.FicheMedicaleService;

public class CardFichController implements Initializable {

    @FXML
    private Button BT_consul, showMenu;
    @FXML
    private Circle circleImg;
    @FXML
    private Label nameLb, lb_allerg, lb_patho, lb_dateCons ;
    @FXML
    private Node card;
    
    private FicheMedicale ficheMedicale;

@Override
public void initialize(URL url, ResourceBundle rb) {
    
}  
void setFicheMedicale(FicheMedicale ficheMedicale) {    
    this.ficheMedicale = ficheMedicale;
    Image im = new Image(getClass().getResourceAsStream("../gui/images/pngegg.png")); // Replace with the path to your actual image file
    circleImg.setFill(new ImagePattern(im)); 
    circleImg.setStroke(Color.TRANSPARENT);
    nameLb.setText(Integer.toString(ficheMedicale.getId()));
    //nameLb.setText(ficheMedicale. getPatient().getNom() + " " + ficheMedicale.getPatient().getPrenom());
    lb_allerg.setText(ficheMedicale.getAllergies());
    lb_patho.setText(ficheMedicale.getPathologie());
    lb_dateCons.setText(ficheMedicale.getDate_de_creation().toString());
}
//----------------------editer buton from the context menu ----------------------------------    
@FXML
void editerFich(ActionEvent event) throws SQLException {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModifierFichMed.fxml"));
        Parent parent = loader.load();
        ModifierFichMedController controller = (ModifierFichMedController) loader.getController();
        controller.inflateUI(ficheMedicale); // pass the selected FicheMedicale to the controller
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Modifier Fiche Medicale");
        stage.setScene(new Scene(parent));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


//----------------------supprimer buton from the context menu ----------------------------------    
@FXML
void delFich(ActionEvent event) throws SQLException {  
    FicheMedicale ficheMedicale = getFicheMedicale();
    if (ficheMedicale != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette fiche medicale ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FicheMedicaleService fichService = new FicheMedicaleService();
            fichService.deleteFicheMedicale(ficheMedicale.getId());            
        }
    }
}
private FicheMedicale getFicheMedicale() {
    if (ficheMedicale != null) {
        return ficheMedicale;
    } else {
        return null;
    }
}
/*
private int getFicheMedicaleId() {
        return 0;
    
    }*/
 //---------------------- buton Consultation----------------------------------    
@FXML
void AjouterConsultation(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AjouterConsultation.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
} 

    

    
}
