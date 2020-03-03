/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author User
 */
public class Traveler {

    public StringProperty naam, achternaam, geb_datum, postcode, stad, land, adres, mobiel, mail, id;

    public Traveler(String naam, String achternaam, String geb_datum, String postcode, String stad, String land, String adres, String mobiel, String mail, String id) {
        this.naam = new SimpleStringProperty(naam);
        this.achternaam = new SimpleStringProperty(achternaam);
        this.geb_datum = new SimpleStringProperty(geb_datum);
        this.postcode = new SimpleStringProperty(postcode);
        this.stad = new SimpleStringProperty(stad);
        this.land = new SimpleStringProperty(land);
        this.adres = new SimpleStringProperty(adres);
        this.mobiel = new SimpleStringProperty(mobiel);
        this.mail = new SimpleStringProperty(mail);
        this.id = new SimpleStringProperty(id);
    }

    public String getNaam() {
        return naam.get();
    }

    public String getAchternaam() {
        return achternaam.get();
    }

    public String getGeb_datum() {
        return geb_datum.get();
    }

    public String getPostcode() {
        return postcode.get();
    }

    public String getStad() {
        return stad.get();
    }

    public String getLand() {
        return land.get();
    }

    public String getAdres() {
        return adres.get();
    }

    public String getMobiel() {
        return mobiel.get();
    }

    public String getMail() {
        return mail.get();
    }

    public String getId() {
        return id.get();
    }
    
    public String getString(String what){
        String value = "";
        switch(what){
            case "naam":
                value = getNaam();
            break;
            case "achternaam":
                value = getAchternaam();
            break;
            case "geboorteDatum":
                value = getGeb_datum();
            break;
            case "postcode":
                value = getPostcode();
            break;
            case "stad":
                value = getStad();
            break;
            case "land":
                value = getLand();
            break;
            
            case "adres":
                value = getAdres();
            break;
            
            case "mobiel":
                value = getMobiel();
            break;
            
            case "mail":
                value = getMail();
            break;
            
            case "id":
                value = getId();
            break;
            
        }
        return value;
    }

}
