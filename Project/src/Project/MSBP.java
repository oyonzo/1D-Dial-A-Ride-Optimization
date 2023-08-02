package Project;

import java.util.*;

public class MSBP {
    int TLIMIT = Graph.TLIMIT;
    int f;

    public void msbp (List<Request> requestList, List<Driver> driverList) {
        int n = requestList.size();
        int k = driverList.size();
        int i = 1;
        if (f % 2 == 1) {
            // at t1, do nothing
            i = 2;
        }

        while (i < f) {
            while (n!=0 && k!=0) {
                List<Request> r = findMaxSizeRequestSet(requestList);
                Driver closestDriver = findClosestDriver(r.get(0), driverList);
                assignRequestsToDriver(r, closestDriver);
                removeRequestsFromList(r, requestList);
            }

            i += 2;
            unassignAllDrivers(driverList);
        }

    }
    // finds max_size_request_set that can be served in a single time segment
    // @return ArrayList of this set
    private static List<Request> findMaxSizeRequestSet(List<Request> requestList) {

        return new ArrayList<>(); 
    }

    // finds closest driver to specific request
    // @return closestDriver
    private static Driver findClosestDriver(Request request, List<Driver> driverList) {
        Driver closestDriver = null;
        double closestDist = Double.MAX_VALUE;
        for (Driver driver : driverList) {
                double distance = Graph.dist(driver.getPosition(), request.startPos);
                if (distance < closestDist) {
                    closestDriver = driver;
                    closestDist = distance;
                }
        }
        return closestDriver;
    }

    // assigns max_size_request_set to driver
    private static void assignRequestsToDriver(List<Request> requestList, Driver driver) {
        for (Request r : requestList) {
            driver.schedule.add(r);
        }
    }

    // removes max_size_request set from requestList
    private static void removeRequestsFromList(List<Request> requestsToRemove, List<Request> requestList) {
        for (int i = 0; i < requestList.size(); i++) {
            for (int j = 0; j < requestsToRemove.size(); j++) {
                if (requestsToRemove.get(j) == requestList.get(i)) {
                    requestList.remove(requestsToRemove.get(j));
                }
            }
        }
    }
    
    // unassigns drivers so they can be assigned more requests
    private static void unassignAllDrivers(List<Driver> driverList) {
        for (Driver d: driverList) {
    		d.clearSchedule();
    	}
    }
}
