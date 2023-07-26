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
            List<Request> requestList1 = Request.createRequests(numRequests);
            System.out.println(requestList1);

    		// initialize the driver list
    		List<Driver> driverList1 = Driver.generateRandDrivers(numDrivers);
    		System.out.println(driverList1);
    		
    		List<Request> requestList2 = Request.createRequests(numRequests);
            System.out.println(requestList2);
	    	// initialize the f value for minFValue algorithm
    		for (Request r : requestList2) {
    			r.setf(requestList2);
    		}

    		// initialize the driver list
    		List<Driver> driverList2 = Driver.generateRandDrivers(numDrivers);
    		System.out.println(driverList2);
    		
    		
      
//        	List<Request> requestList1 = new ArrayList<>();
//        	for (Request r : requestList) {
//        		Request rCopy = new Request(r.startPos, r.finishPos, r.pickTime);
//        		requestList1.add(rCopy);
//        	}
//        	
//        	List<Request> requestList2 = new ArrayList<>();
//	    	for (Request r : requestList) {
//	    		Request rCopy = new Request(r.startPos, r.finishPos, r.pickTime);
//       		 requestList2.add(rCopy);
//	    	}

//
//
//        	List<Driver> driverList1 = new ArrayList<>();
//        	for (Driver d : driverList) {
//        	    Driver dCopy = new Driver(d.getPosition());
//        	    driverList1.add(dCopy);
//        	}
//        	List<Driver> driverList2 = new ArrayList<>();
//	    	for (Driver d : driverList) {
//	    		 Driver dCopy = new Driver(d.getPosition());
//        	    driverList2.add(dCopy);
//	    	}
			
	    	
    		
			//test earliest
        	Earliest runEarliest = new Earliest();
        	runEarliest.earliest(requestList1, driverList1);
        	resEarliest.add(runEarliest.totalRequestsDone(driverList1));
    		
    		// test minFValue
	    	MinFValue runMinF = new MinFValue();
	    	runMinF.minFValue(requestList2, driverList2);
			resMinF.add(runMinF.totalRequestsDone(driverList2));
    		
    		
    		
 

    		
            
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
