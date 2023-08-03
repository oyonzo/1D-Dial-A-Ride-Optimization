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

        System.out.print("How many random requests do you want to have: ");
        numRequests = sc.nextInt();

        System.out.print("How many iterations do you want: ");
        iterations = sc.nextInt();

        System.out.print("How many drivers do you want: ");
        numDrivers = sc.nextInt();

        sc.close();
        
        
        // create two empty lists to keep track of the results of each iteration of the two algorithms 
        List<Integer> resEarliestPickUp = new ArrayList<Integer> ();
        List<Integer> resMinF = new ArrayList<Integer> ();
        List<Integer> resEarliestDropOff = new ArrayList<Integer> ();
        for (int i = 0; i < iterations; i++) {
        	// initialize the request list
            List<Request> requestList = Request.createRequests(numRequests);
            //System.out.println(requestList);

    		// initialize the driver list
    		List<Driver> driverList = Driver.generateRandDrivers(numDrivers);
    		//System.out.println(driverList);
    		
      
	       	List<Request> requestList1 = new ArrayList<>();
       		for (Request r : requestList) {
	       		Request rCopy = new Request(r.startPos, r.finishPos, r.pickTime);
	       		requestList1.add(rCopy);
       		}
	       	
	       	List<Request> requestList2 = new ArrayList<>();
	    	for (Request r : requestList) {
	    		Request rCopy = new Request(r.startPos, r.finishPos, r.pickTime);
      		 requestList2.add(rCopy);
	    	}
	    	// initialize f value for MinFValue test
	    	for (Request r : requestList2) {
				r.setf(requestList2);
			}
		    	
		    List<Request> requestList3 = new ArrayList<>();
	    	for (Request r : requestList) {
	    		Request rCopy = new Request(r.startPos, r.finishPos, r.pickTime);
      		 requestList3.add(rCopy);
	    	}
	    	List<Request> requestList4 = new ArrayList<>();
	    	for (Request r : requestList) {
	    		Request rCopy = new Request(r.startPos, r.finishPos, r.pickTime);
      		 requestList4.add(rCopy);
	    	}


	
	       	List<Driver> driverList1 = new ArrayList<>();
       		for (Driver d : driverList) {
	       	    Driver dCopy = new Driver(d.getPosition());
	       	    driverList1.add(dCopy);
       		}
	       	List<Driver> driverList2 = new ArrayList<>();
	    	for (Driver d : driverList) {
	    		 Driver dCopy = new Driver(d.getPosition());
	    		 driverList2.add(dCopy);
	    	}
		    List<Driver> driverList3 = new ArrayList<>();
	    	for (Driver d : driverList) {
	    		 Driver dCopy = new Driver(d.getPosition());
	    		 driverList3.add(dCopy);
	    	}
	    	List<Driver> driverList4 = new ArrayList<>();
	    	for (Driver d : driverList) {
	    		 Driver dCopy = new Driver(d.getPosition());
	    		 driverList4.add(dCopy);
	    	}
    		
			//test earliest
        	EarliestPickUp runEarliestPickUp = new EarliestPickUp();
        	runEarliestPickUp.earliestPickUp(requestList1, driverList1);
        	resEarliestPickUp.add(runEarliestPickUp.totalRequestsDone(driverList1));
    		
    		// test minFValue
	    	MinFValue runMinF = new MinFValue();
	    	runMinF.minFValue(requestList2, driverList2);
			resMinF.add(runMinF.totalRequestsDone(driverList2));
    		
    		//test latest
			EarliestDropOff runEarliestDropOff = new EarliestDropOff();
			runEarliestDropOff.earliestDropOff(requestList3, driverList3);
			resEarliestDropOff.add(runEarliestDropOff.totalRequestsDone(driverList3));
			
    		
    		
 

    		
            
        }
        //System.out.println(resEarliest);
        double average1 = resEarliestPickUp.stream().mapToInt(i -> i).average().getAsDouble();
        System.out.println("The average for earliest is: " + average1);

        //System.out.println(resMinF);
        double average2 = resMinF.stream().mapToInt(i -> i).average().getAsDouble();
        System.out.println("The average for MinF is: " + average2);
        
        //System.out.println(resLatest);
        double average3 = resEarliestDropOff.stream().mapToInt(i -> i).average().getAsDouble();
        System.out.println("The average for earliestDropOff is: " + average3);
        
//        System.out.println(resShortest);
//        double average4 = resShortest.stream().mapToInt(i -> i).average().getAsDouble();
//        System.out.println("The average for shortest is: " + average4);
    }
    
    public static void clear(List<Driver> driverList) {
    	for (Driver d: driverList) {
    		d.clearSchedule();
    	}
    }

}
