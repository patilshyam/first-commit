package com.ideas.webportal.search;



import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/*
In real world graph libraries with graph as input will be much more efficient
 */


import com.ideas.webportal.model.domain.FlightItinerary;
import com.ideas.webportal.model.domain.FlightSchedule;

public class ItineraryCompiler {

    private final String sourceAirport;
    private final String destinationAirport;
    private final AirportConnectionsInformationBuilder.AirportConnectionsInformation airportAndConnections;

    private final List<String> visitedAirports;
    private final List<FlightItinerary> flightItineraries;

    public ItineraryCompiler(String sourceAirport, String destinationAirport, AirportConnectionsInformationBuilder.AirportConnectionsInformation airportAndConnections) {
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.airportAndConnections = airportAndConnections;

        this.flightItineraries = new ArrayList<FlightItinerary>();
        this.visitedAirports = new ArrayList<String>();
        this.visitedAirports.add(sourceAirport);
    }

    public ItineraryInformation compile() {
        List<FlightSchedule> connectionsFromSource = airportAndConnections.getConnections(sourceAirport);
        for (FlightSchedule flightSchedule : connectionsFromSource) {
            List<FlightSchedule> flights = new LinkedList<FlightSchedule>();

            flights.add(flightSchedule);

            String connectedAirport = flightSchedule.getDestination();

            // Direct flight
            if (connectedAirport.equalsIgnoreCase(destinationAirport)) {
                FlightItinerary flightItinerary = new FlightItinerary();
                flightItinerary.getFlights().addAll(flights);
                flightItineraries.add(flightItinerary);
            } else {
                //Connections only
                findConnections(connectedAirport, flights);
            }
        }
        return new ItineraryInformation(flightItineraries);
    }

    private void findConnections(String sourceAirport, List<FlightSchedule> currentConnections) {

        visitedAirports.add(sourceAirport);

        List<FlightSchedule> connectionsFromAirport = airportAndConnections.getConnections(sourceAirport);
        if (connectionsFromAirport != null && !sourceAirport.equalsIgnoreCase(destinationAirport)) {

            for (FlightSchedule currentFlight : connectionsFromAirport) {
                // As there is a possibility of more connections start all new itinerary with all previous connections
                // and discard old
                // LinkedList is used as its easy to retrieve last connection, which we will need later
                LinkedList<FlightSchedule> newItineraryConnections = new LinkedList<FlightSchedule>(currentConnections);
                newItineraryConnections.add(currentFlight);

                String connectedAirport = currentFlight.getDestination();

                // Airport already visited so don't go there again
                if (!visitedAirports.contains(connectedAirport)) {
                    // Find recursively
                    findConnections(connectedAirport, newItineraryConnections);
                }

                FlightSchedule lastFlightInConnection = newItineraryConnections.getLast();
                boolean isLastFlightEndingAtDestination = lastFlightInConnection.getDestination().equalsIgnoreCase(destinationAirport);

                if (isLastFlightEndingAtDestination) {
                    FlightItinerary newItinerary = new FlightItinerary();
                    newItinerary.getFlights().addAll(newItineraryConnections);
                    flightItineraries.add(newItinerary);
                }
            }
        }
    }

    public static class ItineraryInformation {
        private final List<FlightItinerary> flightItineraries;

        public ItineraryInformation(List<FlightItinerary> flightItineraries) {
            //Sorts based on number of connections
            Collections.sort(flightItineraries);
            this.flightItineraries = flightItineraries;
        }

        public FlightItinerary getShortestRoute() {
            if (flightItineraries.size() > 0)
                return flightItineraries.get(0);
            else {
                return new FlightItinerary();
            }
        }

        @Override
        public String toString() {
            return "ItineraryInformation{" +
                    "flightItineraries=" + flightItineraries +
                    '}';
        }
    }
}
