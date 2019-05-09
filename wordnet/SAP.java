/* *****************************************************************************
 *  Name: Sudharaka Palamakumbura
 *  Date: 2019/05/07
 *  Description: Structure to hold the Shortest Ancestral Path
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths breadthFirstDirectedPaths = new BreadthFirstDirectedPaths(digraph, v);
        if (breadthFirstDirectedPaths.hasPathTo(w))
            return breadthFirstDirectedPaths.distTo(w);
        else
            return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths g1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths g2 = new BreadthFirstDirectedPaths(digraph, w);
        int vertex = digraph.V();
        int minLength = Integer.MAX_VALUE;
        int result = -1;
        for (int i = 0; i < vertex; i++) {
            if (g1.hasPathTo(i) && g2.hasPathTo(i)) {
                int cur = g1.distTo(i) + g2.distTo(i);
                if (cur < minLength) {
                    minLength = cur;
                    result = i;
                }
            }
        }
        if (minLength == Integer.MAX_VALUE) return -1;
        return result;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths breadthFirstDirectedPaths = new BreadthFirstDirectedPaths(digraph, v);
        int lengthShortest = Integer.MAX_VALUE;
        for (Integer vertex: w){
            if (breadthFirstDirectedPaths.hasPathTo(vertex) && breadthFirstDirectedPaths.distTo(vertex) < lengthShortest)
                    lengthShortest = breadthFirstDirectedPaths.distTo(vertex);
        }
        if (lengthShortest == Integer.MAX_VALUE)
            return -1;
        else
            return lengthShortest;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths BFDPathsFromV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths BFDPathsFromW = new BreadthFirstDirectedPaths(digraph, w);
        int numberOfVertices = digraph.V();
        int minLength = Integer.MAX_VALUE;
        int result = -1;
        for (int i = 0; i < numberOfVertices; i++) {
            if (BFDPathsFromV.hasPathTo(i) && BFDPathsFromW.hasPathTo(i)) {
                int cur = BFDPathsFromV.distTo(i) + BFDPathsFromW.distTo(i);
                if (cur < minLength) {
                    minLength = cur;
                    result = i;
                }
            }
        }
        if (minLength == Integer.MAX_VALUE) return -1;
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
