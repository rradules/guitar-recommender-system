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

       
        //String ns_vcard = "http://www.w3.org/2006/vcard/ns#";
        String ns_guitar = "http://www.ois.org/guitar-group#";


// create the reasoning model using the base
        // 
     
        //   OntModel model_res = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF, model);
//        String rule = " [SiblingRule:"
//                + "(?a ?r ?b),"
//                + "isLiteral(?b),"
//                + "(?a ?s ?c),"
//                + "isLiteral(?c),"
//                + "notEqual(?r,?s),"
//                + "strConcat(?r, ?t),"
//                + "regex(?t, 'http://www\\.ois\\.org/guitar-group#.*')"
//                + "strConcat(?s, ?u),"
//                + "regex(?u, 'http://www\\.ois\\.org/guitar-group#.*')"
//                + "->"
//                + "(?r http://www.ois.org/guitar-group#sibling ?s),"
//                + "(?s http://www.ois.org/guitar-group#sibling ?r)"
//                + "]";
      

        String sparql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX ns: <http://www.ois.org/guitar-group#>\n"
                + "SELECT ?subject ?object \n"
                + "	WHERE { ?subject ns:sibling ?object}";

      
//        while (results.hasNext()) {
//            QuerySolution result = results.next();
//            Resource r1 = result.getResource("?subject");
//            Resource r2 = result.getResource("?object");
//            System.out.println(r1.toString() + "  " + r2.toString());
//        }
    }
}
