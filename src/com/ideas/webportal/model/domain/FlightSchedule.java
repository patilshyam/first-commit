package com.ideas.webportal.model.domain;

import java.util.Date;
import java.util.Objects;

public class FlightSchedule {
    private final String airline;
    private final String source;
    private final String destination;
    private final Date date;

    public FlightSchedule(String airline, String source, String destination, Date date) {
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightSchedule that = (FlightSchedule) o;
        return Objects.equals(airline, that.airline) &&
                Objects.equals(source, that.source) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airline, source, destination, date);
    }

    // Using to show some sort of output in logs hence deviating from normal to string
    @Override
    public String toString() {
        return "\n" + "FlightSchedule " + "\n" +
                "-------------" + "\n" +
                "airline: '" + airline + '\'' + "\t" +
                ", source airport: '" + source + '\'' + "\t" +
                ", destination airport: '" + destination + '\'' + "\t" +
                ", departs at: " + date;
    }
}
