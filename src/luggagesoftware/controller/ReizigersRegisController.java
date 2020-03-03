/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import luggagesoftware.Database;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReizigersRegisController implements Initializable {

    @FXML
    private TextField firstNameTxt;
    @FXML
    private Label firstNameLbl;
    @FXML
    private Label lastNameLbl;
    @FXML
    private Label addressLbl;
    @FXML
    private DatePicker dateOfBirthTxt;
    @FXML
    private TextField lastNameTxt;
    @FXML
    private Label dateOfBirthLbl;
    @FXML
    private TextField addressTxt;
    @FXML
    private Label cityLbl;
    @FXML
    private TextField cityTxt;
    @FXML
    private TextField countryTxt;
    @FXML
    private TextField emailAddressTxt;
    @FXML
    private TextField phoneNumberTxt;
    @FXML
    private Label countryLbl;
    @FXML
    private Label emailAddressLbl;
    @FXML
    private Label phoneNumberLbl;
    @FXML
    private Button backBtn;
    @FXML
    private Label travelerHeaderLbl;
    @FXML
    private Button saveBtn1;
    @FXML
    private MenuButton settingsMenuBtn;
    @FXML
    private MenuItem profileBtn;
    @FXML
    private MenuItem logOutBtn;
    @FXML
    private MenuItem closeBtn;
    @FXML
    private Label zipcodeLbl;
    @FXML
    private TextField zipcodeTxt;
    @FXML
    private Button deleteBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "ReizigerRegis";

        //Layout settings
        Database.logStage.setWidth(600);
        saveBtn1.setLayoutX(500);
        settingsMenuBtn.setLayoutX(500);
        Database db = new Database();

        deleteBtn.setText(Database.option("DELETE_TEXT"));

        //if lostsql doesnt have a value then show delete btn
        if (Database.lostSql == null) {
            deleteBtn.setVisible(true);
        } else {
            //if the user must select a traveler to connect it to a luggage hide btn
            deleteBtn.setVisible(false);
        }

        //if data is new traveler, hide delete btn
        if (Database.selectedTravelerRow == 0) {
            deleteBtn.setVisible(false);
        }

        deleteBtn.setOnAction(e -> {
            String sql = "DELETE FROM cn_traveler WHERE TID = '" + Database.selectedTravelerRow + "'";
            db.addLog("DELETED TRAVELER: " + Database.selectedTravelerRow);
            Database.insertQuery(sql);
            db.changeScene(Database.previousScreen);
        });

        logOutBtn.setText(Database.option("LOGOUT_TEXT"));
        logOutBtn.setOnAction(e -> {
            db.destroySession();
        });

        closeBtn.setOnAction(e -> {
            System.exit(1);
        });

        backBtn.setOnAction(e -> {
            goBack();
        });

        //Vertalen van de labels
        backBtn.setText(Database.option("BACK_TEXT"));
        saveBtn1.setText(Database.option("SAVE_TEXT"));
        settingsMenuBtn.setText(Database.option("SETTINGS_TEXT"));
        profileBtn.setText(Database.option("PROFILE_TEXT"));
        closeBtn.setText(Database.option("CLOSE_PROGRAM_TEXT"));

        firstNameLbl.setText(Database.option("FIRST_NAME_LABEL"));
        lastNameLbl.setText(Database.option("LAST_NAME_LABEL"));
        dateOfBirthLbl.setText(Database.option("DATE_OF_BIRTH_LABEL"));
        cityLbl.setText(Database.option("CITY_LABEL"));
        countryLbl.setText(Database.option("COUNTRY_LABEL"));
        emailAddressLbl.setText(Database.option("MAIL_ADDRESS_LABEL"));
        addressLbl.setText(Database.option("ADDRESS_LABEL"));
        zipcodeLbl.setText(Database.option("ZIP_CODE_LABEL"));
        phoneNumberLbl.setText(Database.option("PHONE_NUMBER_LABEL"));
        travelerHeaderLbl.setText(Database.option("ADD_TEXT"));
        if (Database.selectedTravelerRow != 0) {
            travelerHeaderLbl.setText(Database.option("EDIT_TRAVELER_TEXT"));
            ResultSet fetched = Database.fetch("cn_traveler", "TID = '" + Database.selectedTravelerRow + "'", "");
            //get values when user edits a traveler
            try {
                LocalDate date = LocalDate.parse(fetched.getString("TDATE_BIRTH"));
                firstNameTxt.setText(fetched.getString("TNAME"));
                lastNameTxt.setText(fetched.getString("TLASTNAME"));
                dateOfBirthTxt.setValue(date);
                addressTxt.setText(fetched.getString("TADDRESS"));
                cityTxt.setText(fetched.getString("TCITY"));
                countryTxt.setText(fetched.getString("TCOUNTRY"));
                phoneNumberTxt.setText(fetched.getString("TPHONE"));
                emailAddressTxt.setText(fetched.getString("TMAIL"));
                zipcodeTxt.setText(fetched.getString("TZIPCODE"));
            } catch (java.sql.SQLException ex) {
                System.out.println("Error while fetching data: " + ex.getMessage());

            }

        }

        saveBtn1.setOnAction(e -> {
            String sql = null;
            boolean edit;
            //Update table
            if (Database.selectedTravelerRow != 0) {
                edit = true;
                String zipcode = zipcodeTxt.getText().replace(" ", "");

                sql = "UPDATE cn_traveler SET "
                        + "TNAME = '" + firstNameTxt.getText() + "', "
                        + "TLASTNAME = '" + lastNameTxt.getText() + "', "
                        + "TDATE_BIRTH = '" + dateOfBirthTxt.getValue() + "', "
                        + "TADDRESS = '" + addressTxt.getText() + "', "
                        + "TCITY = '" + cityTxt.getText() + "', "
                        + "TZIPCODE = '" + zipcode + "', "
                        + "TCOUNTRY = '" + countryTxt.getText() + "', "
                        + "TMAIL = '" + emailAddressTxt.getText() + "', "
                        + "TPHONE = '" + phoneNumberTxt.getText() + "' "
                        + "WHERE TID = '" + Database.selectedTravelerRow + "'";

               

            } else {
                edit = false;
                //Insert into traveler table
                TextField[] all = {
                    firstNameTxt,
                    lastNameTxt,
                    phoneNumberTxt,
                    cityTxt,
                    zipcodeTxt,
                    countryTxt,
                    addressTxt,
                    emailAddressTxt
                };

                if (!validate(all) && dateOfBirthTxt.getValue() != null) {

                    Database.showMessage(Database.option("REQUIRED_TEXT"), Database.option("ALL_FIELD_ARE_REQUIRED_TEXT"));

                } else {

                    sql = "INSERT INTO cn_traveler ("
                            + "TNAME, TLASTNAME, TDATE_BIRTH, TADDRESS, TCITY, TCOUNTRY, TZIPCODE, TPHONE, TMAIL"
                            + ")VALUES("
                            + "'" + all[0].getText() + "',"
                            + "'" + all[1].getText() + "',"
                            + "'" + dateOfBirthTxt.getValue() + "',"
                            + "'" + all[6].getText() + "',"
                            + "'" + all[3].getText() + "',"
                            + "'" + all[5].getText() + "',"
                            + "'" + all[4].getText().replace(" ", "") + "',"
                            + "'" + all[2].getText() + "',"
                            + "'" + all[7].getText() + "')";

                }

            }
            //if sql has a value
            if (sql != null) {
                //run query and get id
                int getId = Database.insertQueryKey(sql);
                db.addLog(((edit) ? "Edited traveler: " + Database.selectedTravelerRow : getId + ""));

                if (Database.lostSql != null) {
                    //if lostsql has a value, get inserted traveler id and replace with #tid# and run query
                    Database.lostSql = Database.lostSql.replace("#TID#", getId + "");
                    int key = Database.insertQueryKey(Database.lostSql);
                    db.addLog("Added Luggage: " + key + "|| To user: " + getId);
                    //Set lostsql null
                    Database.lostSql = null;
                    db.changeScene("VerlorenBagageManager");
                } else {
                    goBack();
                }

            }

        });

    }

    //go through all fields and validate, return false if a textfield doenst have a value
    private boolean validate(TextField[] txt) {
        boolean valid = true;

        for (TextField textField : txt) {
            if (textField.getText().length() < 1) {
                valid = false;
                break;
            }

        }

        return valid;
    }

    //go back method, set traveler id = 0;
    public void goBack() {
        Database db = new Database();
        Database.selectedTravelerRow = 0;
        db.changeScene(Database.previousScreen);
    }
}
