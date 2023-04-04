package com.sahiti.usf.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    List<Node> nodes;
    List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Graph(List<Node> nodes) {
        this.nodes = nodes;
        this.edges = new ArrayList<>();
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

    public List<Edge> kruskalMST() {
        List<Edge> mst = new ArrayList<>();
        Collections.sort(edges, Comparator.comparingDouble(e -> e.distance));

        Map<Node, Node> parents = new HashMap<>();
        for (Node node : this.nodes) {
            parents.put(node, node);
        }
        for (Edge edge : this.edges) {
            Node parent1 = find(edge.source, parents);
            Node parent2 = find(edge.destination, parents);
            if (parent1 != parent2) {
                mst.add(edge);
                parents.put(parent1, parent2);
            }
        }

        return mst;
    }

    public List<Edge> getMinimumWeightPerfectMatching() {
        List<Edge> result = new ArrayList<>();
        List<Node> nodes = new ArrayList<>(this.nodes);
        while (!nodes.isEmpty()) {
            Node node = nodes.remove(0);
            double minWeight = Double.MAX_VALUE;
            Node minNode = null;
            List<Node> backupNode = this.nodes;
            for (Node otherNode : nodes) {
                double weight = calculateDistance(node, otherNode);
                if (weight < minWeight) {
                    minWeight = weight;
                    minNode = otherNode;
                }
                result.add(new Edge(node, minNode, minWeight));
                backupNode.remove(minNode);
            }
            this.nodes = backupNode;
            return result;
        }
        return result;
    }

    private Node find(Node node, Map<Node, Node> parents) {
        if (parents.get(node) == node) {
            return node;
        }
        Node parent = find(parents.get(node), parents);
        parents.put(node, parent);
        return parent;
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