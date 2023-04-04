package com.sahiti.usf.model;

public class Edge {
    Node source;
    Node destination;
    double distance;

    public Edge(Node source, Node destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

}
