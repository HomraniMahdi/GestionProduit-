/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entities.Categorie;
import entities.Produit;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.serviceCategorieIPM;
import services.serviceProduitIMP;


/**
 *
 * @author LENOVO
 */
public class Main {
    public static void main(String[] args) throws SQLException {
       

            Produit p = new Produit("modifier", "modifier", "modifier", 50);
            serviceProduitIMP s = new serviceProduitIMP();
            
            Categorie c = new Categorie("ali");
            serviceCategorieIPM cs = new serviceCategorieIPM();
     //      cs.ajouter(c);
      //   cs.modifier(2, c);
      
               List<Categorie>  list = new ArrayList();
            
            
//            System.out.println(cs.afficher());
//cs.supprimer(1);
            // cs.getById(2);
            System.out.println(cs.nbCategorie());            
            //  s.ajout(p);
            // s.modifier(11, p);
            //  s.supprimer(5);
            //System.out.println(s.afficher());
            
            /*  List<Produit>  list = new ArrayList();
            
            
            System.out.println(s.afficher());*/


    }
    
    
}
