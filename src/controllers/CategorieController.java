package controllers;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import entities.categorie;
import entities.produit;
import services.CategorieService;
import utils.MyConnection;

public class CategorieController implements Initializable {

    @FXML
    private TextField Description;

     @FXML
    private ChoiceBox<String> Etat;
    @FXML
    private TextField Marque;

    @FXML
    private TextField Nom;
    

    @FXML
    private TableColumn<categorie , String> col_Groupe_age;

    @FXML
    private TableColumn<categorie , String> col_description;

    @FXML
    private TableColumn<categorie , String> col_etat;

    @FXML
    private TableColumn<categorie , String> col_nom;

      @FXML
    private ChoiceBox<String> Groupeage;
    @FXML
    private TableColumn<categorie , String> col_Marque;

    @FXML
    private TableView<categorie> table_categories;
private String[] dispo = {"disponible" , "non disponible" };
private String[] age = {"0 - 4 ans" , "5 - 14 ans " , "15 - 59 ans " , "+60 ans " };

   
private ObservableList<categorie> categories = FXCollections.observableArrayList();


 @FXML
private void Ajouter(ActionEvent event) {
   // Vérifier si tous les champs sont remplis
if(Nom.getText().isEmpty() || Description.getText().isEmpty() || Etat.getValue().isEmpty() || Marque.getText().isEmpty() || Groupeage.getValue().isEmpty()) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("Tous les champs doivent être remplis");
    alert.showAndWait();
    return;
}
// Vérifier que la description contient plus de 4 caractères
String description = Description.getText();
if (description.length() <= 4) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("La description doit contenir plus de 4 caractères");
    alert.showAndWait();
    return;
}
// Mettre le nom et la marque en majuscule
String nom = Nom.getText();
String marque = Marque.getText();
nom = nom.substring(0, 1).toUpperCase() + nom.substring(1);
marque = marque.substring(0, 1).toUpperCase() + marque.substring(1);

// Récupérer les autres données entrées par l'utilisateur
String etat = Etat.getValue();
String groupe_age = Groupeage.getValue();
// Vérifier si la catégorie existe déjà dans la base de données
boolean existe = false;
for (categorie c : categories) {
    if (c.getNom().equals(nom)) {
        existe = true;
        break;
    }
}

if (existe) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText("Cette catégorie existe déjà dans la base de données");
    alert.showAndWait();
    return;
}

    // Créer un nouvel objet produit
    categorie p = new categorie(nom, description, etat, marque, groupe_age);

    // Ajouter le nouvel objet produit à la base de données
    CategorieService.ajouter(p);

    // Rafraîchir la table view pour afficher la liste mise à jour des produits
    produits.clear();
    produits.addAll(CategorieService.recuperer());

    // Effacer les champs de saisie
    Description.clear();
    Marque.clear();
}

 @FXML
void getselect(MouseEvent event) {
    // Récupérer le produit sélectionné dans la table view
    
     Nom.clear();
Description.clear();
Marque.clear();

    categorie p = table_categories.getSelectionModel().getSelectedItem();
    if (p == null) {
        System.out.println("Aucune categorie sélectionné");
        return;
    }

    // Afficher les données du produit dans les champs de texte
    Nom.setText(p.getNom());
    Description.setText(p.getDescription());
    Etat.setValue(p.getEtat());
    Marque.setText(p.getMarque());
    Groupeage.setValue(p.getGroupe_age());
}




///

@FXML
private void Modifier(ActionEvent event) {
    categorie p = table_categories.getSelectionModel().getSelectedItem();
    if (p == null) {
        System.out.println("Aucun produit sélectionné");
        return;
    }
    
    

    // Récupérer les données entrées par l'utilisateur
    String nom = Nom.getText();
    String description = Description.getText();
    String etat = Etat.getValue();
    String marque = Nom.getText();
    String groupe_age = Groupeage.getValue();

    // Créer un nouvel objet produit avec les nouvelles données
    categorie nouveauProduit = new categorie(nom, description, etat, marque, groupe_age);
    nouveauProduit.setId(p.getId());

    // Mettre à jour le produit dans la base de données
    CategorieService.modifier(nouveauProduit);

    // Rafraîchir la table view pour afficher la liste mise à jour des produits
    produits.clear();
    produits.addAll(CategorieService.recuperer());

    // Effacer les champs de texte et l'image
   Nom.clear();
   Description.clear();
    Marque.clear();
}


//



  @FXML
    void Supprimer(ActionEvent event) {
 // Récupérer le produit sélectionné dans la table view
    categorie p = table_categories.getSelectionModel().getSelectedItem();
    if (p == null) {
        System.out.println("Aucun produit sélectionné");
        return;
    }

    // Supprimer le produit de la base de données
    CategorieService.supprimer(p.getId());

    // Supprimer le produit de l'observable list
    produits.remove(p);
}
    
    
    
   @FXML
private void afficherStatistiques(ActionEvent event) {
// Récupérer les données de la statistique
Map<String, Integer> stats = CategorieService.getNombreProduitsParCategorie();

// Créer une liste d'objets PieChart.Data
ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
for (String categorie : stats.keySet()) {
    int nombreProduits = stats.get(categorie);
    PieChart.Data data = new PieChart.Data(categorie, nombreProduits);
    pieChartData.add(data);
}

// Créer le PieChart
PieChart chart = new PieChart(pieChartData);
chart.setTitle("Nombre de produits par catégorie");

// Ajouter une animation à l'affichage du PieChart
for (PieChart.Data data : chart.getData()) {
    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
        double angle = 0;
        double animationDelay = 10;
        double value = data.getPieValue();
        double endAngle = angle + value * 360 / chart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum();

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), chart);
        rotateTransition.setByAngle(endAngle);
        rotateTransition.play();
    });
}

// Afficher le PieChart dans une nouvelle fenêtre
Stage stage = new Stage();
Scene scene = new Scene(new Group());
stage.setTitle("Statistiques");
stage.setWidth(800);
stage.setHeight(600);
((Group) scene.getRoot()).getChildren().add(chart);
stage.setScene(scene);
stage.show();
}

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    categorie produit = null;
    
    private ObservableList<categorie> produits = FXCollections.observableArrayList();
    private CategorieService CategorieService = new CategorieService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Etat.getItems().addAll(dispo);
          Groupeage.getItems().addAll(age);
    categorie p = null;

        // TODO
        Connection connection = MyConnection.getInstance().getCnx();


        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_Marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        col_Groupe_age.setCellValueFactory(new PropertyValueFactory<>("Groupe_age"));

       
      
        List<categorie> produitsList = CategorieService.recuperer();
        produits.addAll(produitsList);

        // Lier la liste observable à la table view
        table_categories.setItems(produits);
    }
}

 