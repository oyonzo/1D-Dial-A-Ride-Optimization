package Project;

import java.util.*;

public class MultiDriverSBP {
    int TLIMIT = Graph.TLIMIT;
    static List<ArrayList<Request>> combinations = new ArrayList<ArrayList<Request>>();
    public static int index = 0;

    /**
     * Filters out requests that are not in the specified start and end time
     */
    public static List<Request> timeFilter(List<Request> rl, double start, double end) {
        List<Request> rlsorted = Request.sortRequests(rl);
        for (Request r : rl) {
            // FIXME: this runs more checks that it needs to
            // (after a certain point, the pickTimes will all be greater than start)
            if (r.pickTime < start) {
                rlsorted.remove(r);
            } else if (start <= r.pickTime && end < r.finishTime) {
                rlsorted.remove(r);
            } else if (end < r.pickTime) {
                rlsorted.remove(r);
            }
        }
        return rl;
    }

    // public static Map<Request, Integer> rltoHash(List<Request> rl) {
    // Map<Request, Integer> map = new HashMap<>();

    // // Loop to generate keys "r1", "r2", "r3", ...
    // for (int i = 0; i <= rl.size(); i++) {
    // Request request = rl.get(i);
    // if(map.containsKey(request){
    // map.replace(request, map.get(request) + 1);
    // }
    // else{
    // map.put(request, 1);
    // }
    // }
    // return map;
    // }

    // requests
    // public static Driver assignSingleDriver(List<Driver> drivers, List<Request>
    // requests) {
    // int largestSched = 0;
    // Driver largestDriver = null;
    // for (Driver d : drivers) {
    // List<Request> currSched = Driver.getOptimalforDriver(requests, d);
    // int currSchedSize = currSched.size();
    // if (currSchedSize > largestSched) {
    // largestSched = currSchedSize;
    // largestDriver = d;
    // d.schedule = currSched;
    // }
    // for (Request r : currSched) {
    // requests.remove(r);
    // }
    // drivers.remove(largestDriver);
    // }
    // return largestDriver;
    // }
    /*
     * gives every permutation of a list of requests (lrl) k should start at 0 to be
     * a counter for recursion
     */
    public static void permute(List<Request> lrl, int k) {

        if (k == lrl.size()) {
            ArrayList<Request> indivSeq = new ArrayList<Request>();
            for (int i = 0; i < lrl.size(); i++) {
                indivSeq.add(lrl.get(i));
                // System.out.print(" [" + lrl.get(i).toString() + "] ");

<<<<<<< HEAD
        // Loop to generate keys "r1", "r2", "r3", ...
        for (int i = 0; i <= rl.size(); i++) {
            Request request = rl.get(i);
            if(map.containsKey(request)){
                map.replace(request, map.get(request) + 1);
=======
>>>>>>> 12b528ce7978ce43d7a3f38b9e0f1d47a0d35745
            }
            combinations.add(index, indivSeq);
            index++;
            // System.out.println();
        } else {
            // recursively swapping one thing each time
            for (int i = k; i < lrl.size(); i++) {
                Collections.swap(lrl, i, k);
                permute(lrl, k + 1);
                Collections.swap(lrl, i, k);
            }
        }

    }

    public static List<Request> getOptimal(List<Request> lrl) {

        // gives us a list of list of requests, in every combination possible
        permute(lrl, 0);
        for (int j = 0; j < combinations.size(); j++) {
            int time = 0;
            for (int i = 0; i < combinations.get(j).size(); i++) {
                if (combinations.get(j).get(i).pickTime < time) {
                    combinations.get(j).remove(i);
                    i--;
                    continue;
                }

            }
        }

        int maxIndex = -1;
        int maxSize = Integer.MIN_VALUE;

        for (int i = 0; i < combinations.size(); i++) {
            List<Request> l = combinations.get(i);
            if (l.size() > maxSize) {
                maxSize = l.size();
                maxIndex = i;
            }
        }
<<<<<<< HEAD
        assignRequests(drivers, requests);
=======

        return combinations.get(maxIndex);
>>>>>>> 12b528ce7978ce43d7a3f38b9e0f1d47a0d35745
    }

}

// driver and finds the longest one and then removes the requests in the set as
// well as the driver
// ^^ perhaps this would take a hashmap of requests and a list of drivers as its
// input
