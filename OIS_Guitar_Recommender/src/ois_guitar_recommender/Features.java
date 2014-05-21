package ois_guitar_recommender;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections4.bag.HashBag;

public class Features {
    public Features(HashMap<String, Integer> guitarFrequencies) {
        this.m_nr_of_features = 2;
        this.m_guitar_frequencies = guitarFrequencies;
        createFeatureMatrix();
        createFeatureFrequencies();
    }

    public double probability(String hypothesis) {
        ArrayList<String> features = m_feature_matrix.get(hypothesis);
        double result = 1.0;
        for (int i = 0; i < m_nr_of_features; ++i) {
            int nrOfEqualValues = m_feature_frequencies.get(i).getCount(features.get(i));
            int nrOfValues = m_feature_frequencies.get(i).size();
            int nrOfDistinctValues = m_feature_frequencies.get(i).uniqueSet().size();

            //probability = (# values conforming to hypothesis) / (# values)
            double probability = laplaceSmoothing(nrOfEqualValues, nrOfValues, nrOfDistinctValues, 0.05);

            //MAP with Laplace Smoothing.
            result *= probability;
        }
        return result;
    }

    public void addRelevantFeatures() {
        double[] relevance = getRelevancePerFeature();

        //Only for color.
        if (relevance[0] >= 0.7) {
            //This should be more elaborate, since we know that 'color' only has one sibling,
            //and that 'material' is not optional when color is given.
            HashBag<String> frequencies = new HashBag<>();
            ++m_nr_of_features;
            for (String desc : m_feature_matrix.keySet()) {                
                String query = "SELECT ?sibling WHERE {" +
                        desc + " ns:has_global_look ?look." +
                        "ns:has_color ns:sibling ?r" +
                        "?look ?r ?sibling.}";
                
                ArrayList<String> featureVector = m_feature_matrix.get(desc);
                for (String sibling : Queries.query(query)) {
                    featureVector.add(sibling);
                    frequencies.add(sibling, m_guitar_frequencies.get(desc));
                }
            }
            m_feature_frequencies.add(frequencies);
        }
    }
 
    private void createFeatureMatrix() {
        m_feature_matrix = new HashMap<>();
        
        ///// SOME JENA MAGIC /////
        String[] guitarDescriptions = Queries.queryGuitarDescriptions();
        ///// END JENA MAGIC /////
        
        //Merge queries maybe
        for (String desc : guitarDescriptions) {
            ArrayList<String> featureVector = new ArrayList<>();
            
            featureVector.add(Queries.queryColor(desc));
            featureVector.add(Queries.queryBrand(desc));
            
            m_feature_matrix.put(desc, featureVector);
        }
    }
    
    private void createFeatureFrequencies() {
        m_feature_frequencies = new ArrayList<>();
        
        for (int i = 0; i < m_nr_of_features; ++i) {
            m_feature_frequencies.add(new HashBag<String>());
        }
        
        for (String guitar : m_guitar_frequencies.keySet()) {
            ArrayList<String> featureVector = m_feature_matrix.get(guitar);
            for (int i = 0; i < m_nr_of_features; ++i) {
                m_feature_frequencies.get(i).add(featureVector.get(i), m_guitar_frequencies.get(guitar));
            }
        }
    }
    
    private double[] getRelevancePerFeature() {
        double[] entropies = new double[m_nr_of_features];
        for (int i = 0; i < m_nr_of_features; ++i) {
            HashBag<String> values = m_feature_frequencies.get(i);
            double entropy = 0.0;
            double total = values.size();
            for (String value : values.uniqueSet()) {
                double probability = (values.getCount(value) / total);
                entropy -= probability * Math.log(probability);
            }
            entropies[i] = 1 - entropy; //A feature with a low entropy is relevant.
        }
        return entropies;
    }
       
    private static double laplaceSmoothing(int x, int N, int d, double alpha) {
        return (x + alpha) / (N + alpha*d);
    }
    
    private int m_nr_of_features;
    private final HashMap<String, Integer> m_guitar_frequencies;
    private HashMap<String, ArrayList<String>> m_feature_matrix;         //Each guitar mapped to its feature vector.
    private ArrayList<HashBag<String>> m_feature_frequencies;            //Each feature index (in the feature vectors) has a bag of values.
}
