/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.util.List;

/**
 *
 * @author Asus
 */
public interface IServiceCat<T> {
    
     public void ajouter(T t);
   public boolean modifier(T t);
   public boolean supprimer(int id);
   public List<T> recuperer();
    
}
