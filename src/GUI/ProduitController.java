    package GUI;

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
    import models.produit;
    import Connextion.MyConnection;
    import Service.ProduitService;
    import java.io.File;
    import java.util.List;
    import javafx.event.ActionEvent;
    import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
    import javafx.scene.control.TableCell;
    import javafx.scene.control.TableView;
    import javafx.scene.control.TextField;
    import javafx.stage.FileChooser;
    import javafx.stage.FileChooser.ExtensionFilter;
    import javafx.scene.image.ImageView;
    import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.AnchorPane;
import models.categorie;


    public class ProduitController implements Initializable {
          @FXML
        private Button Ajouter;
           @FXML
        private Button Modifier;

           @FXML
        private AnchorPane myAnchorPane;


              @FXML
    private ChoiceBox<String> Categorie_id;
              
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
        System.out.println("Erreur : Fichier image introuvable");
        return;
    }
    
    if (nom.isEmpty()) {
    System.out.println("Erreur : Le champ 'Nom' est vide");
    return;
}

if (description.isEmpty()) {
    System.out.println("Erreur : Le champ 'Description' est vide");
    return;
}

if (Prix.getText().isEmpty()) {
    System.out.println("Erreur : Le champ 'Prix' est vide");
    return;
} else {
    try {
        prix = Float.parseFloat(Prix.getText());
    } catch (NumberFormatException ex) {
        System.out.println("Erreur : Le champ 'Prix' doit être un nombre");
        return;
    }
}

if (Quantite.getText().isEmpty()) {
    System.out.println("Erreur : Le champ 'Quantité' est vide");
    return;
} else {
    try {
        quantite = Float.parseFloat(Quantite.getText());
    } catch (NumberFormatException ex) {
        System.out.println("Erreur : Le champ 'Quantité' doit être un nombre");
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

    // Créer un nouvel objet produit avec la catégorie et les autres données entrées par l'utilisateur
    produit p = new produit(nom, description, etat, image, prix, quantite);

    // Ajouter le nouvel objet produit à la base de données
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

    // Créer un nouvel objet produit avec les nouvelles données
    produit nouveauProduit = new produit(nom, description, etat, p.getImage(), prix, quantite);
    nouveauProduit.setId(p.getId());

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
Etat.setValue("disponible");
              Etat.getItems().addAll(dispo);
            produit p = null;

            // TODO
            Connection connection = MyConnection.getInstance().getCnx();


            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
            col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));

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
            col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
             col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
            List<produit> produitsList = produitService.recuperer();
            produits.addAll(produitsList);

            // Lier la liste observable à la table view
            table_produits.setItems(produits);
        }


    }
