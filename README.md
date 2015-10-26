<<<<<<< HEAD
# SmartAirlineBooking

This is a simple distributed system which permits the clients to book travels that involve multiple flights. Each airport maintains its own collection of servers and is able to wisely route requests and replies between any nodes in the network. All the airports are assigned to geographical zones, which are used by the routing policies, resulting in a smaller routing overhead. In order to avoid broadcasting queries to all its neighbours, each airport forwards them only to neighbours placed within the next-hop zones towards the destination.

Please check the .pdf file for more information.

=======
Software/stack use

1. Elipise
2. tomcat8
3. java 1.8
4. Spring 3
5. Hibernat
6. JQuery


Assumption:
1. Need to create dababase and change the db connection details in flightBookingProperties.properties file
2. copy this file in your destop on location c:/flightbooking/
3. Following are tables
	a. Airtine
	b. user
	3. AirelineDetails
4. I have not writtern more as entry and all for this tables
>>>>>>> origin/master
