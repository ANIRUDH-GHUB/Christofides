package com.sahiti.usf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Christofides {

    public static List<Edge> findMST(Graph graph) {
        return graph.kruskalMST();
    }

    public static List<Node> findOddDegreeVertices(Graph graph, List<Edge> mst) {
        Map<Node, Integer> degrees = new HashMap<>();
        for (Node node : graph.nodes) {
            degrees.put(node, 0);
        }
        for (Edge edge : mst) {
            degrees.put(edge.source, degrees.get(edge.source) + 1);
            degrees.put(edge.destination, degrees.get(edge.destination) + 1);
        }
        List<Node> oddDegreeNodes = new ArrayList<>();
        for (Map.Entry<Node, Integer> entry : degrees.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                oddDegreeNodes.add(entry.getKey());
            }
        }
        return oddDegreeNodes;
    }

    public static List<Edge> getMinimumWeightPerfectMatching(List<Node> oddDegreeNodes) {
        Graph subgraph = new Graph(oddDegreeNodes);
        subgraph.connectAllNodes();
        return subgraph.getMinimumWeightPerfectMatching();
    }

    public static List<Edge> mergeGraphs(List<Edge> list1, List<Edge> list2) {
        List<Edge> result = new ArrayList<>(list1);
        result.addAll(list2);
        return result;
    }
    // FileWriter myObj = new FileWriter("filename.txt");
    // myObj.write("start size" + " " + backup.size() + "\n");

    public static List<Node> findTSPPath(Graph graph, List<Edge> eulerTourEdges) {
        List<Node> path = new ArrayList<>();
        Node currentNode = eulerTourEdges.get(0).source;
        Iterator<Edge> iterator = eulerTourEdges.iterator();
        while (iterator.hasNext()) {
            Edge nextEdge = iterator.next();
            if (nextEdge.source == currentNode) {
                iterator.remove();
                currentNode = nextEdge.destination;
                path.add(currentNode);
                if (eulerTourEdges.isEmpty()) {
                    break;
                }
                iterator = eulerTourEdges.iterator();
            } else if (nextEdge.destination == currentNode) {
                iterator.remove();
                currentNode = nextEdge.source;
                path.add(currentNode);
                if (eulerTourEdges.isEmpty()) {
                    break;
                }
                iterator = eulerTourEdges.iterator();
            }
        }
        return path;
    }
}