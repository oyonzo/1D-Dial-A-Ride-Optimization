package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Driver extends Point {
    List<Request> schedule;
    private double[] position;
    private static int speed = 1;
    private double currentTime = 0;

    public Driver(double[] position) {
        this.schedule = new ArrayList<>();
        this.position = position;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double time) {
        currentTime = time;
    }

    public double[] getPosition() {
        return this.position;
    }

    public void setPosition(double[] new_position) {
        this.position = new_position;
    }

    public static double[] generateRandomPosition() {
        double[] res = new double[2];
        Random rand = new Random();

        double x = rand.nextDouble(100); // Generate first double
        double y = rand.nextDouble(100);

        res[0] = x;
        res[1] = y;

        return res;

    }

    <<<<<<<HEAD

    public Driver(double[] position) {
        this.schedule = new ArrayList<>();
        this.position = position;
=======

    /**
     * @return creates a driver at a random location
     */
    public Driver newRandDriver() {
        Driver d = new Driver();
        d.location = Request.randPoint();
        return d;
    }

    /**
     * @param numDrivers the number of drivers to be generated
     * @return a list of drivers of size numDrivers 
     */
    public List<Driver> generateRandDrivers(int numDrivers) {
        List<Driver> drivers = new ArrayList<Driver>();
            for(int i = 0; i < numDrivers; i++;){
                drivers.add(newRandDriver());
<<<<<<< HEAD
            }
            return drivers;
>>>>>>> e8c7fde (Added method to generate a list of random drivers)
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double time) {
        currentTime = time;
    }

    public double[] getPosition() {
        return this.position;
    }

    public void setPosition(double[] new_position) {
        this.position = new_position;
    }

    public static double[] generateRandomPosition() {
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
        System.out.println(Arrays.toString(Driver.generateRandomPosition()));
    }

}=======

    public static void main(String[] args) {
        double[] origin = new double[2];
        origin[0] = 0;
        origin[1] = 0;
        Driver test = new Driver(origin);
        System.out.println(Arrays.toString(Driver.generateRandomPosition()));
    }

    }>>>>>>>955ef d5(Added a method for
    random driver)
    
    

    
    

    

    