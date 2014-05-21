 /**
  * Recommender using an ontology-driven information system.
  */
package ois_guitar_recommender;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;
import java.util.ArrayList;

public class Recommender {
    
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IOException("Both the frequency database file and number of recommendations should be given.");
            } else {
                Recommender recommender = new Recommender(args[0], Integer.parseInt(args[1]));
                recommender.recommend();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Recommender(String file, int nrOfRecommendations) throws IOException {
        this.m_nr_of_recommendations = nrOfRecommendations;
        this.m_frequencies = new HashMap<>();
        
        readFrequenciesFromFile(file);
    }
    
    public String[] recommend() {
        Queries querier = new Queries();
        ArrayList<String> guitarDescriptions = querier.queryGuitarDescriptions();
        Features features = new Features(guitarDescriptions, m_frequencies, querier);
        features.addRelevantFeatures();
        
        ArrayList<ProbabilityDescriptionPair> probabilities = new ArrayList<>();
        for (String desc : guitarDescriptions) {
            if (!m_frequencies.keySet().contains(desc)) {
                probabilities.add(new ProbabilityDescriptionPair(features.probability(desc), desc));
            }
        }
        Collections.sort(probabilities);
        String[] recommendations = new String[m_nr_of_recommendations];
        for (int i = 0; i < m_nr_of_recommendations; ++i) {
            ProbabilityDescriptionPair recommendation = probabilities.get(i);
            recommendations[i] = recommendation.m_description;
            System.out.println(recommendation.m_description);
            System.out.println(recommendation.m_probability);
        }

        System.out.println("done");
        
        return recommendations;
    }
    
    private void readFrequenciesFromFile(String file) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] entry;
        while ((entry = reader.readNext()) != null) {
            if (entry.length != 2) {
                reader.close();
                throw new IOException("The number of fields in an entry is not correct.");
            }
            m_frequencies.put(entry[0], Integer.valueOf(entry[1]));
        }
        reader.close();
    }

    private class ProbabilityDescriptionPair implements Comparable<ProbabilityDescriptionPair> {
        public ProbabilityDescriptionPair(double probability, String description) {
            this.m_probability = probability;
            this.m_description = description;
        }
        
        public int compareTo(ProbabilityDescriptionPair other) {
            if (this.m_probability > other.m_probability) {
                return -1;
            } else if (this.m_probability < other.m_probability) {
                return 1;
            } else {
                return 0;
            }
        }
        
        public double m_probability;
        public String m_description;
    }
    
    private final int m_nr_of_recommendations;
    private final HashMap<String, Integer> m_frequencies;    //Each guitar mapped to its frequency.
}
