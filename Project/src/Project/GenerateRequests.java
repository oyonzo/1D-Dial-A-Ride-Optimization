/*
 * In this program, we will experiment the different values for the weight and will 
 * attain the combination of the weight function has the greatest percentage correctness
 * 
 * We need to attain the optimal subsets by our selves
 */

package Project;

import java.util.*;

public class GenerateRequests {

	// public double[] origin;
	public static int numRequest;
	public static List<Request> set = new ArrayList<Request>();
	public static int iterations;
	// declare the Height and Width of our metric space
	private static int WIDTH = 100;
	private static int HEIGHT = 100;
	private static int TLIMIT = 24;

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
	private static double[] randPoint() {
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
	public static List<Request> createRequests() {
		List<Request> requests = new ArrayList<Request>();
		for (int i = 0; i < numRequest; i++) {
			requests.add(createRandomRequest());
		}
		return requests;
	}
}
