package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Driver extends Graph {
    public List<Request> schedule;
    public List<Request> initialSchedule; // For use in MinFValue
    private double[] position; // the coordinates of the driver
    // private static int speed = 1;
    private double currentTime = 0;
    public static double[][] locations = Graph.generateFixedList(); // Hardcoded list of possible locations (see
                                                                    // Graph.java)

    public Driver(double[] position) {
        this.schedule = new ArrayList<>();
        this.initialSchedule = new ArrayList<>();
        this.position = position;
    }

    public void clearSchedule() {
        this.schedule.clear();
    }

    /**
     * @return creates a driver at a random location
     */
    public static Driver newRandDriver() {
        Random rand = new Random();
        int index = rand.nextInt(20);
        Driver d = new Driver(locations[index]);
        return d;
    }

    /**
     * @param numDrivers the number of drivers to be generated
     * @return a list of drivers of size numDrivers
     */
    public static List<Driver> generateRandDrivers(int numDrivers) {
        List<Driver> drivers = new ArrayList<Driver>();

        for (int i = 0; i < numDrivers; i++) {
            drivers.add(newRandDriver());
        }
        return drivers;
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

    @Override
    public String toString() {
        return "Position" + Arrays.toString(this.getPosition());
    }
}
