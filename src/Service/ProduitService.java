/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import java.sql.Connection;
import Connextion.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;  
import models.produit;


/**
 *
 * @author Asus
 */
public class ProduitService implements IService<produit> {
    
    Connection cnx;

    public ProduitService() {
        cnx=MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(produit p) {
        try {
            String req = "INSERT INTO produit(categorie_id,nom, description, etat, image, prix, quantite) " +
                    "VALUES (?,?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
       
        ps.setObject(1, p.getCategorie_id());
        ps.setString(2, p.getNom());
        ps.setString(3, p.getDescription());
        ps.setString(4, p.getEtat());
        ps.setString(5, p.getImage());
        ps.setFloat(6, p.getPrix());
        ps.setFloat(7, p.getQuantite());
            ps.executeUpdate();
            System.out.println("Produit ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public boolean modifier(produit p) {
       try {
        String req = "UPDATE produit SET nom=?, description=?, etat=?, image=?, prix=?, quantite=? WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, p.getNom());
        ps.setString(2, p.getDescription());
        ps.setString(3, p.getEtat());
        ps.setString(4, p.getImage());
        ps.setFloat(5, p.getPrix());
        ps.setFloat(6, p.getQuantite());
        ps.setInt(7, p.getId());
        
        System.out.println("Requête de mise à jour: " + ps.toString());
        
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("La mise à jour a été effectuée avec succès");
            return true;
        } else {
            System.out.println("La mise à jour a échoué");
            return false;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return false;
    }
}

    @Override
    public boolean supprimer(int id) {
        try {
            String req = "DELETE FROM produit WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Produit supprimé avec succès");
         return true;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return false;
    }
    }

  @Override
public List<produit> recuperer() {
    List<produit> produits = new ArrayList<>();
    try {
        String req = "SELECT * FROM produit";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            produit p = new produit();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setDescription(rs.getString("description"));
            p.setEtat(rs.getString("etat"));
            p.setImage(rs.getString("image"));
            p.setPrix(rs.getFloat("prix"));
            p.setQuantite(rs.getFloat("quantite"));
            produits.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return produits;
}
public produit fetchById(int id) {
    produit produit = null;
    try {
        String req = "SELECT * FROM produit WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nom = rs.getString("nom");
            String description = rs.getString("description");
            String etat = rs.getString("etat");
            String image = rs.getString("image");
            float prix = rs.getFloat("prix");
            float quantite = rs.getFloat("quantite");
            produit = new produit(id, nom, description, etat, image, prix, quantite);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return produit;
}

    public void testSelectProduits() {
        try {
            String query = "SELECT * FROM produit";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("nom") + ", " + resultSet.getFloat("prix"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
