/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.calendarfx.view.CalendarView;
/**
 * FXML Controller class
 *
 * @author rouai
 */
public class CalendarFxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CalendarView calendarView = new CalendarView();
    }    
    
}
