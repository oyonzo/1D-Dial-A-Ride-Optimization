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
        assignClosestRequests(driverList, unassignedRequests, n/k, assignedDrivers);

        // Step 4: For any unassigned request, find the closest driver and assign
        Iterator<Request> iterator = unassignedRequests.iterator();
        while (iterator.hasNext()) {
            Request r = iterator.next();
            Driver closestDriver = findClosestDriver(driverList, r, n/k + THRESHOLD);
            if (closestDriver != null) {
                assignedDrivers.put(r, closestDriver);
                iterator.remove();  // Safe removal using iterator
            }
        }

        // Step 5: For any request assigned to more than one driver, assign to the closest driver
        // Not necessary as we are assigning only one driver to each request

        // Step 6: For each request in Request-List
        for (Request e : requestList) {
            Driver d = assignedDrivers.get(e);

            if (d != null) { // If a driver is assigned
                // If pickup time hasn't passed and there's enough time to drive from current location
                if (e.pickTime > d.getCurrentTime() && enoughTimeToDrive(d, e)) {
                    // Add request to driver's schedule
                    d.schedule.add(e);
                    // Update driver's current location to request's drop-off location
                    d.setPosition(e.finishPos);
                    d.setCurrentTime(e.finishTime);
                }
            }
        }
    }

    
    private void assignClosestRequests(List<Driver> drivers, List<Request> requests, int count, Map<Request, Driver> assignedDrivers) {
        for (Driver driver : drivers) {
            // create a priority queue to hold the requests sorted by distance
            PriorityQueue<Request> closestRequests = new PriorityQueue<>(
                Comparator.comparingDouble(r -> Graph.dist(driver.getPosition(), r.startPos))
            );

            closestRequests.addAll(requests);

            // assign the closest requests to the driver
            for (int i = 0; i < count && !closestRequests.isEmpty(); i++) {
                Request request = closestRequests.poll();  // get the closest request
                if(assignedDrivers.containsKey(request)) {
                	if (Graph.dist(driver.getPosition(),request.startPos) < Graph.dist(assignedDrivers.get(request).getPosition(), request.startPos)) {
                		assignedDrivers.put(request, driver);
                	}
                } else {
                	assignedDrivers.put(request, driver);
                }
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
                double distance = Graph.dist(driver.getPosition(), r.startPos);
                if (distance < closestDistance) {
                    closestDriver = driver;
                    closestDistance = distance;
                }
            }
        }

        return closestDriver;
    }
    
    public int totalRequestsDone(List<Driver> driverList) {
    	int total = 0;
		//print out the schedule for each driver
		for (int i=0; i<driverList.size(); i++) {
			System.out.println("Driver"+ (i+1)+": " + driverList.get(i).schedule);
			total += driverList.get(i).schedule.size();
		}
		return total;
    }



    /*
     * calculate if the driver has enough time to get from their current position to the pickup position of a request
     * if the driver has enough time, return true
     * if not, return false
     */
    public boolean enoughTimeToDrive(Driver d, Request r ) {
        double drivingTime = Graph.dist(d.getPosition(), r.startPos) / 1; // Calculate driving time
        return (d.getCurrentTime() + drivingTime <= r.pickTime); // Return true if driver can arrive on time
    }



//    private double currentTime() {
//        // Actual implementation depends on system specifics
//        return 0;
//    }



	public static void main(String[] args) {
//		double[] start1 = {1.0, 10.0};
//		double[] end1 = {2.0, 10.0};
//		double time1 = 12.0;
//		
//		double[] start2 = {3, 8};
//		double[] end2 = {3, 7};
//		double time2 = 16.0;
//		
//		double[] start3 = {5, 9};
//		double[] end3 = {6, 9};
//		double time3 = 20.0;
//		
//		double[] start4 = {50, 51};
//		double[] end4 = {52, 51};
//		double time4 = 2.0;
//		
//		double[] start5 = {52, 50};
//		double[] end5 = {55, 50};
//		double time5 = 5.0;
//		
//		double[] start6 = {56, 52};
//		double[] end6 = {57, 52};
//		double time6 = 15.0;
//		
//		double[] start7 = {91, 10};
//		double[] end7 = {95, 10};
//		double time7 = 3.0;
//		
//		double[] start8 = {95, 13};
//		double[] end8 = {95, 15};
//		double time8 = 12.0;
//		
//		double[] start9 = {91, 15};
//		double[] end9 = {88, 15};
//		double time9 = 20.0;
//
//		Request r1 = new Request(start1, end1, time1);
//		Request r2 = new Request(start2, end2, time2);
//		Request r3 = new Request(start3, end3, time3);
//		Request r4 = new Request(start4, end4, time4);
//		Request r5 = new Request(start5, end5, time5);
//		Request r6 = new Request(start6, end6, time6);
//		Request r7 = new Request(start7, end7, time7);
//		Request r8 = new Request(start8, end8, time8);
//		Request r9 = new Request(start9, end9, time9);
//		
//		List<Request> requestList = new ArrayList<Request>(Arrays.asList(r1,r2,r3,r4,r5,r6,r7,r8,r9));
//		
//		for (Request r : requestList) {
//			r.setf(requestList);
//		}
//		
//		double[] position1 = {0,0};
//		double[] position2 = {50,50};
//		double[] position3 = {90,10};
//		
//		Driver d1 = new Driver(position1);
//		Driver d2 = new Driver(position2);
//		Driver d3 = new Driver(position3);
//		
//		List<Driver> driverList = new ArrayList<Driver>(Arrays.asList(d1,d2,d3));
		List<Request> requestList = Request.createRequests(75);
		
		for (Request r : requestList) {
			r.setf(requestList);
		}
		List<Driver> driverList = Driver.generateRandDrivers(15);
		//print out the start position of each driver
		for(Driver d:driverList) {
			System.out.println(Arrays.toString(d.getPosition()));
		}
		
		MinFValue program = new MinFValue();
		program.minFValue(requestList, driverList);
		
		
		System.out.println(program.totalRequestsDone(driverList));
//		for(Driver d:driverList) {
//			System.out.println(d.schedule);
//			System.out.println();
//		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
		for (Request r : requestList) {
			System.out.println(r);
		}
		

	}

}
