/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class StatistiekenController implements Initializable {

    @FXML
    private Button lostLuggageBtn, foundLuggageBtn, statisticsBtn, contributorsBtn;
    @FXML
    private MenuButton settingsMenuBtn;
    @FXML
    private ComboBox airportCB;
    @FXML
    private MenuItem profileBtn, logOutBtn, closeBtn;
    @FXML
    private Button filterBtn;
    @FXML
    private DatePicker fromDatePkr, tillDatePkr;
    @FXML
    private PieChart chart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "Statistieken";
        Database db = new Database();

        logOutBtn.setText(Database.option("LOGOUT_TEXT"));
        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        profileBtn.setOnAction(e -> {
            db.changeScene("Profile");
        });

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });

        foundLuggageBtn.setOnAction(e -> {
            db.changeScene("Match");
        });

        lostLuggageBtn.setOnAction(e -> {
            db.changeScene("VerlorenBagageManager");
        });

        contributorsBtn.setOnAction(e -> {
            db.changeScene("Medewerkers");
        });

        //translate
        contributorsBtn.setText(Database.option("CONTRIBUTORS_TEXT"));
        statisticsBtn.setText(Database.option("STATISTICS_TEXT"));
        foundLuggageBtn.setText(Database.option("MATCHES_TEXT"));
        lostLuggageBtn.setText(Database.option("LOST_LUGGAGE_TEXT"));

        settingsMenuBtn.setText(Database.option("SETTINGS_TEXT"));
        profileBtn.setText(Database.option("PROFILE_TEXT"));
        closeBtn.setText(Database.option("CLOSE_PROGRAM_TEXT"));

        airportCB.setPromptText(Database.option("LUCHTHAVEN_TEXT"));

        //show menu adhv rol
        db.showMenu(statisticsBtn, foundLuggageBtn, lostLuggageBtn, settingsMenuBtn);
        airportCB.getItems().add(Database.option("ALL_AIRPORTS_TEXT"));
        airportCB.setValue(Database.option("ALL_AIRPORTS_TEXT"));
        airportCB.getItems().addAll(Database.allAirports);

        LocalDate previousYear = LocalDate.now().minusYears(1);
        fromDatePkr.setValue(previousYear);

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        LocalDate date = LocalDate.parse(timeStamp);
        tillDatePkr.setValue(date);

        filterBtn.setOnAction(e -> {
            //maak eerst chart leeg bij filter
            chart.getData().clear();
            
            //kijk of er een luchthaven is geselecteerd
            boolean airport = (airportCB.getValue().equals(Database.option("ALL_AIRPORTS_TEXT")));

            //set resultsets
            ResultSet fetchFound = null, fetchLost = null, fetchMatched = null, fetchSent = null;
            //query voor lost and found, en query voor matched en sent
            String[][] query = new String[][]{
                {"cn_luggage",
                    "STATE = #STATE# AND #DEST# LDATE >= '" + fromDatePkr.getValue() + "' AND LDATE <= '" + tillDatePkr.getValue() + "' ",
                    "",
                    "COUNT(*)"},
                {
                    "cn_matchedluggage ml INNER JOIN cn_luggage l on l.ID = ml.foundId ",
                    "ml.matchedState = '#STATE#' AND #DEST# ml.matchedDate BETWEEN '" + fromDatePkr.getValue() + "' AND '" + tillDatePkr.getValue() + "' ",
                    "",
                    "COUNT(*)"
                }
            };

            //sla data in results op
            int[] results = new int[4];
            String selectedAirport = null;

            //als er een airport is geselecteerd
            if (!airport) {
                //fetch all data
                selectedAirport = Database.getTable("cn_airports", "airportName = '" + airportCB.getValue() + "'", "airportId");
                fetchFound = Database.fetch(query[0][0], query[0][1].replaceAll("#DEST#", "DESTINATION = '" + selectedAirport + "' AND ").replaceAll("#STATE#", 2 + ""), query[0][2], query[0][3]);
                fetchLost = Database.fetch(query[0][0], query[0][1].replaceAll("#DEST#", "DESTINATION = '" + selectedAirport + "' AND ").replaceAll("#STATE#", 0 + ""), query[0][2], query[0][3]);
                fetchMatched = Database.fetch(query[1][0], query[1][1].replaceAll("#DEST#", "l.DESTINATION = '" + selectedAirport + "' AND ").replaceAll("#STATE#", "MATCHED_TEXT"), query[1][2], query[1][3]);
                fetchSent = Database.fetch(query[1][0], query[1][1].replaceAll("#DEST#", "l.DESTINATION = '" + selectedAirport + "' AND ").replaceAll("#STATE#", "SEND_TEXT"), query[1][2], query[1][3]);

                //Save data in array
                try {
                    results[0] = fetchFound.getInt(1);
                    results[1] = fetchLost.getInt(1);
                    results[2] = fetchMatched.getInt(1);
                    results[3] = fetchSent.getInt(1);
                } catch (SQLException ex) {
                    System.out.println("ERROR : " + ex);
                }

            } else {
                //fetch data of all airports
                selectedAirport = "";
                fetchFound = Database.fetch(query[0][0], query[0][1].replaceAll("#DEST#", selectedAirport).replaceAll("#STATE#", 2 + ""), query[0][2], query[0][3]);
                fetchLost = Database.fetch(query[0][0], query[0][1].replaceAll("#DEST#", selectedAirport).replaceAll("#STATE#", 0 + ""), query[0][2], query[0][3]);
                fetchMatched = Database.fetch(query[1][0], query[1][1].replaceAll("#DEST#", selectedAirport).replaceAll("#STATE#", "MATCHED_TEXT"), query[1][2], query[1][3]);
                fetchSent = Database.fetch(query[1][0], query[1][1].replaceAll("#DEST#", selectedAirport).replaceAll("#STATE#", "SEND_TEXT"), query[1][2], query[1][3]);

                //Save data
                try {
                    results[0] = fetchFound.getInt(1);
                    results[1] = fetchLost.getInt(1);
                    results[2] = fetchMatched.getInt(1);
                    results[3] = fetchSent.getInt(1);
                } catch (SQLException ex) {
                    System.out.println("ERROR : " + ex);
                }

            }

            //voeg legenda toe
            PieChart.Data foundData = (results[0] == 0) ? null : new PieChart.Data(Database.option("Found"), results[0]);
            PieChart.Data lostData = (results[1] == 0) ? null : new PieChart.Data(Database.option("Lost"), results[1]);
            PieChart.Data matchedData = (results[2] == 0) ? null : new PieChart.Data(Database.option("MATCHED_TEXT"), results[2]);
            PieChart.Data sendData = (results[3] == 0) ? null : new PieChart.Data(Database.option("SEND_TEXT"), results[3]);

            //Voeg alle data toe indien er is
            PieChart.Data[] allData = new PieChart.Data[]{
                (results[0] == 0) ? null : new PieChart.Data(Database.option("Found") + " (" + results[0] + ")", results[0]),
                (results[1] == 0) ? null : new PieChart.Data(Database.option("Lost") + " (" + results[1] + ")", results[1]),
                (results[2] == 0) ? null : new PieChart.Data(Database.option("MATCHED_TEXT") + " (" + results[2] + ")", results[2]),
                (results[3] == 0) ? null : new PieChart.Data(Database.option("SEND_TEXT") + " (" + results[3] + ")", results[3])
            };

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            //Voeg data toe in die chartData array indien t niet null is
            for (PieChart.Data data : allData) {
                if (data != null) {
                    pieChartData.add(data);
                }
            }

            //voeg de list in de chart
            chart.getData().addAll(pieChartData);

        });

    }

}
