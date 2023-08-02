package Project;

import java.util.*;

public class MSBP {
	static int TLIMIT = Graph.TLIMIT;
	
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
	
	public static void main(String[] args) {
		double[][] positions = Graph.generateFixedList();
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
		
		System.out.println(maxSet);

	}

}
