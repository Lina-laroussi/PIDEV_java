

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import controllers.CardController;
import controllers.Choose_profileController;
import controllers.DashboardAdminController;
import controllers.LoginController;
import java.io.IOException;
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
import sun.applet.Main;


public class PIDEV extends Application {
    
    @Override
    public void start(Stage primaryStage)  {
        
     
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./gui/choose_profile.fxml"));
            AnchorPane root = loader.load();
            Choose_profileController controller = loader.getController();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
       
               
        
         // load the FXML file
       /* Parent root = FXMLLoader.load(getClass().getResource("RegisterPatient.fxml"));

        // create a new scene with the loaded FXML file as the root node
        Scene scene = new Scene(root, 600, 400);

        // set the scene as the primary stage's scene
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

   

    public static void main(String[] args) {
        launch(args);
    }
    
}
