/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ForgotPasswordController implements Initializable {

    @FXML
    private Button button_login;
    @FXML
    private TextField tf_email;
    @FXML
    private Button button_renvoi_code;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void loginAction(ActionEvent event) {
    }

    
    @FXML
    private void RenvoyerCode(ActionEvent event) {
    }
    
}
