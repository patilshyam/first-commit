package com.ideas.webportal.search;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ideas.webportal.model.domain.FlightSchedule;
import com.ideas.webportal.repository.FlightRepository;

/*
 This class is used only to demonstrate the simple lab exercise, in real world there will be
 huge number of airports so calculating them on each request will be a disaster.
 In real world we can design repository classes to return connections based on source from database directly or
 We can use a GRAPH database to model the connections.
 */
public class AirportConnectionsInformationBuilder {
    private FlightRepository flightRepository;

    public AirportConnectionsInformationBuilder(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public AirportConnectionsInformation build() {

        List<FlightSchedule> allFlightSchedulesFromAllAirports = flightRepository.allFlights();

        Map<String, List<FlightSchedule>> airportAndConnections = new LinkedHashMap<String, List<FlightSchedule>>();

        for (FlightSchedule schedule : allFlightSchedulesFromAllAirports) {

            List<FlightSchedule> connections = airportAndConnections.get(schedule.getSource());

            if (connections == null) {
                connections = new ArrayList<FlightSchedule>();
                airportAndConnections.put(schedule.getSource(), connections);
            }
            connections.add(schedule);
        }
        return new AirportConnectionsInformation(airportAndConnections);
    }

    public static class AirportConnectionsInformation {

        private final Map<String, List<FlightSchedule>> airportAndConnections;

        public AirportConnectionsInformation(Map<String, List<FlightSchedule>> airportAndConnections) {
            this.airportAndConnections = airportAndConnections;
        }

        public List<FlightSchedule> getConnections(String sourceAirport) {
            List<FlightSchedule> schedules = airportAndConnections.get(sourceAirport);
            if (schedules != null) {
                return schedules;
            }
            return new ArrayList<FlightSchedule>(0);
        }
    }
}
