/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Myconnexion;


/**
 *
 * @author bough
 */
public class serviceProduitIMP implements IService<Produit>{
    
    Connection cnxx ;

    public serviceProduitIMP() {
        cnxx = Myconnexion.getInstance().getCnx();
       
    }


    @Override
    public void ajouter(Produit p) {
    try {
            String req = "INSERT INTO produit (designation,marque,image,prix) VALUES (?,?,?,?)";
            System.out.println(p);
            PreparedStatement ps = cnxx.prepareStatement(req);
            System.out.println(req);
            ps.setString(1,p.getDesignation());
            ps.setString(2,p.getMarque());
            ps.setString(3,p.getImage());
            ps.setFloat(4,p.getPrix());
   

            ps.executeUpdate();

        } catch (SQLException ex) {
                Logger.getLogger(serviceProduitIMP.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Error in inserting produit");        }
    }
    

    @Override
    public void modifier(int id_produit, Produit p) {

        try {
            Statement stm = cnxx.createStatement();
            String req = "UPDATE `produit` SET `designation`='"
                    + p.getDesignation()+ "',`image`='"
                    +p.getImage()+"',`marque`='"
                    +p.getMarque()+"',`prix`='"
                    + p.getPrix()+ "' WHERE `id_produit`='"
                    + id_produit+ "'";
                    
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            
            System.out.println("Error in updating produit ");
        }
    
    }
    
  /*      @Override
    public void modifier(long id_MoyenTransport, MoyenDeTransport MTmodifie) {
        try {
            Statement stm = cnx.createStatement();
            String query = "UPDATE `MoyenTransport` SET `Type`='" + MTmodifie.getType() + "',`Num_ligne`='" + MTmodifie.getNum_ligne() + "',`Date_de_mise_en_circulations`='" + MTmodifie.getDate_de_mise_en_circulations() + "',`Etat`='" + MTmodifie.getEtat() + "',`Accessible_au_handicape`='" + MTmodifie.getAccessible_au_handicape() + "',`Prix_achat`='" + MTmodifie.getPrix_achat() + "',`Poids`='" + MTmodifie.getPoids() + "',`longueur`='" + MTmodifie.getLongueur() + "',`Largeur`='" + MTmodifie.getLargeur() + "',`Energie`='" + MTmodifie.getEnergie() + "',`Nombre_de_place`='" + MTmodifie.getNombre_de_place() + "' where id_MoyenTransport=" + id_MoyenTransport;
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }*/

    @Override
    public void supprimer(int id_produit) {
        try {
            String req = "DELETE FROM produit WHERE id_produit = ?" ;
          PreparedStatement ps = cnxx.prepareStatement(req);
          System.out.println(req);
            ps.setInt(1,id_produit);
            ps.executeUpdate();
        } catch (SQLException ex) {
                Logger.getLogger(serviceProduitIMP.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Error in deleting produit");        }
    }
    
//
//    @Override
//    public List<Produit> afficher() {
//        ArrayList<Produit>  list = new ArrayList();
//        try {
//                  String req ="Select * FROM produit";
//             Statement st = cnxx.createStatement();
//             ResultSet rs = st.executeQuery(req);
//             while (rs.next()){
//               
//                       Produit p = new Produit();
//                         p.setIdProduit(rs.getInt("id_produit"));
//                         p.setDesignation(rs.getString("designation"));
//                         p.setMarque(rs.getString("marque"));
//                         p.setImage(rs.getString("image"));
//                         p.setPrix(rs.getFloat("prix"));
//                         list.add(p);
//             }             
//   
//        } catch (SQLException ex) {
//                Logger.getLogger(serviceProduitIMP.class.getName()).log(Level.SEVERE, null, ex);
//                            System.out.println("Error in selecting Hotel");
//
//        }   

    public List<Produit> afficher() throws SQLException {
        List<Produit> lp = new ArrayList<>();



        Statement stm = cnxx.createStatement();


        String query = "SELECT * FROM produit";
        ResultSet rs= stm.executeQuery(query);
        while (rs.next()){
            Produit p = new Produit();
            p.setId_produit(rs.getInt("id_produit"));
            p.setDesignation(rs.getString("designation"));
            p.setPrix(rs.getFloat("prix"));
            p.setMarque(rs.getString("marque"));
            p.setImage(rs.getString("image"));
            
            lp.add(p);
        }
        return lp;
}
    
        public HashMap<String, Double> StatistiqueParMarque() {
        HashMap<String, Double> data = new HashMap<>();
        try {
            Statement stm = cnxx.createStatement();
            String query = "SELECT marque, COUNT(*) as nb FROM produit GROUP BY marque;";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                int nb = rs.getInt("nb");
                String key = nb + " " + rs.getString("marque");
                data.put(key, new Double(nb));
            }
            System.out.println(data.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
}

}
