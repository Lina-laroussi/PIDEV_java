/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Consultation;
import entities.Ordonnance;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import utils.MyConnection;
//to generate the QrCode
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
/**
 * FXML Controller class
 *
 * @author MBM info
 */
public class PrintController implements Initializable {
    @FXML
    private Pane pan_Ord;   
    @FXML
    private Label num_ord, nomMed, dos, nbjrs;    
    @FXML
    private Text nomDoc, specDoc, adrssDoc, telDoc, nomPatient,prenomPatient, telPatient, dateOrdn;
    @FXML
    private TextArea obsvConsul;
    @FXML
    private ImageView qrCode;
    
    private Consultation consultation;
    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
private final Connection cnx;
private PreparedStatement pste;
public PrintController() {
    MyConnection bd = MyConnection.getInstance();
    cnx=bd.getCnx();
}
@Override
public void initialize(URL url, ResourceBundle rb) {       
String query = "SELECT u.prenom, u.specialite, u.adresse, u.num_tel FROM User u WHERE u.roles LIKE '[\"medcin\"]' ";      
try {     
    pste = cnx.prepareStatement(query);
    ResultSet rs = pste.executeQuery();
    if(rs.next()) {
        String prenom = rs.getString("prenom");
        String specialite = rs.getString("specialite");
        String adresse = rs.getString("adresse");
        String tel = rs.getString("num_tel");
        nomDoc.setText(prenom);
        specDoc.setText(specialite);
        adrssDoc.setText(adresse);
        telDoc.setText(tel);

        pste.close();
        rs.close();}
    }catch (SQLException ex) {
        ex.printStackTrace();
    }    
}
public void generateQRCode(String medicationInfo) {
    int width = 250;
    int height = 250;
    String fileType = "png";
    try {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new QRCodeWriter().encode(medicationInfo, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int grayValue = (bitMatrix.get(x, y) ? 0 : 1) & 0xff;
                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }
        String fileName = "medication_qr_code." + fileType;
        File outputFile = new File(fileName);
        ImageIO.write(image, fileType, outputFile);
        // Load the image file and set it as the image of the ImageView
        Image qrImage = new Image(outputFile.toURI().toString());
        qrCode.setImage(qrImage);
    } catch (WriterException | IOException e) {
        e.printStackTrace();
    }
}
public void inflateUI(Ordonnance ordonnance) {
    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //String formattedDate = dateFormat.format(ordonnance.getDate_de_creation());
    Date date = ordonnance.getDate_de_creation();
    String formattedDate = "";
    if (date != null) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = dateFormat.format(date);
        System.out.println("get the date format");
    }
    dateOrdn.setText(formattedDate);
    num_ord.setText(Integer.toString(ordonnance.getId()));
    nomMed.setText(ordonnance.getMedicaments());
    dos.setText(Integer.toString(ordonnance.getDosage()));
    nbjrs.setText(Integer.toString(ordonnance.getNombre_jours()));
    dateOrdn.setText(LocalDate.now().toString());
// Retrieve consultation ID from the selected ordonnance
   int consultationId = ordonnance.getConsult_id();
// Use consultation ID to retrieve patient information
    String queryPatient = "SELECT p.nom, p.prenom, p.num_tel FROM user p "
            + "INNER JOIN rendez_vous rdv ON rdv.patient_id = p.id "
        + "INNER JOIN consultation c ON c.consult_id = rdv.consultation_id "
        + "WHERE c.consult_id=?";
    try {
        pste = cnx.prepareStatement(queryPatient);
        pste.setInt(1, consultationId);
        ResultSet rs = pste.executeQuery();
        if (rs.next()) {
            String prenom = rs.getString("prenom");
            String nom = rs.getString("nom");
            String tel = rs.getString("num_tel");
            nomPatient.setText(nom);
            prenomPatient.setText(prenom);
            telPatient.setText(tel);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    // Retrieve observation from the consultation table
    String queryObservation = "SELECT observation FROM consultation WHERE consult_id=?";
    try {
        pste = cnx.prepareStatement(queryObservation);
        pste.setInt(1, consultationId);
        ResultSet rs = pste.executeQuery();
        if (rs.next()) {
            String observation = rs.getString("observation");
            obsvConsul.setText(observation);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    // QrCode
    String medicationInfo = "Medicament: " + ordonnance.getMedicaments() + ",\n Dosage: " + ordonnance.getDosage() + ",\n nombre de jour " + ordonnance.getNombre_jours();
    generateQRCode(medicationInfo);
}
   
// ------------------------ buton imprimer ---------------------    
@FXML
private void printOrdonnance(ActionEvent event) {

    PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                boolean success = job.printPage(pan_Ord); //replace yourNode with the node you want to print
                    if (success) {
                        job.endJob();
                    }
            }
    }
   
}
