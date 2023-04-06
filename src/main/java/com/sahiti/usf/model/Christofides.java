package com.sahiti.usf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static List<Node> eulerTour(Graph graph, List<Edge> mst, List<Edge> perfEdges) {
        List<Node> eulerTour = new ArrayList<>();
        List<Edge> combinEdges = new ArrayList<>(mst);
        combinEdges.addAll(perfEdges);
        Map<Node, List<Node>> adjacenyMatrix = graph.adjacencyMatrix();
        Set<Node> visited = new HashSet<>();
        Node startNode = combinEdges.get(0).source;
        dfs(startNode, eulerTour, combinEdges, visited, adjacenyMatrix);
        return eulerTour;
    }

    public static List<Node> generateTSPtour(List<Node> eulerTour) {
        List<Node> hamiltonList = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        for (Node node : eulerTour) {
            if (!visited.contains(node)) {
                visited.add(node);
                hamiltonList.add(node);
            }
        }
        return hamiltonList;
    }

    private static void dfs(Node node, List<Node> eulerTour, List<Edge> edges, Set<Node> visited,
            Map<Node, List<Node>> adjacenyMatrix) {

        visited.add(node);
        for (Node v : adjacenyMatrix.get(node)) {
            if (!visited.contains(v)) {
                eulerTour.add(node);
                eulerTour.add(v);

                Edge matchedEdge = edges.get(0);
                for (Edge edge : edges) {
                    if (edge.source.crimeId == node.crimeId && edge.destination.crimeId == v.crimeId) {
                        matchedEdge = edge;
                        break;
                    }
                }
                edges.remove(matchedEdge);
                dfs(v, eulerTour, edges, visited, adjacenyMatrix);
            }
        }
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