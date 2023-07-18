package Project;

import java.util.List;

/*
 * Request class, for creating a request from data
 * startPos = starting position (coordinates)
 * finish = finish position (coordiantes)
 * pickTime is the time requested to be picked up
 */
public class Request implements Comparable<Request> {
	double[] startPos;
	double[] finishPos;
	double pickTime;
	double weight; // given in time
	double finishTime;
	double speed = 1;
	double f_val;

	// In reality, each request can be viewed as a request of starting position,
	// finish position, and when to pick up
	public Request(double[] s, double[] f, double pt) {
		this.startPos = s;
		this.finishPos = f;
		this.pickTime = pt;
		this.weight = dist(s, f) / speed;
		this.finishTime = weight + pickTime;
		this.f_val = 0;
	}

	// calculating the distance between two points
	double dist(double[] a, double[] b) {
		double sum = Math.pow((a[1] - b[1]), 2) + Math.pow((a[2] - b[2]), 2);
		return Math.sqrt(sum);
	}

	// f= w1x1 * w2x2 * w3x3
	public void setf(double weight1, double weight2, double weight3, double input1, double input2, double input3) {
		f_val = weight1 * input1 + weight2 * input2 + weight3 * input3;
	}

	public double getX3(List<Request> rl) {
		double min = Double.MAX_VALUE;
		for (Request r : rl) {
			if (this.equals(r))
				continue;

			double distToNext = dist(r.startPos, this.finishPos);
			if (distToNext < min) {
				min = distToNext;
			}
		}
		return min;
	}

	public boolean equals(Request r) {
		if (this.startPos == r.startPos && this.finishPos == r.startPos && this.pickTime == r.pickTime)
			return true;
		return false;
	}

	// compares two linearRequest objects, returns 1 if this object has a larger f
	// value, -1 if smaller, and 0 if equal to
	@Override
	public int compareTo(Request r) {
		if (this.f_val > r.f_val)
			return 1;
		else if (this.f_val == r.f_val)
			return 0;
		else // smaller than
			return -1;
	}

	public String toString() {
		return "StartPos: " + startPos + ", finishPos: " + finishPos + "; (" + pickTime + "," + finishTime + "); "
				+ "f-value: " + f_val;
	}

}