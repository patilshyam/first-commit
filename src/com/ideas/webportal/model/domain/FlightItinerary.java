package com.ideas.webportal.model.domain;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class FlightItinerary implements Comparable<FlightItinerary> {
    private List<FlightSchedule> flights = new ArrayList<FlightSchedule>();

    public void add(FlightSchedule flightSchedule) {
        flights.add(flightSchedule);
    }

    public List<FlightSchedule> getFlights() {
        return flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightItinerary that = (FlightItinerary) o;
        return Objects.equals(flights, that.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flights);
    }

    @Override
    public String toString() {
        return "FlightItinerary{" +
                "flights=" + flights +
                '}';
    }

    public int compareTo(FlightItinerary other) {
        return getFlights().size() - other.getFlights().size();
    }
}
