package Project;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    List<Request> schedule;
    private double[] position;

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
}