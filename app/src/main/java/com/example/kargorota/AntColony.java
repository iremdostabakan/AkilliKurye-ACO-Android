package com.example.kargorota;

import java.util.ArrayList;
import java.util.List;


public class AntColony {

    private static final double[][] DISTANCES = {
            {0.0, 3.2, 5.1, 4.8, 3.5, 2.0, 3.0, 1.5, 2.2, 3.8, 4.5, 5.5, 6.0, 12.0},
            {3.2, 0.0, 2.5, 2.1, 1.5, 1.8, 2.0, 2.2, 1.0, 1.5, 2.0, 3.5, 4.0, 10.0},
            {5.1, 2.5, 0.0, 0.5, 1.2, 3.5, 2.8, 4.0, 3.0, 1.8, 1.5, 2.5, 3.0, 11.5},
            {4.8, 2.1, 0.5, 0.0, 1.0, 3.2, 2.5, 3.8, 2.8, 1.5, 1.2, 2.2, 2.8, 11.0},
            {3.5, 1.5, 1.2, 1.0, 0.0, 2.0, 1.5, 2.5, 1.8, 0.8, 1.0, 3.0, 3.5, 10.5},
            {2.0, 1.8, 3.5, 3.2, 2.0, 0.0, 1.2, 1.5, 1.0, 2.5, 3.0, 4.5, 5.0, 11.0},
            {3.0, 2.0, 2.8, 2.5, 1.5, 1.2, 0.0, 2.0, 1.5, 1.8, 2.2, 4.0, 4.5, 10.8},
            {1.5, 2.2, 4.0, 3.8, 2.5, 1.5, 2.0, 0.0, 1.2, 3.0, 3.5, 4.8, 5.2, 11.5},
            {2.2, 1.0, 3.0, 2.8, 1.8, 1.0, 1.5, 1.2, 0.0, 2.0, 2.5, 4.0, 4.5, 10.5},
            {3.8, 1.5, 1.8, 1.5, 0.8, 2.5, 1.8, 3.0, 2.0, 0.0, 0.8, 2.8, 3.2, 10.2},
            {4.5, 2.0, 1.5, 1.2, 1.0, 3.0, 2.2, 3.5, 2.5, 0.8, 0.0, 2.0, 2.5, 9.5},
            {5.5, 3.5, 2.5, 2.2, 3.0, 4.5, 4.0, 4.8, 4.0, 2.8, 2.0, 0.0, 1.0, 8.0},
            {6.0, 4.0, 3.0, 2.8, 3.5, 5.0, 4.5, 5.2, 4.5, 3.2, 2.5, 1.0, 0.0, 7.5},
            {12.0, 10.0, 11.5, 11.0, 10.5, 11.0, 10.8, 11.5, 10.5, 10.2, 9.5, 8.0, 7.5, 0.0}
    };

    private final String[] locationNames = {
            "Kampüs (Çıkış)", "Hastane", "Liman AVM", "İDO Terminali", "Meydan",
            "Paşakonak", "Belediye", "Sevgi Yolu", "Sunullah", "Hacıyusuf",
            "Levent", "600 Evler", "Otogar", "Pazar Yeri"
    };

    public String calculateRoute(List<Integer> selectedNodes) {
        if (selectedNodes.isEmpty()) return "Please select points.";

        List<Integer> remainingNodes = new ArrayList<>(selectedNodes);
        StringBuilder routeResult = new StringBuilder();
        double totalDistance = 0.0;
        int currentNode = 0;

        routeResult.append(locationNames[currentNode]);

        while (!remainingNodes.isEmpty()) {
            int nearestNode = -1;
            double minDistance = Double.MAX_VALUE;

            for (int target : remainingNodes) {
                double distance = DISTANCES[currentNode][target];
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestNode = target;
                }
            }

            totalDistance += minDistance;
            currentNode = nearestNode;
            routeResult.append(" ->\n").append(locationNames[currentNode]);
            remainingNodes.remove(Integer.valueOf(currentNode));
        }

        totalDistance += DISTANCES[currentNode][0];
        routeResult.append(" ->\n").append(locationNames[0]).append(" (Return)");

        return "🚚 OPTIMIZED ROUTE:\n\n" + routeResult.toString() +
                "\n\n📍 Total: " + String.format("%.1f", totalDistance) + " km";
    }
}