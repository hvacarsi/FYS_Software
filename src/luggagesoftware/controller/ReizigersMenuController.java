/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import luggagesoftware.Database;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReizigersMenuController implements Initializable {

    @FXML
    private Button lostLuggageBtn;
    @FXML
    private Button foundLuggageBtn;
    @FXML
    private Button reizigersBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private ImageView logoIV;
    @FXML
    private MenuButton settingsMenuBtn;
    @FXML
    private MenuItem profileBtn;
    @FXML
    private MenuItem logOutBtn;
    @FXML
    private MenuItem closeBtn;
    @FXML
    private TableView<Traveler> table;
    @FXML
    private TextField keywordFld;
    @FXML
    private ComboBox<String> whatCB;
    @FXML
    private Button filterBtn;
    @FXML
    private Button resetBtn, connectBtn;

    //tijdelijke list aanmaken
    private ObservableList<Traveler> data = FXCollections.observableArrayList();
    private ObservableList<Traveler> filteredData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Database.currentScreen = "ReizigersMenu";
        logOutBtn.setText(Database.option("LOGOUT_TEXT"));
        Database db = new Database();

        //laad data
        data = loadData();
        table.getItems().clear();
        table.getItems().addAll(data);

        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });

        filterBtn.setText(Database.option("SEARCH_TEXT"));
        resetBtn.setText(Database.option("RESET_TEXT"));

        addBtn.setOnAction(e -> {
            db.changeScene("ReizigersRegis");
        });

        lostLuggageBtn.setOnAction(e -> {
            db.changeScene("VerlorenBagageManager");
        });

        foundLuggageBtn.setOnAction(e -> {
            db.changeScene("Match");
        });

        reizigersBtn.setOnAction(e -> {
            db.changeScene("ReizigersMenu");
        });

        editBtn.setOnAction(e -> {
            //check if row is selected
            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            //check if row is valid
            if (selectedIndex >= 0 && selectedIndex < data.size()) {
                //get id from de Traveler class and set in static var in the db class
                Database.selectedTravelerRow = Integer.parseInt(data.get(selectedIndex).getString("id"));
                db.changeScene("ReizigersRegis");
            } else {
                //laat bericht zien indien niks is geselecteerd
                Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NOTHING_SELECTED_TEXT"));
            }

        });

        //check if lostSql does have a value, so the user must connect the luggage
        //with the traveler, and hide elements, and show the connect button
        if (Database.lostSql != null) {
            reizigersBtn.setVisible(false);
            editBtn.setVisible(false);
            lostLuggageBtn.setVisible(false);
            foundLuggageBtn.setVisible(false);
            settingsMenuBtn.setVisible(false);
            connectBtn.setVisible(true);
        } else {
            //Hide connect button with the luggage if the user doesn;t have to
            //choose a traveler for a luggage
            connectBtn.setVisible(false);
        }

        connectBtn.setOnAction(e -> {
            //Check if row selected
            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            //check if row is valid
            if (selectedIndex >= 0 && selectedIndex < data.size()) {
                //Save id of the traveler from the traveler class in the db class
                Database.selectedTravelerRow
                        = selectedIndex = Integer.parseInt(data.get(selectedIndex).getString("id"));

                //Replace #tid# with the traveler id
                Database.lostSql = Database.lostSql.replace("#TID#", selectedIndex + "");
                //run the query
                Database.insertQuery(Database.lostSql);
                //set sql null
                Database.lostSql = null;
                //change scene
                db.changeScene("VerlorenBagageManager");
            } else {
                //laat bericht zien indien niks is geselecteerd
                Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NOTHING_SELECTED_TEXT"));
            }
        });

        filterBtn.setOnAction(e -> {
            String filterSql = "SELECT * FROM cn_traveler WHERE ";
            String subSql = "";

            if (keywordFld.getText().length() > 0 && !whatCB.getSelectionModel().isEmpty()) {

                subSql = (whatCB.getValue().equals(Database.option("NAME_TEXT"))) ? "TNAME"
                        : (whatCB.getValue().equals(Database.option("LAST_NAME_LABEL"))) ? "TLASTNAME"
                        : (whatCB.getValue().equals(Database.option("ZIP_CODE_LABEL"))) ? "TZIPCODE"
                        : (whatCB.getValue().equals(Database.option("MAIL_ADDRESS_LABEL"))) ? "TMAIL" : "TNAME";

                subSql += " LIKE '%" + keywordFld.getText() + "%'";
                filterSql += subSql;

                if (subSql.length() > 1) {
                    String finalSql = filterSql;
                    System.out.println(finalSql);
                    filteredData.clear();

                    table.getItems().clear();
                    Statement stmtFiltered;

                    try {
                        stmtFiltered = db.connection.createStatement();
                        ResultSet rowx = stmtFiltered.executeQuery(finalSql);
                        LocalDate date;

                        boolean found = false;

                        while (rowx.next()) {
                            found = true;
                            date = LocalDate.parse(rowx.getString("TDATE_BIRTH"));
                            filteredData.add(new Traveler(rowx.getString("TNAME"), rowx.getString("TLASTNAME"), date + "", rowx.getString("TZIPCODE"), rowx.getString("TCITY"), rowx.getString("TCOUNTRY"), rowx.getString("TADDRESS"), rowx.getString("TPHONE"), rowx.getString("TMAIL"), rowx.getString("TID")));
                        }

                        if (!found) {
                            Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NO_RESULTS_TEXT"));
                        }

                        //add all filtered data got from above...
                        table.getItems().addAll(filteredData);
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }

            }

        });

        resetBtn.setOnAction(e -> {
            //reset data and load all data
            keywordFld.setText("");
            whatCB.setValue("");
            data = loadData();
            table.getItems().clear();
            table.getItems().addAll(data);
        });

        profileBtn.setOnAction(e -> {
            db.changeScene("Profile");
        });

        lostLuggageBtn.setText(Database.option("LOST_LUGGAGE_TEXT"));
        foundLuggageBtn.setText(Database.option("MATCHES_TEXT"));
        reizigersBtn.setText(Database.option("TRAVELER_TEXT"));
        addBtn.setText(Database.option("ADD_TEXT"));
        editBtn.setText(Database.option("EDIT_TEXT"));
        settingsMenuBtn.setText(Database.option("SETTINGS_TEXT"));
        profileBtn.setText(Database.option("PROFILE_TEXT"));
        closeBtn.setText(Database.option("CLOSE_PROGRAM_TEXT"));

        db.showMenu(reizigersBtn, foundLuggageBtn, lostLuggageBtn, settingsMenuBtn);

        keywordFld.setPromptText(Database.option("KEYWORD_TEXT"));
        whatCB.setPromptText(Database.option("SELECT_OPTION_TEXT"));
        whatCB.getItems().addAll(Database.option("NAME_TEXT"), Database.option("LAST_NAME_LABEL"), Database.option("ZIP_CODE_LABEL"), Database.option("MAIL_ADDRESS_LABEL"));
        table.setEditable(true);
        TableColumn naam = new TableColumn(Database.option("NAME_TEXT"));
        TableColumn achternaam = new TableColumn(Database.option("LAST_NAME_LABEL"));
        TableColumn mail = new TableColumn(Database.option("MAIL_ADDRESS_LABEL"));

        /*Decided to hide this columns 
        
        TableColumn gebdatum = new TableColumn(("GEBOORTE DATUM"));
        TableColumn mobiel = new TableColumn(("MOBIEL"));
        TableColumn adres = new TableColumn(("ADRES"));
        TableColumn postcode = new TableColumn(("POSTCODE"));
         */
        TableColumn stad = new TableColumn(Database.option("CITY_LABEL"));
        TableColumn land = new TableColumn(Database.option("COUNTRY_LABEL"));

        PropertyValueFactory naamv = new PropertyValueFactory<>("naam");
        naam.setCellValueFactory(naamv);

        PropertyValueFactory achternaamv = new PropertyValueFactory<>("achternaam");
        achternaam.setCellValueFactory(achternaamv);

        PropertyValueFactory mailv = new PropertyValueFactory<>("mail");
        mail.setCellValueFactory(mailv);

        //Decided to hide this cells
        /*PropertyValueFactory gebdatumv = new PropertyValueFactory<>("geb_datum");
        gebdatum.setCellValueFactory(gebdatumv);

        PropertyValueFactory mobielv = new PropertyValueFactory<>("mobiel");
        mobiel.setCellValueFactory(mobielv);

        PropertyValueFactory adresv = new PropertyValueFactory<>("adres");
        adres.setCellValueFactory(adresv);

        PropertyValueFactory postcodev = new PropertyValueFactory<>("postcode");
        postcode.setCellValueFactory(postcodev);*/
        PropertyValueFactory stadv = new PropertyValueFactory<>("stad");
        stad.setCellValueFactory(stadv);

        PropertyValueFactory landv = new PropertyValueFactory<>("land");
        land.setCellValueFactory(landv);

        //set data
        table.setItems(data);

        //add columns
        table.getColumns().addAll(naam, achternaam, stad, land, mail);

    }

    private ObservableList<Traveler> loadData() {
        //Add data into tmpData and return it
        ObservableList<Traveler> tmpData = FXCollections.observableArrayList();
        Database db = new Database();

        String sql = "SELECT * FROM cn_traveler ORDER BY TID DESC";

        Statement stmt;

        try {
            stmt = db.connection.createStatement();
            ResultSet row = stmt.executeQuery(sql);
            LocalDate date;
            while (row.next()) {
                date = LocalDate.parse(row.getString("TDATE_BIRTH"));
                tmpData.add(new Traveler(row.getString("TNAME"), row.getString("TLASTNAME"), date + "", row.getString("TZIPCODE"), row.getString("TCITY"), row.getString("TCOUNTRY"), row.getString("TADDRESS"), row.getString("TPHONE"), row.getString("TMAIL"), row.getString("TID")));
            }

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(luggagesoftware.VerlorenBagageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tmpData;
    }

}
