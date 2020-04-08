package com.wbs.histoair.model;

public class FlightData {
    private String FlightID;
    private String departureAirport;
    private String arrivalAirport;
    private String departureCountry;
    private String arrivalCountry;
    private String callsign;
    private String estimatedFlightTime;
    private String timeOfArrival;
    private String timeOfDeparture;

    public String getFlightID() {
        return FlightID;
    }
    public void setFlightID(String flightID) {
        FlightID = flightID;
    }
    public String getDepartureAirport() {
        return departureAirport;
    }
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }
    public String getArrivalAirport() {
        return arrivalAirport;
    }
    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }
    public String getDepartureCountry() {
        return departureCountry;
    }
    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }
    public String getArrivalCountry() {
        return arrivalCountry;
    }
    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }
    public String getCallsign() {
        return callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
    public String getEstimatedFlightTime() {
        return estimatedFlightTime;
    }
    public void setEstimatedFlightTime(String estimatedFlightTime) {
        this.estimatedFlightTime = estimatedFlightTime;
    }
    public String getTimeOfArrival() {
        return timeOfArrival;
    }
    public void setTimeOfArrival(String timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }
    public String getTimeOfDeparture() {
        return timeOfDeparture;
    }
    public void setTimeOfDeparture(String timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }
}
