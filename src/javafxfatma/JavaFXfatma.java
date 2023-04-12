/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxfatma;

import Connextion.MyConnection;
import Service.CategorieService;
import Service.ProduitService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.categorie;
import models.produit;

/**
 *
 * @author Asus
 */
public class JavaFXfatma extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
       
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/produit.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         // TODO code application logic here
         MyConnection connx = MyConnection.getInstance();
       
        launch(args);
    }
    
}
