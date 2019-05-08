/* *****************************************************************************
 *  Name: Sudharaka Palamakumbura
 *  Date: 2019/05/07
 *  Description: Structure to hold the WordNet digraph
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;

public class WordNet {
        private Digraph digraph;

        // constructor takes the name of the two input files
        public WordNet(String synsets, String hypernyms) {
            if (synsets == null || hypernyms == null)
                throw new IllegalArgumentException();
        }

        // returns all WordNet nouns
        public Iterable<String> nouns() {
            return null;
        }

        // is the word a WordNet noun?
        public boolean isNoun(String word) {
            if (word == null)
                throw new IllegalArgumentException();
            return false;
        }

        // distance between nounA and nounB (defined below)
        public int distance(String nounA, String nounB) {
            if (nounA == null || nounB == null)
                throw new IllegalArgumentException();

            return 0;
        }

        // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
        // in a shortest ancestral path (defined below)
        public String sap(String nounA, String nounB) {
            if (nounA == null || nounB == null)
                throw new IllegalArgumentException();

            return null;
        }

        // do unit testing of this class
        public static void main(String[] args) {}

}
