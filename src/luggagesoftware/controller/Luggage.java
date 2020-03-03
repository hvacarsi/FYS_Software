/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luggagesoftware;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author User
 */
public class Luggage {

    public StringProperty labelNumber, flightNumber, state, color, type, luggageId, destination, departure, date;

    public Luggage(String labelnr, String flightnr, String state, String color, String type, String luggageId, String destination, String departure, String date){
        this.flightNumber = new SimpleStringProperty(flightnr);
        this.state = new SimpleStringProperty(state);
        this.type = new SimpleStringProperty(type);
        this.labelNumber = new SimpleStringProperty(labelnr);
        this.color = new SimpleStringProperty(color);
        this.luggageId = new SimpleStringProperty(luggageId);
        
        this.destination = new SimpleStringProperty(destination);
        this.departure = new SimpleStringProperty(departure);
        this.date = new SimpleStringProperty(date);
        
    }


    public String getLabelNumber() {
        return labelNumber.get();
    }

    public void setLabelNumber(String labelNumber) {
        this.labelNumber.set(labelNumber);
    }

    public void setDestination(String destinationName) {
        this.destination.set(destinationName);
    }

    public void setDeparture(String departureName) {
        this.departure.set(departureName);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDestination() {
        return destination.get();
    }

    public String getDeparture() {
        return departure.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getFlightNumber() {
        return flightNumber.get();
    }
    
    public String getLuggageId() {
        return this.luggageId.get();
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber.set(flightNumber);
    }
    
    public String getString(String what){
        String value = "";
        switch(what){
            case "flightNumber":
                value = getFlightNumber();
            break;
            case "date":
                value = getDate();
            break;
            case "destination":
                value = getDestination();
            break;
            case "departure":
                value = getDeparture();
            break;
            case "labelNumber":
                value = getLabelNumber();
            break;
            case "color":
                value = getColor();
            break;
            
            case "type":
                value = getType();
            break;
            
            case "state":
                value = getState();
            break;
            
            case "luggageId":
                value = getLuggageId();
            break;
            
        }
        return value;
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }
   
    
 
}
