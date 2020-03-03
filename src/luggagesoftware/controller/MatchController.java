/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import luggagesoftware.controller.Match;
import luggagesoftware.controller.PDF;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MatchController implements Initializable {

    @FXML
    private AnchorPane verlorenBagagePane;
    @FXML
    private Button exportPdf;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button contributorsBtn;
    @FXML
    private Button statisticsBtn;
    @FXML
    private Button foundLuggageBtn;
    @FXML
    private Button lostLuggageBtn;
    @FXML
    private MenuButton settingsMenuBtn;
    @FXML
    private MenuItem profileBtn;
    @FXML
    private MenuItem logOutBtn;
    @FXML
    private MenuItem closeBtn;
    @FXML
    private Button travelerBtn;
    @FXML
    private TableView<Match> table;

    private ObservableList<Match> data = FXCollections.observableArrayList();
    private int selectedRow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "Match";
        logOutBtn.setText(Database.option("LOGOUT_TEXT"));
        Database db = new Database();

        data = loadData();
        table.getItems().clear();
        table.getItems().addAll(data);

        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        profileBtn.setOnAction(e -> {
            db.changeScene("Profile");
        });

        exportPdf.setOnAction(e -> {
            //Krijg geselecteerde rij van de tabel
            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            //Controleer of de geselecteerde rij bestaat, of, of er is geselecteerd
            if (selectedIndex >= 0 && selectedIndex < data.size()) {

                //Krijg matchId van de geselecteerde rij
                int matchedID = Integer.parseInt(data.get(selectedIndex).getString("matchId"));

                //krijg de gegevens van de rij door middel van de fetch methode id database class
                ResultSet fetch = Database.fetch("cn_matchedluggage ml "
                        + "INNER JOIN cn_luggage l on l.ID = ml.lostId "
                        + "INNER JOIN cn_traveler t on t.TID = l.TRAVELER_ID", "ml.matchedId = '" + matchedID + "'", "");
                try {
                    //Creeer datum
                    DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
                    Date today = new Date();
                    //stel pdf naam in
                    PDF pdf = new PDF(matchedID + "_ShippingForm.pdf");

                    //Font size van de text in pdf, later word dit verlaagd
                    int size = 22;

                    //Posities van de teksten...
                    int[] pos = {
                        650, //top 
                        30, //mintop 
                        260, //leftValue 
                        140 //left 3
                    };

                    //Stel Koptekst in
                    pdf.setText("Shipping Form", size, new int[]{240, 700});

                    //Wijzig grootte naar 14px
                    size = 14;

                    //Naam van de gebruiker in de pdf stoppen
                    pdf.setText("Name: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TNAME") + " " + fetch.getString("TLASTNAME"), size, new int[]{pos[2], pos[0]});

                    //Wijzig positie van de tekst
                    pos[0] -= pos[1];

                    //geboorte datum instellen
                    pdf.setText("Date of Birth: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TDATE_BIRTH"), size, new int[]{pos[2], pos[0]});
                    pos[0] -= pos[1];

                    //Adres instellen
                    pdf.setText("Address: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TADDRESS"), size, new int[]{pos[2], pos[0]});
                    pos[0] -= pos[1];

                    //Stad
                    pdf.setText("City: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TZIPCODE") + ", " + fetch.getString("TCITY"), size, new int[]{pos[2], pos[0]});
                    pos[0] -= pos[1];

                    //Land
                    pdf.setText("Country: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TCOUNTRY"), size, new int[]{pos[2], pos[0]});
                    pos[0] -= pos[1];

                    //Email
                    pdf.setText("Email: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TMAIL"), size, new int[]{pos[2], pos[0]});
                    pos[0] -= pos[1];
                    //telefoon
                    pdf.setText("Phone: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TPHONE"), size, new int[]{pos[2], pos[0]});
                    pos[0] -= pos[1];

                    //Bagage Type instellen
                    pdf.setText("Luggage Type: ", size, new int[]{pos[3], pos[0]});
                    pdf.setText(fetch.getString("TYPE"), size, new int[]{pos[2], pos[0]});

                    //Handtekeningen
                    pdf.setText("Sigature Traveler", size, new int[]{120, 200});
                    pdf.setText("Signature Manager", size, new int[]{360, 200});

                    //sla pdf op en sluit
                    pdf.saveFile();
                    pdf.openFile();

                    db.addLog("MATCHED Luggage, created PDF, Matched ID: " + matchedID);
                    
                    //Update status van de gematchte bagage
                    db.updateTable("cn_matchedluggage", "matchedState = 'SEND_TEXT'", "matchedId = '" + matchedID + "'", false);

                    //Laad nieuwe data
                    data = loadData();
                    table.getItems().clear();
                    table.getItems().addAll(data);

                } catch (IOException | SQLException ex) {
                    System.out.println(ex);
                }

            } else {
                //laat bericht zien indien niks is geselecteerd
                Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NOTHING_SELECTED_TEXT"));
            }
        });

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });

        lostLuggageBtn.setText(Database.option("LOST_LUGGAGE_TEXT"));
        lostLuggageBtn.setOnAction(e -> {
            db.changeScene("VerlorenBagageManager");
        });
        foundLuggageBtn.setText(Database.option("MATCHES_TEXT"));

        deleteBtn.setText(Database.option("DELETE_TEXT"));
        exportPdf.setText(Database.option("EXPORT_TEXT"));

        statisticsBtn.setText(Database.option("STATISTICS_TEXT"));
        statisticsBtn.setOnAction(e -> {
            db.changeScene("Statistieken");
        });
        contributorsBtn.setText(Database.option("CONTRIBUTORS_TEXT"));
        contributorsBtn.setOnAction(e -> {
            db.changeScene("Medewerkers");
        });
        settingsMenuBtn.setText(Database.option("SETTINGS_TEXT"));
        profileBtn.setText(Database.option("PROFILE_TEXT"));
        closeBtn.setText(Database.option("CLOSE_PROGRAM_TEXT"));
        closeBtn.setOnAction(e -> {
            System.exit(1);
        });

        travelerBtn.setText(Database.option("TRAVELER_TEXT"));
        travelerBtn.setOnAction(e -> {
            db.changeScene("ReizigersMenu");
        });

        //laat de juiste knoppen zien en verplaats of wijzig breedte indien nodig
        db.showMenu(contributorsBtn, statisticsBtn, travelerBtn, foundLuggageBtn, lostLuggageBtn, settingsMenuBtn);

        //Creer de kolommen
        table.setEditable(true);
        TableColumn labelNumberC = new TableColumn(Database.option("LABEL_NUMBER_LABEL"));
        TableColumn stateC = new TableColumn(Database.option("STATE_TEXT"));
        TableColumn nameC = new TableColumn(Database.option("NAME_TEXT"));
        TableColumn dateC = new TableColumn(Database.option("DATE_LABEL"));
        TableColumn colorC = new TableColumn(Database.option("COLOR_TEXT"));
        TableColumn typeC = new TableColumn(Database.option("LUGGAGE_TYPE_LABEL"));
        TableColumn airportC = new TableColumn(Database.option("DESTINATION_LABEL") + " " + Database.option("LUCHTHAVEN_TEXT"));

        //Stel de waardes in
        PropertyValueFactory lblnr = new PropertyValueFactory<>("lblnr");
        labelNumberC.setCellValueFactory(lblnr);

        PropertyValueFactory state = new PropertyValueFactory<>("state");
        stateC.setCellValueFactory(state);

        PropertyValueFactory name = new PropertyValueFactory<>("travelerName");
        nameC.setCellValueFactory(name);

        PropertyValueFactory date = new PropertyValueFactory<>("date");
        dateC.setCellValueFactory(date);

        PropertyValueFactory color = new PropertyValueFactory<>("color");
        colorC.setCellValueFactory(color);

        PropertyValueFactory type = new PropertyValueFactory<>("type");
        typeC.setCellValueFactory(type);

        PropertyValueFactory airport = new PropertyValueFactory<>("airport");
        airportC.setCellValueFactory(airport);

        //gooi data in de table
        table.setItems(data);

        //voeg kolommen toe
        table.getColumns().addAll(labelNumberC, nameC, airportC, stateC, colorC, typeC, dateC);

        deleteBtn.setOnAction(e -> {

            //Krijg geselecteerde rij van de tabel
            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            //Controleer of de geselecteerde rij bestaat, of, of er is geselecteerd
            if (selectedIndex >= 0 && selectedIndex < data.size()) {
                //Krijg match id adhv selected row
                selectedRow = Integer.parseInt(data.get(selectedIndex).getString("matchId"));
                //Zet query op
                String query = "DELETE FROM cn_matchedluggage WHERE matchedId = '" + selectedRow + "'";
                db.addLog("Deleted Match");
                //Voer query uit, verwijder geselecteerde match uit de tabel
                Database.insertQuery(query);

                //laad nieuwe data
                data = loadData();
                table.getItems().clear();
                table.getItems().addAll(data);
            } else {
              //laat bericht zien indien niks is geselecteerd
                Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NOTHING_SELECTED_TEXT"));
            }

        });

    }

    private ObservableList<Match> loadData() {
        //creer list
        ObservableList<Match> tmpData = FXCollections.observableArrayList();
        Database db = new Database();
        
        //Stel de sql op, voor de benodigde informatie
        String sql = "SELECT *, air.airportName as aname, l.COLOR as clr, l.TYPE as tp, tr.TNAME as tname, tr.TLASTNAME as tlname, l.TRAVELER_ID as ti, lf.LABELNR as ln FROM cn_matchedluggage ml "
                + "INNER JOIN cn_luggage l on ml.lostId = l.ID "
                + "INNER JOIN cn_luggage lf on lf.ID = ml.foundId "
                + "INNER JOIN cn_traveler tr on tr.TID = l.TRAVELER_ID "
                + "INNER JOIN cn_airports air on air.airportId = l.DESTINATION "
                + "WHERE l.STATE != 99 AND lf.STATE != 99 ORDER BY ml.matchedId DESC";

        //maak statement
        Statement stmt;

        try {
            stmt = db.connection.createStatement();
            ResultSet row = stmt.executeQuery(sql);

            //Voeg data toe in de tijdelijke list
            while (row.next()) {
                tmpData.add(new Match(row.getString("ln"), Database.option(row.getString("matchedState")), row.getString("ti"), row.getString("matchedId"), row.getString("tname") + " " + row.getString("tlname"), row.getString("matchedDate"), row.getString("clr"), row.getString("tp"), row.getString("aname")));
            }

        } catch (java.sql.SQLException ex) {
            System.out.println(ex);
        }
        //geef de tijdelijke list terug
        return tmpData;
    }

}
