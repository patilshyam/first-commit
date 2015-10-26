package com.ideas.webportal.search;

import com.ideas.webportal.model.domain.FlightItinerary;



public class FlightFinder {

    private final AirportConnectionsInformationBuilder airportConnectionsInformationBuilder;

    public FlightFinder(AirportConnectionsInformationBuilder airportConnectionsInformationBuilder) {
        this.airportConnectionsInformationBuilder = airportConnectionsInformationBuilder;
    }

    public FlightItinerary find(String source, String destination) {

        AirportConnectionsInformationBuilder.AirportConnectionsInformation airportAndConnections = airportConnectionsInformationBuilder.build();

        ItineraryCompiler.ItineraryInformation flightItineraries = new ItineraryCompiler(source, destination, airportAndConnections).compile();

        System.out.println(flightItineraries);

        return flightItineraries.getShortestRoute();
    }


}
