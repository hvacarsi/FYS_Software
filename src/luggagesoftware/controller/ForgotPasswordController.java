package luggagesoftware;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Emin Torun
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private Button sendMailBtn;
    private TextField emailTxt;
    private Hyperlink loginLink;
    private Label emailLbl;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.currentScreen = "ForgotPassword";
        Database.logStage.setWidth(368);
        emailTxt.setPromptText(Database.option(Database.language, "EMAIL_TEXT"));
        sendMailBtn.setText(Database.option(Database.language, "SEND_PASSWORD_TEXT"));
        loginLink.setText(Database.option(Database.language, "GO_LOGIN_TEXT"));
        emailLbl.setText(Database.option("EMAIL_TEXT"));
        
        loginLink.setOnAction(e -> {
            Database db = new Database();
            db.changeScene("Login");
        });
        
        sendMailBtn.setOnAction(e -> {
            checkResetAccount();
        });
        
    }

    public void checkResetAccount() {
        String emailAddress = emailTxt.getText();
        if (emailAddress.length() < 1) {
            //Toon melding indien veld leeg is
            String title = Database.option("ERROR_TEXT");
            String message = Database.option("ERROR_EMPTY_EMAIL_TEXT");
            Database.showMessage(title, message);
        } else {
            //Controleer of email adres geldig is
            if (emailAddress.contains("@") && emailAddress.contains(".")) {
                //Email geldig voer methode id database uit
                Database db = new Database();
                db.checkRestore(emailAddress);
            } else {
                //Email is niet geldig
                String title = Database.option(Database.language, "ERROR_TEXT");
                String message = Database.option(Database.language, "ERROR_EMPTY_EMAIL_TEXT");
                Database.showMessage(title, message);
            }

        }

    }

}
