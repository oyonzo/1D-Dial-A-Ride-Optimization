package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Optimal.newnewMain;

public class Driver extends Graph {
    List<Request> schedule;
    private double[] position;
    private static int speed = 1;
    private double currentTime = 0;

    public Driver(double[] position) {
        this.schedule = new ArrayList<>();
        this.position = position;
    }

    /**
     * @return creates a driver at a random location
     */
    public Driver newRandDriver() {
        Driver d = new Driver(Graph.randPoint());
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

    // public static double[] generateRandomPosition() {
    // double[] res = new double[2];
    // Random rand = new Random();

    // double x = rand.nextDouble(100); // Generate first double
    // double y = rand.nextDouble(100);

    // res[0] = x;
    // res[1] = y;

    // return res;

    // }

    public static List<Request> getOptimalforDriver(List<Request> lrl, Driver driver) {
    
        // gives us a list of list of requests, in every combination possible
        Request.permute(lrl, 0);
        // j is each set of requests
        for (int j = 0; j < newnewMain.combinations.size(); j++) {
            double time = 0;
            // TODO think of what origins should be because we now have multiple drivers
            // with different origins
            double[] origin = driver.getPosition()
            // i is a request in a set
            for (int i = 0; i < newnewMain.combinations.get(j).size(); i++) {
                if (newnewMain.combinations.get(j).get(i).pickTime < time) {
                    newnewMain.combinations.get(j).remove(i);
                    i--;
                    continue;
                }
                if (time + Graph.dist(newnewMain.combinations.get(j).get(i).startPos,origin) > newnewMain.combinations.get(j)
                        .get(i).pickTime) {
                    newnewMain.combinations.get(j).remove(i);
                    i--;
                }
                time = newnewMain.combinations.get(j).get(i).finishTime;
                origin = newnewMain.combinations.get(j).get(i).finishPos;
            }
        }
    
        int maxIndex = -1;
        int maxSize = Integer.MIN_VALUE;
    
        for (int i = 0; i < newnewMain.combinations.size(); i++) {
            List<Request> l = newnewMain.combinations.get(i);
            if (l.size() > maxSize) {
                maxSize = l.size();
                maxIndex = i;
            }
        }
    
        return newnewMain.combinations.get(maxIndex);
    }

    public static void main(String[] args) {
        double[] origin = new double[2];
        origin[0] = 0;
        origin[1] = 0;
        Driver test = new Driver(origin);
        System.out.println(Arrays.toString(Graph.randPoint()));
    }
}
