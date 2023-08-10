package Project;

import java.util.*;

public class EarliestPickUp {
    private static final int THRESHOLD = 1;

    public void earliestPickUp(List<Request> requestList, List<Driver> driverList) {
        // set n to the number of requests and k to the number of drivers
        int n = requestList.size();
        int k = driverList.size();

        Map<Request, Driver> assignedDrivers = new HashMap<>();
        List<Request> unassignedRequests = new ArrayList<>(requestList);

        // // Step 1: Sort the requests in increasing order of pickup time
        Collections.sort(requestList, Comparator.comparingDouble(request -> request.pickTime));

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
        // print out the schedule for each driver
        for (int i = 0; i < driverList.size(); i++) {
            // System.out.println("Driver"+ (i+1)+": " + driverList.get(i).schedule);
            total += driverList.get(i).schedule.size();
        }
        return total;
    }

    // @return driver of specified request
    /*
     * private static Driver getAssignedDriver(Request r, List<Driver> driverList) {
     * for (Driver driver : driverList) {
     * if (driver.schedule.contains(r)) {
     * return driver;
     * }
     * }
     * return null;
     * }
     */

    // test for both random and hard coded drivers and requests (see line 208)
    public static void main(String[] args) {
        double[] start1 = { 8.012079927442816, 33.08897605983439 };
        double[] end1 = { 2.6238602849973187, 40.69015712051377 };
        double time1 = 988.6371256534361;

        double[] start2 = { 8.012079927442816, 33.08897605983439 };
        double[] end2 = { 31.075387422145145, 18.867498939145417 };
        double time2 = 489.3973002990945;

        double[] start3 = { 9.853250978158638, 18.19206186629683 };
        double[] end3 = { 22.165535883352796, 29.53270781117363 };
        double time3 = 871.8169129326469;

        double[] start4 = { 33.712027864019625, 6.31506735439976 };
        double[] end4 = { 24.1370456387911, 29.404562311381152 };
        double time4 = 402.1503152396752;

        double[] start5 = { 2.6238602849973187, 40.69015712051377 };
        double[] end5 = { 37.33011717132071, 26.373511002047405 };
        double time5 = 195.7651417248516;

        double[] start6 = { 39.13189328128983, 33.639049058051015 };
        double[] end6 = { 33.712027864019625, 6.31506735439976 };
        double time6 = 269.289457006706;

        double[] start7 = { 11.246204778898193, 36.64766052587953 };
        double[] end7 = { 39.13189328128983, 33.639049058051015 };
        double time7 = 680.8476350257471;

        double[] start8 = { 1.1030136099127195, 14.376809041999099 };
        double[] end8 = { 31.075387422145145, 18.867498939145417 };
        double time8 = 867.7167828557609;

        double[] start9 = { 33.712027864019625, 6.31506735439976 };
        double[] end9 = { 17.94590574781268, 18.499175392496355 };
        double time9 = 392.7838614173427;

        double[] start10 = { 21.597675610248814, 36.75223736147819 };
        double[] end10 = { 8.012079927442816, 33.08897605983439 };
        double time10 = 596.6198960327864;

        Request r1 = new Request(start1, end1, time1);
        Request r2 = new Request(start2, end2, time2);
        Request r3 = new Request(start3, end3, time3);
        Request r4 = new Request(start4, end4, time4);
        Request r5 = new Request(start5, end5, time5);
        Request r6 = new Request(start6, end6, time6);
        Request r7 = new Request(start7, end7, time7);
        Request r8 = new Request(start8, end8, time8);
        Request r9 = new Request(start9, end9, time9);
        Request r10 = new Request(start10, end10, time10);

        List<Request> requestList = new ArrayList<Request>(Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10));

        for (Request r : requestList) {
            r.setf(requestList);
        }

        double[] position1 = { 28.892212783770375, 42.25182445486328 };
        double[] position2 = { 22.704608370595356, 38.64631471180244 };
        double[] position3 = { 22.165535883352796, 29.53270781117363 };
        double[] position4 = { 17.94590574781268, 18.499175392496355 };
        double[] position5 = { 22.165535883352796, 29.53270781117363 };

        Driver d1 = new Driver(position1);
        Driver d2 = new Driver(position2);
        Driver d3 = new Driver(position3);
        Driver d4 = new Driver(position4);
        Driver d5 = new Driver(position5);

        List<Driver> driverList = new ArrayList<Driver>(Arrays.asList(d1, d2, d3, d4, d5));
        List<Request> requestList_random = Request.createRequests(75);

        for (Request r : requestList_random) {
            r.setf(requestList_random);
        }
        List<Driver> driverList_random = Driver.generateRandDrivers(15);
        // print out the start position of each driver
        for (Driver d : driverList_random) {
            System.out.println(Arrays.toString(d.getPosition()));
        }

        EarliestPickUp program = new EarliestPickUp();
        // switch out with requestList_random and driverList_random if needed
        program.earliestPickUp(requestList, driverList);

        System.out.println(program.totalRequestsDone(driverList));
        // for(Driver d:driverList) {
        // System.out.println(d.schedule);
        // System.out.println();
        // }
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Request r : requestList_random) {
            System.out.println(r);
        }
    }
}
