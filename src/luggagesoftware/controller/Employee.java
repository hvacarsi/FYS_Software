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
public class Employee {
    public StringProperty id, name, username, mail, level, airport_id, state;
    
    
    public Employee(String id, String name, String username, String mail, String level, String airport_id, String state){
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.mail = new SimpleStringProperty(mail);
        this.level = new SimpleStringProperty(level);
        this.airport_id = new SimpleStringProperty(airport_id);
        this.state = new SimpleStringProperty(state);
    }

    public String getName() {
        return name.get();
    }

    
    public String getId() {
        return id.get();
    }

    
    public String getUsername() {
        return username.get();
    }

    public String getMail() {
        return mail.get();
    }

    public String getLevel() {
        return level.get();
    }

    public String getAirport_id() {
        return airport_id.get();
    }

    public String getState() {
        return state.get();
    }
    
    public String getString(String what){
        String value = "";
        switch(what){
            case "name":
                value = getName();
            break;
            case "id":
                value = getId();
            break;
            case "username":
                value = getUsername();
            break;
            case "mail":
                value = getMail();
            break;
            case "level":
                value = getLevel();
            break;
            case "airportId":
                value = getAirport_id();
            break;
            
            case "state":
                value = getState();
            break;
            
        }
        return value;
    }
    
    
    
}
