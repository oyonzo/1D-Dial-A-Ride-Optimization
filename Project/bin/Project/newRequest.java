package Project;

import java.util.*;

public class newRequest {
	
	public String startPos;
	public String finishPos;
	public int pickTime;
	public int dropTime;
	public String date;
	public int travelTime;
	
	
	/*
	 * Constructor, takes in start pos, finish pos, pickup time, if the user input the pickup time or drop
	 * off time, the date they want to be picked up
	 */
	public newRequest(String s, String f, int ptDt, boolean method, String d, int edgeWeight) {
		if(method ==true) {
			this.startPos = s;
			this.finishPos = f;
			this.travelTime = edgeWeight;
			
			this.pickTime = ptDt;
			this.dropTime = ptDt + edgeWeight;
			
			this.date = d;	
		}else { //method is false, user has entered a dropoff time
			
			this.startPos = s;
			this.finishPos = f;
			this.travelTime = edgeWeight;
			
			this.dropTime = ptDt;
			this.pickTime = ptDt-edgeWeight;
			
			this.date = d;
		}
	}
	
	public int getX3(List<newRequest> lrl) {
		int min = Integer.MAX_VALUE;
		for(newRequest r:lrl) {
			if(this.equals(r)) continue;
			
			//api call?
			int distToNext = 1;//dist(r.startPos)
			if(distToNext < min) {
				min = distToNext;
			}
		}
		
		return min;
	}

//	double min = Double.MAX_VALUE;
//	for(Request r : rl) {
//		if(this.equals(r)) continue;
//		
//		double distToNext = dist(r.startPos,this.finishPos);
//		if(distToNext < min) {
//			min = distToNext;
//		}
//	}
//	return min;
}
