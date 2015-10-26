package com.ideas.webportal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ideas.webportal.model.domain.FlightItinerary;
import com.ideas.webportal.search.AirportConnectionsInformationBuilder;
import com.ideas.webportal.search.FlightFinder;
import com.ideas.webportal.search.*;

@Controller  
@SessionAttributes("airline")
@RequestMapping("/portal")
public class AirlineManagementController {
	private static final Logger logger = LoggerFactory.getLogger(AirlineManagementController.class);

	   /**
	    * 
	    * @param model
	    * @param request
	    * @param response
	    * @return
	    */
	    @RequestMapping(value="/airlinemanagement", method = RequestMethod.GET)  
	    public ModelAndView airlineManagement(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) {  
	    	try{
	    		logger.info("airlinemanagement method start...");
	    		FlightFinder FlightFinder=new FlightFinder(com.ideas.webportal.search.AirportConnectionsInformationBuilder airportConnectionsInformationBuilder);
	   	    		FlightItinerary flightItinerary= FlightFinder.find(request.getAttribute("source").toString(), request.getAttribute("destination").toString());
	    		 return new ModelAndView("airline_management", "searchResult",flightItinerary);
	    	}
	    	catch(Exception ex){
	    		ex.printStackTrace();
	    		return new ModelAndView("exception");  
	    	}
	    }       
	}  