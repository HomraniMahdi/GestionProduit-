/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Categorie;
import utils.Myconnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bough
 */
public class serviceCategorieIPM implements IService<Categorie>{
    
    Connection cnxx ;

    public serviceCategorieIPM() {
        cnxx = Myconnexion.getInstance().getCnx();
       
    }

    @Override
    public void ajouter(Categorie c) {
            try {
            String req = "INSERT INTO categorie (nom) VALUES (?)";
            System.out.println(c);
            PreparedStatement ps = cnxx.prepareStatement(req);
            System.out.println(req);
            ps.setString(1,c.getNom());
            
   

            ps.executeUpdate();
        } catch (SQLException ex) {
                Logger.getLogger(serviceCategorieIPM.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Error in inserting categorie");        }
    }
    
    

    

    @Override
    public void modifier(int id_categorie, Categorie c) {

        try {
            Statement stm = cnxx.createStatement();
            String req = "UPDATE `categorie` SET `nom`='"
                    + c.getNom()+ "' WHERE `id_categorie`='"
                    + id_categorie+ "'";
                    
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            
            System.out.println("Error in updating categorie ");
        }
    }
    

    @Override
    public void supprimer(int id_categorie) {

       try {
            String req = "DELETE FROM categorie WHERE id_categorie = ?" ;
          PreparedStatement ps = cnxx.prepareStatement(req);
          System.out.println(req);
            ps.setInt(1,id_categorie);
            ps.executeUpdate();
        } catch (SQLException ex) {
                Logger.getLogger(serviceCategorieIPM.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Error in deleting categorie");        }
    }

 
    @Override
    public List afficher() {
ArrayList<Categorie>  list = new ArrayList();
        try {
                  String req ="Select * FROM categorie";
             Statement st = cnxx.createStatement();
             ResultSet rs = st.executeQuery(req);
             while (rs.next()){
               
                         Categorie c = new Categorie();
                         c.setId_categorie(rs.getInt("id_categorie"));
                         c.setNom(rs.getString("nom"));
                         list.add(c);

             }             
   
        } catch (SQLException ex) {
                Logger.getLogger(serviceCategorieIPM.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Error in selecting categorie");

        }   
     return list;    
    }
    
        public Categorie getById(int id_categorie) throws SQLException{
        Statement stm = cnxx.createStatement();
        Categorie c =new Categorie();
        String query = "select * from categorie where id_categorie="+id_categorie;
        ResultSet rs= stm.executeQuery(query);
        while (rs.next()){
            c.setId_categorie(rs.getInt("id_categorie"));
            c.setNom(rs.getString("nom"));
        }
        return c;
    }
        
        public int nbCategorie(){
        int nbr = 0;
        try {
            ResultSet set = Myconnexion.getInstance().
                    getCnx().prepareStatement("SELECT COUNT(id_categorie) FROM categorie")
                    .executeQuery();
            if (set.next()) {
                nbr = set.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(serviceCategorieIPM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nbr;
    }
        
            
    public HashMap<String, Double> StatistiqueParPrix() {
        HashMap<String, Double> data = new HashMap<>();
        try {
            Statement stm = cnxx.createStatement();
            String query = "SELECT nom, COUNT(*) as nb FROM categorie GROUP BY nom;";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int nb = rs.getInt("nb");
                String key = nb + " " + rs.getString("nom");
                data.put(key, new Double(nb));
            }
            System.out.println(data.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
}
}   


