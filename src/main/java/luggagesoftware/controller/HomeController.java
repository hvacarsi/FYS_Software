/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class HomeController implements Initializable {

    @FXML
    public Label loggedLbl;
    public Pane contributorsPane, managersPane;
    
    @FXML
    public MenuItem profileBtn, logOutBtn, closeBtn;

    @FXML
    public MenuButton settingsMenuBtn;

    @FXML
    public Button lostLuggageBtn, foundLuggageBtn, travelerBtn, statisticsBtn, contributorsBtn;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "Home";
        Database.logStage.setWidth(320);
        Database db = new Database();
        loggedLbl.setText(Database.userFullName);

        logOutBtn.setText(Database.option(Database.language, "LOGOUT_TEXT"));
        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });

        profileBtn.setOnAction(e -> {
            db.changeScene("Profile");
        });
        
        lostLuggageBtn.setOnAction(e -> {
            db.changeScene("VerlorenBagageManager");
        });
        
        travelerBtn.setOnAction(e -> {
            db.changeScene("ReizigersMenu");
        });
        
        statisticsBtn.setOnAction(e -> {
            db.changeScene("Statistieken");
        });
        
        contributorsBtn.setOnAction(e -> {
            db.changeScene("Medewerkers");
        });
        
        foundLuggageBtn.setOnAction(e -> {
            db.changeScene("Match");
        });
        
        lostLuggageBtn.setText(Database.option("LOST_LUGGAGE_TEXT"));
        foundLuggageBtn.setText(Database.option("MATCHES_TEXT"));
        travelerBtn.setText(Database.option("TRAVELER_TEXT"));
        statisticsBtn.setText(Database.option("STATISTICS_TEXT"));
        contributorsBtn.setText(Database.option("CONTRIBUTORS_TEXT"));
        settingsMenuBtn.setText(Database.option("SETTINGS_TEXT"));
        profileBtn.setText(Database.option("PROFILE_TEXT"));
        closeBtn.setText(Database.option("CLOSE_PROGRAM_TEXT"));

        //Toon, verstop, verplaats button afhankelijk van de gebruikersrol
        switch (Database.userLevel) {
            case 1:
                managersPane.setVisible(false);
                contributorsPane.setLayoutY(contributorsPane.getLayoutY() + 50);
                travelerBtn.setLayoutY(travelerBtn.getLayoutY() + 55);
            break;
            
            case 2:
                travelerBtn.setVisible(false);
                contributorsPane.setLayoutY(contributorsPane.getLayoutY() + 20);
                managersPane.setLayoutY(managersPane.getLayoutY() - 40);
            
        }

    }

}
