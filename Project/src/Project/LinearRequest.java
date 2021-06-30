package Project;

import java.util.List;

/*
 * The Linear Request is a hypothetical request that only in 1D,
 * We use this for experimenting the weight function
 */
public class LinearRequest implements Comparable<LinearRequest>{
	public int startPos;
	public int finishPos;
	public int pickTime;
	public int weight; //given in time
	public int finishTime;
	public int speed = 1;
	public double f_value;
	public int direction;
	public boolean isNull;
	public boolean trans = false;
	
	//Start position is the position relevant to the origin, so the origin should always be 0
	//In reality, each request can be viewed as a request of starting position, finish position, and when to pick up
	public LinearRequest (int s, int f, int pt){
		this.startPos = s;
		this.finishPos = f;
		this.pickTime = pt;
		this.weight = Math.abs(f-s);
		this.finishTime = weight + pickTime;
		this.f_value = 0;
		if(f!=s) this.direction = (f-s)/weight;
	}
	
	//Get The size of Clustering
	//Return the negative number
	public int getX4(List<LinearRequest>lrl) {
		int counter = 0;
		
		for(LinearRequest lr : lrl) {
			//making sure we don't add itself
			if(this.equals(lr)) continue;
			
			//If there
			if(Math.abs(lr.finishTime+lr.pickTime)/2 <= this.finishTime + 6) {
				counter++;
			}
		}
		return counter * -1;
	}
	
	//This is the method that will return the minimum time to the next Request
	public int getX3(List<LinearRequest> lrl) {
			int min = 100000;
			for(LinearRequest lr : lrl) {
				//making sure we don't caluculate x3 for itself
				if(this.equals(lr)) continue;
				
				//If there's no pick up time later then the finish time, it's probably going to be a late request
				if(this.finishTime <= lr.pickTime) {
					int distToNext = Math.abs(lr.startPos - this.finishPos);
					if(distToNext < min) {
						min = distToNext;
					}
				}
			}
			return min;//If there are no later requests, we return a 100000(large number) to make it rank back
		}
	
	public int getCluster(List<LinearRequest> lrl) {
		int count = -1;
		int radius = 6;
		 
		for(LinearRequest lr : lrl) {
			//making sure we don't caluculate x3 for itself
			if(this.equals(lr)) 
				count++;
			
			//If there's no pick up time later then the finish time, it's probably going to be a late request
			if(this.finishTime <= lr.pickTime) {
				double midpoint = (lr.finishPos + lr.startPos) / 2;
				if(Math.abs(midpoint -this.finishPos) <= radius) {
					count ++;
				}
				
			}
		}
		return count;
	}
	
	//f= w1x1 * w2x2 * w3x3
	public void setf(double weight1, double weight2, double weight3, double input1, double input2, double input3) {
		f_value = weight1 * input1 + weight2 * input2 + weight3 * input3;
	}
		
	public boolean hasDeadTime(LinearRequest nextRequest) {
		return this.finishTime + Math.abs(nextRequest.startPos - this.finishPos) != nextRequest.pickTime;
	}
	
	public double getf() {
		return f_value;
	}
	
	public boolean equals(LinearRequest lr) {
		if(this.startPos == lr.startPos && this.finishPos == lr.startPos && this.pickTime == lr.pickTime) return true;
		return false;
	}
	
	public boolean isIncluded(LinearRequest r) {
		
		if(this.pickTime >= r.pickTime && this.finishTime <= r.finishTime) {
			//positive
			if(r.direction == 1 && this.sameDirect(r)) {
				if(this.startPos >= r.startPos && this.startPos <= r.finishPos) {
					if(this.finishPos >= r.startPos && this.finishPos <= r.finishPos ) {
						return true;
					}
				}
			}else if (r.direction == -1 && this.sameDirect(r)) {
				//negative
				if(this.startPos <= r.startPos && this.startPos >= r.finishPos) {
						if(this.finishPos <= r.startPos && this.finishPos >= r.finishPos ) {
							return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public String toString() {
		return "StartPos: " + startPos/6 + "h," + startPos%6*10 +  "m " + "finishPos: " + finishPos/6 + "h," + finishPos%6*10 + "m" + ", (" + pickTime/6 + ":" + pickTime%6*10 + "," + finishTime/6 + ":" + finishTime%6*10 + "); " + "f-value: " + f_value;
	}
	
	public boolean inMiddle(LinearRequest r) {
		if(this.startPos >= r.startPos && this.startPos < r.finishPos) {
			return true;
		}
		//Negative Direction
		else if(this.startPos <= r.startPos && this.startPos > r.finishPos) {
			return true;
		}
		return false;
	}
	
	public LinearRequest concat(LinearRequest first, LinearRequest second) {
		if(first.weight == 0) {
			return second;
		}else {
			return new LinearRequest(first.startPos,second.finishPos,first.pickTime);
		}
	}
	
	public boolean isEarlier(LinearRequest r) {
		return this.pickTime < r.pickTime;
	}
		
	public boolean isBefore(LinearRequest r) {
		return this.startPos < r.startPos;
	}
	
	public boolean sameDirect(LinearRequest r) {
		if(this.direction == r.direction) {
			return true;
		}
		return false;
	}
	
	public boolean include(int pos, int time) {
		int T = time - (this.pickTime + Math.abs(pos-this.startPos));
		if(this.direction == 1) {
			if(pos >= this.startPos && pos <= this.finishPos) {
				if(T<=3 && T>=0)  return true;
			}
			return false;
		}else if(this.direction == -1) {
			if(pos <= this.startPos && pos >= this.finishPos) {
				if(T<=3 && T>=0)  return true;
			}
			return false;
		}
		return false;
	}
	
	public void shift(LinearRequest r) {
		int T = this.pickTime - (r.pickTime + Math.abs(this.startPos - r.startPos));
		
		this.pickTime -= T;
		this.finishTime -= T;
		
	}
	
	public void shift2(LinearRequest r) {
		int T = this.pickTime - (r.pickTime - Math.abs(this.startPos - r.startPos));
		
		this.pickTime -= T;
		this.finishTime -= T;
		
	}
	
	
	


	//compares two linearRequest objects, returns 1 if this object has a larger f value, -1 if smaller, and 0 if equal to
	@Override
	public int compareTo(LinearRequest lr) {
		if(this.f_value > lr.f_value)
			return 1;
		else if (this.f_value == lr.f_value)
			return 0;
		else // smaller than
			return -1;
	}




}

