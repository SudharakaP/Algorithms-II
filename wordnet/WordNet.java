/* *****************************************************************************
 *  Name: Sudharaka Palamakumbura
 *  Date: 2019/05/07
 *  Description: Structure to hold the WordNet digraph
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordNet {
        private Digraph digraph;
        private List<String> nounList;
        private List<String> hypernymList;
        private Set<String> wordList;
        private Map<Integer, String> nounMap;
        SAP sap;

        // constructor takes the name of the two input files
        public WordNet(String synsets, String hypernyms) {
            if (synsets == null || hypernyms == null)
                throw new IllegalArgumentException();
            wordList = new HashSet<>();
            nounMap = new HashMap<>();
            nounList = new ArrayList<>(1);
            hypernymList = new ArrayList<>(1);
            In inputNouns = new In(synsets);
            In inputHypernyms = new In(hypernyms);
            while (inputNouns.hasNextLine()) {
                String line = inputNouns.readLine();
                String[] lineArray = line.split(",");
                int index = Integer.parseInt(lineArray[0]);
                String noun = lineArray[1];
                nounList.add(line);
                for (String word: noun.split(" "))
                    wordList.add(word);
                nounMap.put(index, noun);
            }
            while (inputHypernyms.hasNextLine())
                hypernymList.add(inputHypernyms.readLine());
            digraph = new Digraph(nounList.size());
            for (String hypernym: hypernymList){
                String[] vertices = hypernym.split(",");
                for (int i = 1; i < vertices.length; i++)
                    digraph.addEdge(Integer.parseInt(vertices[i]), Integer.parseInt(vertices[0]));
            }
            sap = new SAP(digraph);
        }

        // returns all WordNet nouns
        public Iterable<String> nouns() {
            return nounList;
        }

        // is the word a WordNet noun?
        public boolean isNoun(String word) {
            if (word == null)
                throw new IllegalArgumentException();
            return wordList.contains(word);
        }

        // distance between nounA and nounB
        public int distance(String nounA, String nounB) {
            if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
                throw new IllegalArgumentException();
            int nounVertexIndexA = Integer.parseInt(nounA.split(",")[0]);
            int nounVertexIndexB = Integer.parseInt(nounB.split(",")[0]);
            return sap.length(nounVertexIndexA, nounVertexIndexB);
        }

        // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
        // in a shortest ancestral path
        public String sap(String nounA, String nounB) {
            if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
                throw new IllegalArgumentException();
            int nounVertexIndexA = Integer.parseInt(nounA.split(",")[0]);
            int nounVertexIndexB = Integer.parseInt(nounB.split(",")[0]);
            return nounMap.get(sap.ancestor(nounVertexIndexA, nounVertexIndexB));
        }

        // do unit testing of this class
        public static void main(String[] args) {}

}
