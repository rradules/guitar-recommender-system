package ois_guitar_recommender;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections4.bag.HashBag;

import com.hp.hpl.jena.query.ResultSet;


public class Features {
    public Features(ArrayList<String> guitarDescriptions, HashMap<String, Integer> guitarFrequencies) {
        this.m_nr_of_features = 3;
        this.m_guitar_descriptions = guitarDescriptions;
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
                String queryString = "SELECT ?sibling WHERE {" +
                        desc + " ois:has_global_look ?look." +
                        "ois:has_colour ois:sibling ?r." +
                        "?look ?r ?sibling.}";
                
                ArrayList<String> featureVector = m_feature_matrix.get(desc);
                ResultSet results = Queries.getInstance().query(queryString);
                while (results.hasNext()) {
                    String sibling = results.next().getLiteral("?sibling").getString();
                    featureVector.add(sibling);
                    if (m_guitar_frequencies.containsKey(desc)) {
                        frequencies.add(sibling, m_guitar_frequencies.get(desc));
                    }
                }
            }
            m_feature_frequencies.add(frequencies);
        }
        
        //Print relevance per feature.
        relevance = getRelevancePerFeature();
        for (int i = 0; i < relevance.length; ++i) {
            System.out.print(i);
            System.out.print("\t");
            System.out.println(relevance[i]);
        }
    }
 
    private void createFeatureMatrix() {
        m_feature_matrix = new HashMap<>();

        //Merge queries maybe?
        for (String desc : m_guitar_descriptions) {
            ArrayList<String> featureVector = new ArrayList<>();
            
            featureVector.add(Queries.getInstance().queryColour(desc));
            featureVector.add(Queries.getInstance().queryBrand(desc));
            featureVector.add(Queries.getInstance().queryType(desc));

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
        double[] relevance = new double[m_nr_of_features];
        for (int i = 0; i < m_nr_of_features; ++i) {
            HashBag<String> values = m_feature_frequencies.get(i);
            double entropy = 0.0;
            double total = values.size();
            for (String value : values.uniqueSet()) {
                double probability = (values.getCount(value) / total);
                entropy -= probability * Math.log(probability);
            }
            if (values.uniqueSet().size() > 1) {
                relevance[i] = 1 - entropy / Math.log(values.uniqueSet().size()); //A feature with a low entropy is relevant.
            } else {
                relevance[i] = 1;
            }
        }
        return relevance;
    }
       
    private static double laplaceSmoothing(int x, int N, int d, double alpha) {
        return (x + alpha) / (N + alpha*d);
    }
    
    private int m_nr_of_features;
    private final ArrayList<String> m_guitar_descriptions;
    private final HashMap<String, Integer> m_guitar_frequencies;
    private HashMap<String, ArrayList<String>> m_feature_matrix;         //Each guitar mapped to its feature vector.
    private ArrayList<HashBag<String>> m_feature_frequencies;            //Each feature index (in the feature vectors) has a bag of values.
}
