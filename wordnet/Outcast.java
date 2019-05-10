/* *****************************************************************************
 *  Name: Sudharaka Palamakumubura
 *  Date: 2019/05/07
 *  Description: Class for Outcast detection.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int maxDistance = 0;
        String maxDistanceNoun = null;
        for (String noun: nouns) {
            int distance = distance(noun, nouns);
            if (distance > maxDistance) {
                maxDistance = distance;
                maxDistanceNoun = noun;
            }
        }
        return maxDistanceNoun;
    }

    private int distance(String noun, String[] nounSet) {
        int distance = 0;
        for (String nounSetElement: nounSet)
            distance += wordNet.distance(nounSetElement, noun);
        return distance;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}