
package Optimal;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Project.WeightFunction;
import Project.LinearRequest;
import Project.Test;

public class newMain{
	public static int iterations;
	static List<ArrayList<LinearRequest>> combinations = new ArrayList<ArrayList<LinearRequest>>();
	public static int index =0;
	public static int sumSizeOPT =0;
	public static int sumSizeALG = 0;
	public static int sumSizeCOM = 0;
	public static long sumTimeOPT =0;
	public static long sumTimeALG = 0;
	public static long sumTimeCOM = 0;
	
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
			
	
			
			for(int i=0;i<iterations;i++) {
				List<LinearRequest> rl = wf.creatRandomRequest();
				List<LinearRequest> temp1 = new ArrayList<LinearRequest>();
				List<LinearRequest> temp2 = new ArrayList<LinearRequest>();
				for(LinearRequest lr: rl) {
					temp1.add(lr);
					temp2.add(lr);
				}
				
				//print the randLIst
				System.out.println("The List of RandomRequests:");
				for(LinearRequest r : rl) {
					System.out.println(r.toString());
				}

				
				System.out.println("\nRound " + (i+1));
//				Get The RunTime of OPT
				long startTime = System.nanoTime();
				List<LinearRequest> print = getOptimal(rl);
				int OPT = print.size();
				long endTime = System.nanoTime();
				
				System.out.println("OPT takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				
				//RunTime of ALL Combination of weights
				//Alg with best  combinations
				startTime = System.nanoTime();
				wf.set.clear();
				List<LinearRequest> ml = wf.runAlg1(rl);				
				int s = ml.size();				
				endTime = System.nanoTime();
				System.out.println("ALG with best combination takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				
				//RunTime of ShareRides
				startTime = System.nanoTime();
				ShareRide sr = new ShareRide();
				sr.set.clear();
				List<LinearRequest> share = sr.shareAlg(temp2);	
				int shareSize = share.size();				
				endTime = System.nanoTime();
				System.out.println("Pure Share Rides takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				
				
				System.out.println("\nThe Optimal List: ");
				for(LinearRequest r : print) {
					System.out.println(r.toString());
				}
				//clear the set for the next iteration
				print.clear();
				combinations.clear();
				index = 0;
				
				System.out.println("\nShareRides List:");
				for(LinearRequest r : share) {
					System.out.println(r.toString());
				}
				
				
				
				//RunTime of ALG
				startTime = System.nanoTime();
				wf.set.clear();
				wf.runAlg2(temp1,0,0,0,1,1);
				int size = wf.set.size();
				endTime = System.nanoTime();
				System.out.println("ALG takes "+ (endTime - startTime)/100000.0 + " Millisecond");
				
				
				System.out.println("\n30 in advance & ShareRides List:");
				for(LinearRequest r : wf.set) {
					System.out.println(r.toString());
				}
				
				
				
				
				
				
				//System.out.println("\nThe Max Size from OPT is: " + OPT + "\nThe Max Size from ALG with best combination is: " + s + "\nThe Max Size from ALG is(30 in advance & sharerides): " + size + "\nThe Max Size from Share Ride: " + shareSize);
				System.out.println("\nThe Max Size from OPT is: " + OPT + "\nThe Max Size from ALG is(30 in advance & sharerides): " + size + "\nThe Max Size from Share Ride: " + shareSize);
			}
			
			
			
			
			
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
				
				ShareRide sr = new ShareRide();

				
				//RunTime of ALG
				sr.alg(rl,0,0);
				
				System.out.println("\nThe Path: ");
				for(LinearRequest r : sr.set) {
					System.out.println(r.toString());
				}
				
				System.out.println("\nOverlapped ");
				for(LinearRequest r : sr.overlap) {
					System.out.println(r.toString());
				}
			
			
				sr.shareStart(sr.set.get(0), 0, 0, 0);
				
				System.out.println("\nShareStarts ");
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
