/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sami.services;

import java.util.List;

/**
 *
 * @author sbent
 */
public interface IService<F> {
    public void ajouter(F f);
    public void supprimer(int id);
    public void modifier(F f);
    public F getOneById(int id);
    public List<F> getAll();
}
