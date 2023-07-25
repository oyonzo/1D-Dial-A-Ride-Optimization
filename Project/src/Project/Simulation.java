package Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
	public static int iterations;
    public static int index = 0;
    public static int sumSizeEarliest = 0;
    public static int sumSizeMinF = 0;
    public static long sumTimeEarliest = 0;
    public static long sumTimeMinF = 0;
    public static int numDrivers;
    public static int numRequests;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("How mamy random requests do you want to have: ");
        numRequests = sc.nextInt();

        System.out.print("How many iterations do you want: ");
        iterations = sc.nextInt();

        System.out.print("How many drivers do you want: ");
        numDrivers = sc.nextInt();

        sc.close();
        
        
        // create two empty lists to keep track of the results of each iteration of the two algorithms 
        List<Integer> resEarliest = new ArrayList<Integer> ();
        List<Integer> resMinF = new ArrayList<Integer> ();
        
        
        for (int i = 0; i < iterations; i++) {
        	// initialize the request list
            List<Request> requestList = Request.createRequests(numRequests);
    		// initialize the f value
    		for (Request r : requestList) {
    			r.setf(requestList);
    		}
    		// initialize the driver list
    		List<Driver> driverList = Driver.generateRandDrivers(numDrivers);
    		
        	// test earliest
        	List<Request> requestList1 = new ArrayList<>();
        	for (Request r : requestList) {
        	    // Assuming Request class has a copy constructor
        	    requestList1.add(r);
        	}

        	List<Driver> driverList1 = new ArrayList<>();
        	for (Driver d : driverList) {
        	    // Assuming Driver class has a copy constructor
        	    driverList1.add(d);
        	}
        	Earliest runEarliest = new Earliest();
        	runEarliest.earliest(requestList1, driverList1);
    		int total1 = 0;
    		//print out the schedule for each driver
    		for (int j=0; j<driverList1.size(); j++) {
    			//System.out.println("Driver"+ (j+1)+": " + driverList1.get(j).schedule);
    			total1 += driverList1.get(j).schedule.size();
    		}
    		resEarliest.add(total1);
    		Simulation.clear(driverList);
    		
  
        	// test minFValue
			List<Request> requestList2 = new ArrayList<>();
	    	for (Request r : requestList) {
	    	    requestList2.add(r);
	    	}
	
	    	List<Driver> driverList2 = new ArrayList<>();
	    	for (Driver d : driverList) {
	    	    driverList2.add(d);
	    	}
	    	MinFValue runMinF = new MinFValue();
	    	runMinF.minFValue(requestList2, driverList2);
			int total2 = 0;
			//print out the schedule for each driver
			for (int j=0; j<driverList2.size(); j++) {
				//System.out.println("Driver"+ (j+1)+": " + driverList2.get(j).schedule);
				total2 += driverList2.get(j).schedule.size();
			}
			resMinF.add(total2);
			Simulation.clear(driverList);
            
        }
        System.out.println(resEarliest);
        System.out.println(resMinF);
    }
    
    public static void clear(List<Driver> driverList) {
    	for (Driver d: driverList) {
    		d.clearSchedule();
    	}
    }

}
