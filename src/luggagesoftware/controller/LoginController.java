/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author User
 */
public class LoginController implements Initializable {

    @FXML
    public Label label;
    public Label loginErrorLbl;
    public Hyperlink forgotPassword;
    public TextField name;
    public TextField password;
    public Button button, spanishBtn, englishBtn, turkishBtn, dutchBtn;

    public static String username;
    public static String errorText = "";

    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        Database.checkLogin(name.getText(), password.getText());

        if(!errorText.equals("")){
            loginErrorLbl.setText(errorText);
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Database.currentScreen = "Login";
        Database.logStage.setWidth(455);
        Database db = new Database();
        changeLang(Database.language);
        
        //Wijzig taal wanneer een taal (button) is geselecteerd
        spanishBtn.setOnAction(e -> {
            changeLang(5);
            loginErrorLbl.setText("");
        });

        dutchBtn.setOnAction(e -> {
            changeLang(6);
            loginErrorLbl.setText("");
        });

        turkishBtn.setOnAction(e -> {
            changeLang(4);
            loginErrorLbl.setText("");
        });

        englishBtn.setOnAction(e -> {
            changeLang(3);
            loginErrorLbl.setText("");
        });

        forgotPassword.setOnAction(e -> {
            username = name.getText();
            db.changeScene("ForgotPassword");
        });

          
    }

    public void changeLang(int langId) {
        Database.language = langId; //zet id vd language in Database class variabel
        
        //Vertaal huidige scherm
        name.setPromptText(Database.option(Database.language, "USERNAME_TEXT"));
        password.setPromptText(Database.option(Database.language, "PASSWORD_TEXT"));
        button.setText(Database.option(Database.language, "LOGIN_TEXT"));
        forgotPassword.setText(Database.option(Database.language, "FORGOT_PASSWORD_TEXT"));
    }

}
