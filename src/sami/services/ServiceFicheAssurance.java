/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sami.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sami.entities.FicheAssurance;
import sami.utils.MyConnection;

/**
 *
 * @author sbent
 */
public class ServiceFicheAssurance implements IService<FicheAssurance>{

    Connection cnx = MyConnection.getInstance().getCnx();
    
    @Override
    public void ajouter(FicheAssurance f) {
       
        try { 
            String req = "INSERT INTO `ficheassurance`(`num_adherant`, `image`, `description`, `etat`) VALUES ('"+f.getNum_adherant()+"',"+f.getImage()+","+f.getDescription()+","+f.getEtat()+")";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("fiche assurance ajoutée!");

        } catch (SQLException ex) {
        }
    }

    @Override
    public void supprimer(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifier(FicheAssurance f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FicheAssurance getOneById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FicheAssurance> getAll() {
List<FicheAssurance> ficheAssurances = new ArrayList<>();
        try {
            
            String req = "SELECT * FROM ficheassurance";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {                
                FicheAssurance p = new FicheAssurance();
                p.setId(rs.getInt(1));
                p.setNum_adherant(rs.getInt(2));
                p.setImage(rs.getString(3));
                p.setDescription(rs.getString(4));
                 p.setEtat(rs.getString(5));
                
                ficheAssurances.add(p);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


return ficheAssurances;

    }
    
}
