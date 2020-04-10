package com.wbs.histoair.controller;

import com.wbs.histoair.model.FlightData;
import com.wbs.histoair.repository.FlightsRepository;
import org.apache.jena.rdf.model.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class RestController {

    private FlightsRepository flightsRepository;

    public RestController(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    @GetMapping("/flightsByCountry")
    List<FlightData> flightsByCountry(@RequestParam String country, @RequestParam String info) {
        Model flightRecords = ModelFactory.createDefaultModel();
        flightRecords.read("https://www.dropbox.com/s/oat5rchu4ohkmvz/FlightsRecord.ttl?dl=1", "TTL");

        if (info.equals("Arrival")) {
            return this.flightsRepository.getFlightsWithArrivalCountry(flightRecords, country);
        } else {
            return this.flightsRepository.getFlightsWithDepartureCountry(flightRecords, country);
        }
    }

    @GetMapping("/flightsByAirport")
    List<FlightData> flightsByAirport(@RequestParam String airport, @RequestParam String info) {
        Model flightRecords = ModelFactory.createDefaultModel();
        flightRecords.read("https://www.dropbox.com/s/oat5rchu4ohkmvz/FlightsRecord.ttl?dl=1", "TTL");

        if (info.equals("Arrival")) {
            return this.flightsRepository.getFlightsWithArrivalAirport(flightRecords, airport);
        } else {
            return this.flightsRepository.getFlightsWithDepartureAirport(flightRecords, airport);
        }
    }

    @GetMapping("/flights-callsign")
    List<FlightData> flightsByCallsign(@RequestParam String callsign) {
        Model flightRecords = ModelFactory.createDefaultModel();
        flightRecords.read("https://www.dropbox.com/s/oat5rchu4ohkmvz/FlightsRecord.ttl?dl=1", "TTL");

        return this.flightsRepository.getFlightsWithCallsign(flightRecords, callsign);
    }

    @GetMapping("/flights-estimatedflighttime")
    List<FlightData> flightsByEstimatedFlightTime(@RequestParam int time) {
        Model flightRecords = ModelFactory.createDefaultModel();
        flightRecords.read("https://www.dropbox.com/s/oat5rchu4ohkmvz/FlightsRecord.ttl?dl=1", "TTL");

        return this.flightsRepository.getFlightsWithEstimatedFlightTime(flightRecords, time);
    }

    @GetMapping("/flights-withtimeoff")
    List<FlightData> flightsByFlightsWithTimeOf(@RequestParam boolean before, @RequestParam  String date, @RequestParam String info) {
        Model flightRecords = ModelFactory.createDefaultModel();
        flightRecords.read("https://www.dropbox.com/s/oat5rchu4ohkmvz/FlightsRecord.ttl?dl=1", "TTL");


        ZonedDateTime localDateTime = ZonedDateTime.parse(date);

        if (info.equals("Arrival")) {
            return this.flightsRepository.getFlightsWithTimeOfArival(flightRecords, localDateTime, before);
        } else {
            return this.flightsRepository.getFlightsWithTimeOfDeparture(flightRecords, localDateTime, before);
        }

    }
}
