/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import luggagesoftware.Database;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MedewerkersToevoegenController implements Initializable {

    @FXML
    private Label headerLbl;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField newPassTxt;
    @FXML
    private Label lastloginLbl, currentAirportLblValue;
    @FXML
    private Label newPassLbl;
    @FXML
    private Label statusLbl;
    @FXML
    private Label createdLblTxt;
    @FXML
    private Label emailLbl;
    @FXML
    private Label nameLbl;
    @FXML
    private Button backBtn;
    @FXML
    private Button saveBtn, deleteBtn;
    @FXML
    private MenuButton settingsMenuBtn;
    @FXML
    private MenuItem profileBtn;
    @FXML
    private MenuItem logOutBtn;
    @FXML
    private MenuItem closeBtn;
    @FXML
    private Label usernameLbl;
    @FXML
    private TextField usernameTxt;
    @FXML
    private Label createdLbl;
    @FXML
    private ComboBox<String> statusCB;
    @FXML
    private Label lastloginLblValue;
    @FXML
    private ToggleGroup levelGroup;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton managerRB;
    @FXML
    private RadioButton userRB;
    @FXML
    private Label userLevelLbl;
    @FXML
    private Label genderLbl;
    @FXML
    private RadioButton vrouwRB;
    @FXML
    private RadioButton manRB;
    @FXML
    private Label currentAirportLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Database.currentScreen = "MedewerkersToevoegen";
        Database db = new Database();

        logOutBtn.setText(Database.option(Database.language, "LOGOUT_TEXT"));

        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });

        backBtn.setOnAction(e -> {
            db.changeScene("Medewerkers");

        });

        //Sla gegevens op indien save word geklikt
        saveBtn.setOnAction(e -> {
            String[] logString = new String[]{"", ""};
            String passChange = "", genderString;
            int lvl, status;
            genderString = (manRB.isSelected() && !vrouwRB.isSelected()) ? "M" : (!manRB.isSelected() && vrouwRB.isSelected()) ? "V" : "";
            lvl = (managerRB.isSelected() && !userRB.isSelected()) ? 2 : (!managerRB.isSelected() && userRB.isSelected()) ? 1 : 0;
            status = (statusCB.getValue() != null && statusCB.getValue().equals(Database.option("ACTIVE_TEXT"))) ? 1 : (statusCB.getValue() != null && statusCB.getValue().equals(Database.option("DISABLED_TEXT"))) ? 0 : -1;

            //valideer de velden
            boolean doorgaan = true;
            if (status == -1 || lvl == 0 || genderString.equals("") || usernameTxt.getText().length() < 1 || emailTxt.getText().length() < 1 || nameTxt.getText().length() < 1 || (Database.selectedUser == 0 && newPassTxt.getText().length() < 1)) {
                doorgaan = false;
            }

            //Ga door met opslaan indien er een bestaande gebruiker is en alles is gevalideerd
            if (Database.selectedUser != 0 && doorgaan) {
                //Als er een wachtwoord is ingevoerd, wijzig dan het wachtwoord
                if (newPassTxt.getText().length() > 0) {
                    //Met MD5 encryptie via sql
                    passChange = "PASSWORD = MD5('" + newPassTxt.getText() + "'), ";
                    logString[0] = "Changed password of user: " + Database.selectedUser;
                }
                logString[1] = "Updated user: " + Database.selectedUser;

                //update de user gegevens
                String query = "UPDATE cn_users SET "
                        + "NAME = '" + nameTxt.getText() + "', "
                        + "USERNAME = '" + usernameTxt.getText() + "', "
                        + "GENDER = '" + genderString + "', "
                        + passChange
                        + "MAIL = '" + emailTxt.getText() + "', "
                        + "LEVEL = '" + lvl + "', "
                        + "USER_STATE = '" + status + "' "
                        + "WHERE USER_ID = '" + Database.selectedUser + "'";
                
                //update de tabel
                Database.insertQuery(query);

                //voeg log toe
                for (String log : logString) {
                    if (log.length() > 0) {
                        try {
                            db.addLog(Database.userId, "NORMAL", log);
                        } catch (SQLException ex) {
                            Logger.getLogger(MedewerkersToevoegenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                //zet selected user op 0
                Database.selectedUser = 0;
                
                db.changeScene(Database.previousScreen);

            } else if (Database.selectedUser == 0 && doorgaan) {
                //Als nieuwe gebruier word geregistreerd en alles is gevalideerd
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(new Date());
                String sql = "INSERT INTO cn_users (NAME, USERNAME, MAIL, PASSWORD, LEVEL, GENDER, USER_STATE, CREATED_DATE, AIRPORT_ID)VALUES("
                        + "'" + nameTxt.getText() + "',"
                        + "'" + usernameTxt.getText() + "',"
                        + "'" + emailTxt.getText() + "',"
                        + "MD5('" + newPassTxt.getText() + "'),"
                        + "'" + lvl + "',"
                        + "'" + genderString + "',"
                        + "'" + status + "',"
                        + "'" + date + "',"
                        + "'" + Database.connectedAirportID + "'"
                        + ")";

                //voer query uit
                Database.insertQuery(sql);
                db.changeScene(Database.previousScreen);
            } else {
                //Laat bericht zien indien niet alles is gevalideerd
                Database.showMessage(Database.option("ERROR_TEXT"), Database.option("STAR_FIELDS_REQUIRED"));
            }

        });

        //verwijder user en voeg toe aan log
        deleteBtn.setOnAction(e -> {
            String sql = "DELETE FROM cn_users WHERE USER_ID = '" + Database.selectedUser + "'";
            Database.insertQuery(sql);
            db.addLog("DELETED USER: " + nameTxt.getText());
            db.changeScene(Database.previousScreen);
        });

        headerLbl.setText(Database.option((Database.selectedUser != 0) ? "USER_EDIT_TEXT" : "USER_REGISTRATION_LABEL"));
        nameLbl.setText(Database.option("NAME_TEXT") + " *");
        usernameLbl.setText(Database.option("USERNAME_TEXT") + " *");
        emailLbl.setText(Database.option("MAIL_ADDRESS_LABEL") + " *");
        String star = (Database.selectedUser == 0) ? " *" : "";
        newPassLbl.setText(Database.option("RESET_PASS_HEADER") + star);
        createdLblTxt.setText(Database.option("CREATED_DATE_TEXT"));

        statusCB.setPromptText(Database.option("STATE_TEXT"));

        statusLbl.setText(Database.option("STATE_TEXT") + " *");
        userLevelLbl.setText(Database.option("LEVEL_TEXT") + " *");
        genderLbl.setText(Database.option("GENDER_TEXT") + " *");
        lastloginLbl.setText(Database.option("LAST_LOGIN_TEXT"));

        managerRB.setText(Database.option("MANAGER_TEXT"));
        userRB.setText(Database.option("EMPLOYEE_TEXT"));

        manRB.setText(Database.option("MEN_TEXT"));
        vrouwRB.setText(Database.option("WOMAN_TEXT"));

        statusCB.getItems().addAll(Database.option("DISABLED_TEXT"), Database.option("ACTIVE_TEXT"));

        currentAirportLbl.setText(Database.option("LUCHTHAVEN_TEXT"));

        closeBtn.setText(Database.option("CLOSE_PROGRAM_TEXT"));
        backBtn.setText(Database.option("BACK_TEXT"));
        saveBtn.setText(Database.option("SAVE_TEXT"));
        settingsMenuBtn.setText(Database.option("SETTINGS_TEXT"));
        profileBtn.setText(Database.option("PROFILE_TEXT"));

        String airport;

        //get data, indien er een bestaande gebruiker word gewijzigd
        if (Database.selectedUser != 0) {
            ResultSet fetch = Database.fetch("cn_users", "USER_ID = '" + Database.selectedUser + "'", "");

            try {
                airport = Database.getTable("cn_airports", "airportId = '" + fetch.getString("AIRPORT_ID") + "'", "airportName", "");
                currentAirportLblValue.setText(airport);
                nameTxt.setText(fetch.getString("NAME"));
                usernameTxt.setText(fetch.getString("USERNAME"));
                emailTxt.setText(fetch.getString("MAIL"));
                createdLbl.setText(fetch.getString("CREATED_DATE"));
                statusCB.setValue((Integer.parseInt(fetch.getString("USER_STATE")) == 1) ? Database.option("ACTIVE_TEXT") : Database.option("DISABLED_TEXT"));

                //Krijg laatste login datum (niet huidige sessie maar daarvoor)
                String lastLogin = Database.getTable("cn_log", "USER_ID = '" + Database.userId + "' AND LOG_MESSAGE = 'LOGIN'", "LOG_DATE", "ORDER BY LOG_ID DESC LIMIT 1,1");
                lastloginLblValue.setText((lastLogin.equals("")?"":lastLogin));

                levelGroup.selectToggle((Integer.parseInt(fetch.getString("LEVEL")) == 2) ? managerRB : userRB);

                genderGroup.selectToggle((fetch.getString("GENDER").equals("M")) ? manRB : vrouwRB);

            } catch (SQLException ex) {
                System.out.println(ex);
            }

        } else {
            //als nieuwe gebruiker word geregistreerd
            currentAirportLbl.setVisible(false);
            currentAirportLblValue.setVisible(false);
            lastloginLbl.setVisible(false);
            lastloginLblValue.setVisible(false);
            createdLblTxt.setVisible(false);
            createdLbl.setVisible(false);
            deleteBtn.setVisible(false);

        }
        
        //layout netjes instellen
        Database.logStage.setWidth(600);
        settingsMenuBtn.setLayoutX(settingsMenuBtn.getLayoutX() - 180);
        headerLbl.setLayoutX(headerLbl.getLayoutX() - 50);
        saveBtn.setPrefWidth(130);
        saveBtn.setLayoutX(Database.logStage.getWidth() - 150);
        headerLbl.setLayoutX(headerLbl.getLayoutX() - 30);
        headerLbl.setPrefWidth(400);
        deleteBtn.setLayoutX(deleteBtn.getLayoutX() - 100);
        deleteBtn.setLayoutY(100);
        deleteBtn.setPrefWidth(180);
    }

}
