package Project;

import java.util.*;
import java.util.stream.Collectors;


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
	public static double[][] locations = Graph.generateFixedList();
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

	// f= w1x1 * w2x2 * w3x3
	// we have the best weights found, which are 4,5,4
	public void setf(List<Request> rl) {
		f_val = 0 * this.getX1() + 5 * this.getX2() + 500 * this.getX3(rl);
	}
	
	// sets weights for MinFValue
	public void setf(int w1, int w2, int w3, List<Request> rl, Driver d) {
		f_val = w1 * this.getX1(d) + w2 * this.getX2() + w3 * this.getX3(rl);
	}

	public double getX1() {
		double[] origin = new double[2];
		origin[0] = 0;
		origin[1] = 0;
		return Graph.dist(origin, this.startPos);
	}
	
	public double getX1(Driver d) {
		return Graph.dist(d.getPosition(), this.startPos);
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

	/**
	 * @param a request
	 * @return true if it's the same request
	 */
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
		return "StartPos: " + Arrays.toString(startPos) + ", finishPos: " + Arrays.toString(finishPos)
				+ "; start,end: ("
				+ pickTime + "," + finishTime + "); "
				+ "f-value: " + f_val;
	}




	/*
	 * Generates a random request in the metric space
	 * 
	 * Returns:
	 * An array representing a request [startpos, finishpos, picktime]
	 */
	public static Request createRandomRequest() {
		Random rand = new Random();
		int start = rand.nextInt(20);
		double[] s = locations[start];
		int finish = rand.nextInt(20);
		while (finish==start) {
			finish = rand.nextInt(20);
		}
		double[] f = locations[finish];
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

	/**
	 * @param rl A list of Requests
	 * @return the of Requests sorted by pickTime
	 */
	public static List<Request> sortRequests(List<Request> rl) {
		List<Request> sortedList = rl.stream()
				.sorted(Comparator.comparingDouble(Request -> Request.pickTime))
				.collect(Collectors.toList());
		return sortedList;
	}
}