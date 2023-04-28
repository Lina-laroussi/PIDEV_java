/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AdminService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author LINA
 */
public class VerifyCodePasswordController implements Initializable {

    @FXML
    private TextField tf_code;
    @FXML
    private Button button_code;
    @FXML
    private Button button_renvoyer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

  @FXML
    private void EnvoyerCode(ActionEvent event) throws IOException{
        
        
        AdminService adminservice = new AdminService();
        String userCode =tf_code.getText();
        try{
            User user = adminservice.getUserByCode(userCode);

            if (user.getEmail() == null)  {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("il semble que vous avez entré le mauvais code");
                alert.showAndWait();

            } else {
              
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("veuillez réintialiser votre mot de passe ");
                alert.showAndWait();


                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/ResetPassword.fxml"));
                Parent parent = loader.load();
                ResetPasswordController controller = (ResetPasswordController) loader.getController();
                controller.getUser(user);
                
                Scene sceneRegister = new Scene(parent);
                Stage stageRegister  = (Stage)((Node)event.getSource()).getScene().getWindow();
                stageRegister.hide();
                stageRegister.setScene(sceneRegister);
                stageRegister.show();
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }   
    }
    
    
}
