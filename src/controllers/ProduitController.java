    package controllers;

    import java.net.URL;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.util.ResourceBundle;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.cell.PropertyValueFactory;
    import entities.produit;
import services.CategorieService;
    import services.ProduitService;
    import java.io.File;
    import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
    import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
    import javafx.scene.control.TableCell;
    import javafx.scene.control.TableView;
    import javafx.scene.control.TextField;
    import javafx.stage.FileChooser;
    import javafx.stage.FileChooser.ExtensionFilter;
    import javafx.scene.image.ImageView;
    import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import entities.categorie;
import utils.MyConnection;


    public class ProduitController implements Initializable {
          @FXML
        private Button Ajouter;
           @FXML
        private Button Modifier;

           @FXML
        private AnchorPane myAnchorPane;


              @FXML
    private ComboBox<categorie> Categorie_id;

              
              
      @FXML
        private TextField Description;

          @FXML
    private ChoiceBox<String> Etat;
private String[] dispo = {"disponible" , "non disponible" };


        @FXML
        private TextField Nom;

        @FXML
        private TextField Prix;

        @FXML
        private TextField Quantite;

        @FXML
        private Button browseButtonImage;
    private File selectedFile;
    private Image selectedImage;


    @FXML
        private TableColumn<produit, String> col_Id;

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
        private TableColumn<produit, Float> col_quantite;

        
        @FXML
        private TableColumn<produit,Integer > idcateg;
        
        @FXML
        private TableView<produit> table_produits;
        
        
    
        @FXML
    private void handleBrowseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImage = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(selectedImage);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(165);
            imageView.setFitHeight(130);
            myAnchorPane.getChildren().add(imageView);
        }
    }



   @FXML
private void Ajouter(ActionEvent event) {
    
    
    // Récupérer les données entrées par l'utilisateur
    String nom = Nom.getText();
    String description = Description.getText();
    String etat = Etat.getValue();
    float prix = Float.parseFloat(Prix.getText());
    float quantite = Float.parseFloat(Quantite.getText());

String image = null;

    try {
        image = selectedFile.getAbsolutePath();
    } catch (NullPointerException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : Fichier image introuvable");
        return;
    }
    
    if (nom.isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : Le champ 'Nom' est vide");
        alert.showAndWait();
    return;
}

if (description.isEmpty()) {
   Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : Le champ 'Description' est vide");
        alert.showAndWait();
    return;
}
if (description.length() < 4) {
     Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : La description doit contenir au moins 4 caractères");
        alert.showAndWait();
    return;
}

if (Prix.getText().isEmpty()) {
  Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : Le champ 'Prix' est vide");
        alert.showAndWait();
    return;
} else {
    try {
        prix = Float.parseFloat(Prix.getText());
    } catch (NumberFormatException ex) {
     Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : Le champ 'Prix' doit être un nombre");
        alert.showAndWait();
        return;
    }
}

if (Quantite.getText().isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : Le champ 'Quantité' est vide");
        alert.showAndWait();
    return;
} else {
    try {
        quantite = Float.parseFloat(Quantite.getText());
    } catch (NumberFormatException ex) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Erreur : Le champ 'Quantité' doit être un nombre");
        alert.showAndWait();
        return;
    }
}

    // Vérifier si le produit existe déjà dans la base de données
    boolean existe = false;
    for (produit p : produits) {
        if (p.getNom().equals(nom)) {
            existe = true;
            break;
        }
    }

    if (existe) {
        System.out.println("Erreur : Ce produit existe déjà dans la base de données");
        return;
    }

    // Récupérer la catégorie sélectionnée dans le ComboBox
categorie categorieSelectionnee = (categorie) Categorie_id.getSelectionModel().getSelectedItem();

// Créer un nouvel objet produit avec la catégorie et les autres données entrées par l'utilisateur
produit p = new produit(nom, description, etat, image, prix, quantite, categorieSelectionnee);

// Ajouter le nouvel objet
    produitService.ajouter(p);
    

    // Rafraîchir la table view pour afficher la liste mise à jour des produits
    produits.clear();
    produits.addAll(produitService.recuperer());
    Nom.clear();
    Description.clear();
    Prix.clear();
    Quantite.clear();
    myAnchorPane.getChildren().clear();
}

    int index = -1 ;
   
  @FXML
void getselect(MouseEvent event) {
    // Récupérer le produit sélectionné dans la table view
    
     Nom.clear();
Description.clear();
Prix.clear();
Quantite.clear();
myAnchorPane.getChildren().clear();

    produit p = table_produits.getSelectionModel().getSelectedItem();
    if (p == null) {
        System.out.println("Aucun produit sélectionné");
        return;
    }

    // Afficher les données du produit dans les champs de texte
    Nom.setText(p.getNom());
    Description.setText(p.getDescription());
    Etat.setValue(p.getEtat());
    Prix.setText(Float.toString(p.getPrix()));
    Quantite.setText(Float.toString(p.getQuantite()));
    Categorie_id.setValue(p.getCategorieId());

    // Afficher l'image du produit dans myAnchorPane
    String imagePath = p.getImage();
    if (imagePath != null) {
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(165);
            imageView.setFitHeight(130);
            myAnchorPane.getChildren().add(imageView);
        }
    }
}
    
    
 @FXML
private void Modifier(ActionEvent event) {
    // Récupérer le produit sélectionné dans la table view
    produit p = table_produits.getSelectionModel().getSelectedItem();
    if (p == null) {
        System.out.println("Aucun produit sélectionné");
        return;
    }
    
    // Récupérer les données entrées par l'utilisateur
    String nom = Nom.getText();
    String description = Description.getText();
    String etat = Etat.getValue();
    float prix = Float.parseFloat(Prix.getText());
    float quantite = Float.parseFloat(Quantite.getText());
    categorie categorieid = Categorie_id.getValue();

 

    // Mettre à jour l'image du produit si nécessaire
    String imagePath = null;
    if (selectedFile != null) {
        imagePath = selectedFile.getAbsolutePath();
    } else {
        // Use the existing image path if no new file was selected
        imagePath = p.getImage();
    }
           
    // Créer un nouvel objet produit avec les nouvelles données
    produit nouveauProduit = new produit(nom, description, etat, imagePath, prix, quantite);
    nouveauProduit.setId(p.getId());
    nouveauProduit.setCategorieId(categorieid); // Ajouter l'ID de la catégorie sélectionnée

    // Mettre à jour le produit dans la base de données
    produitService.modifier(nouveauProduit);
    // Rafraîchir la table view pour afficher la liste mise à jour des produits
    produits.clear();
    produits.addAll(produitService.recuperer());

    // Effacer les champs de texte et l'image
    Nom.clear();
    Description.clear();
    Prix.clear();
    Quantite.clear();
    myAnchorPane.getChildren().clear();
}




      @FXML
        void Supprimer(ActionEvent event) {
     // Récupérer le produit sélectionné dans la table view
        produit p = table_produits.getSelectionModel().getSelectedItem();
        if (p == null) {
            System.out.println("Aucun produit sélectionné");
            return;
        }

        // Supprimer le produit de la base de données
        produitService.supprimer(p.getId());

        // Supprimer le produit de l'observable list
        produits.remove(p);
    }
        
public void afficherStatistiques() {
    // Calculer le nombre total de produits
    int nombreTotalProduits = produits.size();

    // Calculer le prix moyen de tous les produits
    double prixTotalProduits = 0.0;
    for (produit p : produits) {
        prixTotalProduits += p.getPrix();
    }
    double prixMoyenProduits = prixTotalProduits / nombreTotalProduits;

    // Calculer la quantité totale de produits
    int quantiteTotaleProduits = 0;
    for (produit p : produits) {
        quantiteTotaleProduits += p.getQuantite();
    }

    // Créer les données pour le graphique pie
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data("Nombre total de produits", nombreTotalProduits),
        new PieChart.Data("Prix moyen des produits", prixMoyenProduits),
        new PieChart.Data("Quantité totale de produits", quantiteTotaleProduits)
    );

    // Créer le graphique pie
    PieChart pieChart = new PieChart(pieChartData);
    pieChart.setTitle("Statistiques des produits");

    // Afficher le graphique pie dans une boîte de dialogue
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Statistiques des produits");
    alert.setHeaderText(null);
    alert.getDialogPane().setContent(pieChart);
    alert.showAndWait();
}

        
        
        
        String query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        produit produit = null;

        private ObservableList<produit> produits = FXCollections.observableArrayList();
        private ProduitService produitService = new ProduitService();
    //===================================================================
        @Override
        public void initialize(URL url, ResourceBundle rb) {
             // Récupérer les catégories à partir du service de catégories
   CategorieService categorieService = new CategorieService();
List<categorie> categories = categorieService.recupererIds();

// Ajouter les catégories à la liste des éléments du ComboBox
Categorie_id.getItems().addAll(categories);

// Spécifier quelle propriété de l'objet de catégorie doit être affichée dans le ComboBox
Categorie_id.setCellFactory(new Callback<ListView<categorie>, ListCell<categorie>>() {
    @Override
    public ListCell<categorie> call(ListView<categorie> param) {
        return new ListCell<categorie>() {
            @Override
            protected void updateItem(categorie item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText("");
                } else {
                    setText(String.valueOf(item.getId()));
                }
            }
        };
    }
});

// Spécifier quelle propriété de l'objet de catégorie doit être affichée dans le ComboBox lorsque la liste déroulante est ouverte
Categorie_id.setButtonCell(new ListCell<categorie>() {
    @Override
    protected void updateItem(categorie item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText("");
        } else {
            setText(String.valueOf(item.getId()));
        }
    }
});

Etat.setValue("disponible");
              Etat.getItems().addAll(dispo);
            produit p = null;
 //Categorie_id.getItems().addAll(cat);
            // TODO
            Connection connection = MyConnection.getInstance().getCnx();


            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
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
// Ajouter une colonne pour l'id de la catégorie


// Ajouter la colonne à la table view

            col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
             col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
     idcateg.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCategorieId().getId()).asObject());

// Ajouter la colonne à la table view
table_produits.getColumns().add(idcateg);
            List<produit> produitsList = produitService.recuperer();
        
            produits.addAll(produitsList);

            // Lier la liste observable à la table view
            table_produits.setItems(produits);
        }

 public void setProduit(produit produit) {
    this.produit = produit;
    Nom.setText(produit.getNom());
    Prix.setText(String.valueOf(produit.getPrix()));
    Description.setText(produit.getDescription());
    Image image = new Image(new File(produit.getImage()).toURI().toString());
    Categorie_id.getSelectionModel().select(produit.getCategorieId());

    // vous pouvez ajouter d'autres actions ici, par exemple :
    //   - cacher/montrer certains éléments en fonction du produit
    //   - mettre en forme les éléments de la vue
}




    }
