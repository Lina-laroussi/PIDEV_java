package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.MyConnection;

public class DashbordMedecinController implements Initializable {

    @FXML
    private ImageView imgDoc;
    @FXML
    private TextField Txt_nom, Txt_special, Txt_adress, Txt_mail, Txt_tel, nb_Med, nb_Tot_Consult, Rev;    
    @FXML
    private BarChart<String, Number> barChart;    
    
    private final Connection cnx;
    private PreparedStatement pste;
    
    public DashbordMedecinController() {
        MyConnection bd = MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              
//        nb_Med.setText("3");
        String query = "SELECT u.prenom, u.specialite, u.adresse, u.email, u.num_tel, u.image FROM User u WHERE u.roles LIKE '[\"medcin\"]' ";
        try {
            
            pste = cnx.prepareStatement(query);
            ResultSet rs = pste.executeQuery();
            if(rs.next()) {
                String nom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                String adresse = rs.getString("adresse");
                String email = rs.getString("email");
                String num_tel = rs.getString("num_tel");
                
                if (rs.next()) {
                    InputStream inputStream = rs.getBinaryStream("image");
                    // Use the inputStream to read the image data
                }     
                Txt_nom.setText(nom);
                Txt_special.setText(specialite);
                Txt_adress.setText(adresse);
                Txt_mail.setText(email);
                Txt_tel.setText(num_tel);
                
                pste.close();
                rs.close();
            }
            
        } catch(SQLException ex) {
            System.out.println("Error while fetching doctor information: " + ex.getMessage());
        }
        //******* stat****************************************************************************************************
        try {
            String queryStat = "SELECT COUNT(*) AS nbConsultations, SUM(prix) AS totalPrix FROM consultation";
            pste = cnx.prepareStatement(queryStat);
            ResultSet rs = pste.executeQuery();
            if (rs.next()) {
                nb_Tot_Consult.setText(Integer.toString(rs.getInt("nbConsultations")));
                Rev.setText(Double.toString(rs.getDouble("totalPrix")));
            }
            pste.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
//------- chart -----------------------------------------------------------------------------------------------------
       try {
            String queryChartData = "SELECT date, COUNT(*) AS nbConsultations FROM rendez_vous GROUP BY date";
            pste = cnx.prepareStatement(queryChartData);
            ResultSet rs = pste.executeQuery();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Consultations par jour");
            while (rs.next()) {
                String date = rs.getString("date");
                int nbConsultations = rs.getInt("nbConsultations");
                series.getData().add(new XYChart.Data<>(date, nbConsultations));
            }
            barChart.getData().add(series);
            pste.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    // ************************ BUTTON CONSULTATION*********************************
    @FXML
    private void afficherConsultation(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AfficherConsultation.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}

