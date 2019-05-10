/* *****************************************************************************
 *  Name: Sudharaka Palamakumbura
 *  Date: 2019/05/07
 *  Description: Structure to hold the WordNet digraph
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WordNet {
    private Digraph digraph;
    private List<Synset> synsetsList;
    private Map<String, Integer> nounsMap;
    private Map<Integer, String> nounsByInteger;
    SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        In synsetsInput = new In(synsets);
        In hypernymsInput = new In(hypernyms);
        synsetsList = new ArrayList<>();

        while(synsetsInput.hasNextLine()) {
            String synset = synsetsInput.readLine();
            String[] synsetArray = synset.split(",");
            String[] nouns = synsetArray[1].split(" ");
            Synset synsetObject = new Synset(Integer.parseInt(synsetArray[0]), Arrays.asList(nouns), synsetArray[2]);
            synsetsList.add(synsetObject);

            for (String noun: nouns) {
                nounsMap.put(noun, Integer.parseInt(synsetArray[0]));
                nounsByInteger.put(Integer.parseInt(synsetArray[0]), noun);
            }
        }
        this.digraph = new Digraph(synsetsList.size());

        while(hypernymsInput.hasNextLine()){
            String hypernym = hypernymsInput.readLine();
            String[] hypernymArray = hypernym.split(",");
            Integer index = Integer.parseInt(hypernymArray[0]);
            List<Synset> hypernymsForSynset = new ArrayList<>();
            for (int i = 1; i < hypernymArray.length; i++){
                hypernymsForSynset.add(synsetsList.get(Integer.parseInt(hypernymArray[i])));
                digraph.addEdge(i, index);
            }
            synsetsList.get(index).hypernyms = hypernymsForSynset;
        }
        this.sap = new SAP(digraph);
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

    private class Synset {
        int id;
        List<String> nouns;
        String gloss;
        List<Synset> hypernyms;

        public Synset (int id, List<String> nouns, String gloss) {
            this.id = id;
            this.nouns = nouns;
            this.gloss = gloss;
            this.nouns = new ArrayList<>();
            this.hypernyms = new ArrayList<>();
        }

        public void addHypernym(Synset synset) {
            hypernyms.add(synset);
        }

        public List<Synset> getHypernyms() {
            return hypernyms;
        }

        public List<String> getNouns() {
            return nouns;
        }
    }

    public static void main(String[] args) {
    }
}
