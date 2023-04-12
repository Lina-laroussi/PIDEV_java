/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sami.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author sbent
 */
public class MyConnection {
    
    private Connection cnx;
    
    static MyConnection instance;
    
    private final String USER ="root";
    private final String PWD ="";
    private final String URL ="jdbc:mysql://localhost:3306/pi_java";

    private MyConnection(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("connected to db");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Connection getCnx() {
        return cnx;
    }
    
    public static MyConnection getInstance() {
        if(instance == null)
            instance = new MyConnection();
        
        return instance;
    }
}
