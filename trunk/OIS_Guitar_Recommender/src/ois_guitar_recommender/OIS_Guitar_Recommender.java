/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ois_guitar_recommender;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphUtil;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.Iterator;

/**
 *
 * @author Roxana
 */
public class OIS_Guitar_Recommender {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String file = "guitar_new.owl";
        String ns_vcard = "http://www.w3.org/2006/vcard/ns#";
        String ns_guitar = "http://www.ois.org/guitar.owl#";

        OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        
        base.read(file, "RDF/XML");

// create the reasoning model using the base
        OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF, base);

        Graph graph = inf.getGraph();

        System.out.println(graph.size());

        String sparql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT ?subject ?object\n"
                + "	WHERE { ?subject rdfs:subClassOf ?object }";

        Query query = QueryFactory.create(sparql);
        ResultSet results = QueryExecutionFactory.create(query, inf).execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.next();
            
            Resource individual = result.getResource("?subject");
            System.out.println(individual.toString());
        }
//
//        Iterator it = GraphUtil.findAll(inf.getGraph());
//        while (it.hasNext()) {
//            System.out.println("triple from OntModel " + it.next().toString());
//        }
    }
}
