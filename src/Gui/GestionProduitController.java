/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import entities.Produit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.serviceCategorieIPM;
import services.serviceProduitIMP;
import utils.JfreeChartApi;

/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class GestionProduitController implements Initializable {

    @FXML
    private HBox chosenhotelCard;
    @FXML
    private HBox hosenhotelCard;
    @FXML
    private ImageView hotelimage;
    @FXML
    private Label label;
    @FXML
    private TextField designationft;
    @FXML
    private TextField marqueft;
    @FXML
    private Label file_path;
    @FXML
    private TextField prixtx;
    @FXML
    private TableView<Produit> TableProduit;
    @FXML
    private TableColumn<Produit, Integer> IdPoduitTab;
    @FXML
    private TableColumn<Produit, String> DesignationTab;
    @FXML
    private TableColumn<Produit, String> MarqueTab;
    @FXML
    private TableColumn<Produit, Float> PrixTab;
    @FXML
    private TableColumn<Produit, String> ImageTab;
    @FXML
    private AnchorPane left_main;
    @FXML
    private ImageView image_view;
    @FXML
    private TextField TFSearch;

    /**
     * Initializes the controller class.
     */
    
    serviceProduitIMP Ps = new serviceProduitIMP();
    int id_produit ; 
    ObservableList<Produit> data=FXCollections.observableArrayList();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         refreshlist();
         recherche_avance();
         
    }
    
        public void recherche_avance() {
          
        try {
            data = FXCollections.observableArrayList(Ps.afficher());
            //System.out.println(data);
            FilteredList<Produit> filteredData = new FilteredList<>(data, b -> true);
            TFSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(p -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (String.valueOf(p.getId_produit()).toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true; 
                    } 
                    else if(p.getDesignation().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                    }
                    else if(p.getMarque().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                    }
                    else if(String.valueOf(p.getPrix()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                    }
                    
                    else
                        return false; // Does not match.
                });
                
            });
            System.out.println(filteredData);
            TableProduit.setItems(filteredData);
        } catch (SQLException ex) {
            Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    public void refreshlist(){


        try {
            data.clear();
            data = FXCollections.observableArrayList(Ps.afficher());
            TableProduit.setEditable(true);
            TableProduit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            IdPoduitTab.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
            DesignationTab.setCellValueFactory(new PropertyValueFactory<>("designation"));
            MarqueTab.setCellValueFactory(new PropertyValueFactory<>("marque"));
            PrixTab.setCellValueFactory(new PropertyValueFactory<>("prix"));
            ImageTab.setCellValueFactory(new PropertyValueFactory<>("image"));
            
            TableProduit.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void AjouterProduit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Gui/AjouterProduit.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
         refreshlist();
         recherche_avance();
    }

    @FXML
    private void fillforum(MouseEvent event) {
        
        Produit pr=TableProduit.getSelectionModel().getSelectedItem();
        id_produit=pr.getId_produit();
        designationft.setText(pr.getDesignation());
        marqueft.setText(pr.getMarque());
        prixtx.setText(Float.toString(pr.getPrix()));
      
        
    }

    @FXML
    private void ModifierProduit(ActionEvent event) {
            updateProduit();
            refreshlist();
            recherche_avance();
        
    }
    
     public void updateProduit(){
        serviceProduitIMP Sp = new serviceProduitIMP();
        Produit p = new Produit(designationft.getText(), marqueft.getText() ,file_path.getText(),Float.parseFloat(prixtx.getText()));
        Sp.modifier(id_produit, p);
        refreshlist(); 
    }

    @FXML
    private void SupprimerProduit(ActionEvent event) {
        
        id_produit=TableProduit.getSelectionModel().getSelectedItem().getId_produit();
        Ps.supprimer(id_produit);
        refreshlist();
        recherche_avance();
    }

    @FXML
    private void Statistique(ActionEvent event) {
        serviceProduitIMP Cs = new serviceProduitIMP();
        HashMap<String, Double> data = Cs.StatistiqueParMarque();
        JfreeChartApi chart = new JfreeChartApi(data);
        chart.afficherStatistique();
    }

    @FXML
    private void modifierImage(ActionEvent event) {
        FileChooser open = new FileChooser();

        Stage stage = (Stage)left_main.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if(file != null){

            String path = file.getAbsolutePath();

            path = path.replace("\\", "\\\\");

           file_path.setText(path);

            Image image = new Image(file.toURI().toString(), 110, 110, false, true);
            image_view.setImage(image);
           
        }else{

            System.out.println("NO DATA EXIST!");

        }
    }

    @FXML
    private void Acceuil(ActionEvent event) {
                        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Gui/Menu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjouterCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
