package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class WeightsCombination {
	public static int numDrivers;
    public static int numRequests;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

        System.out.print("How many random requests do you want to have: ");
        numRequests = sc.nextInt();

        System.out.print("How many drivers do you want: ");
        numDrivers = sc.nextInt();
        
		MinFValue program = new MinFValue();
		int bestRes=0;
		//int[] bestWeights = new int[3];
		
		List<Request> requestList = Request.createRequests(numRequests);
		List<Driver> driverList = Driver.generateRandDrivers(numDrivers);
		Map<int[], Integer> result = new HashMap<>();
		List<int[]> bestWeights = new ArrayList<>();
		double first=0;
		double second=0;
		double third=0;
		double[] average = new double[3];
		
		
		
		// testing with all combos of weights. each weight is an integer in range 0-9
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				for (int k=0; k<10; k++) {
					List<Request> requestListCopy = new ArrayList<>();
		       		for (Request r : requestList) {
			       		Request rCopy = new Request(r.startPos, r.finishPos, r.pickTime);
			       		requestListCopy.add(rCopy);
		       		}
		       		
		       		List<Driver> driverListCopy = new ArrayList<>();
		       		for (Driver d : driverList) {
			       	    Driver dCopy = new Driver(d.getPosition());
			       	    driverListCopy.add(dCopy);
		       		}
		       		int[] weightsCombo = new int[3];
					weightsCombo[0] = i;
					weightsCombo[1] = j;
					weightsCombo[2] = k;
					program.minFValue(i, j, k,requestListCopy, driverListCopy);
					int numCompleted = program.totalRequestsDone(driverListCopy);
					result.put(weightsCombo, numCompleted);
					if (numCompleted > bestRes) {
						bestRes = numCompleted;
					}	
				}
			}
		}
		for (Entry<int[], Integer> entry : result.entrySet()) {
			if (entry.getValue() == bestRes) {
				bestWeights.add(entry.getKey());
			}
		}
		
		
		for (int[] c : bestWeights) {
			first += c[0];
			second += c[1];
			third += c[2];
		}
		
		average[0] = first/((double) bestWeights.size());
		average[1] = second/((double) bestWeights.size());
		average[2] = third/((double) bestWeights.size());
		
		
//		System.out.println(requestList);
//		System.out.println(driverList);
		System.out.println("Number of requests completed: " + bestRes);
		System.out.println("Number of best weight combos: " + bestWeights.size());
		System.out.println();
//		System.out.println("Best weight combos: ");
//		for (int[] c : bestWeights) {
//			System.out.print(Arrays.toString(c));
//		}
		System.out.println("Take the average:");
		System.out.println(Arrays.toString(average));
	}

}
