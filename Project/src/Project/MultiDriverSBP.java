package Project;

import java.util.List;

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
    // TODO: Make a method to convert the list of requests into a hashmap
    // TODO: Make a function that recursively compares the optimal sets for each
    // driver and finds the longest one and then removes the requests in the set as
    // well as the driver
    // ^^ perhaps this would take a hashmap of requests and a list of drivers as its
    // input
}
