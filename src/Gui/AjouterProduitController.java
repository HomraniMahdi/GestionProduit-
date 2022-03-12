/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import entities.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.serviceProduitIMP;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.MailerApi;
import utils.SMSApi;

/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class AjouterProduitController implements Initializable {

    @FXML
    private HBox chosenhotelCard;
    @FXML
    private HBox hosenhotelCard;
    @FXML
    private ImageView hotelimage;
    @FXML
    private ScrollPane scroll;
    @FXML
    private TextField designationft;
    @FXML
    private TextField marqueft;
    @FXML
    private TextField prixtx;
    @FXML
    private Button image;
    @FXML
    private AnchorPane left_main;
    @FXML
    private Label file_path;
    @FXML
    private ImageView image_view;
    @FXML
    private TextField Emailtf;
    @FXML
    private TextField numero;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Enregister(ActionEvent event) {
            InsertProduit();
    }
    
    public void InsertProduit(){
        serviceProduitIMP Sp = new serviceProduitIMP();
        Produit p = new Produit(designationft.getText(), marqueft.getText() ,file_path.getText(),Float.parseFloat(prixtx.getText()));
        
        
                //Notification
        String tilte;
        String message;
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tilte = "Added Successful";
        message ="Produit créer avec success";
        tray.setTitle(tilte);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(2000));
        
    
        // SEND MAIL
        MailerApi mailer = new MailerApi();
        mailer.SendMail(Emailtf.getText(), "Admin.");

        //SEND SMS
        SMSApi sms = new SMSApi();
        //sms.sendSMS("+216"+numero.getText(), "Admin.");
        sms.sendSMS("+21620071775", "Admin.");
        Sp.ajouter(p);
    }

    @FXML
    private void image(ActionEvent event) {
        
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

