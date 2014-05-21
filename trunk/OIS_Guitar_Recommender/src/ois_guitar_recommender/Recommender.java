 /**
  * Recommender using an ontology-driven information system.
  */
package ois_guitar_recommender;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

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
        this.m_recommendations = nrOfRecommendations;
        this.m_frequencies = new HashMap<>();
        
        readFrequenciesFromFile(file);
    }
    
    public String[] recommend() {
        Features features = new Features(m_frequencies);
        double probability = features.probability("e");
        System.out.println(probability);
        probability = features.probability("f");
        System.out.println(probability);
        
        return new String[0];
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
    
    private final int m_recommendations;
    private final HashMap<String, Integer> m_frequencies;    //Each guitar mapped to its frequency.
}
