package Project;

import java.util.*;

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
	double speed = 1;
	double f_val;
	// declare the Height and Width of our metric space
	static int WIDTH = 100;
	static int HEIGHT = 100;
	private static int TLIMIT = 24;

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
		return this.dist(origin, this.startPos);
	}

	public double getX2() {
		return this.pickTime;
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
		return "StartPos: " + Arrays.toString(startPos) + ", finishPos: " + Arrays.toString(finishPos) + "; ("
				+ pickTime + "," + finishTime + "); "
				+ "f-value: " + f_val;
	}

	/*
	 * Generate a random double within a range
	 * 
	 * Args:
	 * rangeMax: Exclusive upper bound
	 * rangeMin: Inclusive lower bound
	 * 
	 * Returns:
	 * A random double
	 */
	private static double randDouble(double rangeMax, double rangeMin) {
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}

	/*
	 * Create a random point with an x and y value within the constraints of the
	 * metric space
	 * 
	 * Returns:
	 * An Array containing the coordinates of the point [x,y]
	 */
	static double[] randPoint() {
		double x = randDouble(WIDTH, 0);
		double y = randDouble(HEIGHT, 0);

		double[] point = new double[] { x, y };
		return point;
	}

	/*
	 * Generates a random request in the metric space
	 * 
	 * Returns:
	 * An array representing a request [startpos, finishpos, picktime]
	 */
	public static Request createRandomRequest() {
		double[] s = randPoint();
		double[] f = randPoint();
		double pt = randDouble(TLIMIT, 0);

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