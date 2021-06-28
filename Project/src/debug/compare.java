
package debug;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Project.WeightFunction;
import Project.LinearRequest;
import Project.Test;

public class compare{
	public static int iterations;
	static List<ArrayList<LinearRequest>> combinations = new ArrayList<ArrayList<LinearRequest>>();
	static int index = 0;
	
	
	public static void main(String[] args)throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print("What types of request do you want have? Random(1), Text Input(2): ");
		int choice = sc.nextInt();
		if(choice == 1) {
			System.out.print("How mamy random requests do you want to have: ");
			
			WeightFunction wf = new WeightFunction();
			wf.numRequest = sc.nextInt();
			
			
			System.out.print("How many iterations do you want: ");
			iterations = sc.nextInt();
			
			//print random request list
//			for(LinearRequest r : rl) {
//				System.out.println(r.toString());
//			}
			
			/*
			 * For comparison purposes of the three ride sharing algorithms. 
			 * These variables will contain net values, which will be devided by the number of iterations at the end
			 */
			double totIterations = iterations;
			
			double netRuntimeAlg = 0.0;
			double netRuntimeSR = 0;
			double netRuntimeMP = 0;
			
			int netReqAlg = 0;
			int netReqSR = 0;
			int netReqMP = 0;
			
			for(int i=0;i<iterations;i++) {
				
				List<LinearRequest> rl = wf.creatRandomRequest();
				List<LinearRequest> temp1 = new ArrayList<LinearRequest>();
				List<LinearRequest> temp2 = new ArrayList<LinearRequest>();
				for(LinearRequest lr: rl) {
					temp1.add(new LinearRequest(lr.startPos,lr.finishPos,lr.pickTime));
					temp2.add(new LinearRequest(lr.startPos,lr.finishPos,lr.pickTime));
				}
				
				//print the randLIst
//				System.out.println("The List of RandomRequests:");
//				for(LinearRequest r : rl) {
//					System.out.println(r.toString());
//				}

				
				System.out.println("\nRound " + (i+1));
				//print the randLIst
				System.out.println("The List of RandomRequests:");
				for(LinearRequest r : rl) {
					System.out.println(r.toString());
				}
				//Get The RunTime of OPT
//				long startTime = System.nanoTime();
//				int OPT = getOptimal(rl).size();
//				long endTime = System.nanoTime();
//				System.out.println("OPT takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				
				//RunTime of ALL Combination of weights
//				long startTime = System.nanoTime();
//				wf.set.clear();
//				List<LinearRequest> ml = wf.runAlg1(rl);				
//				int s = ml.size();				
//				long endTime = System.nanoTime();
//				System.out.println("\nALG with best combination takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				
				//RunTime of ALG
				long startTime = System.nanoTime();
				wf.set.clear();
				wf.runAlg2(rl,0,0,0,1,1);
				int size = wf.set.size();
				long endTime = System.nanoTime();
				System.out.println("\nALG takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				netRuntimeAlg += (endTime - startTime)/100000.0;
				netReqAlg += size;
				

				
				
				//Runtime of Share Rides
				startTime = System.nanoTime();
				debug sr = new debug();
				sr.set.clear();
				sr.overlap.clear();
				sr.shareRides.clear();
				List<LinearRequest> fin  = sr.shareAlg(temp1);
				int s = sr.set.size() + sr.shareRides.size();
				endTime = System.nanoTime();
				System.out.println("Share Rides takes "+ (endTime - startTime)/100000.0 + " Milliseconds");
				netRuntimeSR += (endTime - startTime)/100000.0;
				netReqSR += s;
				
				//RunTime of Make Path ShareRides
				startTime = System.nanoTime();
				debug sr2 = new debug();
				sr2.set.clear();
				sr2.overlap.clear();
				sr2.shareRides.clear();
				sr2.path.clear();
				sr2.nervous(temp2);	
				int shareSize = sr2.set.size() + sr2.shareRides.size();		
				System.out.println("Set size: " + sr2.set.size() + " ShareRides size: " + sr2.shareRides.size());
				endTime = System.nanoTime();
				System.out.println("Make Path Share Rides takes "+ (endTime - startTime)/100000.0 + " Milliseconds");
				netRuntimeMP += (endTime - startTime)/100000.0;
				netReqMP += shareSize;
				
				System.out.println("\nPath List:");
				for(LinearRequest r : sr.set) {
					System.out.println(r.toString());
				}
//				
				System.out.println("\nShareRides List:");
				for(LinearRequest r : sr.shareRides) {
					System.out.println(r.toString());
				}


				System.out.println("ALG List:");
				for(LinearRequest r : wf.set) {
					System.out.println(r.toString());
				}
				
				System.out.println("\n");
				
				
				
				
				//System.out.println("\nThe Max Size from OPT is: " + OPT + "\nThe Max Size from ALG with best combination is: " + s + "\nThe Max Size from ALG is(30 in advance & sharerides): " + size + "\nThe Max Size from Share Ride: " + shareSize);
				System.out.println("\nThe Max Size from ALG is: " + size + "\nThe Max Size Share Rides: " + s + "\nThe Max Size from Make Path Share Ride: " + shareSize);
				
				System.out.println("\nThe Path:");
				for(LinearRequest l : sr.path) {
					System.out.println(l.toString());
				}
				
				
			
			}
			
			/*
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
			
			double AvgRuntimeAlg = netRuntimeAlg / totIterations;
			double AvgRuntimeSR = netRuntimeSR / totIterations;
			double AvgRuntimeMP = netRuntimeMP / totIterations;
			
			double AvgReqAlg = netReqAlg / totIterations;
			double AvgReqSR = netReqSR / totIterations;
			double AvgReqMP = netReqMP / totIterations;
			
			System.out.println("\n");
			System.out.println("The Average Runtime over " + totIterations + " iterations was: \n" + "Alg: " + AvgRuntimeAlg + " Serve Request: " + AvgRuntimeSR + "  Make Path: " + AvgRuntimeMP);
			System.out.println();
			System.out.println("The Average number of requests served over " + totIterations + " iterations was: \n" + "Alg: " + AvgReqAlg + " Serve Request: " + AvgReqSR + "  Make Path: " + AvgReqMP);

		
		}else{//Text Scan
	        File file = new File("/Users/haiyiluo/eclipse-workspace/Project/src/input.txt");
	            Scanner reader = new Scanner(file);
	            
	            List<LinearRequest> rl = new ArrayList<LinearRequest>();
	            while(reader.hasNextLine()) {
	            	int s = reader.nextInt();
	            	int d = reader.nextInt();
	            	int f = reader.nextInt();
	            	rl.add(new LinearRequest(s,d,f));
	            }
	            
	            System.out.println("\nOur Request Input: ");
	            
				for(LinearRequest r : rl) {
					System.out.println(r.toString());
				}
				
				debug sr = new debug();

				
				//RunTime of ALG
				sr.alg(rl,0,0);
				
				System.out.println("\nThe Path: ");
				for(LinearRequest r : sr.set) {
					System.out.println(r.toString());
				}
				
				
				System.out.println("\noverlap ");
				for(LinearRequest r : sr.overlap) {
					System.out.println(r.toString());
				}
			
				sr.shareAlg(rl);
				
				System.out.println("\nShare ");
				for(LinearRequest r : sr.shareRides) {
					System.out.println(r.toString());
				}
				
				//List<LinearRequest> s = sr.shareEnd();
				
//				System.out.println("\nShareEnds ");
//				for(LinearRequest r : s) {
//					System.out.println(r.toString());
//				}
	        
		}
	}
	
	
	public static void permute(List<LinearRequest> lrl, int k) {
        if (k == lrl.size()) {
        	ArrayList<LinearRequest> indivSeq = new ArrayList<LinearRequest>();
        		for (int i = 0; i < lrl.size(); i++) {
        			indivSeq.add(lrl.get(i));
//        			System.out.print(" [" + lrl.get(i).toString() + "] ");
                
            }
        		combinations.add(index, indivSeq);
        		index++;
//        		System.out.println();
        } 
        else {
        	//recursively swapping one thing each time
            for (int i = k; i < lrl.size(); i++) {
            	Collections.swap(lrl, i, k);
                permute(lrl, k + 1);
                Collections.swap(lrl, i, k);
            }
        }
 
	}
	
	public static List<LinearRequest> getOptimal (List<LinearRequest> lrl){


		//gives us a list of list of requests, in every combination possible
        permute(lrl, 0);
        for(int j = 0; j < combinations.size(); j++) {
        	int time = 0;
        	int origin = 0;
        	for(int i = 0; i< combinations.get(j).size(); i++) {
        		if(combinations.get(j).get(i).pickTime < time) {
        			combinations.get(j).remove(i);
        			i--;
        			continue;
        		}
        		if(time + Math.abs(combinations.get(j).get(i).startPos - origin) > combinations.get(j).get(i).pickTime) {
        			combinations.get(j).remove(i);
        			i--;
        		}
        		time = combinations.get(j).get(i).finishTime;
    			origin = combinations.get(j).get(i).finishPos;
        	}
        }

        int maxIndex = -1;
        int maxSize = Integer.MIN_VALUE;

        for(int i =0; i< combinations.size(); i++) {
        	List<LinearRequest> l = combinations.get(i);
        	if (l.size() > maxSize) {
        		maxSize = l.size();
        		maxIndex = i;
        	}
        }


        return combinations.get(maxIndex);
	}
	
	
}