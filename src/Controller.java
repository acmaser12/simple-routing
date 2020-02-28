/*

Adam Maser
CSC 420 Week 7
2.28.2020

Controller.java
Main execution file for program.

 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {

    // array of city names
    private String[] cityNames = new String[]{"Hollywood", "San Francisco", "Salt Lake City", "Albuquerque", "Seattle",
            "Calgary", "Helena", "Winnipeg", "Denver", "Dallas", "Duluth", "Kansas City", "Chicago", "Montreal",
            "Atlanta", "New Orleans", "New York", "Washington", "Miami"};
    // create hashmap to house cities and their ID
    // this makes it easy to place cities and get IDs
    private HashMap<String, Integer> cities = getCityMap(cityNames);  // there are 19 cities in project doc


    public static void main(String[] args) {
        Controller program = new Controller();
        program.start();
    }

    private void start() {
        // output necessary info
        System.out.println("Submitted by Adam Maser  - masera1@csp.edu");
        System.out.println("I certify that this is my own work.\n");

        // read graph from file
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        try {
            edges = loadEdges();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found, exiting program...");
            System.exit(2);
        }

        // construct weighted graph
        WeightedGraph<Integer> weightedGraph = new WeightedGraph<>(edges, 19);

        // print cities and their ids
        System.out.println("Please select a Starting and Destination city: <Enter the city number>");
        for (int j = 0; j < cityNames.length; j++) {
            System.out.println(j + " > " + cityNames[j]);
        }

        // get info from user
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter starting city: ");
        int source = in.nextInt();
        System.out.println("Please enter destination city: ");
        int end = in.nextInt();

        // get MST from user's choice of source to choice of destination
        WeightedGraph.ShortestPathTree path = weightedGraph.getShortestPath(source);
        // get list of vertices from path
        ArrayList foundPath = path.getPath(end);
        // loop through list and print path
        System.out.print("A path from " + cityNames[source] + " to " + cityNames[end] + ":");
        for (int i = foundPath.size(); i > 0;  i--) {
            System.out.print(" " + cityNames[(int) foundPath.get(i-1)]);
        }
        System.out.print(" (cost " + path.getCost(end) + ")");

    }

    private ArrayList<WeightedEdge> loadEdges() throws FileNotFoundException {
        Scanner in = new Scanner(new File("edges.txt"));
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        while (in.hasNextLine()) {
            // split input from file
            String[] info = in.nextLine().split("\\|");
            // get the id of the city from the hashmap of cities
            int u = cities.get(info[0]);
            int v = cities.get(info[1]);
            edges.add(new WeightedEdge(u, v, Integer.parseInt(info[2])));
        }
        return edges;
    }

    private HashMap<String, Integer> getCityMap(String[] cityNames) {
        HashMap<String, Integer> cities = new HashMap<>();
        // loop through array and add to map
        for (int i = 0; i < 19; i++) { // there are 19 cities in this project (hardcoded value)
            cities.put(cityNames[i], i);
        }

        return cities;
    }
}
