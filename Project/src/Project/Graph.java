package Project;

import java.util.*;

public class Graph {

	static int WIDTH = 100;
	static int HEIGHT = 100;
	static int TLIMIT = 150;

	public static double dist(double[] a, double[] b) {
		double sum = Math.pow((a[0] - b[0]), 2) + Math.pow((a[1] - b[1]), 2);
		return Math.sqrt(sum);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<double[]> points = new ArrayList<double[]>();
		points = generateRandPoints(20);
		for (double[] p : points) {
			System.out.println(Arrays.toString(p));

		}
		System.out.println(generateFixedList());
	}

	/*
	 * Create a random point with an x and y value within the constraints of the
	 * metric space
	 * 
	 * Returns:
	 * An Array containing the coordinates of the point [x,y]
	 */
	public static double[] randPoint() {
		double x = Graph.randDouble(42.4, 0);
		double y = Graph.randDouble(42.4, 0);

		double[] point = new double[] { x, y };
		return point;
	}

	/**
	 * @param amount number of points to generate
	 * @return a list containing amount number of points
	 */
	public static List<double[]> generateRandPoints(int amount) {
		List<double[]> points = new ArrayList<double[]>();
		for (int i = 0; i < amount; i++) {
			points.add(randPoint());
		}
		return points;
	}

	public static double[][] generateFixedList(){
		double[][] coordinatesList = new double[][] {
            {10.962229830586004, 0.9439729881256506},
            {22.165535883352796, 29.53270781117363},
            {37.33011717132071, 26.373511002047405},
            {21.246204778898193, 36.64766052587953},
            {2.6238602849973187, 40.69015712051377},
            {22.704608370595356, 38.64631471180244},
            {8.012079927442816, 33.08897605983439},
            {31.075387422145145, 18.867498939145417},
            {27.56424598311243, 18.141349866825912},
            {9.853250978158638, 18.19206186629683},
            {6.498811170561941, 29.151853831575337},
            {28.892212783770375, 42.25182445486328},
            {33.712027864019625, 6.31506735439976},
            {21.597675610248814, 36.75223736147819},
            {30.399461087266303, 36.689079802511756},
            {10.79996349371082, 29.46797529967659},
            {24.1370456387911, 29.404562311381152},
            {17.94590574781268, 18.499175392496355},
            {39.13189328128983, 33.639049058051015},
            {1.1030136099127195, 14.376809041999099}
		};
		return coordinatesList;
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
	static double randDouble(double rangeMax, double rangeMin) {
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}

}
