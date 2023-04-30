

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controllers.AfficherPlanningController;
import controllers.AjouterPlanningController;
import controllers.MedecinDashbordController;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author rouai
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage){
       /* try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./gui/MedecinDashbord.fxml"));
        AnchorPane root = loader.load();
        MedecinDashbordController controller = loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());

        }*/
         try {
            Parent root = FXMLLoader.load(getClass().getResource("./gui/MedecinDashbord.fxml"));
            Scene scene = new Scene(root);
            //AfficherPlanningController controller = loader.getController();

            primaryStage.setScene(scene);
           // primaryStage.setScene(new Scene(new FullCalendarView(LocalDate.now()).getView()));
            primaryStage.setTitle("Medcare");
            primaryStage.show();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
