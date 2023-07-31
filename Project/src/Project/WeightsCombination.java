package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WeightsCombination {
	public static int numDrivers;
    public static int numRequests;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

        System.out.print("How mamy random requests do you want to have: ");
        numRequests = sc.nextInt();

        System.out.print("How many drivers do you want: ");
        numDrivers = sc.nextInt();
        
		int[] weightsCombo = new int[3];
		MinFValue program = new MinFValue();
		
		List<Request> requestList = Request.createRequests(numRequests);
		List<Driver> driverList = Driver.generateRandDrivers(numDrivers);
		List<Integer> res = new ArrayList<Integer> ();
		
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
		       		
					weightsCombo[0] = i;
					weightsCombo[1] = j;
					weightsCombo[2] = k;
					program.minFValue(requestListCopy, driverListCopy);
					//System.out.println(Arrays.toString(weightsCombo));
					res.add(program.totalRequestsDone(driverListCopy));
				}
			}
		}
		System.out.println(res);
	}

}
