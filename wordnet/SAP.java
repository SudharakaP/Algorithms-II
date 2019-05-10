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
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        this.digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return ancestorAndMinLength(v, w)[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestorAndMinLength(v, w)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return ancestorAndMinLength(v, w)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return ancestorAndMinLength(v, w)[1];
    }

    private int[] ancestorAndMinLength(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        for (Integer item: v)
            if (item == null)
                throw new IllegalArgumentException();
        for (Integer item: w)
            if (item == null)
                throw new IllegalArgumentException();
        int numVertices = digraph.V();
        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(digraph, w);
        int minLength = Integer.MAX_VALUE;
        int result = -1;
        for (int i = 0; i < numVertices; i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                int cur = pathV.distTo(i) + pathW.distTo(i);
                if (cur < minLength) {
                    minLength = cur;
                    result = i;
                }
            }
        }

        if (minLength == Integer.MAX_VALUE) {
            minLength = -1;
            result = -1;
        }
        int[] ancestorAndMinLengthArray = {minLength, result};
        return ancestorAndMinLengthArray;
    }

    private int[] ancestorAndMinLength(int v, int w) {
        int numVertices = digraph.V();
        if (v >= numVertices || w >= numVertices)
            throw new IllegalArgumentException();
        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(digraph, w);
        int minLength = Integer.MAX_VALUE;
        int result = -1;
        for (int i = 0; i < numVertices; i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                int cur = pathV.distTo(i) + pathW.distTo(i);
                if (cur < minLength) {
                    minLength = cur;
                    result = i;
                }
            }
        }

        if (minLength == Integer.MAX_VALUE) {
            minLength = -1;
            result = -1;
        }
        int[] ancestorAndMinLengthArray = {minLength, result};
        return ancestorAndMinLengthArray;
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
