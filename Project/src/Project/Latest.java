package Project;

import java.util.*;

public class Latest {
    private static final int THRESHOLD = 1;

    public void latest(List<Request> requestList, List<Driver> driverList) {
        int n = requestList.size();
        int k = driverList.size();

        Map<Request, Driver> assignedDrivers = new HashMap<>();
        List<Request> unassignedRequests = new ArrayList<>(requestList);

        // // Step 1: Sort the requests in increasing order of pickup time
        Collections.sort(requestList, Comparator.comparingDouble(request -> request.finishTime));

        // Step 3: Assign each driver the (at most) n/k requests with the closest pickup
        // locations
        assignClosestRequests(driverList, unassignedRequests, n / k, assignedDrivers);

        // Step 4: For any unassigned request, find the closest driver and assign
        Iterator<Request> iterator = unassignedRequests.iterator();
        while (iterator.hasNext()) {
            Request r = iterator.next();
            Driver closestDriver = findClosestDriver(driverList, r, n / k + THRESHOLD);
            if (closestDriver != null) {
                assignedDrivers.put(r, closestDriver);
                iterator.remove(); // Safe removal using iterator
            }
        }

        // Step 6: For each request in Request-List
        for (Request e : requestList) {
            Driver d = assignedDrivers.get(e);
            // for an assigned driver
            if (d != null) {
                // if pickup time hasn't passed and enough time to drive to pickup location, add
                // request e to schedule
                if (e.pickTime > d.getCurrentTime() && hasEnoughTime(d, e)) {
                    d.schedule.add(e);
                    // update driver's currLocation to finishPos of completed request
                    d.setPosition(e.finishPos);
                    d.setCurrentTime(e.finishTime);
                }
            }
        }
    }

    // assigns closest requests to driver
    private void assignClosestRequests(List<Driver> drivers, List<Request> requests, int count,
            Map<Request, Driver> assignedDrivers) {
        for (Driver driver : drivers) {
            // create a priority queue to hold the requests sorted by distance
            PriorityQueue<Request> closestRequests = new PriorityQueue<>(
                    Comparator.comparingDouble(r -> Graph.dist(driver.getPosition(), r.startPos)));

            closestRequests.addAll(requests);

            // assign the closest requests to the driver
            for (int i = 0; i < count && !closestRequests.isEmpty(); i++) {
                Request request = closestRequests.poll(); // get the closest request
                if (assignedDrivers.containsKey(request)) {
                    if (Graph.dist(driver.getPosition(), request.startPos) < Graph
                            .dist(assignedDrivers.get(request).getPosition(), request.startPos)) {
                        assignedDrivers.put(request, driver);
                    }
                } else {
                    assignedDrivers.put(request, driver);
                }
                requests.remove(request); // remove it from the list of requests
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

    // calculate if the driver has enough time to get from their current position to
    // the pickup position of a request
    // @return true if there is enough time, false otherwise
    public boolean hasEnoughTime(Driver d, Request r) {
        double drivingTime = Graph.dist(d.getPosition(), r.startPos) / 1; // Calculate driving time
        return (d.getCurrentTime() + drivingTime <= r.pickTime); // Return true if driver can arrive on time
    }
    
    public int totalRequestsDone(List<Driver> driverList) {
    	int total = 0;
		//print out the schedule for each driver
		for (int i=0; i<driverList.size(); i++) {
			//System.out.println("Driver"+ (i+1)+": " + driverList.get(i).schedule);
			total += driverList.get(i).schedule.size();
		}
		return total;
    }

    public static void main(String[] args) {
        List<Request> requestList = Request.createRequests(75);
        List<Driver> driverList = Driver.generateRandDrivers(15);
        // print out the start position of each driver
        for (Driver d : driverList) {
            System.out.println(Arrays.toString(d.getPosition()));
        }

        Latest program = new Latest();
        program.latest(requestList, driverList);
        int total = 0;
        // print out the schedule for each driver
        for (int i = 0; i < driverList.size(); i++) {
            System.out.println("Driver" + (i + 1) + ": " + driverList.get(i).schedule);
            total += driverList.get(i).schedule.size();
        }

        System.out.println(total);
        // for(Driver d:driverList) {
        // System.out.println(d.schedule);
        // System.out.println();
        // }
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Request r : requestList) {
            System.out.println(r);
        }
    }
}

