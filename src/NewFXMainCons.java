
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewFXMainCons extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("./gui/DashbordMedecin.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("MedCare");
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
