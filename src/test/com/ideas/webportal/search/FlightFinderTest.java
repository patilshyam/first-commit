package test.com.ideas.webportal.search;


import com.ideas.webportal.model.domain.FlightItinerary;
import com.ideas.webportal.model.domain.FlightSchedule;
import com.ideas.webportal.repository.FlightRepository;
import com.ideas.webportal.search.AirportConnectionsInformationBuilder;
import com.ideas.webportal.search.FlightFinder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FlightFinderTest {

    public static final String INDIGO = "Indigo";
    public static final String AIR_INDIA = "Air India";
    public static final String SAHARA = "sahara";
    public static final String JET_AIRWAYS = "Jet Airways";
    public static final String JET_LIGHT = "Jet Light";
    public static final String SOUTHWEST = "Southwest";

    @Mock
    private FlightRepository flightRepository;

    private FlightFinder flightFinder;

    private Date anyDate = new Date();

    @Before
    public void setUp() throws Exception {
        AirportConnectionsInformationBuilder airportConnectionsInformationBuilder = new AirportConnectionsInformationBuilder(flightRepository);
        flightFinder = new FlightFinder(airportConnectionsInformationBuilder);
    }

    @Test
    public void shouldReturnDirectFlight() {
        //Given
        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(new FlightSchedule(AIR_INDIA, "a", "b", anyDate));

        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(new FlightSchedule(AIR_INDIA, "a", "b", anyDate));
        flightSchedules.add(new FlightSchedule(SOUTHWEST, "b", "a", anyDate));
        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("a", "b");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void dosNotReturnFlightItineraryIfNoFlightsFound() {
        //Given
        FlightItinerary expectedFlight = new FlightItinerary();
        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(new FlightSchedule(SAHARA, "a", "b", anyDate));
        flightSchedules.add(new FlightSchedule(SOUTHWEST, "b", "a", anyDate));
        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("b", "c");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnFlightItineraryWithOneHop() {
        //Given
        FlightSchedule aTob = new FlightSchedule(JET_AIRWAYS, "a", "b", anyDate);
        FlightSchedule bToc = new FlightSchedule(JET_LIGHT, "b", "c", anyDate);

        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(aTob);
        expectedFlight.add(bToc);

        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(aTob);
        flightSchedules.add(new FlightSchedule(AIR_INDIA, "b", "a", anyDate));
        flightSchedules.add(bToc);
        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("a", "c");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnFlightItineraryWithMultipleHops() {
        //Given
        FlightSchedule aTob = new FlightSchedule(SAHARA, "a", "b", anyDate);
        FlightSchedule bToc = new FlightSchedule(JET_AIRWAYS, "b", "c", anyDate);
        FlightSchedule cTod = new FlightSchedule(AIR_INDIA, "c", "d", anyDate);
        FlightSchedule dToe = new FlightSchedule(INDIGO, "d", "e", anyDate);
        FlightSchedule eTof = new FlightSchedule(SAHARA, "e", "f", anyDate);

        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(aTob);
        expectedFlight.add(bToc);
        expectedFlight.add(cTod);
        expectedFlight.add(dToe);
        expectedFlight.add(eTof);

        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(aTob);
        flightSchedules.add(new FlightSchedule(AIR_INDIA, "b", "a", anyDate));
        flightSchedules.add(new FlightSchedule(SAHARA, "c", "a", anyDate));
        flightSchedules.add(new FlightSchedule(SOUTHWEST, "e", "c", anyDate));
        flightSchedules.add(new FlightSchedule(JET_AIRWAYS, "e", "b", anyDate));
        flightSchedules.add(new FlightSchedule(JET_LIGHT, "f", "g", anyDate));
        flightSchedules.add(bToc);
        flightSchedules.add(cTod);
        flightSchedules.add(dToe);
        flightSchedules.add(eTof);
        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("a", "f");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnFlightItineraryWithLeastHops() {
        //Given
        FlightSchedule aTob = new FlightSchedule(INDIGO, "a", "b", anyDate);
        FlightSchedule bToc = new FlightSchedule(AIR_INDIA, "b", "c", anyDate);
        FlightSchedule cTod = new FlightSchedule(SAHARA, "c", "d", anyDate);
        FlightSchedule dToe = new FlightSchedule(JET_AIRWAYS, "d", "e", anyDate);
        FlightSchedule eTof = new FlightSchedule(JET_LIGHT, "e", "f", anyDate);
        FlightSchedule cToe = new FlightSchedule(SOUTHWEST, "c", "e", anyDate);

        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(aTob);
        expectedFlight.add(bToc);
        expectedFlight.add(cToe);
        expectedFlight.add(eTof);

        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(aTob);
        flightSchedules.add(new FlightSchedule(INDIGO, "b", "a", anyDate));
        flightSchedules.add(new FlightSchedule(AIR_INDIA, "c", "a", anyDate));
        flightSchedules.add(cToe);
        flightSchedules.add(new FlightSchedule(JET_AIRWAYS, "e", "b", anyDate));
        flightSchedules.add(new FlightSchedule(SAHARA, "f", "g", anyDate));
        flightSchedules.add(bToc);
        flightSchedules.add(cTod);
        flightSchedules.add(dToe);
        flightSchedules.add(eTof);
        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("a", "f");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnShortestFlightItineraryForGivenExampleForFToD() {
        //Given

        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(new FlightSchedule(JET_LIGHT, "f", "a", anyDate));
        expectedFlight.add(new FlightSchedule(JET_AIRWAYS, "a", "b", anyDate));
        expectedFlight.add(new FlightSchedule(JET_LIGHT, "b", "c", anyDate));
        expectedFlight.add(new FlightSchedule(AIR_INDIA, "c", "d", anyDate));

        ArrayList<FlightSchedule> flightSchedules = setupDataGivenInExample();

        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("f", "d");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnShortestFlightItineraryForGivenExampleForFToE() {
        //Given

        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(new FlightSchedule(JET_LIGHT, "f", "a", anyDate));
        expectedFlight.add(new FlightSchedule(JET_AIRWAYS, "a", "b", anyDate));
        expectedFlight.add(new FlightSchedule(JET_LIGHT, "b", "e", anyDate));

        ArrayList<FlightSchedule> flightSchedules = setupDataGivenInExample();

        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("f", "e");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnShortestFlightItineraryForGivenExampleForHToF() {
        //Given

        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(new FlightSchedule(JET_AIRWAYS, "h", "b", anyDate));
        expectedFlight.add(new FlightSchedule(AIR_INDIA, "b", "a", anyDate));
        expectedFlight.add(new FlightSchedule(AIR_INDIA, "a", "f", anyDate));

        ArrayList<FlightSchedule> flightSchedules = setupDataGivenInExample();

        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("h", "f");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnShortestFlightItineraryForGivenExampleForHToE() {
        //Given

        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(new FlightSchedule(JET_AIRWAYS, "h", "b", anyDate));
        expectedFlight.add(new FlightSchedule(JET_LIGHT, "b", "e", anyDate));

        ArrayList<FlightSchedule> flightSchedules = setupDataGivenInExample();

        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("h", "e");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnsDirectFlightOnFirstNode() {
        //Given
        FlightSchedule bToc = new FlightSchedule(INDIGO, "b", "c", anyDate);
        FlightItinerary expectedFlight = new FlightItinerary();
        expectedFlight.add(bToc);

        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(new FlightSchedule(INDIGO, "h", "b", anyDate));
        flightSchedules.add(new FlightSchedule(INDIGO, "b", "a", anyDate));
        flightSchedules.add(bToc);

        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("b", "c");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    @Test
    public void returnsNoFlightsIfAirportNotFound() {
        //Given
        FlightItinerary expectedFlight = new FlightItinerary();

        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(new FlightSchedule(INDIGO, "h", "b", anyDate));
        flightSchedules.add(new FlightSchedule(INDIGO, "b", "a", anyDate));

        given(flightRepository.allFlights()).willReturn(flightSchedules);

        //When
        FlightItinerary actualFlight = flightFinder.find("z", "h");

        //Then
        assertEquals("Expected flight itinerary not returned", expectedFlight, actualFlight);
    }

    private ArrayList<FlightSchedule> setupDataGivenInExample() {
        FlightSchedule aTob = new FlightSchedule(JET_AIRWAYS, "a", "b", anyDate);
        FlightSchedule bToc = new FlightSchedule(JET_LIGHT, "b", "c", anyDate);
        FlightSchedule cTod = new FlightSchedule(AIR_INDIA, "c", "d", anyDate);
        FlightSchedule dToe = new FlightSchedule(SAHARA, "d", "e", anyDate);

        FlightSchedule cToe = new FlightSchedule(JET_AIRWAYS, "c", "e", anyDate);

        FlightSchedule fToa = new FlightSchedule(JET_LIGHT, "f", "a", anyDate);

        ArrayList<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
        flightSchedules.add(aTob);
        flightSchedules.add(new FlightSchedule(AIR_INDIA, "b", "a", anyDate));
        flightSchedules.add(bToc);
        flightSchedules.add(new FlightSchedule(AIR_INDIA, "c", "b", anyDate));
        flightSchedules.add(cTod);
        flightSchedules.add(new FlightSchedule(JET_AIRWAYS, "d", "c", anyDate));
        flightSchedules.add(dToe);

        flightSchedules.add(new FlightSchedule(AIR_INDIA, "e", "d", anyDate));
        flightSchedules.add(cToe);
        flightSchedules.add(new FlightSchedule(JET_LIGHT, "c", "e", anyDate));
        flightSchedules.add(new FlightSchedule(INDIGO, "c", "e", anyDate));
        flightSchedules.add(new FlightSchedule(AIR_INDIA, "c", "e", anyDate));
        flightSchedules.add(new FlightSchedule(SOUTHWEST, "c", "e", anyDate));

        flightSchedules.add(new FlightSchedule(AIR_INDIA, "e", "c", anyDate));
        flightSchedules.add(new FlightSchedule(JET_AIRWAYS, "e", "c", anyDate));
        flightSchedules.add(new FlightSchedule(JET_LIGHT, "e", "c", anyDate));
        flightSchedules.add(new FlightSchedule(INDIGO, "e", "c", anyDate));

        flightSchedules.add(new FlightSchedule(SAHARA, "e", "b", anyDate));
        flightSchedules.add(new FlightSchedule(JET_LIGHT, "b", "e", anyDate));

        flightSchedules.add(new FlightSchedule(AIR_INDIA, "a", "f", anyDate));
        flightSchedules.add(fToa);
        flightSchedules.add(new FlightSchedule(JET_AIRWAYS, "h", "b", anyDate));
        flightSchedules.add(new FlightSchedule(INDIGO, "b", "h", anyDate));
        return flightSchedules;
    }

}
