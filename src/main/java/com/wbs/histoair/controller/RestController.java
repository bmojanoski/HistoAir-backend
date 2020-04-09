package com.wbs.histoair.controller;

import com.wbs.histoair.model.FlightData;
import com.wbs.histoair.repository.FlightsRepository;
import org.apache.jena.rdf.model.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class RestController {

    private FlightsRepository flightsRepository;
    public RestController(FlightsRepository flightsRepository){
        this.flightsRepository = flightsRepository;
    }

    @GetMapping("/flights")
    List<FlightData> result(@RequestParam String country) {
        Model flightRecords = ModelFactory.createDefaultModel();
        flightRecords.read("https://www.dropbox.com/s/oat5rchu4ohkmvz/FlightsRecord.ttl?dl=1", "TTL");

        return this.flightsRepository.getFlightsWithArrivalCountry(flightRecords, country);
    }
}
