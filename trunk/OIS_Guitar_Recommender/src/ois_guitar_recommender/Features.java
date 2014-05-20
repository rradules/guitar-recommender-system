package ois_guitar_recommender;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections4.bag.HashBag;

public class Features {
	public Features(HashMap<String, Integer> guitarFrequencies) {
		this.m_nr_of_features = 2;
		createFeatureMatrix();
		createFeatureFrequencies(guitarFrequencies);
	}
	
	public double probability(String hypothesis) {
		String[] features = m_feature_matrix.get(hypothesis);
		double result = 1.0;
		for (int i = 0; i < m_nr_of_features; ++i) {
			int nrOfEqualValues = m_feature_frequencies.get(i).getCount(features[i]);
			int nrOfValues = m_feature_frequencies.get(i).size();
			int nrOfDistinctValues = m_feature_frequencies.get(i).uniqueSet().size();
		
			// probability = (# values conforming to hypothesis) / (# values)
			double probability = laplaceSmoothing(nrOfEqualValues, nrOfValues, nrOfDistinctValues, 0.05);

			// MAP with Laplace Smoothing.
			result *= probability;
		}
		return result;
	}
	
	private double laplaceSmoothing(int x, int N, int d, double alpha) {
		return (x + alpha) / (N + alpha*d);
	}
	
	private void createFeatureMatrix() {
		m_feature_matrix = new HashMap<String, String[]>();
		
		///// SOME JENA MAGIC /////
		String[] guitarDescriptions = queryGuitarDescriptions();
		///// END JENA MAGIC /////
	
		//Merge queries maybe
		for (String desc : guitarDescriptions) {
			String color = queryColor(desc);
			String brand = queryBrand(desc);

			m_feature_matrix.put(desc, new String[]{color, brand});
		}
	}
	
	private void createFeatureFrequencies(HashMap<String, Integer> guitarFrequencies) {	
		m_feature_frequencies = new ArrayList<HashBag<String>>();
		
		for (int i = 0; i < m_nr_of_features; ++i) {
			m_feature_frequencies.add(new HashBag<String>());
		}

		for (String guitar : guitarFrequencies.keySet()) {
			String[] featureVector = m_feature_matrix.get(guitar);
			for (int i = 0; i < m_nr_of_features; ++i) {
				m_feature_frequencies.get(i).add(featureVector[i], guitarFrequencies.get(guitar));
			}
		}
	}
	
	private String queryColor(String desc) {
		String query = "SELECT ?color WHERE {" +
				desc + " <http://www.ois.org/guitar.owl#has_global_look> ?look." +
				"?look <http://www.ois.org/guitar.owl#has_color> ?color.}";
		
		////START JENA MAGIC////
		String color;
		if (desc.equals("a")) {
			color = "white";
		} else if (desc.equals("b")) {
			color = "white";
		} else if (desc.equals("c")) {
			color = "cherry";
		} else if (desc.equals("d")) {
			color = "brown";
		} else if (desc.equals("e")) {
			color = "white";
		} else if (desc.equals("f")) {
			color = "cherry";
		} else {
			color = "unknown";
		}
		////END JENA MAGIC////
		
		return color;
	}
	
	private String queryBrand(String desc) {
		String query = "SELECT ?brand WHERE {" + desc + " <http://www.ois.org/guitar.owl#has_brand> ?brand.}";
		
		////START JENA MAGIC////
		String brand = "Fender";
		////END JENA MAGIC////
		
		return brand;
	}
	
	private String[] queryGuitarDescriptions() {
		return new String[] {"a", "b", "c", "d", "e", "f", "g"};
	}

	private int m_nr_of_features;
	private HashMap<String, String[]> m_feature_matrix;         //Each guitar mapped to its feature vector.
	private ArrayList<HashBag<String>> m_feature_frequencies;   //Each feature index (in the feature vectors) has a bag of values.
}
