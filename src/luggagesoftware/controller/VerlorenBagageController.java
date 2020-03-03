/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VerlorenBagageController implements Initializable {

    @FXML
    private AnchorPane verlorenBagagePane;
    @FXML
    private Button editBtn;
    @FXML
    private Button lostLuggageBtn;
    @FXML
    private Button foundLuggageBtn;
    @FXML
    private Button statisticsBtn;
    @FXML
    private Button contributorsBtn;
    @FXML
    private MenuButton settingsMenuBtn;
    @FXML
    private MenuItem profileBtn;
    @FXML
    private MenuItem logOutBtn;
    @FXML
    private MenuItem closeBtn;
    @FXML
    private Button travelersBtn;
    @FXML
    private TableView<Luggage> table;
    @FXML
    private Button addLuggageBtn;
    @FXML
    private TextField lblnrTxt;
    @FXML
    private TextField flightnrTxt;
    @FXML
    private TextField typeTxt;
    @FXML
    private TextField colorTxt;
    @FXML
    private TextField brandTxt;
    @FXML
    private TextField weightclsTxt;
    @FXML
    private DatePicker datePkr;
    @FXML
    private ComboBox<String> depCB;
    @FXML
    private ComboBox<String> desCB;
    @FXML
    private ComboBox<String> stateCB;
    @FXML
    private Button filterBtn;
    @FXML
    private Button resetBtn;

    private ObservableList<Luggage> data = FXCollections.observableArrayList();
    private ObservableList<Luggage> filteredData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "VerlorenBagageManager";

        Database db = new Database();
        data = loadData();
        table.getItems().clear();
        table.getItems().addAll(data);

        logOutBtn.setText(Database.option("LOGOUT_TEXT"));

        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        editBtn.setVisible(true);

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });
        lostLuggageBtn.setText(Database.option("LOST_LUGGAGE_TEXT"));
        lostLuggageBtn.setStyle("-fx-cursor:default;");

        foundLuggageBtn.setText(Database.option("MATCHES_TEXT"));
        foundLuggageBtn.setOnAction(e -> {
            db.changeScene("Match");
        });

        travelersBtn.setText(Database.option("TRAVELER_TEXT"));
        travelersBtn.setOnAction(e -> {
            db.changeScene("ReizigersMenu");
        });
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

        profileBtn.setOnAction(e -> {
            db.changeScene("Profile");
        });

        flightnrTxt.setPromptText(Database.option("FLIGHT_NUMBER_LABEL"));
        lblnrTxt.setPromptText(Database.option("LABEL_NUMBER_LABEL"));
        brandTxt.setPromptText(Database.option("LUGGAGE_BRAND_LABEL"));
        typeTxt.setPromptText(Database.option("LUGGAGE_TYPE_LABEL"));
        colorTxt.setPromptText(Database.option("COLOR_TEXT"));
        weightclsTxt.setPromptText(Database.option("WEIGHT_CLASS_LABEL"));
        datePkr.setPromptText(Database.option("DATE_LABEL"));

        depCB.setPromptText(Database.option("DEPARTURE_TEXT"));
        desCB.setPromptText(Database.option("DESTINATION_LABEL"));
        stateCB.setPromptText(Database.option("STATE_TEXT"));

        depCB.getItems().add(Database.option("DEPARTURE_TEXT"));
        desCB.getItems().add(Database.option("DESTINATION_LABEL"));

        stateCB.getItems().addAll(Database.option("STATE_TEXT"), Database.option("Lost"), Database.option("Found"));
        depCB.getItems().addAll(Database.allAirports);
        desCB.getItems().addAll(Database.allAirports);

        filterBtn.setText(Database.option("SEARCH_TEXT"));
        resetBtn.setText(Database.option("RESET_TEXT"));

        //reset
        resetBtn.setOnAction(e -> {
            lblnrTxt.setText("");
            flightnrTxt.setText("");
            depCB.setValue(Database.option("DEPARTURE_TEXT"));
            desCB.setValue(Database.option("DESTINATION_LABEL"));
            typeTxt.setText("");
            colorTxt.setText("");
            brandTxt.setText("");
            weightclsTxt.setText("");
            stateCB.setValue(Database.option("STATE_TEXT"));
            datePkr.setValue(null);
            //load all data
            data = loadData();
            table.getItems().clear();
            table.getItems().addAll(data);

        });

        filterBtn.setOnAction(e -> {
            //maak query adhv de filters
            String subSql = "";
            if (!lblnrTxt.getText().equals("")) {
                subSql += "luggage.LABELNR LIKE '%" + lblnrTxt.getText() + "%' AND ";
            }

            if (!typeTxt.getText().equals("")) {
                subSql += "luggage.TYPE LIKE '%" + typeTxt.getText() + "%' AND ";
            }

            if (!colorTxt.getText().equals("")) {
                subSql += "luggage.COLOR LIKE '%" + colorTxt.getText() + "%' AND ";
            }

            if (!brandTxt.getText().equals("")) {
                subSql += "luggage.BRAND LIKE '%" + brandTxt.getText() + "%' AND ";
            }

            if (!flightnrTxt.getText().equals("")) {
                subSql += "luggage.FLIGHTNR LIKE '%" + flightnrTxt.getText() + "%' AND ";
            }

            if (!weightclsTxt.getText().equals("")) {
                subSql += "luggage.WEIGHT_CLASS LIKE '%" + weightclsTxt.getText() + "%' AND ";
            }

            if (datePkr.getValue() != null) {
                subSql += "luggage.LDATE LIKE '%" + datePkr.getValue() + "%' AND ";
            }

            if (!stateCB.getSelectionModel().isEmpty() && !stateCB.getValue().equals(Database.option("STATE_TEXT"))) {
                int state = Database.getNewState(stateCB.getValue());
                subSql += (state != 99) ? "luggage.STATE = " + state + " AND " : "";
            }

            if (!depCB.getSelectionModel().isEmpty() && !depCB.getValue().equals(Database.option("DEPARTURE_TEXT"))) {
                int depId = Integer.parseInt(Database.getTable("cn_airports", "airportName = '" + depCB.getValue() + "'", "airportId"));
                subSql += (depId != 0) ? "luggage.DEPARTURE_ID = " + depId + " AND " : "";
            }

            if (!desCB.getSelectionModel().isEmpty() && !desCB.getValue().equals(Database.option("DESTINATION_LABEL"))) {
                int desId = Integer.parseInt(Database.getTable("cn_airports", "airportName = '" + desCB.getValue() + "'", "airportId"));
                subSql += (desId != 0) ? "luggage.DESTINATION = " + desId + " AND " : "";
            }

            //voer query uit indien er een filter is
            if (subSql.length() > 1) {
                String filterSql = "SELECT *, air_.airportName as departureName, air.airportName as destinationName FROM cn_luggage luggage"
                        + " INNER JOIN cn_airports air ON air.airportId = luggage.DESTINATION"
                        + " INNER JOIN cn_airports air_ ON air_.airportId = luggage.DEPARTURE_ID"
                        + " INNER JOIN cn_state ON luggage.STATE = cn_state.STATE_ID"
                        + " WHERE ";
                subSql = subSql.substring(0, subSql.length() - 4);

                String finalSql = filterSql + subSql;
                //maak filterd data leeg
                filteredData.clear();

                table.getItems().clear();
                Statement stmtFiltered;

                //voeg waardes in de filteredData
                try {
                    stmtFiltered = db.connection.createStatement();
                    ResultSet rowx = stmtFiltered.executeQuery(finalSql);

                    boolean found = false;

                    while (rowx.next()) {
                        found = true;
                        String status;
                        String destination = rowx.getString("destinationName");
                        String departure = rowx.getString("departureName");
                        status = Database.option(rowx.getString("VALUE"));
                        filteredData.add(new Luggage(rowx.getString("LABELNR"), rowx.getString("FLIGHTNR"), status, rowx.getString("COLOR"), rowx.getString("TYPE"), rowx.getString("ID"), destination, departure, rowx.getString("LDATE")));

                    }
                    
                    if (!found) {
                        Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NO_RESULTS_TEXT"));
                    }
                    
                    table.getItems().addAll(filteredData);

                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }

        });

        //Hide add luggage button if role is manager
        if (Database.userLevel == 2) {
            addLuggageBtn.setVisible(false);
        }

        addLuggageBtn.setText(Database.option("ADD_LUGGAGE"));
        addLuggageBtn.setOnAction(e -> {
            Database.selectedDataRow = 0;
            db.changeScene("WijzigVerlorenBagage");
        });

        editBtn.setText(Database.option("EDIT_TEXT"));
        editBtn.setOnAction(e -> {

            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            if (selectedIndex >= 0 && selectedIndex < data.size()) {
                Database.selectedDataRow = Integer.parseInt(data.get(selectedIndex).getString("luggageId"));
                db.changeScene("WijzigVerlorenBagage");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(Database.option("NOTHING_SELECTED_TEXT"));
                alert.show();
            }

        });

        //show menu aan de hand van de rol
        db.showMenu(contributorsBtn, statisticsBtn, travelersBtn, foundLuggageBtn, lostLuggageBtn, settingsMenuBtn);

        //maak kolommen
        table.setEditable(true);
        TableColumn labelNumberC = new TableColumn(Database.option("LABEL_NUMBER_LABEL"));
        TableColumn flightNumberC = new TableColumn(Database.option("FLIGHT_NUMBER_LABEL"));
        TableColumn stateC = new TableColumn(Database.option("STATE_TEXT"));
        TableColumn colorC = new TableColumn(Database.option("COLOR_TEXT"));
        TableColumn typeC = new TableColumn(Database.option("LUGGAGE_TYPE_LABEL"));
        TableColumn depC = new TableColumn(Database.option("DEPARTURE_TEXT"));
        TableColumn desC = new TableColumn(Database.option("DESTINATION_LABEL"));
        TableColumn dateC = new TableColumn(Database.option("DATE_LABEL"));

        //Maak de cellen
        PropertyValueFactory lblnr = new PropertyValueFactory<>("labelNumber");
        labelNumberC.setCellValueFactory(lblnr);

        PropertyValueFactory depa = new PropertyValueFactory<>("departure");
        depC.setCellValueFactory(depa);

        PropertyValueFactory dest = new PropertyValueFactory<>("destination");
        desC.setCellValueFactory(dest);

        PropertyValueFactory date = new PropertyValueFactory<>("date");
        dateC.setCellValueFactory(date);

        PropertyValueFactory flightnr = new PropertyValueFactory<>("flightNumber");
        flightNumberC.setCellValueFactory(flightnr);

        PropertyValueFactory statex = new PropertyValueFactory<>("state");
        stateC.setCellValueFactory(statex);

        PropertyValueFactory colorx = new PropertyValueFactory<>("color");
        colorC.setCellValueFactory(colorx);

        PropertyValueFactory typex = new PropertyValueFactory<>("type");
        typeC.setCellValueFactory(typex);

        //sla waardes op in de tabel van data list
        table.setItems(data);

        //voeg kolommen toe
        table.getColumns().addAll(labelNumberC, flightNumberC, stateC, colorC, typeC, depC, desC, dateC);

    }

    private ObservableList<Luggage> loadData() {
        ObservableList<Luggage> tmpData = FXCollections.observableArrayList();
        Database db = new Database();

        String sql = "SELECT *, air.airportName as destinationName, air_.airportName as departureName FROM cn_luggage luggage "
                + "INNER JOIN cn_state state ON luggage.STATE = state.STATE_ID "
                + "INNER JOIN cn_airports air ON air.airportId = luggage.DESTINATION "
                + "INNER JOIN cn_airports air_ ON air_.airportId = luggage.DEPARTURE_ID "
                + "WHERE luggage.STATE != 99  ORDER BY ID DESC";

        Statement stmt;

        try {
            stmt = db.connection.createStatement();
            ResultSet row = stmt.executeQuery(sql);

            while (row.next()) {
                String destination = row.getString("destinationName");
                String departure = row.getString("departureName");
                String status = Database.option(row.getString("VALUE"));
                tmpData.add(new Luggage(row.getString("LABELNR"), row.getString("FLIGHTNR"), status, row.getString("COLOR"), row.getString("TYPE"), row.getString("ID"), destination, departure, row.getString("LDATE")));
            }

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(VerlorenBagageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tmpData;
    }
}
