package Project;

public class Point {
	
	
	
	public static double dist(double[] a, double[] b) {
		double sum = Math.pow((a[0] - b[0]), 2) + Math.pow((a[1] - b[1]), 2);
		return Math.sqrt(sum);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
