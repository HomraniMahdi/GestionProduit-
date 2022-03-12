/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author bough
 */
public class Produit {
    private int id_produit;	
    private String designation;	
    private String marque;
    private String image;	
    private float prix;
    
    public Produit() {
    }
    
    
    public Produit(int id_produit, String designation, String marque, String image, float prix) {
        this.id_produit = id_produit;
        this.designation = designation;
        this.marque = marque;
        this.image = image;
        this.prix = prix;
    }

    public Produit(String designation, String marque, String image, float prix) {
        this.designation = designation;
        this.marque = marque;
        this.image = image;
        this.prix = prix;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }



    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" + "id_produit=" + id_produit + ", designation=" + designation + ", marque=" + marque + ", image=" + image + ", prix=" + prix + "\n" +'}';
    }
    
    
    
    
}
