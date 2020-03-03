/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class WijzigVerlorenBagageController implements Initializable {
    
    @FXML
    private Label requiredLbl, ownerLbl, dateLbl, typeLbl, lblnrLbl, flightnrLbl, depLbl, desLbl, colorLbl, brandLbl, weightclsLbl, stateTxtLbl, currentStateLbl;
    @FXML
    private TextField lblnrTxt, flightnrTxt, typeTxt, colorTxt, brandTxt, weightclsTxt;
    @FXML
    private Button backBtn, saveBtn, addLuggageBtn, deleteBtn;
    @FXML
    private ComboBox<String> depCB, desCB, stateCB;
    @FXML
    private DatePicker datePkr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set language values
        Database db = new Database();
        Database.logStage.setWidth(600);
        Database.currentScreen = "WijzigVerlorenBagage";
       
        //Als vorige scherm reiziger was, dan maak er verlorenbagagemanager van
        if(Database.previousScreen.equals("ReizigerRegis")){
            Database.previousScreen = "VerlorenBagageManager";
        }
        
        //hide add luggagebtn
        addLuggageBtn.setVisible(false);
        saveBtn.setVisible(true);
        stateCB.setVisible(true);
        
        if (Database.selectedDataRow == 0) {
            deleteBtn.setVisible(false);
            addLuggageBtn.setVisible(true); //laat add luggage btnzien indien nieuwe registratie is
            saveBtn.setVisible(false);
            
            //voeg statussen toe in de combobox
            stateCB.getItems().addAll(Database.option("Lost"), Database.option("Found"));
        }
        
        addLuggageBtn.setText(Database.option("ADD_LUGGAGE"));
        addLuggageBtn.setOnAction(e -> {
            
            if (stateCB.getValue() == null || colorTxt.getText().equals("") || typeTxt.getText().equals("") || desCB.getValue() == null || datePkr.getValue() == null) {
                Database.showMessage(Database.option("REQUIRED_TEXT"), Database.option("COLOR_TYPE_REQUIRED"));
            } else {
                int weightInt = (weightclsTxt.getText().equals("")) ? 0 : Integer.parseInt(weightclsTxt.getText());
                
                int desCBValue = (desCB.getValue() != null) ? Integer.parseInt(Database.getTable("cn_airports", "airportName = '" + desCB.getValue() + "'", "airportId")) : 0;
                int depCBValue = (depCB.getValue() != null) ? Integer.parseInt(Database.getTable("cn_airports", "airportName = '" + depCB.getValue() + "'", "airportId")) : 0;
                int state = Database.getNewState(stateCB.getValue());

                //Als niks beschikbaar is dan is het verloren
                if (desCBValue == 0 && depCBValue == 0) {
                    state = 0;
                }
                
                String sql = "INSERT INTO cn_luggage (STATE, TRAVELER_ID, LABELNR, FLIGHTNR, DESTINATION, DEPARTURE_ID, TYPE, COLOR, BRAND, WEIGHT_CLASS, LDATE)"
                        + "VALUES(" + state + ", " + ((state == 0) ? "#TID#" : 0) + ", '" + lblnrTxt.getText() + "'  , '" + flightnrTxt.getText() + "'  , " + desCBValue + "  , " + depCBValue + "  , '" + typeTxt.getText() + "'  , '" + colorTxt.getText() + "'  , '" + brandTxt.getText() + "'  , " + weightInt + "  , '" + datePkr.getValue() + "'  )";
                
                
                if (state == 0) {
                    //Als status lost is dan maak de INSERT sql voor de lost luggage,
                    //maar voer nog niet uit, nadat traveler is geselecteerd voer dan uit
                    Database.lostSql = sql;
                    db.changeScene("ReizigersMenu");
                } else {
                    //als status gevonden is controleer match
                    int curLuggage = Database.insertQueryKey(sql);
                    db.addLog("Added Luggage: " + curLuggage);
                    checkMatching(curLuggage, state, desCBValue);
                    db.changeScene(Database.previousScreen);
                }
                
            }
        });
        
        //TRANSLATE...
        lblnrLbl.setText(Database.option("LABEL_NUMBER_LABEL"));
        flightnrLbl.setText(Database.option("FLIGHT_NUMBER_LABEL"));
        depLbl.setText(Database.option("DEPARTURE_TEXT"));
        desLbl.setText(Database.option("DESTINATION_LABEL") + " *");
        typeLbl.setText(Database.option("LUGGAGE_TYPE_LABEL") + " *");
        colorLbl.setText(Database.option("COLOR_TEXT") + " *");
        brandLbl.setText(Database.option("LUGGAGE_BRAND_LABEL"));
        weightclsLbl.setText(Database.option("WEIGHT_CLASS_LABEL"));
        stateTxtLbl.setText(Database.option("STATE_TEXT") + " *");
        backBtn.setText(Database.option("CANCEL_BACK_TEXT"));
        saveBtn.setText(Database.option("SAVE_TEXT"));
        dateLbl.setText(Database.option("DATE_LABEL") + " *");
        requiredLbl.setText("* " + Database.option("REQUIRED_TEXT"));
        
        colorTxt.setPromptText(Database.option("ENGLISH_TXT"));
        typeTxt.setPromptText(Database.option("ENGLISH_TXT"));
       
        depCB.getItems().addAll(Database.allAirports);
        desCB.getItems().addAll(Database.allAirports);
        
        if (Database.selectedDataRow != 0) {
            //Gooi waards in de txt fields indien er een gebruiker is
            ResultSet fetched = Database.fetch("cn_luggage"
                    + " INNER JOIN cn_airports as dep on dep.airportId = cn_luggage.DEPARTURE_ID"
                    + " INNER JOIN cn_airports as des on des.airportId = cn_luggage.DESTINATION"
                    + " INNER JOIN cn_state as state on state.STATE_ID = cn_luggage.STATE",
                    "cn_luggage.ID = '" + Database.selectedDataRow + "'", "",
                    "dep.airportName as 'depName', des.airportName as 'desName', cn_luggage.TRAVELER_ID, cn_luggage.LABELNR, cn_luggage.STATE, cn_luggage.LDATE, cn_luggage.FLIGHTNR, cn_luggage.TYPE, cn_luggage.COLOR, cn_luggage.BRAND, cn_luggage.WEIGHT_CLASS, state.VALUE");
            
            try {
                LocalDate date = LocalDate.parse(fetched.getString("LDATE"));
                
                lblnrTxt.setText(fetched.getString("LABELNR"));
                flightnrTxt.setText(fetched.getString("FLIGHTNR"));
                typeTxt.setText(fetched.getString("TYPE"));
                colorTxt.setText(fetched.getString("COLOR"));
                brandTxt.setText(fetched.getString("BRAND"));
                weightclsTxt.setText((fetched.getInt("WEIGHT_CLASS") != 0) ? fetched.getString("WEIGHT_CLASS") : "");
                currentStateLbl.setText(Database.option(fetched.getString("VALUE")));
                datePkr.setValue(date);
                depCB.setValue(fetched.getString("depName"));
                desCB.setValue(fetched.getString("desName"));
                stateCB.getItems().addAll(Database.option(fetched.getString("VALUE")), Database.option("Lost"), Database.option("Found"));
                stateCB.setValue(Database.option(fetched.getString("VALUE")));
                
                if (fetched.getInt("STATE") == 0) {
                    //Als status lost is, dan is er een gebruiker, laat gebruiker zien (klikbaar)
                    ResultSet traveler = Database.fetch("cn_traveler", "TID = '" + fetched.getString("TRAVELER_ID") + "'", "");
                    ownerLbl.setText(Database.option("NAME_TEXT") + ": " + traveler.getString("TNAME") + " " + traveler.getString("TLASTNAME"));
                    ownerLbl.setOnMouseClicked(e -> {
                        try {
                            Database.selectedTravelerRow = fetched.getInt("TRAVELER_ID");
                            db.changeScene("ReizigersRegis");
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    });
                } else {
                    //als status found is dan owner label niet late zien
                    ownerLbl.setVisible(false);
                }
                
            } catch (SQLException ex) {
                System.out.println("Error while fetching data: " + ex.getMessage());
            }
        }
        
        backBtn.setOnAction(e -> {
            db.changeScene(Database.previousScreen);
        });
        
        if (Database.userLevel == 2) {
            saveBtn.setVisible(false);
            deleteBtn.setVisible(false);
            addLuggageBtn.setVisible(false);
        }
        
        saveBtn.setOnAction(e -> {
            
            if (colorTxt.getText().equals("") || typeTxt.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(Database.option("COLOR_TYPE_REQUIRED"));
                alert.show();
            } else {
                
                int desCBValue = Integer.parseInt(Database.getTable("cn_airports", "airportName = '" + desCB.getValue() + "'", "airportId"));
                int depCBValue = Integer.parseInt(Database.getTable("cn_airports", "airportName = '" + depCB.getValue() + "'", "airportId"));
                int state = Database.getNewState(stateCB.getValue());
                
                int weightCls = (weightclsTxt.getText().equals("") ? 0 : Integer.parseInt(weightclsTxt.getText()));
                
                db.addLog("Edited luggage: " + Database.selectedDataRow);
                
                db.updateTable("cn_luggage", "STATE = '" + state + "', DESTINATION = '" + desCBValue + "', DEPARTURE_ID = '" + depCBValue + "', LABELNR = '" + lblnrTxt.getText() + "', FLIGHTNR = '" + flightnrTxt.getText() + "', TYPE = '" + typeTxt.getText() + "', BRAND = '" + brandTxt.getText() + "', COLOR = '" + colorTxt.getText() + "', WEIGHT_CLASS = '" + weightCls + "', LDATE = '" + datePkr.getValue() + "'", "ID = '" + Database.selectedDataRow + "'");
                
                checkMatching(Database.selectedDataRow, state, desCBValue);
 
                Database.selectedDataRow = 0;
            }
            
        });
        
        deleteBtn.setText(Database.option("DELETE_TEXT"));
        deleteBtn.setOnAction(e -> {
            String sql = "UPDATE cn_luggage SET STATE = 99 WHERE ID = '" + Database.selectedDataRow + "'";
            db.addLog("Deleted luggage: " + Database.selectedDataRow);
            Database.insertQuery(sql);
            db.changeScene(Database.previousScreen);
        });
        
        if (Database.selectedDataRow == 0) {
            ownerLbl.setVisible(false);
        }
        
    }
    
    private void checkMatching(int curLuggage, int state, int desCBValue) {
        
        String matchSql, sql;
        String lblnr = (lblnrTxt.getText().length() > 0) ? "LABELNR = '" + lblnrTxt.getText() + "' AND STATE = '" + ((state == 2) ? 0 : 2) + "' OR " : null;
        String type = (typeTxt.getText().length() > 0) ? "TYPE = '" + typeTxt.getText() + "' AND " : null;
        String color = (colorTxt.getText().length() > 0) ? "COLOR = '" + colorTxt.getText() + "' AND " : null;
        String wc = (weightclsTxt.getText().length() > 0) ? "WEIGHT_CLASS ='" + weightclsTxt.getText() + "' AND " : null;
        String ldate = (datePkr.getValue() != null) ? "LDATE ='" + datePkr.getValue() + "'" : null;
        
        if (type != null && color != null && ldate != null) {
            
            lblnr = (lblnr == null) ? "" : lblnr;
            
            matchSql = lblnr + "STATE = " + ((state == 2) ? 0 : 2) + " AND "
                    + "DESTINATION = " + desCBValue + " AND "
                    + type
                    + color
                    + ((wc == null) ? "" : wc)
                    + ldate
                    + "";
            
            ResultSet fetched = Database.fetch("cn_luggage", matchSql, "");
            
            if (fetched != null) {
                try {
                    int lostId = (state == 0) ? curLuggage : fetched.getInt("ID");
                    int foundId = (state == 2) ? curLuggage : fetched.getInt("ID");
                    
                    if (lostId != foundId) {
                        sql = "INSERT INTO cn_matchedluggage (lostId, foundId, matchedState, matchedDate)VALUES"
                                + "(" + lostId + "," + foundId + ",'MATCHED_TEXT', CURDATE())";
                        Database.insertQuery(sql);
                    }
                    
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                
            }
            
        }
        
    }
    
}
