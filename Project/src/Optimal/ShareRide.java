package Optimal;

import java.util.*;


import Project.LinearRequest;

public class ShareRide {
	public static List<LinearRequest> set = new ArrayList<LinearRequest>();
	public static List<LinearRequest> overlap  = new ArrayList<LinearRequest>();//Overlap
	public static List<LinearRequest> shareRides = new ArrayList<LinearRequest>(); //The LinearRequests with valid starting
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
	
	//Allowing the sharerides
	public static void shareStart(LinearRequest serving, int counter, double time, int location) {
		
		
		for(int i=0;i<overlap.size();i++) {
			LinearRequest l = overlap.get(i);
			if(l.inMiddle(serving) && l.sameDirect(serving)) {
				if(serving.pickTime + Math.abs(l.startPos-serving.startPos) == l.pickTime) {
					shareRides.add(overlap.remove(i));
					i--;
				}
			}else if(l.isEarlier(serving) && l.sameDirect(serving)) {
				if(l.pickTime + (serving.startPos- l.startPos) == serving.pickTime) {
					if(counter == 0) {
						shareRides.add(overlap.remove(i));
						i--;
					}
					if(counter!=0){
						int j = counter-1;
						while(j >= 0) {
							if(l.pickTime > set.get(j).pickTime && l.sameDirect(set.get(j)) && l.pickTime + (serving.startPos- l.startPos) == serving.pickTime) {
								shareRides.add(overlap.remove(i));
								i--;
								break;
							}
							if(!l.sameDirect(set.get(j))) break;
							j--;
						}
					}
					
				}
			}
		}
		
		counter++;
		time = serving.finishTime;
		location = serving.finishPos;
		if(counter == set.size()) return;
		shareStart(set.get(counter),counter,time,location);
	}
	
	//Allowing the sharerides but with 30 mins buffer-zone
	public static void shareStart2(LinearRequest serving, int counter, double time, int location) {
		
		
		for(int i=0;i<overlap.size();i++) {
			LinearRequest l = overlap.get(i);
			if(l.inMiddle(serving) && l.sameDirect(serving)) {
				int T = l.pickTime - (serving.pickTime + Math.abs(l.startPos-serving.startPos));
				if(serving.pickTime + Math.abs(l.startPos-serving.startPos) >= l.pickTime - 3) {
					if(T>=3) T = 3;
					l.pickTime -= T;
					l.finishTime -= T;
					shareRides.add(l);
					overlap.remove(i);
					i--;
				}
			}else if(l.isBefore(serving) && l.sameDirect(serving)) {
				if(counter == 0) {
					int T = serving.pickTime - Math.abs(serving.startPos- l.startPos);
					if(T <= l.pickTime && l.pickTime-T <= 3){
						if(T>=3) T=3;
						l.pickTime -= T;
						l.finishTime -= T;
						shareRides.add(l);
						overlap.remove(i);
						i--;
					}
				}else{
						int j = counter-1;
						while(j >= 0) {
							//Find the request serving when l happens
							if(l.startPos > set.get(j).startPos && l.sameDirect(set.get(j))) {
								int T = Math.abs(l.startPos - set.get(j).startPos);
								if(set.get(j).pickTime + T >= l.pickTime - 3) {
									if(T>=3) T=3;
									l.pickTime -= T;
									l.finishTime -= T;
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
		shareStart(set.get(counter),counter,time,location);
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
		
		for(int i=0; i<shareRides.size();i++) {
			LinearRequest l = shareRides.get(i);
			
			for(int j =0;j<set.size();j++) {
				if(l.isBefore(set.get(j))) {
					int k = j;
					while(l.finishTime > set.get(k).finishTime) {
						if(k>=set.size()-1) break;
						k++;
					}
					
					if(Math.abs(l.finishTime - set.get(k).pickTime) < Math.abs(l.finishPos - set.get(k).startPos)) {
						shareRides.remove(i);
						i--;
						break;
					}
					
				}
				
			}
		}
	}
		

	
	
}
