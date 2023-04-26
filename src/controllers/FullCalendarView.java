package controllers;
import entities.Planning;
import entities.RendezVous;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.PlanningService;
import services.RendezVousService;
import entities.RendezVous;
import java.text.SimpleDateFormat;
import javafx.scene.input.DataFormat;


public class FullCalendarView{
    private LocalDate currentDate;
    private Text calendarTitle;
    private GridPane calendar;
    private List<AnchorPaneNode> allCalendarDays = new ArrayList<>();
    private VBox view;
    public RendezVousService rdvService = new RendezVousService();
    public PlanningService pService = new PlanningService();
    SimpleDateFormat heureFormatPicker = new SimpleDateFormat("HH:mm");

   private static final DataFormat RDV_FORMAT = new DataFormat("application/x-java-serialized-object");


    public FullCalendarView(LocalDate date) {
        currentDate = date;
        // Create the calendar grid pane
        calendar = new GridPane();
        calendar.setPrefSize(600, 600);
       calendar.setGridLinesVisible(true);
       calendar.setStyle(" -fx-border-width: 1px; -fx-opacity: 0.9;");

      // calendar.setStyle("-fx-background-color: white; -fx-font-size: 10pt;");

        StackPane calendarWrapper = new StackPane(calendar);
        calendarWrapper.setPadding(new Insets(10));
        // Create 7 columns with anchor panes for the calendar
        for (int i = 0; i < 7; i++) {
            AnchorPaneNode ap = new AnchorPaneNode();
            ap.setPrefSize(400,600);
            calendar.add(ap, i, 1);
            allCalendarDays.add(ap);
        }
        
        // Create days of the week labels
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        for (int i = 0; i < daysOfWeek.length; i++) {
            Text dayLabel = new Text(daysOfWeek[i].getDisplayName(TextStyle.SHORT, Locale.getDefault()));
            calendar.add(dayLabel, i, 0);
            dayLabel.setStyle("-fx-font-weight: bold;");
            GridPane.setHalignment(dayLabel, HPos.CENTER);

        }
        
        // Create calendarTitle and buttons to change current week
        calendarTitle = new Text();
        Button previousWeek = new Button("<<");
        previousWeek.setOnAction(e -> previousWeek());
        Button nextWeek = new Button(">>");
        nextWeek.setOnAction(e -> nextWeek());
        HBox titleBar = new HBox(previousWeek, calendarTitle, nextWeek);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar();
        // Create the week view
        
        
       view = new VBox(titleBar,calendarWrapper);
    }

    private void previousWeek() {
        currentDate = currentDate.minusWeeks(1);
        populateCalendar();
    }

    private void nextWeek() {
        currentDate = currentDate.plusWeeks(1);
        populateCalendar();
    }
    
    public VBox getView() {
        return view;
    }


    private void populateCalendar() {
        // Dial back the day until it is SUNDAY
        LocalDate calendarDate = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            VBox vbox = new VBox();
            vbox.setSpacing(5.0); // Set spacing between nodes
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            vbox.getChildren().add(txt);


            ap.getChildren().add(vbox);
            ap.setDate(calendarDate);
            Planning p =pService.findByDate(ap.getDate());
            ap.setPlanningId(p.getIdPlanning());
            if(p.getIdPlanning()==0){
              ap.setStyle("-fx-background-color: white;");
            }else if(p.getIdPlanning()!=0){
              ap.setStyle("-fx-background-color: rgba(143, 223, 130, 0.3);");
              ap.setOnMouseClicked(e -> {
        
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterRdv.fxml"));
                Parent parent;
                  try {
                      parent = loader.load();
                 
                AjouterRdvController controller = (AjouterRdvController) loader.getController();
                controller.setPlanningId(p);
                controller.setDate(ap.getDate());
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Ajouter rendez vous");
                stage.setScene(new Scene(parent));
                stage.show();
                stage.setOnHiding((t) -> {
                    if(controller.getRdv()!=null){
                    Button event = new Button(heureFormatPicker.format(controller.getRdv().getHeureDebut())+"-"+heureFormatPicker.format(controller.getRdv().getHeureFin())+"\n"+controller.getRdv().getEtat());

                //Button event = new Button(heureFormatPicker.format(rdv1.getHeureDebut())+"-"+heureFormatPicker.format(rdv1.getHeureFin())+"\n"+rdv1.getEtat());
                if(controller.getRdv().getEtat().equals("en attente")){
                    event.setStyle("-fx-background-color: rgb(244, 164, 66);-fx-background-radius: 10px;-fx-text-fill: white;");
                }else if(controller.getRdv().getEtat().equals("confirmé")){
                    event.setStyle("-fx-background-color: rgb(130, 205, 71);-fx-background-radius: 10px;-fx-text-fill: white;");
                }
                else if(controller.getRdv().getEtat().equals("archivé")){
                    event.setStyle("-fx-background-color: red;-fx-background-radius: 10px;-fx-text-fill: white;");
                }                    vbox.getChildren().add(event);
                    }
            });
                 } 
                catch (IOException ex) {
                      System.out.println("eeeeeeeeeeeeeeeeee");
                 }
                        
              }
              );
            }

            ap.setTopAnchor(vbox, 5.0);
            ap.setLeftAnchor(vbox, 5.0);

            try {
                List<RendezVous>rdvList = rdvService.findByDate(calendarDate);
            
            for (RendezVous rdv: rdvList) { // Add two event nodes for demonstration purposes
                Button event = new Button(rdv.getHeureDebut().toString().substring(0, 5)+"-"+rdv.getHeureFin().toString().substring(0, 5)+"\n"+rdv.getEtat());
                if(rdv.getEtat().equals("en attente")){
                    event.setStyle("-fx-background-color: rgb(244, 164, 66);-fx-background-radius: 10px;-fx-text-fill: white;");
                }else if(rdv.getEtat().equals("confirmé")){
                    event.setStyle("-fx-background-color: rgb(130, 205, 71);-fx-background-radius: 10px;-fx-text-fill: white;");
                }
                else if(rdv.getEtat().equals("archivé")){
                    event.setStyle("-fx-background-color: red;-fx-background-radius: 10px;-fx-text-fill: white;");
                }
            event.setOnDragDetected(e -> {
                Dragboard db = event.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(event.getText());
                content.put(RDV_FORMAT, rdv);
                db.setContent(content);
                e.consume();

                // Remove event from current anchor pane
                ((VBox) event.getParent()).getChildren().remove(event);
                
            });
                vbox.getChildren().add(event);
            }
            } catch (SQLException ex) {
                System.out.println("test findByDate"+ex.getMessage());
            }
            ap.setOnDragOver(e -> {
                if (e.getGestureSource() != ap && e.getDragboard().hasString() && ap.getPlanningId()!=0) {
                    e.acceptTransferModes(TransferMode.MOVE);
                }
                e.consume();
            });

       /* ap.setOnDragDropped(e -> {
             Dragboard db = e.getDragboard();
             boolean success = false;
             if (db.hasString()) {
                 Button event = new Button(db.getString());
                 ((VBox) ap.getChildren().get(0)).getChildren().add(event);
                 success = true;
             }
             e.setDropCompleted(success);
             e.consume();
         });*/
        ap.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasString()) {
               if(p.getIdPlanning()!=0){
                RendezVous rdv = (RendezVous) db.getContent(RDV_FORMAT);
                //load edit dialog
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EditerRdv.fxml"));
                Parent parent;
                try {
                parent = loader.load();
                 
                EditerRdvController controller = (EditerRdvController) loader.getController();
                //controller.setPlanningId(p);
                rdv.setIdPlanning(p.getIdPlanning());
                controller.inflateUI(rdv);
                controller.setDate(ap.getDate());
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Editer rendez vous");
                stage.setScene(new Scene(parent));
                stage.show();
                controller.setOnValiderAction(() -> {
                RendezVous rdv1 = controller.getRendezVous();

                Button event = new Button(heureFormatPicker.format(rdv1.getHeureDebut())+"-"+heureFormatPicker.format(rdv1.getHeureFin())+"\n"+rdv1.getEtat());
                if(rdv.getEtat().equals("en attente")){
                    event.setStyle("-fx-background-color: rgb(244, 164, 66);-fx-background-radius: 10px;-fx-text-fill: white;");
                }else if(rdv.getEtat().equals("confirmé")){
                    event.setStyle("-fx-background-color: rgb(130, 205, 71);-fx-background-radius: 10px;-fx-text-fill: white;");
                }
                else if(rdv.getEtat().equals("archivé")){
                    event.setStyle("-fx-background-color: red;-fx-background-radius: 10px;-fx-text-fill: white;");
                }
                ((VBox) ap.getChildren().get(0)).getChildren().add(event);                });               
                stage.setOnHiding((t) -> {
         
   
                });
                success = true;

                 } 
                catch (IOException ex) {
                      System.out.println("eeeeeeeeeeeeeeeeee");
                 }
            }
            e.setDropCompleted(success);
            e.consume();
            }
        });
       
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        LocalDate weekStart = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate weekEnd = weekStart.plusDays(6);
        calendarTitle.setText(weekStart.format(DateTimeFormatter.ofPattern("MMM d, yyyy")) + " - " + weekEnd.format(DateTimeFormatter.ofPattern("MMM d, yyyy")));
    }


}