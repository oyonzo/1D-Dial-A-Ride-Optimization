package Project;

import java.util.*;

import Optimal.newnewMain;

/*
 * Request class, for creating a request from data
 * startPos = starting position (coordinates)
 * finish = finish position (coordiantes)
 * pickTime is the time requested to be picked up
 */
public class Request implements Comparable<Request> {
	public double[] startPos;
	public double[] finishPos;
	public double pickTime;
	double weight; // given in time
	public double finishTime;
	static double speed = 1;
	double f_val;
	// declare the Height and Width of our metric space

	// In reality, each request can be viewed as a request of starting position,
	// finish position, and when to pick up
	public Request(double[] s, double[] f, double pt) {
		this.startPos = s;
		this.finishPos = f;
		this.pickTime = pt;
		this.weight = weight(s, f);
		this.finishTime = weight + pickTime;
		this.f_val = 0;
	}

	public static double weight(double[] s, double[] f) {
		double rweight = Graph.dist(s, f) / speed;
		return rweight;
	}

	// // calculating the distance between two points
	// public double dist(double[] a, double[] b) {
	// double sum = Math.pow((a[0] - b[0]), 2) + Math.pow((a[1] - b[1]), 2);
	// return Math.sqrt(sum);
	// }

	// f= w1x1 * w2x2 * w3x3
	// we have the best weights found, which are 0, 4 or 5, 1
	public void setf(List<Request> rl) {
		f_val = 0 * this.getX1() + 5 * this.getX2() + 1 * this.getX3(rl);
	}

	public double getX1() {
		double[] origin = new double[2];
		origin[0] = 0;
		origin[1] = 0;
		return Graph.dist(origin, this.startPos);
	}

	public double getX2() {
		return this.pickTime;
	}

	public double getX3(List<Request> rl) {
		double min = Double.MAX_VALUE;
		for (Request r : rl) {
			if (this.equals(r))
				continue;

			double distToNext = Graph.dist(r.startPos, this.finishPos);
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
		return "StartPos: " + Arrays.toString(startPos) + ", finishPos: " + Arrays.toString(finishPos) + "; ("
				+ pickTime + "," + finishTime + "); "
				+ "f-value: " + f_val;
	}

	// make a list of lists of requests in every possible permutation
	public static void permute(List<Request> lrl, int k) {
		if (k == lrl.size()) {
			ArrayList<Request> indivSeq = new ArrayList<Request>();
			for (int i = 0; i < lrl.size(); i++) {
				indivSeq.add(lrl.get(i));
				// System.out.print(" [" + lrl.get(i).toString() + "] ");

			}
			newnewMain.combinations.add(newnewMain.index, indivSeq);
			newnewMain.index++;
			// System.out.println();
		} else {
			// recursively swapping one thing each time
			for (int i = k; i < lrl.size(); i++) {
				Collections.swap(lrl, i, k);
				permute(lrl, k + 1);
				Collections.swap(lrl, i, k);
			}
		}

	}

	/*
	 * Generates a random request in the metric space
	 * 
	 * Returns:
	 * An array representing a request [startpos, finishpos, picktime]
	 */
	public static Request createRandomRequest() {
		double[] s = Graph.randPoint();
		double[] f = Graph.randPoint();
		double pt = Graph.randDouble(Graph.TLIMIT - weight(s, f), 0);

		Request request = new Request(s, f, pt);

		return request;
	}

	/*
	 * Generates a list of random requests
	 * 
	 * Returns:
	 * A list representing a request [startpos, finishpos, picktime]
	 */
	public static List<Request> createRequests(int numRequest) {
		List<Request> requests = new ArrayList<Request>();
		for (int i = 0; i < numRequest; i++) {
			requests.add(createRandomRequest());
		}
		return requests;
	}
}