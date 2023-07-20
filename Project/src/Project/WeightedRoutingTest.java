package Project;

import Project.Request;
import java.util.*;

public class WeightedRoutingTest {

	public static void main(String[] args) {
		WeightedRoutingTest program = new WeightedRoutingTest();

        Request.numRequest = 10;
        List<Request> requestList = Request.createRequests();
        double[] weights = randomWeightGenerator();
		for (Request request : requestList) {
			request.setf(weights[0], weights[1], weights[2],requestList);
		}
        System.out.println(requestList);
        System.out.println();

        double t = 0; // current time
        double[] c = new double[2]; // current location of driver

        program.weightedRouting(requestList, t, c);
    }
	
	public void weightedRouting(List<Request> requestList, double t, double[] c) {
        // Sort the requestList in ascending order of f function value
        Collections.sort(requestList, Comparator.comparingDouble(request -> request.f_val));

        while(!requestList.isEmpty()) {
            Iterator<Request> iterator = requestList.iterator();
            while (iterator.hasNext()) {
                Request request = iterator.next();
                // Adjust pickup time based on travel time
                request.pickTime += request.dist(request.startPos, c) / request.speed;
                if (request.pickTime < t || request.finishTime < t) {
                    // Remove all requests whose desired pickup time or arrival time has already passed
                    iterator.remove();
                } else {
                    // Choose request with smallest f value that does not overlap any previously chosen request
                    System.out.println("Serving request: " + request.toString()); // print the request being served
                    t = request.finishTime; // set current time to drop-off time of the request
                    c = request.finishPos; // set current location to drop-off location of the request
                    iterator.remove(); // remove this request from list as it's served
                    break; // stop iteration as we've served a request
                }
            }
        }
    }
	
	public static double[] randomWeightGenerator() {
		double[] res = new double[3];
		Random rand = new Random();

        double w1 = rand.nextDouble(); // Generate first double
        double w2 = rand.nextDouble() * (1 - w1); // Generate second double, scale it so the sum of w1 and w2 is not more than 1
        double w3 = 1 - w1 - w2; // Subtract the sum of the first two from 1 to get the third
        
        res[0] = w1;
        res[1] = w2;
        res[2] = w3;
		
        return res;
	}


}
