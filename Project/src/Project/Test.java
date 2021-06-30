package Project;

public class Test implements Comparable<Test>{
	
	public int setSize;
	public double weight1, weight2, weight3;
	
	public Test(int size, double w1, double w2, double w3){
		setSize = size;
		weight1 = w1;
		weight2 = w2;
		weight3 = w3;
	}
	
	public String toString() {
		return "The set has size of: " + setSize + " w1 = " + weight1 + " w2 = " + weight2 + " w3 = " + weight3;
	}
	
	//compares two Test objects, returns 1 if this object has a smaller set size, -1 if larger, and 0 if equal to
	//We expect to Sort from large to small
	@Override
	public int compareTo(Test t) {
		if(this.setSize > t.setSize)
			return -1;
		else if (this.setSize == t.setSize)
			return 0;
		else // smaller than
			return 1;
	}

}
