/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import services.ProduitService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import entities.produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import entities.categorie;
import utils.MyConnection;



/**
 * FXML Controller class
 *
 * @author Asus
 */
public class FrontProduitController implements Initializable {

    /**
     * Initializes the controller class.
     */
    

        
 @FXML
void addProductToMenuTable(MouseEvent event) {
   
}

@FXML
private Button formulaire;
    
    @FXML
private Button btnTriNom;

@FXML
private Button btnTriPrix;

       @FXML
    private Button Confirmer;
       
      @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cvvCodeField;

    @FXML
    private TextField dateField;
    
    @FXML
private VBox productContainer;
    
      @FXML
    private TextField menu_amount;
      

    @FXML
    private TextField expirationDateField;

    

    @FXML
    private Label menu_change;
   
        
     //   expYearField
    @FXML
    private TableColumn<produit, Float> menu_col_price;

    @FXML
    private TableColumn<produit, String> menu_col_productName;

    @FXML
    private TableColumn<produit, Float> menu_col_quantity;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    
    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;
 @FXML
    private TextField searchField;
    @FXML
    private TableView<produit> menu_tableView;
 @FXML
    private TextField creditCardField;
   
    
    private Button browseButtonImage;
    private File selectedFile;
    private Image selectedImage;
   
        @FXML
        private TableColumn<produit, String> col_description;

        @FXML
        private TableColumn<produit, String> col_etat;

        @FXML
        private TableColumn<produit, String> col_image;

        @FXML
        private TableColumn<produit, String> col_nom;

        @FXML
        private TableColumn<produit, Float> col_prix;

      

        @FXML
        private TableView<produit> table_produits;
    
        
         @FXML
    private Label menu_total;
         
  private categorie categorie;

  
  
  
  
public void setCategorie(categorie categorie) {
    this.categorie = categorie;
}

public List<produit> menuGetData() {
    if (produitService == null || categorie == null) {
        return new ArrayList<>(); // retourne une liste vide si les variables ne sont pas initialisées
    }
    List<produit> produits = produitService.recupererParCategorie(categorie.getId());
    return produits;
}




private ObservableList<produit> cardListData = FXCollections.observableArrayList();



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
            load.setLocation(getClass().getResource("../gui/cardProduct.fxml"));
            AnchorPane pane = load.load();
            CardProductController cardC = load.getController();
            cardC.setData(cardListData.get(q));
            cardC.setMenuTableView(menu_tableView);
            cardC.setMenuTotal(menu_total);

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

      
              
    public void sortTableByName() {
cardListData.sort(Comparator.comparing(p -> p.getNom()));
    menuDisplayCard();
}

public void sortTableByPrice() {
cardListData.sort(Comparator.comparingDouble(p -> Double.valueOf(p.getPrix())));
    menuDisplayCard();
}


@FXML
void onTriNomClicked(ActionEvent event) {
    sortTableByName();
}

@FXML
void onTriPrixClicked(ActionEvent event) {
    sortTableByPrice();
}            
              
            
              
              
        
        
    @FXML
private void searchProducts() {
    // Récupérer le texte saisi par l'utilisateur
    String searchText = searchField.getText().toLowerCase();

    // Filtrer les produits pour ne récupérer que ceux qui commencent par la chaîne de caractères saisie
    List<produit> filteredList = cardListData.filtered(p -> p.getNom().toLowerCase().startsWith(searchText));

    // Effacer les cartes existantes
    menu_gridPane.getChildren().clear();

    // Afficher les cartes filtrées
    int row = 0;
    int column = 0;
    for (produit p : filteredList) {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("../gui/cardProduct.fxml"));
            AnchorPane pane = load.load();
            CardProductController cardC = load.getController();
            cardC.setData(p);
            cardC.setMenuTableView(menu_tableView); 
            cardC.setMenuTotal(menu_total);

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


private void displaySearchResults(List<produit> searchResults) {
    // Effacer les anciennes cartes affichées
    menu_gridPane.getChildren().clear();
    
    // Afficher les nouvelles cartes correspondant à la recherche
    int row = 0;
    int column = 0;
    for (int q = 0; q < searchResults.size(); q++) {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("../gui/cardProduct.fxml"));
            AnchorPane pane = load.load();
            CardProductController cardC = load.getController();
            cardC.setData(searchResults.get(q));
            cardC.setMenuTableView(menu_tableView); 
            cardC.setMenuTotal(menu_total);

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
      List<produit> produitsList = produitService.recuperer();
           
            table_produits.setItems(produits);
}
        
        
        

 @FXML
private boolean paymentCompleted = false;
@FXML
void printReceipt(MouseEvent event) throws ParseException {

    if (!paymentCompleted) {
        // Afficher une boîte de dialogue indiquant que le paiement doit être effectué en premier
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Payment Required");
        alert.setHeaderText(null);
        alert.setContentText("Please complete the payment first.");
        alert.showAndWait();

        return; // Sortir de la fonction sans afficher le reçu
    }

    // Récupérer la liste des produits choisis avec leur quantité
    ObservableList<produit> produitsChoisis = menu_tableView.getItems();

    // Calculer le total avant réduction
    float totalPriceBeforeDiscount = 0;
    for (produit p : produitsChoisis) {
        totalPriceBeforeDiscount += p.getPrix();
    }

    // Appliquer la réduction si le client a acheté plus de 3 produits
    float totalPriceAfterDiscount = totalPriceBeforeDiscount;
    String discountMessage = "";
    if (produitsChoisis.size() > 3) {
        float discount = totalPriceBeforeDiscount * 0.2f;
        totalPriceAfterDiscount -= discount;
        discountMessage = "20% discount applied.";
    }

    // Mise à jour des variables totalPriceBeforeDiscount et totalPriceAfterDiscount avec les montants calculés
    String totalPriceBeforeDiscountString = String.format("%.2f", totalPriceBeforeDiscount);
    String totalPriceAfterDiscountString = String.format("%.2f", totalPriceAfterDiscount);

    // Créer une chaîne de caractères contenant les informations du reçu
    StringBuilder receiptBuilder = new StringBuilder();
    receiptBuilder.append("================================\n");
    receiptBuilder.append("          RECEIPT\n");
    receiptBuilder.append("================================\n");
    receiptBuilder.append(String.format("%-20s %-10s %-10s\n", "Product", "Quantity", "Price"));
    receiptBuilder.append("--------------------------------\n");
    for (produit p : produitsChoisis) {
        receiptBuilder.append(String.format("%-20s %-10.2f %-10.2f\n", p.getNom(), p.getQuantite(), p.getPrix()));
    }
    receiptBuilder.append("--------------------------------\n");
    receiptBuilder.append(String.format("%-20s %-10s %-10.2f\n", "Total before discount", "", Float.parseFloat(totalPriceBeforeDiscountString.replace(",", "."))));
    if (!discountMessage.isEmpty()) {
        receiptBuilder.append(String.format("%-20s %-10s %-10s\n", "Discount", "", discountMessage));
    }
    receiptBuilder.append(String.format("%-20s %-10s %-10.2f\n", "Total after discount", "", Float.parseFloat(totalPriceAfterDiscountString.replace(",", "."))));
    receiptBuilder.append("================================\n");
    receiptBuilder.append("Thank you for your order and welcome!\n");

    // Afficher le reçu dans une boîte de dialogue
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Receipt");
    alert.setHeaderText(null);
    alert.setContentText(receiptBuilder.toString());
    alert.showAndWait();

    // désactiver le bouton "Print Receipt"
    menu_receiptBtn.setDisable(true);
}


        





@FXML
void removeProductFromMenuTable(MouseEvent event) {
    produit selectedProduit = menu_tableView.getSelectionModel().getSelectedItem();
    if (selectedProduit != null) {
        menu_tableView.getItems().remove(selectedProduit);
        float totalPrice = 0;
        for (produit p : menu_tableView.getItems()) {
            totalPrice += p.getPrix();
        }
        menu_total.setText(String.format("%.2f", totalPrice));
    }
}
 

@FXML
void handlePayment(MouseEvent event) throws StripeException, IOException {
// Configure Stripe API key
  Stripe.apiKey = "sk_test_51MexHOEHtgKqVnqGoOdGOXC554rKgAtVYgc7j1kv2ZyAPAbozfHaRCjDObfqfn1s5iIa8Cv4mS8DA7ktXYb5yqj900h06MM6Yl";

    // Retrieve customer information
      // Create a payment using the card information
        Map<String, Object> chargeParams = new HashMap<>();
String totalPriceString = menu_total.getText().replace("$", "").replace(",", ".");
float totalPrice = Float.parseFloat(totalPriceString);
chargeParams.put("amount", (int)(totalPrice * 100));
        chargeParams.put("currency", "eur");
        chargeParams.put("description", "Example payment");
        chargeParams.put("source", "tok_visa"); // Card token generated by Stripe

        try {
            // Charge the customer's card
            Charge.create(chargeParams);



        } catch (StripeException e) {
 Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Payment Error");
    alert.setContentText(e.getMessage());
    alert.showAndWait();        }
        paymentCompleted = true;
    }

  @FXML
    private void handleButtonAction() throws IOException {
     Stage paymentStage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Payment.fxml"));
    try {
        AnchorPane paymentPane = loader.load();
        Scene paymentScene = new Scene(paymentPane);
        paymentStage.setScene(paymentScene);
        paymentStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    
   private AnchorPane paymentPane;
 
      String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        produit produit = null;

        private ObservableList<produit> produits = FXCollections.observableArrayList();
        private ProduitService produitService = new ProduitService();
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
onTriNomClicked(null);
          FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Payment.fxml"));
    try {
        paymentPane = loader.load();
    } catch (IOException e) {
        e.printStackTrace();
    }

        
        
        menuDisplayCard();
        produit p = null;
searchField.textProperty().addListener((observable, oldValue, newValue) -> {
    searchProducts();
});
    // TODO
    
    connection = MyConnection.getInstance().getCnx();

    col_nom.setCellValueFactory(new PropertyValueFactory<>("nom")); 
    col_nom.setSortable(true);

    col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
    col_image.setCellValueFactory(new PropertyValueFactory<>("image"));

    col_image.setCellFactory(column -> {
        return new TableCell<produit, String>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (imagePath == null || empty) {
                    setGraphic(null);
                    return;
                }

                // Charger l'image depuis la base de données ou le système de fichiers
                Image image = new Image(new File(imagePath).toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                setGraphic(imageView);
            }
        };
    });

    col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    col_prix.setSortable(true);

    col_image.setCellValueFactory(new PropertyValueFactory<>("image"));

    menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("nom"));
    menu_col_price.setCellValueFactory(new PropertyValueFactory<>("prix"));
    menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantite"));

    List<produit> produitsList = produitService.recuperer();
    produits.addAll(produitsList);

    // Lier la liste observable à la table view
    table_produits.setItems(produits);


    
}    
  
    }