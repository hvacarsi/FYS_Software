/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.net.URL;
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
public class ResetPasswordController implements Initializable {

    @FXML
    public TextField userNameTxt;
    public Label resetPasswordHeader;
    public PasswordField passField, repeatPassField;
    public Button savePassBtn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "ForgotPassword";
        Database.logStage.setWidth(339);
        passField.setPromptText(Database.option("NEW_PASSWORD_TEXT"));
        repeatPassField.setPromptText(Database.option("REPEAT_NEW_PASSWORD_TEXT"));
        savePassBtn.setText(Database.option("SAVE_PASSWORD_TEXT"));
        resetPasswordHeader.setText(Database.option("RESET_PASS_HEADER"));
        userNameTxt.setText(Database.userFullName);
        
        savePassBtn.setOnAction(e ->{
            Database db = new Database();
            
            //Call changepassword method, this method will validate and run the query if everything is ok
            db.changePassword(passField.getText(), repeatPassField.getText());
            
        });
        
        
    }    
    
}
