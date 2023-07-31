package Project;

import java.util.*;

public class MultiDriverSBP {
    int TLIMIT = Graph.TLIMIT;

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

    public static Map<Request, Integer> rltoHash(List<Request> rl) {
        Map<Request, Integer> map = new HashMap<>();

        // Loop to generate keys "r1", "r2", "r3", ...
        for (int i = 0; i <= rl.size(); i++) {
            Request request = rl.get(i);
            if(map.containsKey(request){
                map.replace(request, map.get(request) + 1);
            }
            else{
                map.put(request, 1);
            }
        }
        return map;
    }

    // TODO: Make it so that we work with maps of requests instead of lists of
    // requests
    public static List<Driver> assignRequests(List<Driver> drivers, Map<Request, Integer> requests) {
        int largestSched = 0;
        Driver largestDriver = null;
        List<Request> lrl = new ArrayList<Request>(requests.keySet());
        for (Driver d : drivers) {
            List<Request> currSched = Driver.getOptimalforDriver(lrl, d)
            int currSchedSize = currSched.size();
            if (currSchedSize > largestSched) {
                largestSched = currSchedSize;
                largestDriver = d;
                d.schedule = currSched;
            }

        }
        return ;
    }
    // driver and finds the longest one and then removes the requests in the set as
    // well as the driver
    // ^^ perhaps this would take a hashmap of requests and a list of drivers as its
    // input
}
