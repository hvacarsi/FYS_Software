/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import luggagesoftware.controller.Employee;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MedewerkersController implements Initializable {

    @FXML
    private Button lostLuggageBtn;
    @FXML
    private Button foundLuggageBtn;
    @FXML
    private Button statisticsBtn;
    @FXML
    private Button contributorsBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button registerBtn;
    @FXML
    private MenuButton settingsMenuBtn;
    @FXML
    private MenuItem profileBtn;
    @FXML
    private MenuItem logOutBtn;
    @FXML
    private MenuItem closeBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private ComboBox<String> whatCB;
    @FXML
    private TextField keywordFld;
    @FXML
    private TableView<Employee> table;

    //Maak list, een voor alle data de andere voor gefilterde data
    private ObservableList<Employee> data = FXCollections.observableArrayList();
    private ObservableList<Employee> filteredData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Database.currentScreen = "Medewerkers";
        Database.selectedUser = 0;
        Database db = new Database();

        //Laad data...
        data = loadData();
        table.getItems().clear();
        table.getItems().addAll(data);

        logOutBtn.setText(Database.option(Database.language, "LOGOUT_TEXT"));
        closeBtn.setText(Database.option("CLOSE_PROGRAM_TEXT"));

        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });
        lostLuggageBtn.setOnAction(e -> {
            db.changeScene("VerlorenBagageManager");
        });

        profileBtn.setOnAction(e -> {
            db.changeScene("Profile");

        });

        foundLuggageBtn.setOnAction(e -> {
            db.changeScene("Match");
        });

        statisticsBtn.setOnAction(e -> {
            db.changeScene("Statistieken");
        });

        editBtn.setOnAction(e -> {
            //Krijg matchId van de geselecteerde rij
            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            if (selectedIndex >= 0 && selectedIndex < data.size()) {
                //krijg de gegevens van de rij door middel van de fetch methode id database class
                Database.selectedUser = Integer.parseInt(data.get(selectedIndex).getString("id"));
                db.changeScene("MedewerkersToevoegen");
            } else {
                //laat bericht zien indien niks is geselecteerd
                Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NOTHING_SELECTED_TEXT"));
            }
        });

        searchBtn.setOnAction(e -> {
            String filterSql = "SELECT * FROM cn_users INNER JOIN cn_airports airport ON airport.airportId = cn_users.AIRPORT_ID WHERE cn_users.USER_ID != " + Database.userId + " AND ";
            String subSql;

            //bekijk of beide velden een waarde hebben, filter dan pas
            if (keywordFld.getText().length() > 0 && !whatCB.getSelectionModel().isEmpty()) {

                subSql = (whatCB.getValue().equals(Database.option("NAME_TEXT"))) ? "NAME"
                        : (whatCB.getValue().equals(Database.option("USERNAME_TEXT"))) ? "USERNAME"
                        : (whatCB.getValue().equals(Database.option("MAIL_ADDRESS_LABEL"))) ? "MAIL" : "NAME";

                subSql += " LIKE '%" + keywordFld.getText() + "%'";

                //Meng de 2 queries
                filterSql += subSql;

                //indien de subsql een waarde heeeft... 
                if (subSql.length() > 1) {
                    String finalSql = filterSql;
                    //maak gefilterde data leeg
                    filteredData.clear();
                    //clean the table
                    table.getItems().clear();
                    Statement stmtFiltered;

                    try {
                        stmtFiltered = db.connection.createStatement();
                        ResultSet rowx = stmtFiltered.executeQuery(finalSql);
                        LocalDate date;
                        String rol, status;

                        //Kijk of er een waarde is gekregen uit de db
                        boolean found = false;

                        while (rowx.next()) {
                            found = true;
                            rol = Database.option(((Integer.parseInt(rowx.getString("LEVEL")) == 2)) ? "MANAGER_TEXT" : "EMPLOYEE_TEXT");
                            status = Database.option(((Integer.parseInt(rowx.getString("USER_STATE")) == 0)) ? "DISABLED_TEXT" : "ACTIVE_TEXT");
                            date = LocalDate.parse(rowx.getString("CREATED_DATE"));
                            filteredData.add(new Employee(rowx.getString("USER_ID"), rowx.getString("NAME"), rowx.getString("USERNAME"), rowx.getString("MAIL"), rol, rowx.getString("airportName"), status));
                        }

                        //voeg filtered list toe in de table
                        table.getItems().addAll(filteredData);

                        if (!found) {
                            Database.showMessage(Database.option("ERROR_TEXT"), Database.option("NO_RESULTS_TEXT"));
                        }

                    } catch (java.sql.SQLException ex) {
                        System.out.println(ex);
                    }
                }

            }

        });

        resetBtn.setOnAction(e -> {
            //Maak velden leeg indien reset word geklikt, en laad alle data terug
            keywordFld.setText("");
            whatCB.setValue("");
            data = loadData();
            table.getItems().clear();
            table.getItems().addAll(data);

        });

        registerBtn.setOnAction(e -> {
            db.changeScene("MedewerkersToevoegen");
        });
        //Translation
        lostLuggageBtn.setText(Database.option(Database.language, "LOST_LUGGAGE_TEXT"));
        foundLuggageBtn.setText(Database.option(Database.language, "MATCHES_TEXT"));
        statisticsBtn.setText(Database.option(Database.language, "STATISTICS_TEXT"));
        contributorsBtn.setText(Database.option(Database.language, "CONTRIBUTORS_TEXT"));
        editBtn.setText(Database.option(Database.language, "EDIT_TEXT"));
        searchBtn.setText(Database.option(Database.language, "SEARCH_TEXT"));
        registerBtn.setText(Database.option(Database.language, "REGISTER_TEXT"));
        settingsMenuBtn.setText(Database.option(Database.language, "SETTINGS_TEXT"));
        profileBtn.setText(Database.option(Database.language, "PROFILE_TEXT"));

        //Show menu adhv de gebruikersrol
        db.showMenu(registerBtn, foundLuggageBtn, lostLuggageBtn, settingsMenuBtn);

        editBtn.setVisible(true);
        searchBtn.setVisible(true);
        registerBtn.setVisible(true);

        keywordFld.setPromptText(Database.option("KEYWORD_TEXT"));
        whatCB.setPromptText(Database.option("SELECT_OPTION_TEXT"));
        whatCB.getItems().addAll(Database.option("NAME_TEXT"), Database.option("USERNAME_TEXT"), Database.option("MAIL_ADDRESS_LABEL"));
        table.setEditable(true);

        //creer kolommen
        TableColumn namec = new TableColumn(Database.option("NAME_TEXT"));
        TableColumn usernamec = new TableColumn(Database.option("USERNAME_TEXT"));
        TableColumn mailc = new TableColumn(Database.option("MAIL_ADDRESS_LABEL"));
        TableColumn levelc = new TableColumn(Database.option("LEVEL_TEXT"));
        TableColumn airportc = new TableColumn(Database.option("LUCHTHAVEN_TEXT"));
        TableColumn statec = new TableColumn(Database.option("STATE_TEXT"));

        //Verwijs naar de juiste waardes in de Employee class
        PropertyValueFactory naamv = new PropertyValueFactory<>("name");
        namec.setCellValueFactory(naamv);

        PropertyValueFactory achternaamv = new PropertyValueFactory<>("username");
        usernamec.setCellValueFactory(achternaamv);

        PropertyValueFactory mailv = new PropertyValueFactory<>("mail");
        mailc.setCellValueFactory(mailv);

        PropertyValueFactory levelv = new PropertyValueFactory<>("level");
        levelc.setCellValueFactory(levelv);

        PropertyValueFactory statev = new PropertyValueFactory<>("state");
        statec.setCellValueFactory(statev);

        PropertyValueFactory airportv = new PropertyValueFactory<>("airport_id");
        airportc.setCellValueFactory(airportv);

        //Voeg waardes in de tabel toe
        table.setItems(data);
        //voeg colommen toe
        table.getColumns().addAll(namec, usernamec, mailc, levelc, airportc, statec);

    }

    private ObservableList<Employee> loadData() {
        //creer list
        ObservableList<Employee> tmpData = FXCollections.observableArrayList();
        Database db = new Database();

        String sql = "SELECT * FROM cn_users INNER JOIN cn_airports airport ON airport.airportId = cn_users.AIRPORT_ID WHERE cn_users.USER_ID != " + Database.userId + " ORDER BY USER_ID DESC";

        Statement stmt;

        try {
            stmt = db.connection.createStatement();
            ResultSet row = stmt.executeQuery(sql);
            LocalDate date;
            String rol, status;
            while (row.next()) {
                rol = Database.option(((Integer.parseInt(row.getString("LEVEL")) == 2)) ? "MANAGER_TEXT" : "EMPLOYEE_TEXT");
                status = Database.option(((Integer.parseInt(row.getString("USER_STATE")) == 0)) ? "DISABLED_TEXT" : "ACTIVE_TEXT");
                date = LocalDate.parse(row.getString("CREATED_DATE"));
                tmpData.add(new Employee(row.getString("USER_ID"), row.getString("NAME"), row.getString("USERNAME"), row.getString("MAIL"), rol, row.getString("airportName"), status));
            }

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(luggagesoftware.VerlorenBagageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tmpData;
    }

}
