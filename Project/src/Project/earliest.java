package Project;
import java.util.*;
import java.util.Collections;
import java.util.Comparator;

public class earliest {
    public static List<Request> requests = new ArrayList<Request>();
    //public static LinkedList<Request> linkedRequests = new LinkedList<Request>();
    //public static List<Request> unassignedRequests = new ArrayList<Request>();
    //public static List<Integer> sorted = new ArrayList<>();
    public static int numDrivers;
    public static int n = requests.size();
    public static int threshold = 1;
    public static double[] startPos, finishPos;
    public static double pickTime;
    public static Iterator<Request> nextRequest = requests.iterator();

    // Adds requests to requestList, sorts requests by increasing pickup time, creates drivers
    public static void assign(List<Request> requests) {
        requests = Request.createRequests();
        Request request = new Request(startPos, finishPos, pickTime);
        Collections.sort(requests, Comparator.comparingDouble(r -> r.pickTime));
        List<Driver> drivers = new ArrayList<>();
        double[] position = Driver.generateRandomPosition();
        
        // creates numDrivers drivers with random starting position
        for (int i = 0; i < numDrivers; i++) {
            drivers.add(new Driver(position));
        }

        for (int i = 0; i < n; i++) {
            if (!assignRequest(request, drivers, n / numDrivers + threshold)) {
                assignClosestDriver(request, drivers);
            }
        }
    }
    
    // determines if driver can add an additional request
    // @return true if it can, false otherwise
    private static boolean assignRequest(Request request, List<Driver> drivers, int maxAssigned) {
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
            double distance = request.getX3(requests);
            if (distance < closestDist) {
                closestDriver = driver;
                closestDist = distance;
            }
        }
        if (closestDriver != null) {
            closestDriver.schedule.add(request);
        }
    }

    // Compares the time required to travel from the Driver's currLocation to the pickup location with 
    // the time remaining until the pickup time
    // @return true if there is enough time, false otherwise
    private static boolean hasEnoughTime (Driver driver, Request request) {
        return (request.dist(driver.getPosition(), request.finishPos) <= request.getX2());
    }

    // @return driver of specified request
    private static Driver getAssignedDriver(Request request, List<Driver> drivers) {
        for (Driver driver : drivers) {
            if (driver.schedule.contains(request)) {
                return driver;
            }
        }
        return null;
    } 
}


