/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Connextion.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.categorie;

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

    
}


