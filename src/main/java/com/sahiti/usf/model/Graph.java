package com.sahiti.usf.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> nodes;
    List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Graph(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Node source, Node destination) {
        double distance = calculateDistance(source, destination);
        Edge edge = new Edge(source, destination, distance);
        this.edges.add(edge);
    }

    private double calculateDistance(Node source, Node destination) {
        int R = 6371; // Earth's radius in km
        double lat1 = Math.toRadians(source.getLatitude());
        double lat2 = Math.toRadians(destination.getLatitude());
        double lon1 = Math.toRadians(source.getLongitude());
        double lon2 = Math.toRadians(destination.getLongitude());
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }

    public void connectAllNodes() {
        for (int i = 0; i < nodes.size(); i++) {
            Node node1 = nodes.get(i);
            for (int j = 0; j < nodes.size(); j++) {
                if (i != j) {
                    Node node2 = nodes.get(j);
                    this.addEdge(node1, node2);
                }

            }
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}