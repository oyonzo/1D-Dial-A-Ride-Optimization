package Project;

import java.util.*;

public class MSBP {
    int TLIMIT = Graph.TLIMIT;
    int f;

    public void msbp (List<Request> requestList, List<Driver> driverList) {
        int n = requestList.size();
        int k = driverList.size();
        int i = 1;
        double[][] positions = Graph.generateFixedList();
		int segmentLength = findSegLength(positions);
		f = TLIMIT / segmentLength;
        if (f % 2 == 1) {
            // at t1, do nothing
            i = 2;
        }
        while (i < f) { // loop through time chunks until total time is reached
        	int timeLimit = i * segmentLength;
            while (n!=0 && k!=0) { // while there are unassigned drivers and unassigned requests
                List<Request> r = maxRequests(requestList, timeLimit);
                Driver closestDriver = findClosestDriver(r.get(0), driverList);
                assignRequestsToDriver(r, closestDriver);
                removeRequestsFromList(r, requestList);
            }

            i += 2;
            unassignAllDrivers(driverList);
        }

    }
    public static int findSegLength(double[][] positions) {
		double longest = 0;
		for (int i=0; i<positions.length; i++) {
			for (int j=i+1; j<positions.length; j++) {
				double distance = Graph.dist(positions[i], positions[j]);
				if (distance > longest) {
					longest = distance;
				}
			}
		}
		int res = (int) Math.round(longest);
		return res;
	}
	
	public static void findPaths(List<Request> requests, double currentTime, List<Request> currentPath, List<Request> bestPath, double timeLimit) {
		if (currentPath.size() > bestPath.size()) {
			 bestPath.clear();
			 bestPath.addAll(currentPath);
		}
		for (Request request : requests) {
			if (currentPath.contains(request)) continue;
			
			 double pickupTime = (currentPath.isEmpty() ? 0 : currentPath.get(currentPath.size() - 1).finishTime);
			 double finishTime = pickupTime + request.weight; // Finish time for this request
			
			 if (pickupTime <= request.pickTime && finishTime <= request.finishTime && finishTime <= timeLimit) {
				 currentPath.add(request);
				 findPaths(requests, finishTime, currentPath, bestPath, timeLimit);
				 currentPath.remove(currentPath.size() - 1); // backtrack
			  }
		}
	}
	
	// finds max_size_request_set that can be served in a single time segment
    // @return ArrayList of this set
	public static List<Request> maxRequests(List<Request> requests, double timeLimit) {
		 // Sort requests by pickup time
		 requests.sort(Comparator.comparingDouble(r -> r.pickTime));
		 List<Request> bestPath = new ArrayList<>();
		 for (Request request : requests) {
		 if (request.pickTime + request.weight <= request.finishTime) {
		 List<Request> currentPath = new ArrayList<>();
		 currentPath.add(request);
		 findPaths(requests, request.pickTime + request.weight, currentPath, bestPath, timeLimit);
		 }
		 }
		 return bestPath;
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
		/*double[][] positions = Graph.generateFixedList();
		int segmentLength = findSegLength(positions);
		double[] start1 = {0, 0};
		double[] end1 = {1,0};
		double time1 = 0;
		
		double[] start2 = {1,0};
		double[] end2 = {2,0};
		double time2 = 1;
		
		double[] start3 = {2,0};
		double[] end3 = {7,0};
		double time3 = 2;
		
		double[] start4 = {7,0};
		double[] end4 = {10,0};
		double time4 = 7;
		
		double[] start5 = {1,1};
		double[] end5 = {2,2};
		double time5 = 3;
		
		double[] start6 = {0,9};
		double[] end6 = {0,10};
		double time6 = 9;
		
		Request r1 = new Request(start1, end1, time1);
		Request r2 = new Request(start2, end2, time2);
		Request r3 = new Request(start3, end3, time3);
		Request r4 = new Request(start4, end4, time4);
		Request r5 = new Request(start5, end5, time5);
		Request r6 = new Request(start6, end6, time6);
		
		List<Request> requestList = new ArrayList<Request>(Arrays.asList(r1,r2,r3,r4,r5,r6));
		
		List<Request> maxSet = maxRequests(requestList, 10);
		
		System.out.println(maxSet);*/
		double[] start1 = {8.012079927442816, 33.08897605983439};
		double[] end1 = {2.6238602849973187, 40.69015712051377};
		double time1 = 988.6371256534361;
		
		double[] start2 = {8.012079927442816, 33.08897605983439};
		double[] end2 = {31.075387422145145, 18.867498939145417};
		double time2 = 489.3973002990945;
		
		double[] start3 = {9.853250978158638, 18.19206186629683};
		double[] end3 = {22.165535883352796, 29.53270781117363};
		double time3 = 871.8169129326469;
		
		double[] start4 = {33.712027864019625, 6.31506735439976};
		double[] end4 = {24.1370456387911, 29.404562311381152};
		double time4 = 402.1503152396752;
		
		double[] start5 = {2.6238602849973187, 40.69015712051377};
		double[] end5 = {37.33011717132071, 26.373511002047405};
		double time5 = 195.7651417248516;
		
		double[] start6 = {39.13189328128983, 33.639049058051015};
		double[] end6 = {33.712027864019625, 6.31506735439976};
		double time6 = 269.289457006706;
		
		double[] start7 = {11.246204778898193, 36.64766052587953};
		double[] end7 = {39.13189328128983, 33.639049058051015};
		double time7 = 680.8476350257471;
		
		double[] start8 = {1.1030136099127195, 14.376809041999099};
		double[] end8 = {31.075387422145145, 18.867498939145417};
		double time8 = 867.7167828557609;
		
		double[] start9 = {33.712027864019625, 6.31506735439976};
		double[] end9 = {17.94590574781268, 18.499175392496355};
		double time9 = 392.7838614173427;
		
		double[] start10 = {21.597675610248814, 36.75223736147819};
		double[] end10 = {8.012079927442816, 33.08897605983439};
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
		
		List<Request> requestList = new ArrayList<Request>(Arrays.asList(r1,r2,r3,r4,r5,r6,r7,r8,r9,r10));
		
		double[] position1 = {28.892212783770375, 42.25182445486328};
		double[] position2 = {22.704608370595356, 38.64631471180244};
		double[] position3 = {22.165535883352796, 29.53270781117363};
		double[] position4 = {17.94590574781268, 18.499175392496355};
		double[] position5 = {22.165535883352796, 29.53270781117363};
		
		Driver d1 = new Driver(position1);
		Driver d2 = new Driver(position2);
		Driver d3 = new Driver(position3);
		Driver d4 = new Driver(position4);
		Driver d5 = new Driver(position5);
		
		List<Driver> driverList = new ArrayList<Driver>(Arrays.asList(d1,d2,d3,d4,d5));
		List<Request> requestList_random = Request.createRequests(75);
		
		List<Driver> driverList_random = Driver.generateRandDrivers(15);
		//print out the start position of each driver
		for(Driver d:driverList_random) {
			System.out.println(Arrays.toString(d.getPosition()));
		}
		
		MSBP program = new MSBP();
		program.msbp(requestList, driverList);
		
		
		System.out.println(program.totalRequestsDone(driverList));
		for(Driver d:driverList) {
			System.out.println(d.schedule);
			System.out.println();
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
		for (Request r : requestList_random) {
			System.out.println(r);
		}
		

	}
	}

