/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProfileController implements Initializable {

    @FXML
    private Label nameLbl, changePassLbl, newPassLbl, repeatPassLbl, createdLbl, lastLoginLbl, emailLbl;

    @FXML
    private Label lastLoginValueLbl, createdValueLbl;

    @FXML
    private TextField nameTxt, emailTxt;

    @FXML
    private PasswordField nPassTxt, rnPassTxt;

    @FXML
    private Button saveBtn, gobackBtn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "Profile";
        Database.logStage.setWidth(600);
        Database db = new Database();
        //vertaal
        nameLbl.setText(Database.option("NAME_TEXT"));
        lastLoginLbl.setText(Database.option("LAST_LOGIN_TEXT"));
        createdLbl.setText(Database.option("CREATED_TEXT"));
        emailLbl.setText(Database.option("MAIL_ADDRESS_LABEL"));
        changePassLbl.setText(Database.option("PASSWORD_TEXT"));
        newPassLbl.setText(Database.option("NEW_PASSWORD_TEXT"));
        repeatPassLbl.setText(Database.option("REPEAT_NEW_PASSWORD_TEXT"));
        saveBtn.setText(Database.option("SAVE_TEXT"));
        gobackBtn.setText(Database.option("CANCEL_BACK_TEXT"));

        //Krijg last login
        String lastLogin = Database.getTable("cn_log", "USER_ID = '" + Database.userId + "' AND LOG_MESSAGE = 'LOGIN'", "LOG_DATE", "ORDER BY LOG_ID DESC LIMIT 1,1");
        lastLogin = lastLogin.substring(0, lastLogin.length() - 5);

        //Get current user info
        ResultSet row = Database.fetch("cn_users", "USER_ID = '" + Database.userId + "'", "");

        //voer values in de text vanuit de tabel
        try {
            nameTxt.setText(row.getString("NAME"));
            createdValueLbl.setText(row.getString("CREATED_DATE"));
            emailTxt.setText(row.getString("MAIL"));
            lastLoginValueLbl.setText(lastLogin);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        gobackBtn.setOnAction(e -> {
            db.changeScene(Database.previousScreen);
        });

        saveBtn.setOnAction(e -> {
            String query = "";
            
            //Wijzig wachtwoord indien beide velden zijn ingevuld, valideer het wachtwoord in de methode
            if (nPassTxt.getText().length() > 0 && rnPassTxt.getText().length() > 0) {
                db.changePassword(nPassTxt.getText(), rnPassTxt.getText(), 1);
            }

            //Voeg query toe als veld een waarde heeft
            if (nameTxt.getText().length() > 0) {
                query += "NAME = '" + nameTxt.getText() + "'";
                Database.userFullName = nameTxt.getText(); 
            }

            //Voeg query toe als veld is gevalideerd
            if (emailTxt.getText().contains("@") && emailTxt.getText().contains(".")) {
                query += (!query.equals("")) ? ", " : "";
                query += "MAIL = '" + emailTxt.getText() + "'";
            }
            
            db.addLog("Updated profile");
            
            //Update de tabel
            db.updateTable("cn_users", query, "USER_ID = " + Database.userId + "");            
        });

    }

}
