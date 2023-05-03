
package controllers;

import entities.Consultation;
import entities.FicheMedicale;
import entities.Ordonnance;
import entities.RendezVous;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ConsultationService;
import services.FicheMedicaleService;
import services.OrdonnanceService;
import utils.MyConnection;



public class AfficherConsultationController implements Initializable {    
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabConsult, tabOrd;
    //*************  consultation *********
    @FXML
    private TableView<Consultation> table_consult;
    @FXML
    private TableColumn<RendezVous, Integer> patient_name, mal,trait;  
    @FXML
    private TableColumn<Consultation, Float> taille, px, poids, temp; 
    
    //*************  Ordonnance *********
    @FXML
    private TableView<Ordonnance> tab_Ord;
    @FXML
    private TableColumn<Ordonnance, String> code_ordonnance, desc, medicaments, dosage;
    @FXML
    private TableColumn<Ordonnance, Integer> nombre_jours;
    @FXML
    private TableColumn<Ordonnance, Date> date_de_creation;
    @FXML
    private Button BT_DelOrd, BT_ModifOrd, BT_print, BT_AjoutOrd, BT_AjoutConsult, bt_ajoutFich;
    
    //*************  Fich medicale ********
     @FXML
    private TilePane tilePane;
    
private Consultation selectedConsultation; 
private final Connection cnx;
private PreparedStatement pste;   
public AfficherConsultationController() {
    MyConnection bd = MyConnection.getInstance();
    cnx=bd.getCnx();
}   
@Override
public void initialize(URL url, ResourceBundle rb) {
// ----------------------------------------------------------------------------------tab ordonnance ---------------
    List<Ordonnance> listOrdonnance ;
    OrdonnanceService ordnltServ = new OrdonnanceService();
    listOrdonnance= ordnltServ.showOrdonnance(); 
    code_ordonnance.setCellValueFactory(new PropertyValueFactory<>("code_ordonnance"));  
    desc.setCellValueFactory(new PropertyValueFactory<>("description"));
    medicaments.setCellValueFactory(new PropertyValueFactory<>("medicaments"));
    dosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
    nombre_jours.setCellValueFactory(new PropertyValueFactory<>("nombre_jours"));
    date_de_creation.setCellValueFactory(new PropertyValueFactory<>("date_de_creation"));
    tab_Ord.getItems().setAll(listOrdonnance);
    
// -------------------------------------------------------------------------------  tab consultation  ---------------
    try {
        loadData();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    table_consult.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            BT_AjoutOrd.setDisable(false);
            selectedConsultation = newSelection;
        } else {
            BT_AjoutOrd.setDisable(true);
            selectedConsultation = null;         
        }
    });
//------------------------------------------------------------------------------------tab fich medicale ---------------
    try {
            displayFicheMedicaleCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   public void displayFicheMedicaleCards() throws IOException {
    // Retrieve fiche medicale data from the database
    List<FicheMedicale> listFichMed ;
    FicheMedicaleService fichServ = new FicheMedicaleService();
    listFichMed= fichServ.showFicheMedicale();     
    // Clear the VBox to remove any existing cards
    tilePane.getChildren().clear();
    // Create a card for each fiche medicale and add it to the VBox
    for (FicheMedicale ficheMedicale : listFichMed) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CardFich.fxml"));
        Node card = loader.load();
        card.setId(Integer.toString(ficheMedicale.getId()));
        CardFichController cardController = loader.getController();
        cardController.setFicheMedicale(ficheMedicale);
        tilePane.getChildren().add(card);
    }
}
//------------------------------------------------------------------------Other methodes -------------
public void loadData() throws SQLException {
    List<Consultation> listConsultation;
    ConsultationService consultServ = new ConsultationService();
    listConsultation = consultServ.showConsultation();
    // Join consultation table with user table to get patient name
String query = "SELECT u.prenom AS patient_name, c.consult_id, c.poids, c.taille, "
              + "c.temperature, c.maladie, c.traitement, c.prix, rdv.patient_id " +
                "FROM consultation c " +
                "JOIN rendez_vous rdv ON c.consult_id = rdv.consultation_id " +
                "JOIN user u ON rdv.patient_id = u.id";
    PreparedStatement preparedStatement = cnx.prepareStatement(query);
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
        int idConslt = resultSet.getInt("consult_id");
        for (Consultation consultation : listConsultation) {
            if (consultation.getIdConslt() == idConslt) {
                String patientName = resultSet.getString("patient_name");
                consultation.setPatientName(patientName);
                break;}}
    }
    // Set the cell value factory and items of the table
    patient_name.setCellValueFactory(new PropertyValueFactory<>("patientName"));
    poids.setCellValueFactory(new PropertyValueFactory<>("poids"));
    taille.setCellValueFactory(new PropertyValueFactory<>("taille"));
    temp.setCellValueFactory(new PropertyValueFactory<>("temperature"));
    mal.setCellValueFactory(new PropertyValueFactory<>("maladie"));
    trait.setCellValueFactory(new PropertyValueFactory<>("traitement"));
    px.setCellValueFactory(new PropertyValueFactory<>("prix"));    
    table_consult.getItems().setAll(listConsultation);
}
public void updateOrdonnanceList(int consultationId) {
    // get the list of ordonnances for the given consultation from the database
    OrdonnanceService ordService = new OrdonnanceService();
    List<Ordonnance> ordonnances = ordService.showOrdonnance();
    // update the table view
    ObservableList<Ordonnance> ordonnancesObservable = FXCollections.observableArrayList(ordonnances);
    tab_Ord.setItems(ordonnancesObservable);
}

//---------------------------------------- buton ajouter -------------------------------------------   
@FXML
private void AjouterConsultation(ActionEvent event) throws IOException {
    // Close the AfficherConsultation window
    Stage afficherConsultationStage = (Stage) BT_AjoutConsult.getScene().getWindow();
    afficherConsultationStage.close();
    //show the ajouter consultation window
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AjouterConsultation.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage(StageStyle.DECORATED);
    stage.setTitle("Ajouter Ordonnance");
    stage.setScene(new Scene(root));
    stage.show();
}    
@FXML
private void ajouterOrdonnance(ActionEvent event) throws IOException { 
    Consultation selectedForEdit = table_consult.getSelectionModel().getSelectedItem();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AjouterOrdonnance.fxml"));
    Parent parent = loader.load();
    AjouterOrdonnanceController controller = (AjouterOrdonnanceController) loader.getController();
    controller.setSelectedConsultation(selectedConsultation);
    Stage stage = new Stage(StageStyle.DECORATED);
    stage.setTitle("Ajouter Ordonnance");
    stage.setScene(new Scene(parent));
    stage.showAndWait(); // use showAndWait() to wait for the window to close before executing the next line
    //updateOrdonnanceList(); // call the method to update the table view
}
@FXML
private void AjouterFich(ActionEvent event) throws IOException {
    // Close the AfficherConsultation window
    Stage afficherConsultationStage = (Stage) bt_ajoutFich.getScene().getWindow();
    afficherConsultationStage.close();
    //show the ajouter consultation window
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AjouterFiceMedicale.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage(StageStyle.DECORATED);
    stage.setTitle("Ajouter nouveau fiche Medicale");
    stage.setScene(new Scene(root));
    stage.show();

}


//----------------------------------------- buton modifier --------------------------------------------
@FXML
private void editerConsultation(ActionEvent event) throws IOException {
    Consultation selectedForEdit = table_consult.getSelectionModel().getSelectedItem();   
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModifierConsultation.fxml"));
    Parent parent = loader.load();
    ModifierConsultationController controller = (ModifierConsultationController) loader.getController();
    controller.inflateUI(selectedForEdit);
    Stage stage = new Stage(StageStyle.DECORATED);
    stage.setTitle("Modifier Consultation");
    stage.setScene(new Scene(parent));
    stage.show();
}    
@FXML
private void editerOrdonnance(ActionEvent event) throws IOException {
    Ordonnance selectedForEdit = tab_Ord.getSelectionModel().getSelectedItem();   
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ModifierOrdonnance.fxml"));
    Parent parent = loader.load();
    ModifierOrdonnanceController controller = (ModifierOrdonnanceController) loader.getController();
    controller.inflateUI(selectedForEdit);
    Stage stage = new Stage(StageStyle.DECORATED);
    stage.setTitle("Modifier ordonnance");
    stage.setScene(new Scene(parent));
    stage.show();
}   
//----------------------------------------------- buton supprimer ---------------------------------------------
@FXML
 private void supprimerConsultation(ActionEvent event) throws IOException, SQLException {
    Consultation selectedForDelete = table_consult.getSelectionModel().getSelectedItem();
    System.out.println("selected consultation "+selectedForDelete.getIdConslt());
    ConsultationService service = new ConsultationService();
    service.deleteConsultation(selectedForDelete.getIdConslt());
    // Remove the selected consultation from the table view
    table_consult.getItems().remove(selectedForDelete);
    String title = "Succes de suppression";
    String message = "ARE YOU SURE YOU WANT TO DELETE THIS CONSULTATION";        
}
@FXML
private void supprimerOrdonnance(ActionEvent event) throws IOException, SQLException {
   Ordonnance selectedForDelete = tab_Ord.getSelectionModel().getSelectedItem();
   System.out.println("selected ordonnance "+selectedForDelete.getId());
   OrdonnanceService serviceOrd = new OrdonnanceService();
   serviceOrd.deleteOrdonnance(selectedForDelete.getId());
   // Remove the selected consultation from the table view
   tab_Ord.getItems().remove(selectedForDelete);

   String title = "Succes de suppression";
   String message = " THIS ordonnance has been deleted permanently";
}    
//--------------------------------------------------- buton telecharger --------------------------------------------
@FXML
 public void download() throws SQLException  {
    FileChooser fileChooser = new FileChooser();// Create a new FileChooser object
    fileChooser.setTitle("Save List Consultations");// Set the title of the dialog box       
    fileChooser.setInitialFileName("List Consultations.xlsx");// Set the default file name
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");  // Set the file extension filter
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(new Stage());// Get the file path from the user     
    try {
        createExcelFile(file.getAbsolutePath()); // Call the method to create the Excel file and pass the file path
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
}
private void createExcelFile(String filePath) throws IOException, SQLException {

    Workbook workbook = new XSSFWorkbook(); // Create a new sheet 
    Sheet sheet = workbook.createSheet("Consultations"); // Create a new sheet
    Row headerRow = sheet.createRow(0); // Create a header row
        headerRow.createCell(0).setCellValue("Poids");
        headerRow.createCell(1).setCellValue("Taille");
        headerRow.createCell(2).setCellValue("IMC");
        headerRow.createCell(3).setCellValue("Température");
        headerRow.createCell(4).setCellValue("Prix");
        headerRow.createCell(5).setCellValue("Pression artérielle");
        headerRow.createCell(6).setCellValue("Fréquence cardiaque");
        headerRow.createCell(7).setCellValue("Taux de glycémie");
        headerRow.createCell(8).setCellValue("Maladie");
        headerRow.createCell(9).setCellValue("Traitement");
        headerRow.createCell(10).setCellValue("Observation");            
        // Add data to the sheet
        int rowNum = 1;
        List<Consultation> listConsultation ;
        ConsultationService consultServ = new ConsultationService();
        listConsultation= consultServ.showConsultation();
        for (Consultation consultation : listConsultation) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(consultation.getPoids());
            row.createCell(1).setCellValue(consultation.getTaille());
            row.createCell(2).setCellValue(consultation.getImc());
            row.createCell(3).setCellValue(consultation.getTemperature());
            row.createCell(4).setCellValue(consultation.getPrix());
            row.createCell(5).setCellValue(consultation.getPression_arterielle());
            row.createCell(6).setCellValue(consultation.getFrequence_cardiaque());
            row.createCell(7).setCellValue(consultation.getTaux_glycemie());
            row.createCell(8).setCellValue(consultation.getMaladie());
            row.createCell(9).setCellValue(consultation.getTraitement());
            row.createCell(10).setCellValue(consultation.getObservation());
        }   // Auto-size the columns
        for (int i = 0; i < 11; i++) {
            sheet.autoSizeColumn(i);
        }  
    try (FileOutputStream fileOut = new FileOutputStream(filePath) // Save the workbook to the specified file path
    ) {
        workbook.write(fileOut);
        fileOut.close();
    }
} 
//------------------ buton Imprimer in afficherConsultationController-----------------------------------
@FXML
 private void printOrdonnance(ActionEvent event) throws IOException {
     Ordonnance selectedForEdit = tab_Ord.getSelectionModel().getSelectedItem();   
     FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/print.fxml"));
     Parent parent = loader.load();
     PrintController controller = (PrintController) loader.getController();
     controller.inflateUI(selectedForEdit);
     Stage stage = new Stage(StageStyle.DECORATED);
     stage.setTitle("Imprimer ordonnance");
     stage.setScene(new Scene(parent));
     stage.show();
 }   
}
