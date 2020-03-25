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
public class Match {

    StringProperty lblnr, state, travelerId, matchId, travelerName, date, color, type, airport;

    public Match(String lblnr, String state, String travelerId, String matchId, String travelerName, String date, String color, String type, String airport) {
        this.lblnr = new SimpleStringProperty(lblnr);
        this.state = new SimpleStringProperty(state);
        this.travelerId = new SimpleStringProperty(travelerId);
        this.matchId = new SimpleStringProperty(matchId);
        this.travelerName = new SimpleStringProperty(travelerName);
        this.date = new SimpleStringProperty(date);
        this.color = new SimpleStringProperty(color);
        this.type = new SimpleStringProperty(type);
        this.airport = new SimpleStringProperty(airport);

    }

    public String getType() {
        return type.get();
    }
    
    public String getAirport() {
        return airport.get();
    }
    

    public String getColor() {
        return color.get();
    }

    public String getLblnr() {
        return lblnr.get();
    }

    public String getState() {
        return state.get();
    }

    public String getTravelerId() {
        return travelerId.get();
    }

    public String getMatchId() {
        return matchId.get();
    }

    public String getTravelerName() {
        return travelerName.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getString(String what) {
        String value = "";
        switch (what) {
            case "color":
                value = getColor();
                break;
            case "type":
                value = getType();
                break;
            case "lblnr":
                value = getLblnr();
                break;
            case "state":
                value = getState();
                break;
            case "travelerId":
                value = getTravelerId();
                break;
            case "matchId":
                value = getMatchId();
                break;
            case "aname":
                value = getMatchId();
                break;
            case "date":
                value = getDate();
                break;
            case "travelerName":
                value = getTravelerName();
                break;

        }
        return value;
    }

}
