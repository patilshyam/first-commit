package com.ideas.webportal.repository;



import java.util.List;

import com.ideas.webportal.model.domain.FlightSchedule;

public interface FlightRepository {
    // Implementation is left as the tests is mocking behaviour
    // This could be implemented using any suitable database
    List<FlightSchedule> allFlights();
}
