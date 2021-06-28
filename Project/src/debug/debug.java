package debug;

import java.util.*;


import Project.LinearRequest;

public class debug {
	public static List<LinearRequest> set = new ArrayList<LinearRequest>();
	public static List<LinearRequest> overlap  = new ArrayList<LinearRequest>();//Overlap
	public static List<LinearRequest> shareRides = new ArrayList<LinearRequest>(); //The LinearRequests with valid starting
	public static List<LinearRequest> path = new ArrayList<LinearRequest>();
	public static List<LinearRequest> check = new ArrayList<LinearRequest>();
	public static double w1 = 0;
	public static double w2 = 1;
	public static double w3 = 1;
	
	
	//Assign the f-value to a given linear request list
	public static void assignf(List<LinearRequest> lrl, double origin) {
		for(LinearRequest lr: lrl) {
			lr.setf(w1,w2,w3,Math.abs((double)lr.startPos - origin),lr.finishTime,lr.getX3(lrl));
		}
	}
	
	
	//simulate the running of the algorithm
	public static void alg(List<LinearRequest> lrl, double time, int origin){
		
		//Assign f-value after removing all illegal requests
		assignf(lrl,origin);
		//sorts list based on f value
		Collections.sort(lrl);;
		
		//remove all illegal requests
		for(int i = 0; i<lrl.size(); i++) {
			
			if(lrl.get(i).pickTime < time) {
				overlap.add(lrl.remove(i));
				i--;
				continue;
			}
			//if arrival time is greater than the time desired for pickup
			if(Math.abs(lrl.get(i).startPos-origin)+ time > lrl.get(i).pickTime) {
				overlap.add(lrl.remove(i));
				i--;
			}
		}
		
		
		//adds first element of lrl to our set
		
		/*
		 * Create a new linear request with updated time to add into the set
		 */
		if (lrl.size()>0) {
			
			LinearRequest l = new LinearRequest(lrl.get(0).startPos, lrl.get(0).finishPos, lrl.get(0).pickTime);
			
			
			if(Math.abs(lrl.get(0).startPos - origin) <= (lrl.get(0).pickTime - time - 3)) {
				l = new LinearRequest(lrl.get(0).startPos, lrl.get(0).finishPos, lrl.get(0).pickTime-3);
			}else if (Math.abs(lrl.get(0).startPos - origin) == (lrl.get(0).pickTime - time - 2)) {
				l = new LinearRequest(lrl.get(0).startPos, lrl.get(0).finishPos, lrl.get(0).pickTime-2);
			}else if (Math.abs(lrl.get(0).startPos - origin) == (lrl.get(0).pickTime - time - 1)) {
				l = new LinearRequest(lrl.get(0).startPos, lrl.get(0).finishPos, lrl.get(0).pickTime-1);
			}
			
			l.setf(w1, w2, w3, Math.abs((double)l.startPos - origin),l.finishTime,l.getX3(lrl));
			
			set.add(l);
			
			//update time & origin
			time = l.finishTime;
			origin = l.finishPos;
			lrl.remove(0);
		
			alg(lrl,time,origin);//Recursive call
		}
		
		return;
	}
	
	public static void nervous(List<LinearRequest> lrl) {
		//List<LinearRequest> result = new ArrayList<LinearRequest>();
		//Add End Later
		alg(lrl,0,0);
		makePath2(0,0,Integer.signum(set.get(0).startPos),new LinearRequest(0,0,0),0);
		goodShare();
	}
	

	/*
	 * Creates a path including transition time for sharerides
	 */
	public static void makePath2(int time, int origin, int direction, LinearRequest merged, int counter) {
//		System.out.println("\nTime: " + time + ", origin: " + origin + ", direction: " + direction + ", merged: " + merged.toString() + ", counter: " + counter);
		
		if (counter == set.size()) {
			path.add(merged);
			return;
		}
		
		LinearRequest l = set.get(counter);
		
		//Transition
		if(origin != l.startPos) {
			
			LinearRequest trans = new LinearRequest(origin,l.startPos, time );
			
			//tags the LinearRequest as a transition, not a request
			trans.trans = true;
			//merged is not null
			if(merged.weight != 0) {
				if(trans.direction == merged.direction) {
					//check deadtime, dont merge if deadtime
					if(merged.hasDeadTime(trans)) {
						path.add(merged);
						merged = trans;
						//calls function with trans set to the new merge and l remains
						makePath2(merged.finishTime,merged.finishPos,merged.direction, merged,counter);
						
					}
					merged = l.concat(merged, trans);
					makePath2(merged.finishTime,merged.finishPos,merged.direction, merged,counter);
				}else {
					path.add(merged);
					merged = trans;
					makePath2(merged.finishTime,merged.finishPos,merged.direction,merged,counter);
				}
			}else { //merged is null
				merged = trans;
				makePath2(merged.finishTime,merged.finishPos,merged.direction,merged,counter);
			}
		}else { //origin = startpos, aka request not trans
			
			if(merged.weight != 0) {
				if(l.direction == merged.direction) {
					if(merged.hasDeadTime(l)) {
						path.add(merged);
						merged = l;
						makePath2(merged.finishTime,merged.finishPos,merged.direction, merged,counter+1);
					}else {
						merged = l.concat(merged, l);
						makePath2(merged.finishTime,merged.finishPos,merged.direction, merged,counter+1);
					}
				}else {
					path.add(merged);
					merged = l;
					makePath2(merged.finishTime,merged.finishPos,merged.direction, merged,counter+1);
				}
			}else { //merged is null
				merged = l;
				makePath2(merged.finishTime,merged.finishPos,merged.direction, merged,counter+1);
			}
		}
		
	}
	
	/*
	 * if the path.get(j).isTrans and 
	 */

	
	public static void goodShare() {
		//for each overlap, we loop through every path
		for(int i=0;i<overlap.size();i++) {
			LinearRequest o = overlap.get(i);
			for(int j=0;j<path.size();j++) {
				
				if(o.isIncluded(path.get(j))) {
					int T = o.pickTime - (path.get(j).pickTime + Math.abs(o.startPos - path.get(j).startPos));
					if(T <= 3 && T>=0) {
						o.pickTime -= T;
						o.finishTime -= T;
					
						shareRides.add(o);
					}
				} else {
					if(path.get(j).include(o.startPos, o.pickTime) && path.get(j).sameDirect(o)){
						//check deadtime between current and next
						if(j == path.size() - 1) {
								o.shift(path.get(j));
								System.out.println("Added through end: " + o.toString());
								shareRides.add(o);
						}else if(path.get(j+1).trans == true){
							int deadTime = path.get(j+2).pickTime - path.get(j+1).finishTime;
							if(Math.abs(o.finishPos - path.get(j).finishPos) <= deadTime/2) {
								o.shift(path.get(j));
								shareRides.add(o);
								//shift next request(trans)
								path.set(j+1,new LinearRequest(o.finishPos,path.get(j+2).startPos,o.finishTime));
								path.get(j+1).trans = true; 
								System.out.println("Added through shifting trans: " + o.toString());
							}
							
						}else{
							if(path.get(j).hasDeadTime(path.get(j+1))) {
								int deadTime = path.get(j+1).pickTime - path.get(j).finishTime;
								if(Math.abs(o.finishPos - path.get(j).finishPos) <= deadTime/2) {
									o.shift(path.get(j));
									shareRides.add(o);
									System.out.println("Added through deadtime: " + o.toString());
									System.out.println("Request in Path: " + path.get(j).toString());
								}
							}
						}
						
					}else if (path.get(j).include(o.finishPos, o.finishTime) && path.get(j).sameDirect(o)) {
						//check the deadtime between current and prev
						if(j>0 && path.get(j-1).hasDeadTime(path.get(j))) {
							int deadTime = path.get(j).pickTime - path.get(j-1).finishTime;
							if(Math.abs(path.get(j).startPos - o.startPos) <= deadTime/2) {
								o.shift2(path.get(j));

								shareRides.add(o);
								System.out.println("Added through deadtime: " + o.toString());
								System.out.println("Request in Path: " + path.get(j).toString());
							}
						}
					}
				}
			}
		}
	}
	
	
	
	
	
	public static void actualGoodShare() {
		for(int i=0;i<overlap.size();i++) {
			for(int j=0;j<path.size();j++) {
				if(overlap.get(i).isIncluded(path.get(j))) {
					int T = overlap.get(i).pickTime - (path.get(j).pickTime + Math.abs(overlap.get(i).startPos - path.get(j).startPos));
					if(T <= 3 && T>=0) {
						overlap.get(i).pickTime -= T;
						overlap.get(i).finishTime -= T;
//						System.out.println("T: " + T);
//						System.out.println(overlap.get(i).toString());
						shareRides.add(overlap.get(i));
					}
				
				}
			}
		}
	}
	
	
	
	//Allowing the sharerides but with 30 mins buffer-zone
	public static void shareStart2(LinearRequest serving, int counter, double time, int location) {
		
		
		for(int i=0;i<overlap.size();i++) {
			LinearRequest l = overlap.get(i);
			
//			System.out.println("\n l: " + l.toString() + " , " + l.);
			//starts in the middle, goes in same direction
			if(l.inMiddle(serving) && l.sameDirect(serving)) {
//				System.out.println("l : " + l.toString());
//				
//				System.out.println("T: " + l.pickTime + " - " + serving.pickTime + " + abs(" + l.startPos + " - " + serving.startPos + ")");
				int T = l.pickTime - (serving.pickTime + Math.abs(l.startPos-serving.startPos));
				if(T<=3 && T>=0) {
					//if(T>=3) T = 3;
					l.pickTime -= T;
					l.finishTime -= T;
					
//					System.out.println("\nMiddle");
//					System.out.println("Serving: " + serving.toString());
//					System.out.println("Share-Ride: " + l.toString());
//					System.out.println("T: "+ T) ;
					
					shareRides.add(l);
					overlap.remove(i);
					i--;
				}
			}else if(l.isBefore(serving) && l.sameDirect(serving)) {
				if(counter == 0) {
					LinearRequest trans = new LinearRequest(l.startPos,serving.startPos,l.pickTime);
					if(trans.sameDirect(serving)) {
						//T means the extra time that l has
						int T = l.pickTime + Math.abs(serving.startPos - l.startPos) - serving.pickTime;
						if(T<=3 && T>=0){
							//if(T>=3) T=3;
							l.pickTime -= T;
							l.finishTime -= T;
							
//							System.out.println("\nFirst-Before");
//							System.out.println("\nServing: " + serving.toString());
//							System.out.println("Share-Ride: " + l.toString());
//							System.out.println("T: "+ T) ;
							
							shareRides.add(l);
							overlap.remove(i);
							i--;
						}
					}
					
				}else{
						int j = counter-1;
						
						while(j >= 0) {
							//Find the request serving when l happens
							if(l.pickTime <= set.get(j).finishTime && l.sameDirect(set.get(j))) {
								int T = l.pickTime - (Math.abs(l.startPos - set.get(j).startPos) + set.get(j).pickTime);
								
								if(T<=3 && T>=0) {
									//if(T>=3) T=3;
									l.pickTime -= T;
									l.finishTime -= T;
									
//									System.out.println("\nBefore");
//									System.out.println("\nServing: " + set.get(j).toString());
//									System.out.println("Share-Ride: " + l.toString());
//									System.out.println("T: "+ T) ;
									
									shareRides.add(l);
									overlap.remove(i);
									i--;
									break;
								}
								
							}
							if(!l.sameDirect(set.get(j))) break;
							j--;
						}
						
					}
					
				}
			}
		
		
		counter++;
		time = serving.finishTime;
		location = serving.finishPos;
		if(counter == set.size()) return;
		shareStart2(set.get(counter),counter,time,location);
	}
	
	public static List<LinearRequest> shareAlg(List<LinearRequest> lrl){
		List<LinearRequest> result = new ArrayList<LinearRequest>();
		//Add End Later
		alg(lrl,0,0);
		
		shareStart2(set.get(0),0,0,0);
		shareEnd();
		
		for(LinearRequest lr : set) {
			result.add(lr);
		}
		for(LinearRequest lr : shareRides) {
			result.add(lr);
		}
		
		
		
		return result;
	}
	
	
	//Remove all the requests in shareRides that cannot
	
		public static void shareEnd(){
			
			//list of share rides
			for(int i=0; i<shareRides.size();i++) {
				LinearRequest l = shareRides.get(i);
				
				//Checking our list of best requests
				for(int j =0;j<set.size();j++) {
					//if(l.isBefore(set.get(j))) {
					if(((l.direction == 1 && l.startPos <= set.get(j).finishPos)||(l.direction == -1 && l.startPos >= set.get(j).finishPos)) && l.pickTime <= set.get(j).finishTime) {
						//The very first
						
						boolean remove = false;
						int time = set.get(j).finishTime;
						int k = j;
						while(l.finishTime > time) {
							//if k is the last
							if(k >= set.size()-1) {
								remove = false;
								break;
							}
							
							if(l.finishTime >= set.get(k+1).pickTime) {
								if(!set.get(k).sameDirect(set.get(k+1))) {
									remove = true;
									break;
								}
								if(set.get(k).hasDeadTime(set.get(k+1))) {
									remove = true;
									break;
								}
							}
							
							
							//Remove by temp : positive
							if(l.direction == 1 && set.get(k+1).startPos < set.get(k).finishPos) {
								remove = true;
								break;
							}
							//Remove by temp : negative
							if(l.direction == -1 && set.get(k+1).startPos > set.get(k).finishPos) {
								remove = true;
								break;
							}
							
							
							
							time = set.get(k+1).finishTime;
							k++;
						}
						if(remove == false && k<set.size()-1) {
							if((l.finishTime + Math.abs(set.get(k).startPos - l.finishPos)) > set.get(k).pickTime) remove = true;
							
							if(k>=1 && l.finishTime > set.get(k-1).finishTime) {
								if(l.direction == 1 && set.get(k).startPos < set.get(k-1).finishPos)remove = true;
								if(l.direction == -1 && set.get(k).startPos > set.get(k-1).finishPos) remove = true;
							}
							
						}
						if(remove == true) {
							shareRides.remove(i);
							i--;
							break;
						}else { //if we do not remove i
							break;
						}
					}
			}
		}
	}
		

	
	
}
