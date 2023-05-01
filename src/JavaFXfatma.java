/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import services.CategorieService;
import services.IService;
import services.ProduitService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import entities.categorie;
import entities.produit;
import utils.MyConnection;

/**
 *
 * @author Asus
 */
public class JavaFXfatma extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
       
   //Parent root = FXMLLoader.load(getClass().getResource("/GUI/produit.fxml"));
   //Parent root = FXMLLoader.load(getClass().getResource("/GUI/Categorie.fxml"));
      Parent root = FXMLLoader.load(getClass().getResource("/GUI/FrontProduit.fxml"));
    // Parent root = FXMLLoader.load(getClass().getResource("/GUI/FrontCategorie.fxml"));
//        
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
