package Project;

import java.util.*;

public class earliest {
    private static final int THRESHOLD = 1;

    public void Earliest(List<Request> requestList, List<Driver> driverList) {
        int n = requestList.size();
        int k = driverList.size();

        Map<Request, Driver> assignedDrivers = new HashMap<>();
        List<Request> unassignedRequests = new ArrayList<>(requestList);

        // // Step 1: Sort the requests in increasing order of pickup time
        Collections.sort(requestList, Comparator.comparingDouble(request -> request.pickTime));
       
        // Step 3: Assign each driver the (at most) n/k requests with the closest pickup locations
        assignClosestRequests(driverList, requestList, n/k);

        // Step 4: For any unassigned request, find the closest driver and assign
        for (Request request : unassignedRequests) {
            Driver closestDriver = findClosestDriver(driverList, request, n/k + THRESHOLD);

            if (closestDriver != null) {
                assignedDrivers.put(request, closestDriver);
                unassignedRequests.remove(request);
            }
        }

        // Step 6: For each request in Request-List
        for (Request e : requestList) {
            Driver d = assignedDrivers.get(e);
            // for an assigned driver
            if (d != null) {
                // if pickup time hasn't passed and enough time to drive to pickup location, add request e to schedule
                if (e.pickTime > currentTime() && hasEnoughTime(d, e)) {
                    d.schedule.add(e);
                    // update driver's currLocation to finishPos of completed request
                    d.setPosition(e.finishPos);
                }
            }
        }
    }

    // assigns closest requests to driver
    private void assignClosestRequests(List<Driver> drivers, List<Request> requests, int count) {
        for (Driver driver : drivers) {
            // create a priority queue to hold the requests sorted by distance
            PriorityQueue<Request> closestRequests = new PriorityQueue<>(
                Comparator.comparingDouble(r -> Graph.dist(driver.getPosition(), r.startPos))
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

    // assigns drivers to closest requests
    public Driver findClosestDriver(List<Driver> driverList, Request r, double maxRequests) {
        Driver closestDriver = null;
        double closestDist = Double.MAX_VALUE;
        for (Driver driver : driverList) {
            if (driver.schedule.size() < maxRequests) {
                double distance = Graph.dist(driver.getPosition(), r.startPos);
                if (distance < closestDist) {
                    closestDriver = driver;
                    closestDist = distance;
                }
            }
        }
        return closestDriver;
        }

    // calculate if the driver has enough time to get from their current position to the pickup position of a request
    // @return true if there is enough time, false otherwise
    public boolean hasEnoughTime (Driver d, Request r) {
        double drivingTime = Graph.dist(d.getPosition(), r.startPos) / 1;
        return (currentTime() + drivingTime <= r.pickTime);
    }

    // @return driver of specified request
    /*private static Driver getAssignedDriver(Request r, List<Driver> driverList) {
        for (Driver driver : driverList) {
            if (driver.schedule.contains(r)) {
                return driver;
            }
        }
        return null;
    }*/

    private double currentTime() {
        // Actual implementation depends on system specifics
        return 0;
    }
}


