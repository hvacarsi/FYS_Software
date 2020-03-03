/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.xml.bind.DatatypeConverter;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javax.mail.*;
import javax.mail.internet.*;
import luggagesoftware.controller.Mail;

/**
 *
 * @author User
 */
public class Database {

    //Taal van het programma
    public static int language = 3;//3-Engels, 4-Turks, 5-Spaans, 6-Nederlands

    //Datarows (id) van de baggage en reiziger en medewerker
    public static int selectedDataRow, selectedTravelerRow, selectedUser;

    public static String lostSql = null;

    public static Stage logStage;
    public static Scene myScene;

    public static String previousScreen = null;//Vorige Scherm, handig om aante roepen om naar vorige scherm te gaan
    public static String currentScreen = null;//Huidige Scherm, bij changeScene slaan we dit op id previousScreen

    //Ingelogde User Identical Number
    public static int userId, connectedAirportID;
    public static String userFullName; //Hele naam van de gebruiker
    public static int userLevel; //Rol nummer van de gebuiker word opgeslagen (manager of medewerker)

    //Hierin worden de vertalingen opgeslagen in een array van elke taal (Tijdens het opstarten)
    public static String[] optionsName, optionsEN, optionsTR, optionsSP, optionsNL;

    //Hierin worden alle luchthavens opgeslagen, handig om te zoeken voor een bagage
    public static ObservableList<String> allAirports = FXCollections.observableArrayList();

    //Database Credentials
    private final static String DB_DRIVER_URL = "com.mysql.jdbc.Driver";
    private final static String DB_DRIVER_PREFIX = "jdbc:mysql://";
    private final static String DB_NAME = "corendon_db", USER = "root", PASSWORD = "", HOST = "localhost";

    //Connection waarde
    public Connection connection = null;

    public Database() {
        try {
            // verify that a proper JDBC driver has been installed and linked
            if (!selectDriver(DB_DRIVER_URL)) {
                return;
            }
            // establish a connection to a named database on a specified server	

//            connection = DriverManager.getConnection(DB_DRIVER_PREFIX + "sql7.freemysqlhosting.net:3306" + "/" + "sql7145943", "sql7145943", "L(&HAN@#");
            connection = DriverManager.getConnection(DB_DRIVER_PREFIX + HOST + ":3306" + "/" + DB_NAME, USER, PASSWORD);
        } catch (SQLException eSQL) {
            System.out.println(eSQL);
        }
    }

    //Voeg luchthavens toe in de allAirports variable om dit later toetevoegen in de ComboBox
    public static void addAirports() {
        ResultSet rs = fetch("cn_airports", "1=1", "ORDER BY airportName ASC");
        try {
            while (rs.next()) {
                allAirports.add(rs.getString("airportName"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static boolean selectDriver(String driverName) {
        // Selects proper loading of the named driver for database connections.
        // This is relevant if there are multiple drivers installed that match the JDBC type.
        try {
            Class.forName(driverName);
            // Put all non-prefered drivers to the end, such that driver selection hits the first
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver d = drivers.nextElement();
                if (!d.getClass().getName().equals(driverName)) {
                    // move the driver to the end of the list
                    DriverManager.deregisterDriver(d);
                    DriverManager.registerDriver(d);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    //Extra methode voor getTable met minder variabelen
    public static String getTable(String table, String where, String value) {
        return getTable(table, where, value, "");
    }

    //Return de kolom waarde vanuit de database met de opgegeven query
    public static String getTable(String table, String where, String value, String order) {
        final String finalString = "SELECT * FROM " + table + " WHERE " + where + " " + order;
        String returnedValue;
        Database db = new Database();
        Statement stmt;

        try {
            stmt = db.connection.createStatement();
            ResultSet row = stmt.executeQuery(finalString);
            row.next();
            returnedValue = row.getString(value.toString());
        } catch (SQLException e) {
            returnedValue = "";
        }

        return returnedValue;
    }

    //Controleren van de inloggegevens...
    public static void checkLogin(String username, String password) throws IOException {
        password = getMD5Hash(password);//Encypt het wachtwoord
        //Maak de statement
        Database db = new Database();
        Statement stmt;
        try {
            stmt = db.connection.createStatement(); //Aanroepen van de methode

            //Maak de query mbv de ResultSet
            ResultSet fetch = stmt.executeQuery("SELECT * FROM cn_users WHERE md5(USERNAME) = md5('" + username + "') AND PASSWORD = '" + password + "'");

            if (!fetch.next()) {
                //Ongeldige inloggegevens, controleer of de wachtwoord is gereset !
                ResultSet fetchTMP = stmt.executeQuery("SELECT * FROM cn_users WHERE USERNAME = '" + username + "' AND TEMP_PASSWORD = '" + password + "'");

                //indien ww niet is gereset, dan zijn de inloggegevens ongeldig
                if (!fetchTMP.next()) {
                    //showMessage(option(language, "ERROR_TEXT"), option(language, "INCORRECT_LOGIN"));
                    luggagesoftware.LoginController.errorText = option("INCORRECT_LOGIN");
                } else {
                    //Wachtwoord reset code is correct, reset wachtwoord formulier.
                    userFullName = fetchTMP.getString("USERNAME");
                    userId = fetchTMP.getInt("USER_ID");
                    userLevel = fetchTMP.getInt("LEVEL");
                    db.changeScene("ResetPassword");
                }

            } else {
                //Gegevens correct...

                if (fetch.getInt("USER_STATE") == 0) {
                    luggagesoftware.LoginController.errorText = option("EMAIL_DISABLED_TEXT").replace(", ", "\n");
                } else {
                    userFullName = fetch.getString("NAME"); //Sla username op
                    userId = fetch.getInt("USER_ID"); //Sla username op
                    userLevel = fetch.getInt("LEVEL"); //Sla rol op
                    connectedAirportID = fetch.getInt("AIRPORT_ID"); //Sla luchthaven op waar diegene werkt
                    //Voeg log toe in de database met dat de gebruiker is ingelogd
                    db.addLog(fetch.getInt("USER_ID"), "NORMAL", "LOGIN");

                    //Ga naar volgende scherm
                    db.changeScene("Home");
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    //Update table in de database methode
    public void updateTable(String table, String setValues, String whereClause) {
        updateTable(table, setValues, whereClause, true);
    }

    //updatetabel adhv tablenaam, set waardes, where waard, ga terug ja nee
    public void updateTable(String table, String setValues, String whereClause, boolean goBack) {
        String query = "UPDATE " + table + " SET " + setValues + " WHERE " + whereClause;
        try {
            Statement stmtUpdateTbl = connection.createStatement();
            stmtUpdateTbl.executeUpdate(query);
            if (goBack) {
                changeScene(previousScreen);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    //run query en krijg de id
    public static int insertQueryKey(String sql) {
        int key = 0;
        Database db = new Database();
        try {
            Statement stmtUpdateTbl = db.connection.createStatement();
            key = stmtUpdateTbl.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS); //Execute hier...
            ResultSet rs = stmtUpdateTbl.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return key;
    }

    //Uitvoeren van een sql
    public static void insertQuery(String sql) {
        Database db = new Database();
        try {
            Statement stmtUpdateTbl = db.connection.createStatement();
            stmtUpdateTbl.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS); //Execute hier...
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void changePassword(String pass, String repPass, int loggedChange) {
        String passString = pass;

        pass = getMD5Hash(pass); //Opslaan in een md5 versleuteling
        repPass = getMD5Hash(repPass); //HerhaalWachtwoord Opslaan in een md5 versleuteling

        //Als beide wachtwoorden met elkaar overeenkomen
        if (pass.equals(repPass)) {
            //Kijk of de lengte van het wachtwoord > 5
            if (passString.length() > 5) {
                //ZO ja, sla nieuwe wachtwoord op
                updateTable("cn_users", "PASSWORD = '" + pass + "', TEMP_PASSWORD = ''", "USER_ID = '" + userId + "'");

                //Toon bericht
                showMessage(option("MESSAGE_TEXT"), option("PASSWORD_SAVED_TEXT"));

                try {
                    //Sla log op, en kijk hoe het is veranderd
                    String hoeVeranderd = (loggedChange == 0) ? " with reset code" : "";
                    addLog(userId, "NORMAL", "Changed the password" + hoeVeranderd);
                } catch (SQLException ex) {
                    System.out.println("SQLMessage: " + ex.getMessage());
                }

            } else {
                //Als lengte < 5, voer volgende methode uit.
                showMessage("ERROR", option(language, "INCORRECT_PASSWORD_LENGTH_TEXT"));
            }
        } else {
            //Als wachtwoorden niet overeenkomen
            showMessage("ERROR", option(language, "INCORRECT_PASSWORD_SAME_TEXT"));
        }

    }

    public void changePassword(String pass, String repPass) {
        //Dit word aangeroepen bij reset wachtwoord ipv wachtwoord veranderen na het inloggen
        changePassword(pass, repPass, 0);
    }

    public void addLog(String message) {
        try{
            addLog(userId, "NORMAL", message);
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    //Log opslaan in de database mbv een methode
    public void addLog(int userId, String type, String message) throws SQLException {
        //Met deze methode worden alle acties van de gebruikers opgeslagen in de database
        //Door meteen addLog(door, bijbhorende, parameters, door, te, geven);
        String timeStamp; //Maak String timeStamp aan
        timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); //Sla hierin de datum en tijdstip op
        Statement stmtUpdate = connection.createStatement();
        stmtUpdate.executeUpdate("INSERT INTO cn_log (USER_ID, LOG_TYPE, LOG_MESSAGE, LOG_DATE)VALUES('" + userId + "', '" + type + "',  '" + message + "', '" + timeStamp + "')");
    }

    public void checkRestore(String email) {

        //Maak de statement
        Database db = new Database();
        Statement stmt;
        try {
            stmt = db.connection.createStatement();
            //Kijk of email bestaat query
            ResultSet fetch = stmt.executeQuery("SELECT * FROM cn_users WHERE MAIL = '" + email + "'");

            if (!fetch.next()) {
                //Email adres niet gevonden
                String title = option(language, "ERROR_TEXT");
                String message = option(language, "EMAIL_NOT_FOUND_TEXT");
                showMessage(title, message);

            } else {
                //Controleer Account status (actief, inactief)
                if (fetch.getInt("USER_STATE") == 1) {
                    //Send email...
                    Database dbx = new Database();
                    final String tmpPassword = passwordGenerator(8); //Maak een randomPassword aan met 12 karakters
                    Statement stmtUpdate = dbx.connection.createStatement();
                    //Sla random generated wachtwoord op in de database kolom genaamd temp_password
                    stmtUpdate.executeUpdate("UPDATE cn_users SET TEMP_PASSWORD = '" + getMD5Hash(tmpPassword) + "' WHERE MAIL = '" + fetch.getString("MAIL") + "'");

                    //Log toevoegen
                    addLog(fetch.getInt("USER_ID"), "NORMAL", "Password Reset Requested");

                    //Password Reset Text
                    String PRT = option("PASSWORD_RESET_TEXT");

                    //Heel bericht in huidige taal met de nieuwe credentials
                    String PRMT = option("PASSWORD_RESET_MESSAGE_TEXT");
                    PRMT = PRMT.replace("#PASS#", tmpPassword);
                    PRMT = PRMT.replace("#ACC#", fetch.getString("USERNAME"));

                    //HTML code met de account naam en wachtwoord
                    String message = Database.option("DEAR_TEXT") + " " + fetch.getString("NAME") + ",\n\n" + PRMT.replace("||", "\n") + "\n\nCorendon";

                    Mail mail = new Mail(fetch.getString("MAIL"), PRT, message);
                    mail.sendMail();
                    showMessage("", option("EMAIL_SEND_TEXT"));
                    dbx.changeScene("Login");

                } else {
                    //Account uitgeschakeld
                    String title = option(language, "ERROR_TEXT");
                    String message = option(language, "EMAIL_DISABLED_TEXT");
                    showMessage(title, message);
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    //Zoek vertaling
    public static String option(String what) {
        int lang = language;
        return option(lang, what);
    }

    public static String option(int lang, String what) {
        String finalValue = "";
        //Gevonden is 0 dus false;
        int foundID = 0;

        for (int i = 1; i <= optionsName.length; i++) {
            //Loop de hele array van de DEFAULT WAARDES en kijk of ze overeenkomen
            if (optionsName[i].equals(what)) {
                foundID = i;
                break;
                //Zo ja, stop met de loop en found zetten op de array key
            }
        }

        //Controleer wat de huidige taal is, en finalValue waarde opslaan mbv gevonden array key
        switch (lang) {

            case 3:
                finalValue = optionsEN[foundID];
                break;

            case 4:
                finalValue = optionsTR[foundID];
                break;

            case 5:
                finalValue = optionsSP[foundID];
                break;

            case 6:
                finalValue = optionsNL[foundID];
                break;

        }

        return finalValue;
    }

    //Versleutel hiering de wachtwoord
    private static String getMD5Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash); // return dit met de volgende methode
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    //Return hier de wachtwoord met de Converter
    private static String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }

    //Show message methode, mbv Alert
    public static void showMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(content);
        alert.show();
    }

    //Wijzigen van schermen methode
    public void changeScene(String scenexx) {

        //Als verzoek login scherm is dan ingelogde gebruiker op null zetten
        if (scenexx.equals("Login")) {
            userFullName = null;
            userId = 0;
            userLevel = 0;
        }

        //Probeer van scherm te wisselen met de scenexx variabel
        try {
            //Oude scherm opslaan om bij back button de vorige scherm aan te roepen
            previousScreen = currentScreen;
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("view/" + scenexx + ".fxml"));
            Parent root = (Parent) loginLoader.load();
            myScene = new Scene(root); //Root aangeven in de scene
            logStage.setScene(myScene); //Scene instellen
            logStage.show();//Laat zien
            logStage.centerOnScreen(); //Centreer Scherm
        } catch (IOException ex) {
            //Geef error indien niet mogelijk is om van scherm te veranderen
            System.out.println("ERROR WHILE SCREEN CHANGE: " + ex.getMessage());
        }
    }

    public String passwordGenerator(int length) {
        //Alle characters
        String allChars = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,x,"
                + "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,"
                + "5,6,6,7,8,9,!,@,{,},(,),$,/,#";
        String finalString = "";

        //Array v.d characters
        String[] chars = allChars.split(",");
        int key;
        for (int i = 0; i < length; i++) {
            key = (int) (Math.random() * chars.length);
            //Voeg random character toe...
            finalString += "" + chars[key];
        }

        return finalString;
    }

    
    //Hierin worden de opties met vertalingen opgeslagen, bij opstarten word dit aangeroepen om vertraging te voorkomen
    public static void setOptions() {
        Database db = new Database();
        Statement stmt;
        int totalRows;

        try {
            stmt = db.connection.createStatement();
            //Execute the query
            ResultSet count = stmt.executeQuery("SELECT COUNT(*) FROM cn_options");

            count.next();
            //Krijg totale nummmer van de tabel
            totalRows = count.getInt(1);
            //Voeg 50 extra regels in om java error te voorkomen, auto_icrement in de database kan anders
            //zorgen voor conflicten
            totalRows += 50;

            //Maak de query gesorteerd by the option id Ascending
            ResultSet row = stmt.executeQuery("SELECT * FROM cn_options ORDER BY OPTION_ID ASC");

            //Maak nummer van aantal arrays aan
            optionsName = new String[totalRows];
            optionsEN = new String[totalRows];
            optionsTR = new String[totalRows];
            optionsSP = new String[totalRows];
            optionsNL = new String[totalRows];

            //Sla de vertalingen hierin op
            while (row.next()) {

                optionsName[row.getInt("OPTION_ID")] = row.getString("OPTION_NAME");
                optionsEN[row.getInt("OPTION_ID")] = row.getString("OPTION_VALUE_EN");
                optionsTR[row.getInt("OPTION_ID")] = row.getString("OPTION_VALUE_TR");
                optionsSP[row.getInt("OPTION_ID")] = row.getString("OPTION_VALUE_SP");
                optionsNL[row.getInt("OPTION_ID")] = row.getString("OPTION_VALUE_NL");
            }

            db.connection.close();
        } catch (SQLException ex) {
            System.out.println("NEXT ERROR: " + ex.getMessage());
        }

    }

    //Fetch methode om makkelijker uit te voeren
    public static ResultSet fetch(String table, String where, String order, String selectVars) {

        final String finalString = "SELECT " + selectVars + " FROM " + table + " WHERE " + where + " " + order;
        String returnedValue = "";
        Database db = new Database();
        Statement stmt;
        ResultSet fetch;
        try {
            stmt = db.connection.createStatement();
            fetch = stmt.executeQuery(finalString);
            fetch.next();
        } catch (SQLException e) {
            fetch = null;
        }

        return fetch;

    }

    //Extra methode voor fetch, met SELECT >*< als gevraagde kolommen
    public static ResultSet fetch(String table, String where, String order) {
        return fetch(table, where, order, "*");
    }

    //shortcut volgende methode
    public void showMenu(Button travelersBtn, Button foundLuggageBtn, Button lostLuggageBtn, MenuButton settingsMenuBtn) {
        Button cont = new Button("");
        Button stat = new Button("");
        showMenu(cont, stat, travelersBtn, foundLuggageBtn, lostLuggageBtn, settingsMenuBtn);
    }

    //Verplaats, verstop, laten zien van elementen aan de hand van de rol vd gebruiker
    public void showMenu(Button contributorsBtn, Button statisticsBtn, Button travelersBtn, Button foundLuggageBtn, Button lostLuggageBtn, MenuButton settingsMenuBtn) {
        double btnWidth = 252;

        if (!contributorsBtn.getText().equals("")) {

            switch (userLevel) {
                case 1:
                    contributorsBtn.setVisible(false);
                    statisticsBtn.setVisible(false);
                    travelersBtn.setVisible(true);

                    travelersBtn.setPrefWidth(btnWidth);
                    lostLuggageBtn.setPrefWidth(btnWidth);
                    foundLuggageBtn.setPrefWidth(btnWidth);

                    lostLuggageBtn.setLayoutX(lostLuggageBtn.getLayoutX() - 2);
                    travelersBtn.setLayoutX(travelersBtn.getLayoutX() - 107);
                    foundLuggageBtn.setLayoutX(foundLuggageBtn.getLayoutX() + 46);
                    logStage.setWidth(750);
                    break;

                case 2:
                    contributorsBtn.setVisible(true);
                    statisticsBtn.setVisible(true);
                    travelersBtn.setVisible(false);
                    logStage.setWidth(806);
                    break;

            }

        } else {
            switch (userLevel) {
                case 1:

                    travelersBtn.setPrefWidth(btnWidth);
                    lostLuggageBtn.setPrefWidth(btnWidth);
                    foundLuggageBtn.setPrefWidth(btnWidth);
                    logStage.setWidth(750);
                    lostLuggageBtn.setLayoutX(lostLuggageBtn.getLayoutX() - 2);
                    travelersBtn.setLayoutX(travelersBtn.getLayoutX() - 40);
                    foundLuggageBtn.setLayoutX(foundLuggageBtn.getLayoutX() - 19);
                    break;

                case 2:
                    logStage.setWidth(806);
                    travelersBtn.setVisible(true);//Bij deze is het de statisticsBtn
                    break;
            }

        }

        settingsMenuBtn.setPrefHeight(30);
        settingsMenuBtn.setLayoutX(logStage.getWidth() - 83);
        settingsMenuBtn.setLayoutY(-1);
    }

    //krijg de int terug van de vertaling, methode
    public static int getNewState(String word) {
        int state;

        switch (word) {
            case "Lost":
                state = 0;
                break;
            case "Verloren":
                state = 0;
                break;
            case "Kayip":
                state = 0;
                break;
            case "Perdida":
                state = 0;
                break;

            case "Found":
                state = 2;
                break;

            case "Gevonden":
                state = 2;
                break;
            case "Bulundu":
                state = 2;
                break;

            case "Fundar":
                state = 2;
                break;

            default:
                state = 99;
                break;
        }

        return state;
    }

    public static void removeLuggage() {
        //Verwijder Luggage ouder dan 1 jaar
        String sql = "DELETE FROM cn_luggage WHERE LDATE < DATE(NOW() - INTERVAL 1 YEAR);";
        Database.insertQuery(sql);
    }

    
    //set waardes op 0 en null bij het uitloggen
    public void destroySession() {
        userId = 0;
        userLevel = 0;
        userFullName = null;
        changeScene("Login");
    }

}
