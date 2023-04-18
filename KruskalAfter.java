package kruskalafter;

/**
 *
 * @author Lenovo
 */
import java.util.*;
import java.lang.*;
import java.io.*;

class Graph {

    // A class to represent a graph edge
    class Edge {

        int src, dest;
        double weight;

        public Edge(int src, int dest, double weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public Edge() {
            super();
        }

        public Edge(Edge edge) {
            this.src = edge.src;
            this.dest = edge.dest;
            this.weight = edge.weight;
        }

        public void displayEdge() {
            System.out.println("source = " + src + " , destination= " + dest + " , weight= " + weight);
        }

    }

    // A class to represent a subset for union-find
    class subset {

        int parent, rank;
    }

    int V, E; // V  no. of vertices & E  no.of edges
    Edge edge[]; // all edges

    // Creates a graph with V vertices and E edges
    Graph(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i) {
            edge[i] = new Edge();
        }
    }

    int find(subset subsets[], int i) {

        if (subsets[i].parent != i) {
            subsets[i].parent
                    = find(subsets, subsets[i].parent);
        }

        return subsets[i].parent;
    }

    // A function that does union of two sets of x and y 
    void Union(subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank
                < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank
                > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
        } else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // The main function to construct MST using Kruskal's
    // algorithm
    void KruskalMST() {

        sortEdges();

        // This will store the resultant MST
        Edge result[] = new Edge[V];

        // An index variable, used for result[]
        int e = 0;

        // An index variable, used for sorted edges
        int i = 0;
        for (i = 0; i < V; ++i) {
            result[i] = new Edge();
        }

        //  Sort all the edges in non-decreasing
        // order of their weight.  
        subset subsets[] = new subset[V];
        for (i = 0; i < V; ++i) {
            subsets[i] = new subset();
        }

        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0; // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < V - 1) {
            //  Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }

        }

        // print the contents of result[] to display
        // the built MST
        System.out.println("Following are the edges in "
                + "the constructed MST and Bucket Sort");
        double minimumCost = 0;
        for (i = 0; i < e; ++i) {
            System.out.println(result[i].src + " -- "
                    + result[i].dest
                    + " == " + result[i].weight);
            minimumCost += result[i].weight;
        }
        System.out.printf("Minimum Cost Spanning Tree : %.4f%n  ",
                minimumCost);

    }

// Testing on small number of edges
//    public void fillGraph_1() {
//        edge[0] = new Edge(0, 1, 0.4);
//        edge[1] = new Edge(0, 3, 0.1);
//        edge[2] = new Edge(1, 3, 0.3);
//        edge[3] = new Edge(1, 2, 0.2);
//        edge[4] = new Edge(1, 9, 0.5);
//        edge[5] = new Edge(2, 4, 0.6);
//        edge[6] = new Edge(2, 5, 0.7);
//        edge[7] = new Edge(3, 7, 0.8);
//        edge[8] = new Edge(3, 9, 0.9);
// 
//    }
    public void displayEdges() {
        for (int i = 0; i < E; i++) {
            edge[i].displayEdge();
        }
    }

    public void fillGraph_random() {
        int count = 0;
        for (int i = 0; i < V && count < E; i++) {
            for (int j = i + 1; j < V && count < E; j++) {

                edge[count++] = new Edge(i, j, Math.random());

            }
        }
    }

    public void sortEdges() {
        bucketSort(edge, edge.length);

    }
//
//    public void callRunningTime() {
//
//        System.out.println("The running time in nano seconds is " + duration);
//    }

    // Function to sort arr[] of size n
    // using bucket sort
    void bucketSort(Edge arr[], int n) {
        if (n <= 0) {
            return;
        }

        // 1) Create n empty buckets
        Edge[][] buckets = new Edge[n][n];
        int nb[] = new int[n];
        // 2) Put array elements in different buckets
        for (int i = 0; i < n; i++) {
            double idx = arr[i].weight * n;

            buckets[(int) idx][nb[(int) idx]] = arr[i];
            nb[(int) idx]++;
        }

        // 3) Sort individual buckets
        for (int i = 0; i < n; i++) {
            if (nb[i] > 0) {
                insertionsort(buckets[i], nb[i]);
            }
        }

        // 4) Concatenate all buckets into arr[]
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < nb[i]; j++) {
                arr[index++] = buckets[i][j];
            }
        }
    }

    void insertionsort(Edge arr[], int n) {
        for (int i = 0; i < n; ++i) {
            Edge key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].weight > key.weight) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }

    }

}

public class KruskalAfter {

    public static void main(String[] args) {

//        int V   // Number of vertices in graph
//        int E  // Number of edges in graph
        Graph graph = new Graph(21, 200);
        graph.fillGraph_random();

        System.out.println("before kruskal");

        graph.displayEdges();
        System.out.println("###########");
        System.out.println("");

        System.out.println("After kruskal with bucekt");

        double startTime = System.nanoTime();
        graph.KruskalMST();
        double duration = System.nanoTime() - startTime;

        System.out.println("###########");

        graph.displayEdges();
        System.out.println("");
        System.out.println("the runing time in nano seconed is : " + duration / 1000000 + "ms");
    }
}
