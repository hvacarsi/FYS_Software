/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class LuggageSoftware extends Application {

    Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        //Sla de taal op in arrays
        Database.setOptions();

        //Sla de airports op in een observableList
        Database.addAirports();
        
        //Verwijder bagage ouder dan 1 jaar, wanneer applicatie opstart;
        Database.removeLuggage();
                
        window = stage;
        window.setResizable(false);

        Database.logStage = stage;

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
        Parent root = (Parent) loginLoader.load();

        Scene scene = new Scene(root);
        Database.myScene = scene;
        window.setTitle(Database.option("PROGRAM_NAME"));
        window.setScene(scene);
        window.getIcons().add(new Image("/images/icon.png"));
        window.show();
        
        
    }

    public static void main(String[] args) {
        launch(args);
        
    }

}
