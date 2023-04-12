package sami.main;

import sami.entities.FicheAssurance;
import sami.services.IService;
import sami.services.ServiceFicheAssurance;
import sami.utils.MyConnection;

/**
 *
 * @author sbent
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     /*  //test du connection kif yebda public 
        MyConnection ds1= new MyConnection();
        MyConnection ds2= new MyConnection();
        */
          MyConnection.getInstance();
     
           // service init
        IService ps = new ServiceFicheAssurance();
        
                //FicheAssurance init
        FicheAssurance p = new FicheAssurance();
          
        p.setNum_adherant(14);
          p.setImage("28");
          p.setDescription("19");
          p.setEtat("28");
           //add action
       ps.ajouter(p);

          //select
        System.out.println(ps.getAll());
          
    }
 
}