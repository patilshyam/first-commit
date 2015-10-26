package com.ideas.webportal.model.domain;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class FlightBookingProperties implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public static Map<String, String> property = new HashMap<String, String>();
	
	public static String getProperty(String key){
		if(property.isEmpty()){
			loadFlightBookingProperty();
		}
		return property.get(key);
	}
	
	public static void loadFlightBookingProperty(){
		try{
			FileInputStream fis = new FileInputStream("/opt/FlightBookingPortalProperties/FlightBookingPortal.properties");
			Properties file = new Properties();
			file.load(fis);
			Set<String> allKeys = file.stringPropertyNames();
			for(String key : allKeys){
				property.put(key, file.getProperty(key));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
