/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import services.CategorieService;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import entities.categorie;
import entities.produit;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class FrontCategorieController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
       @FXML
    private TableColumn<categorie , String> col_Groupe_age;

    @FXML
    private TableColumn<categorie , String> col_description;
 @FXML
    private ScrollPane menu_scrollPane;
    @FXML
    private TableColumn<categorie , String> col_etat;

    @FXML
    private TableColumn<categorie , String> col_nom;

        @FXML
private VBox productContainer;
    @FXML
    private TableColumn<categorie , String> col_Marque;

    @FXML
    private TableView<categorie> table_categories;
    @FXML
    private TextField searchField;

    
     @FXML
    private GridPane menu_gridPane;
      
         public List<categorie> menuGetData() {
       CategorieService.recuperer();
        return CategorieService.recuperer();
    }
    
            private ObservableList<categorie> cardListData = FXCollections.observableArrayList();

              public void menuDisplayCard() {
        
        cardListData.clear();
        cardListData.addAll(menuGetData());
        
        int row = 0;
        int column = 0;
        
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
        
       for (int q = 0; q < cardListData.size(); q++) {
    try {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("../gui/CARDCAT.fxml"));
        AnchorPane pane = load.load();
        CARDCATController cardC = load.getController();
         cardC.setData(cardListData.get(q));

        if (column == 3) {
            column = 0;
            row += 1;
        }

        menu_gridPane.add(pane, column++, row);

        GridPane.setMargin(pane, new Insets(10));

    } catch (Exception e) {
        e.printStackTrace();
    }
}
              } 
    
    
  @FXML
private void searchCategories() {
    // Récupérer le texte saisi par l'utilisateur
    String searchText = searchField.getText().toLowerCase();

    // Filtrer les catégories pour ne récupérer que celles qui commencent par la chaîne de caractères saisie
    List<categorie> filteredList = cardListData.filtered(c -> c.getNom().toLowerCase().startsWith(searchText));

    // Effacer les cartes existantes
    menu_gridPane.getChildren().clear();

    // Afficher les cartes filtrées
    int row = 0;
    int column = 0;
    for (categorie c : filteredList) {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("CARDCAT.fxml"));
            AnchorPane pane = load.load();
            CARDCATController cardC = load.getController();
            cardC.setData(c);

            if (column == 3) {
                column = 0;
                row += 1;
            }

            menu_gridPane.add(pane, column++, row);

            GridPane.setMargin(pane, new Insets(10));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


    @FXML
void Refresh(ActionEvent event) {
      List<categorie> produitsList = CategorieService.recuperer();
           
            table_categories.setItems(produits);
}
       
    
    
      
    public void sortTableByName() {
    // Trier les catégories par nom
    Collections.sort(cardListData, Comparator.comparing(categorie::getNom));

    // Mettre à jour l'affichage des cartes
    menu_gridPane.getChildren().clear();
    int row = 0;
    int column = 0;
    for (categorie c : cardListData) {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("../gui/CARDCAT.fxml"));
            AnchorPane pane = load.load();
            CARDCATController cardC = load.getController();
            cardC.setData(c);

            if (column == 3) {
                column = 0;
                row += 1;
            }

            menu_gridPane.add(pane, column++, row);

            GridPane.setMargin(pane, new Insets(10));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


public void sortTableByMarque() {
    // Trier les catégories par marque
    Collections.sort(cardListData, Comparator.comparing(categorie::getMarque));

    // Mettre à jour l'affichage des cartes
    menu_gridPane.getChildren().clear();
    int row = 0;
    int column = 0;
    for (categorie c : cardListData) {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("CARDCAT.fxml"));
            AnchorPane pane = load.load();
            CARDCATController cardC = load.getController();
            cardC.setData(c);

            if (column == 3) {
                column = 0;
                row += 1;
            }

            menu_gridPane.add(pane, column++, row);

            GridPane.setMargin(pane, new Insets(10));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





@FXML
void onTriNomClicked(ActionEvent event) {
    sortTableByName();
}

@FXML
void onTriMarqueClicked(ActionEvent event) {
    sortTableByMarque();
}
    
     String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    private ObservableList<categorie> produits = FXCollections.observableArrayList();
    private CategorieService CategorieService = new CategorieService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       menuDisplayCard();
    categorie p = null;
searchField.textProperty().addListener((observable, oldValue, newValue) -> {
    searchCategories();
});
        // TODO
        Connection connection = MyConnection.getInstance().getCnx();


        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_nom.setSortable(true);
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_Marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        col_Marque.setSortable(true);
        col_Groupe_age.setCellValueFactory(new PropertyValueFactory<>("Groupe_age"));

       
      
        List<categorie> produitsList = CategorieService.recuperer();
        produits.addAll(produitsList);

        // Lier la liste observable à la table view
        table_categories.setItems(produits);
    }
   

}
