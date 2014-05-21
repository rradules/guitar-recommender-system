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
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import java.util.HashSet;
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

        //JENA SANDBOX

//        String schema = "http://wilma.vub.ac.be/~yajadoul/guitars/ontology.rdf";
//        String indiv = "http://wilma.vub.ac.be/~yajadoul/guitars/instances.rdf";
//        String ois = "http://www.ois.org/guitar-group#";
//
//        OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
//        OntModel individuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
//
//        base.read(schema, "RDF/XML");
//        base.read(indiv, "RDF/XML");
//        Model model = ModelFactory.createUnion(base, individuals);
//
//        String rules = "http://wilma.vub.ac.be/~yajadoul/guitars/rules";
//
//        Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(rules));
//        reasoner.setDerivationLogging(true);
//        InfModel inf = ModelFactory.createInfModel(reasoner, model);
//
//        String sparql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
//                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
//                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
//                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
//                + "PREFIX ois: <http://www.ois.org/guitar-group#>\n"
//                + "SELECT ?gd ?color WHERE {"
//                + "?gd ois:has_global_look ?look."
//                + "?look ois:has_colour ?color.}";
//
//
//        Query query = QueryFactory.create(sparql);
//        ResultSet results = QueryExecutionFactory.create(query, inf).execSelect();
//        while (results.hasNext()) {
//            QuerySolution result = results.next();
//            Literal l1 = result.getLiteral("?color");
//           // l1.
//           // Resource r1 = result.getResource("?color");
//            Resource r2 = result.getResource("?gd");
//            System.out.println(l1.getValue() + "  " + r2.toString());
//        }
        
        Queries q = new Queries();
        System.out.println(q.queryColor("<http://www.ois.org/guitar-group#GuitarDescription/Fender/Modern_Player_Jazz_Bass%C2%AE%2C_Rosewood_Fingerboard%2C_Olympic_White>"));
        System.out.println(q.queryBrand("<http://www.ois.org/guitar-group#GuitarDescription/Fender/Modern_Player_Jazz_Bass%C2%AE%2C_Rosewood_Fingerboard%2C_Olympic_White>"));
    }
}
