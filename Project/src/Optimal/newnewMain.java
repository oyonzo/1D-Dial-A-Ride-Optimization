
package Optimal;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Project.Request;
import Project.LinearRequest;

public class newnewMain {
    public static int iterations;
    public static List<ArrayList<Request>> combinations = new ArrayList<ArrayList<Request>>();
    public static int index = 0;
    public static int sumSizeOPT = 0;
    public static int sumSizeALG = 0;
    public static int sumSizeCOM = 0;
    public static long sumTimeOPT = 0;
    public static long sumTimeALG = 0;
    public static long sumTimeCOM = 0;
    public static int numDrivers;
    public static int numRequests;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("How mamy random requests do you want to have: ");
        numRequests = sc.nextInt();

        System.out.print("How many iterations do you want: ");
        iterations = sc.nextInt();

        System.out.print("How many drivers do you want: ");
        numDrivers = sc.nextInt();

        sc.close();
        for (int i = 0; i < iterations; i++) {
            List<Request> rl = Request.createRequests(numRequests);

            // print the randLIst
            System.out.println("The List of RandomRequests:");
            for (Request r : rl) {
                System.out.println(r.toString());
            }

        }

    }

}