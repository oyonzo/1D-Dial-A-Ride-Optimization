package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Driver extends Point{
    List<Request> schedule;
    private double[] position;
    private static int speed=1;

    public Driver(double[] position) {
        this.schedule = new ArrayList<>();
        this.position = position;
    }
    
    public double[] getPosition() {
    	return this.position;
    }
    
    public void setPosition(double[] new_position) {
    	this.position = new_position;
    }
    
    public double[] generateRandomPosition() {
    	double[] res = new double[2];
		Random rand = new Random();

        double x = rand.nextDouble(100); // Generate first double
        double y = rand.nextDouble(100);
        
        res[0] = x;
        res[1] = y;
        
        return res;
        
    }
    public static void main(String[] args) {
    	double[] origin = new double[2];
    	origin[0] = 0;
    	origin[1] = 0;
    	Driver test = new Driver(origin);
    	System.out.println(Arrays.toString(test.generateRandomPosition()));
    }
    
}