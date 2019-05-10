/* *****************************************************************************
 *  Name: Sudharaka Palamakumbura
 *  Date: 2019/05/07
 *  Description: Structure to hold the WordNet digraph
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private final Map<String, List<Integer>> nounsMap;
    private final Map<Integer, String> nounsByInteger;
    private final SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        In synsetsInput = new In(synsets);
        In hypernymsInput = new In(hypernyms);
        nounsMap = new HashMap<>();
        nounsByInteger = new HashMap<>();

        int synsetCount = 0;
        while (synsetsInput.hasNextLine()) {
            String synset = synsetsInput.readLine();
            String[] synsetArray = synset.split(",");
            String[] nouns = synsetArray[1].split(" ");
            synsetCount++;

            for (String noun : nouns) {
                List<Integer> nounsList = nounsMap.get(noun);
                if (nounsList == null)
                    nounsMap.put(noun, new ArrayList<>());
                nounsMap.get(noun).add(Integer.valueOf(synsetArray[0]));
            }

            nounsByInteger.put(Integer.valueOf(synsetArray[0]), synsetArray[1]);
        }
        Digraph digraph = new Digraph(synsetCount);

        while (hypernymsInput.hasNextLine()) {
            String hypernym = hypernymsInput.readLine();
            String[] hypernymArray = hypernym.split(",");
            Integer index = Integer.valueOf(hypernymArray[0]);
            for (int i = 1; i < hypernymArray.length; i++)
                digraph.addEdge(index, Integer.parseInt(hypernymArray[i]));

        }
        this.sap = new SAP(digraph);
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle())
            throw new IllegalArgumentException();
        int count = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0)
                count++;
        }
        if (count != 1)
            throw new IllegalArgumentException();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return nounsMap.containsKey(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return sap.length(nounsMap.get(nounA), nounsMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return nounsByInteger.get(sap.ancestor(nounsMap.get(nounA), nounsMap.get(nounB)));
    }

    public static void main(String[] args) {
        // no unit tests for this one :)
    }
}
