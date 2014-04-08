package book.chapter.sixteen;

/**
 * Given a flight schedule consistenting of <Start City, End City, Departure Time, Arrival Time>,
 * calculate the soonest time someone can arrive in city B, if they start in city A at time X.
 * Assume flights are daily and that connections take 60 minutes of overhead.
 * 
 * My solution is pseudo-code as there are a lot of implementation details related to Time
 * that I didn't deal with. I was going to finish it, but I wanted test data. I looked at the book's
 * solution to see if they included test data, but none was given. In fact, the book "solution"
 * does not provide any code at all but a small paragraph describing how they would solve it.
 * Perhaps I don't understand their proposed solution, but to me their solution wouldn't work.
 * They talk about using Dijkstra's, with a small modification to handle the 60 minute overhead.
 * But what are their edge weights and vertices? They only mention the edges are flights and vertices are cities.
 * Okay, but how do you use flights as edges? The weight is dependent on the arrival time in a given city,
 * since you may have to wait for a flight.
 * 
 * Example: JFK -> LHR leaving at 1200, arriving 1800. That's six hours, but if I start in JFK at 900, it's 9 hours (3 waiting).
 * I don't see how one can take this into account without making a massive graph.
 * 
 * My solution doesn't try to create a full graph before hand. It assumes we parse the data file into a collection of City and Flights.
 * Flight has:
 *  + timeOFDeparture (some time format recording hours/minutes)
 *  + timeOFArrival (some time format recording hours/minutes)
 *  + nextDayArrival (boolean indicating if arrival is next day. Perhaps not needed if we just do arrival < departure)
 *  + destinationCity: City
 *  method calculateArrivalTime(startTime, connecting)
 *  	Takes a given startTime and whether or not we need to add 60 minutes for connection time, and then calculates
 *  	when they would arrive in destinationCity using this flight.
 *  
 *  City has:
 *  + outgoingFlights: ArrayList<Flight> (aka adjacency list)
 *  + visited: boolean - used during DFS
 *  
 *  And basically I do a recursive DFS, incrementing startTime as I recurse.
 *  The base condition (termination) case is endCity == startCity.
 *  
 * @author rob
 *
 */
public class Problem16_10 {
	// This is commented out so that my whole project isn't considered an error in Eclipse.
	
	/*public static DateTime soonest(City start, City end, DateTime startTime, boolean connecting) {
		if (start.equals(end)) return startTime;
		start.setVisited(true);
		DateTime soonest = DateTime.MAX; // e.g. December 31, 9999
		for (Flight flight : start.getOutgoingFlights()) {
			if (flight.getDestinationCity.getVisited()) continue; // prevent cycles
			DateTime localSoonest = soonest(flight.getDestinationCity(), end, flight.calculateArrivalTime(startTime, connecting), connecting);
			// Maintain min 'soonest' value we find.
			if (localSoonest < soonest)
				soonest = localSoonest;
		}
		start.setVisited(false);
		return soonest;
	}*/
}
