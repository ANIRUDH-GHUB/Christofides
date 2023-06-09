package com.sahiti.usf;

import java.io.*;
import java.util.List;

import com.sahiti.usf.model.Christofides;
import com.sahiti.usf.model.Edge;
import com.sahiti.usf.model.Graph;
import com.sahiti.usf.model.Node;

public class App {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        createGraph();
    }

    public static void createGraph() {
        Graph graph = getNodesFromCSV();
        graph.connectAllNodes();
        List<Edge> mst = Christofides.findMST(graph);
        System.out.println("MST :" + mst.size());
        List<Node> oddDegrNodes = Christofides.findOddDegreeVertices(graph, mst);
        List<Edge> perfectMatchingEdges = Christofides.getMinimumWeightPerfectMatching(oddDegrNodes);
        System.out.println("PFM :" + perfectMatchingEdges.size());
        List<Node> eulerTour = Christofides.eulerTour(graph, mst, perfectMatchingEdges);
        System.out.println("EUL :" + eulerTour.size());
        List<Node> hamiltonCycle = Christofides.generateTSPtour(eulerTour);
        System.out.println("TSP :" + hamiltonCycle.size());
        System.out.println("HTL :" + Christofides.calculateTourLength(hamiltonCycle));
        List<Node> randomTour = Christofides.randomSwapOptimise(hamiltonCycle, 10);
        System.out.println("TSP :" + randomTour.size());
        List<Node> twoOptTour = Christofides.twoOpt(randomTour);
        System.out.println("TSP :" + twoOptTour.size());
        System.out.println("OTL :" + Christofides.calculateTourLength(twoOptTour));
        
    }

    public static Graph getNodesFromCSV() {
        Graph graph = new Graph();
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2023-01-metropolitan-street-backup.csv"));
            reader.readLine();
            int count = 0;
            while ((line = reader.readLine()) != null && count < 2000) {
                count += 1;
                String[] node = line.split(splitBy);
                // System.out.println(node.length);
                if(node.length>0 && !(node[0].isEmpty() || node[4].isEmpty() || node[5].isEmpty())){
                    Node n = new Node(node[0], Double.parseDouble(node[4]), Double.parseDouble(node[5]));
                    graph.addNode(n);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return Math.sqrt(distance);
    }
}
