package Project;
import java.util.*;
import java.util.Collections;
import java.util.Comparator;

// where do drivers start?
// how big is our metric space?
// scan in #drivers and #requests

public class earliest {
    
    public static List<Request> requestList = new ArrayList<Request>();
    public static List<Request> unassignedRequests = new ArrayList<Request>();
    public static List<Integer> sorted = new ArrayList<>();
    public static int numDrivers;
    public static double pickupTime = Request.pickTime;
    public static int n = requestList.size();
    public static int threshold = 1;
    public static double[] startPos, finishPos;
    double pickTime;
    Request request = new Request(startPos, finishPos, pickTime);

    // Sorts requests by increasing pickup time, creates drivers
    public static void assign(List<Request> requestList) {
        Collections.sort(requestList, Comparator.comparingDouble(r -> Request.pickTime));

        List<Driver> drivers = new ArrayList<>();
        
        for (int i = 0; i < numDrivers; i++) {
            drivers.add(new Driver());
        }

        for (Request request : requestList) {
            if (!assignRequest(request, drivers, n / numDrivers + threshold)) {
                assignClosestDriver(request, drivers);
            }
        }

    }
    
    // determines if driver can add an additional request
    // @return true if it can, false otherwise
    private static boolean assignRequest(Requests request, List<Driver> drivers, int maxAssigned) {
        for (Driver driver : drivers) {
            if (driver.schedule.size() < maxAssigned) {
                driver.schedule.add(request);
                return true;
            }
        }
        return false;
    }

    // assigns drivers to their closest requests
    private static void assignClosestDriver(Request request, List<Driver> drivers) {
        Driver closestDriver = null;
        double closestDist = Double.MAX_VALUE;
        for (Driver driver : drivers) {
            double dist = getDistance()
        }
    }

    // @return current location of a driver (after serving a request)
    // ? put in driver class ?
    public static double[] getDriverLocation (Request request) {
        return request.finishPos;
    }

    // @return pikcup location of request
    private static double[] getRequestLocation (Request request) {
        return request.startPos;
    }
    /*public double getX3(List<Request> rl) {
		double min = Double.MAX_VALUE;
		for(Request r : rl) {
			if(this.equals(r)) continue;
			
			double distToNext = dist(r.startPos,this.finishPos);
			if(distToNext < min) {
				min = distToNext;
			}
		}
		return min;
	}*/

    // Compares the time required to travel from the Driver's currLocation to the pickup location with 
    // the time remaining until the pickup time
    // @return true if there is enough time, false otherwise
    private static boolean hasEnoughTime (Driver driver, Requests request) {
        return (getDistance(getDriverLocation(driver), getRequestLocation(request)) <= pickupTime);
    }

    private static Driver getAssignedDriver(Requests request, List<Driver> drivers) {
        for (Driver driver : drivers) {
            if (driver.schedule.contains(request)) {
                return driver;
            }
        }
        return null;
    }

    //calculating the distance between two points
    private static double getDistance(double[] a, double[] b) {
        double sum = Math.pow((a[1]-b[1]),2)  +  Math.pow((a[2]-b[2]),2);
		return Math.sqrt(sum);
    }    
}


