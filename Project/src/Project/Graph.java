package Project;

public class Graph {

	static int WIDTH = 100;
	static int HEIGHT = 100;
	static int TLIMIT = 24;

	public static double dist(double[] a, double[] b) {
		double sum = Math.pow((a[0] - b[0]), 2) + Math.pow((a[1] - b[1]), 2);
		return Math.sqrt(sum);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	 * Create a random point with an x and y value within the constraints of the
	 * metric space
	 * 
	 * Returns:
	 * An Array containing the coordinates of the point [x,y]
	 */
	static double[] randPoint() {
		double x = Request.randDouble(WIDTH, 0);
		double y = Request.randDouble(HEIGHT, 0);

		double[] point = new double[] { x, y };
		return point;
	}

}
