/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import entities.Categorie;
import entities.Produit;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.serviceCategorieIPM;
import utils.JfreeChartApi;

/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class GestionCategorieController implements Initializable {

    @FXML
    private AnchorPane left_main;
    @FXML
    private HBox chosenhotelCard;
    @FXML
    private HBox hosenhotelCard;
    @FXML
    private ImageView hotelimage;
    @FXML
    private Label label;
    @FXML
    private TextField nomft;
    @FXML
    private ImageView image_view;
    @FXML
    private TableColumn<Categorie, Integer> IdcategorieTab;
    @FXML
    private TableColumn<Categorie, String> NomTab;
    @FXML
    private TableView<Categorie> TableCategorie;
    @FXML
    private Label LabelNbCategorie;
    

    /**
     * Initializes the controller class.
     */
    
    serviceCategorieIPM Sc = new serviceCategorieIPM();
    int id_categorie ; 
    ObservableList<Categorie> data=FXCollections.observableArrayList();
    @FXML
    private TextField TFSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshlist();
        LabelNbCategorie.setText("nombre categorie :     "+Sc.nbCategorie());
        recherche_avance();
    } 
    
    
            public void recherche_avance() {
          
                data = FXCollections.observableArrayList(Sc.afficher());
                //System.out.println(data);
                FilteredList<Categorie> filteredData = new FilteredList<>(data, b -> true);
                TFSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(c -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (c.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                            return true;
                        }
                        else if(String.valueOf(c.getId_categorie()).toLowerCase().indexOf(lowerCaseFilter)!=-1){
                            return true;
                        }
                        
                        else
                            return false; // Does not match.
                    });
                    
                });
                System.out.println(filteredData);
                TableCategorie.setItems(filteredData);
        }
    
        
    public void refreshlist(){


        
            data.clear();
            data = FXCollections.observableArrayList(Sc.afficher());
            TableCategorie.setEditable(true);
            TableCategorie.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            IdcategorieTab.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
            NomTab.setCellValueFactory(new PropertyValueFactory<>("nom"));;
            
            TableCategorie.setItems(data);
    }

    @FXML
    private void AjouterCategorie(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Gui/AjouterCategorie.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        refreshlist();
        recherche_avance();
    }

    @FXML
    private void fillforum(MouseEvent event) {
        Categorie c=TableCategorie.getSelectionModel().getSelectedItem();
        id_categorie=c.getId_categorie();
        nomft.setText(c.getNom());
         refreshlist();
         recherche_avance();
    }

    @FXML
    private void ModifierCategorie(ActionEvent event) {
         updateCategorie();
         refreshlist();
         recherche_avance();
    }
    
    public void updateCategorie(){
        serviceCategorieIPM Cs = new serviceCategorieIPM();
        Categorie c = new Categorie(nomft.getText());
        Cs.modifier(id_categorie, c);
        refreshlist();
        recherche_avance();
    }

    @FXML
    private void SupprimerCategorie(ActionEvent event) {
        id_categorie=TableCategorie.getSelectionModel().getSelectedItem().getId_categorie();
        Sc.supprimer(id_categorie);
        refreshlist();
        recherche_avance();
    }

    @FXML
    private void Statistique(ActionEvent event) {
        serviceCategorieIPM Cs = new serviceCategorieIPM();
        HashMap<String, Double> data = Cs.StatistiqueParPrix();
        JfreeChartApi chart = new JfreeChartApi(data);
        chart.afficherStatistique();
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
