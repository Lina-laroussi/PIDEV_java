/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entities.categorie;
import entities.produit;
import utils.MyConnection;

/**
 *
 * @author Asus
 */
public class CategorieService implements IServiceCat<categorie>{
      Connection cnx;

    public CategorieService() {
        cnx=MyConnection.getInstance().getCnx();
    }

      @Override
    public void ajouter(categorie p) {
        try {
            String req = "INSERT INTO categorie(nom, description, etat, marque, groupe_age) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNom());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getEtat());
            ps.setString(4, p.getMarque());
            ps.setString(5, p.getGroupe_age());
            ps.executeUpdate();
            System.out.println("categorie ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

      @Override
    public boolean modifier(categorie p) {
        try {
            
            String req = "UPDATE categorie SET nom=?, description=?, etat=?, marque=?, groupe_age=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNom());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getEtat());
            ps.setString(4, p.getMarque());
            ps.setString(5, p.getGroupe_age());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            return true;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return false;
    }
    }

    @Override
    public boolean supprimer(int id) {
        try {
            String req = "DELETE FROM categorie WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("categorie supprimé avec succès");
         return true;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return false;
    }
    }

  @Override
public List<categorie> recuperer() {
    List<categorie> categories = new ArrayList<>();
    try {
        String req = "SELECT * FROM categorie";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            categorie p = new categorie();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setDescription(rs.getString("description"));
            p.setEtat(rs.getString("etat"));
            p.setMarque(rs.getString("marque"));
            p.setGroupe_age(rs.getString("groupe_age"));
            categories.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return categories;
}
public categorie fetchById(int id) {
    categorie categorie = null;
    try {
        String req = "SELECT * FROM categorie WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nom = rs.getString("nom");
            String description = rs.getString("description");
            String etat = rs.getString("etat");
            String marque = rs.getString("marque");
            String groupe_age = rs.getString("groupe_age");
            categorie = new categorie(nom, description, etat, marque, groupe_age);
            
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return categorie;
}

   public List<produit> getProduitsByCategorie(int categorieId) {
    List<produit> produits = new ArrayList<>();
    try {
        String req = "SELECT * FROM produit WHERE categorie_id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, categorieId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            produit p = new produit();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setDescription(rs.getString("description"));
            p.setImage(rs.getString("image"));
             p.setPrix( rs.getFloat("prix"));
            p.setQuantite(rs.getFloat("quantite"));
            // et ainsi de suite pour les autres attributs du produit
            produits.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return produits;
}
 public List<Integer> getIdCategories() {
    List<Integer> idCategories = new ArrayList<>();
    try {
        String req = "SELECT id FROM categorie";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            idCategories.add(id);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return idCategories;
}
public List<categorie> recupererIds() {
    List<categorie> categories = new ArrayList<>();
    try {
        String req = "SELECT id FROM categorie";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            categorie cat = new categorie();
            cat.setId(id);
            categories.add(cat);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return categories;
}

public Map<String, Integer> getNombreProduitsParCategorie() {
    Map<String, Integer> nombreProduitsParCategorie = new HashMap<>();
    try {
        String req = "SELECT categorie.nom, COUNT(produit.id) as nbProduits FROM produit JOIN categorie ON produit.categorie_id = categorie.id GROUP BY categorie.id";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nomCategorie = rs.getString("nom");
            int nombreProduits = rs.getInt("nbProduits");
            nombreProduitsParCategorie.put(nomCategorie, nombreProduits);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return nombreProduitsParCategorie;
}

}


