package com.wbs.histoair.repository;

import com.wbs.histoair.model.FlightData;
import org.apache.jena.rdf.model.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public class FlightsRepository {

    public FlightData createFlightDataObject(Resource subject) {
        FlightData fl = new FlightData();
        fl.setFlightID(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasFlightID")).getObject().toString());
        fl.setCallsign(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasCallsign")).getObject().toString());
        fl.setDepartureAirport(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasDepartureAirport")).getObject().toString());
        fl.setArrivalAirport(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasArrivalAirport")).getObject().toString());
        fl.setTimeOfDeparture(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "TimeOfDeparture")).getObject().toString());
        fl.setTimeOfArrival(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "TimeOfArrival")).getObject().toString());
        fl.setEstimatedFlightTime(LocalTime.MIN.plusSeconds(Long.parseLong(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "EstimatedFlightTime")).getObject().toString())).toString());

        if(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasArrivalCountry")).getObject().toString().isEmpty()) {
            fl.setArrivalCountry("");
        } else {
            fl.setArrivalCountry(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasArrivalCountry")).getObject().toString().split("/")[4]);
        }

        if(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasDepartureCountry")).getObject().toString().isEmpty()) {
            fl.setDepartureCountry("");
        } else {
            fl.setDepartureCountry(subject.getProperty(ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasDepartureCountry")).getObject().toString().split("/")[4]);
        }

        return fl;
    }

    public List<FlightData> getFlightsWithEstimatedFlightTime(Model model, int timeInSeconds){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "EstimatedFlightTime"), (RDFNode) null);


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();

            if(Integer.parseInt(value.toString()) < timeInSeconds) {

                flightsInfo.add(createFlightDataObject(subject));
            }
            if(flightsInfo.size() == 4) break;
        }

        return flightsInfo;
    }

    public List<FlightData> getFlightsWithTimeOfArival(Model model, ZonedDateTime arrivalTime, boolean before){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "TimeOfArival"), (RDFNode) null);


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();

            ZonedDateTime localDateTime = ZonedDateTime.parse(value.toString());
            if(before && localDateTime.isBefore(arrivalTime)) {

                flightsInfo.add(createFlightDataObject(subject));
            } else if(!before && localDateTime.isAfter(arrivalTime)) {

                flightsInfo.add(createFlightDataObject(subject));

            }
            if(flightsInfo.size() == 10) break;
        }

        return flightsInfo;
    }

    public List<FlightData> getFlightsWithTimeOfDeparture(Model model, ZonedDateTime departureTime, boolean before){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "TimeOfDeparture"), (RDFNode) null);


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();
            ZonedDateTime localDateTime = ZonedDateTime.parse(value.toString());
            if(before && localDateTime.isBefore(departureTime)) {

                flightsInfo.add(createFlightDataObject(subject));
            } else if(!before && localDateTime.isAfter(departureTime)) {

                flightsInfo.add(createFlightDataObject(subject));
            }
            if(flightsInfo.size() == 10) break;

        }

        return flightsInfo;
    }

    public List<FlightData> getFlightsWithCallsign(Model model, String callsign){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasCallsign"), callsign);


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();

            flightsInfo.add(createFlightDataObject(subject));
            if(flightsInfo.size() == 10) break;
        }

        return flightsInfo;
    }

    public List<FlightData> getFlightsWithArrivalAirport(Model model, String airportCode){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasArrivalAirport"), airportCode);


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();

            flightsInfo.add(createFlightDataObject(subject));
            if(flightsInfo.size() == 10) break;
        }

        return flightsInfo;
    }

    public List<FlightData> getFlightsWithDepartureAirport(Model model, String airportCode){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasDepartureAirport"), airportCode);


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();

            flightsInfo.add(createFlightDataObject(subject));
            if(flightsInfo.size() == 10) break;
        }

        return flightsInfo;
    }

    public List<FlightData> getFlightsWithArrivalCountry(Model model, String country){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasArrivalCountry"), ResourceFactory.createResource("http://dbpedia.org/resource/" + country));


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();

            if(!value.toString().isEmpty()) {
                Model countryModel = ModelFactory.createDefaultModel();
                countryModel.read(value.toString());

                flightsInfo.add(createFlightDataObject(subject));
            }
            if(flightsInfo.size() == 10) break;
        }


        return flightsInfo;
    }

    public List<FlightData> getFlightsWithDepartureCountry(Model model, String country){

        List<FlightData> flightsInfo = new ArrayList<FlightData>();

        StmtIterator iter = model.listStatements(null, ResourceFactory.createProperty("https://www.dropbox.com/s/i18cxhwbdrul2g5/Flights.ttl?dl=1#", "hasDepartureCountry"), ResourceFactory.createResource("http://dbpedia.org/resource/" + country));


        while(iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode value = stmt.getObject();

            if(!value.toString().isEmpty()) {
                Model countryModel = ModelFactory.createDefaultModel();
                countryModel.read(value.toString());

                flightsInfo.add(createFlightDataObject(subject));
            }
            if(flightsInfo.size() == 10) break;
        }

        return flightsInfo;
    }


}
