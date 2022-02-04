package Project;
import java.util.*;



public class Algorithm {
	
	public double speed = 35; //estimated 35 mph

	
	void sortRequest(double T, double[] orgin, ArrayList<Request> requests) {
		
		double t =0;
		double[] a = orgin;
		
		for(int i = 0; i<requests.size(); i++) {
			if (requests.get(i).pickTime > T) {
				requests.remove(i);
			}
			//compute arrival time
			double arrivalTime = t + requests.get(i).weight;
			if(arrivalTime > requests.get(i).pickTime) {
				requests.remove(i);
			}
		}
	}
	
}
