package Project;

import java.util.*;

public class MinFValue {
    // Constants
    private static final int THRESHOLD = 1;

    public void minFValue(List<Request> requestList, List<Driver> driverList) {
        int n = requestList.size();
        int k = driverList.size();

        Map<Request, Driver> assignedDrivers = new HashMap<>();
        List<Request> unassignedRequests = new ArrayList<>(requestList);

        // Step 1: Sort the requests in increasing order of function f
        Collections.sort(requestList, Comparator.comparingDouble(request -> request.f_val));

        // Step 3: Assign each driver the (at most) n/k requests with the closest pickup locations
        assignClosestRequests(driverList, requestList, n/k);

        // Step 4: For any unassigned request, find the closest driver and assign
        for (Request r : unassignedRequests) {
            Driver closestDriver = findClosestDriver(driverList, r, n/k + THRESHOLD);
            if (closestDriver != null) {
                assignedDrivers.put(r, closestDriver);
                unassignedRequests.remove(r);
            }
        }

        // Step 5: For any request assigned to more than one driver, assign to the closest driver
        // Not necessary as we are assigning only one driver to each request

        // Step 6: For each request in Request-List
        for (Request e : requestList) {
            Driver d = assignedDrivers.get(e);

            if (d != null) { // If a driver is assigned
                // If pickup time hasn't passed and there's enough time to drive from current location
                if (e.pickTime > currentTime() && enoughTimeToDrive(d, e)) {
                    // Add request to driver's schedule
                    d.schedule.add(e);
                    // Update driver's current location to request's drop-off location
                    d.setPosition(e.finishPos);
                }
            }
        }
    }

    private void assignClosestRequests(List<Driver> drivers, List<Request> requests, int count) {
        for (Driver driver : drivers) {
            // create a priority queue to hold the requests sorted by distance
            PriorityQueue<Request> closestRequests = new PriorityQueue<>(
                Comparator.comparingDouble(r -> Point.dist(driver.getPosition(), r.startPos))
            );

            closestRequests.addAll(requests);

            // assign the closest requests to the driver
            for (int i = 0; i < count && !closestRequests.isEmpty(); i++) {
                Request request = closestRequests.poll();  // get the closest request
                driver.schedule.add(request);  // add it to the driver's schedule
                requests.remove(request);  // remove it from the list of requests
            }
        }
    }



    /*
     * 
     */
    public Driver findClosestDriver(List<Driver> driverList, Request r, double maxRequests) {
        Driver closestDriver = null;
        double closestDistance = Double.MAX_VALUE;

        for (Driver driver : driverList) {
            // Check if the driver has fewer than maxRequests requests assigned
            if (driver.schedule.size() < maxRequests) {
                double distance = Point.dist(driver.getPosition(), r.startPos);
                if (distance < closestDistance) {
                    closestDriver = driver;
                    closestDistance = distance;
                }
            }
        }

        return closestDriver;
    }



    /*
     * calculate if the driver has enough time to get from their current position to the pickup position of a request
     * if the driver has enough time, return true
     * if not, return false
     */
    public boolean enoughTimeToDrive(Driver d, Request r ) {
        double drivingTime = Point.dist(d.getPosition(), r.startPos) / 1; // Calculate driving time
        return (currentTime() + drivingTime <= r.pickTime); // Return true if driver can arrive on time
    }



    private double currentTime() {
        // Actual implementation depends on system specifics
        return 0;
    }



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
