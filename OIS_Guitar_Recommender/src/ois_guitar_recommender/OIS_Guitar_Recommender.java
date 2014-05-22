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
//
//        String schema = "http://wilma.vub.ac.be/~yajadoul/guitars/ontology_sizes2.rdf";
//        String indiv = "http://wilma.vub.ac.be/~yajadoul/guitars/instances.rdf";
//        String ois = "http://www.ois.org/guitar-group#";
//
//        OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
//        OntModel individuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_TRANS_INF);
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

        String sparql = "SELECT ?guitar ?thing WHERE {"
                + "?guitar a ois:Guitar_Description."
                + "?guitar a ?thing }";

//        String sparql = "SELECT ?guitar WHERE {"
//                + "?guitar rdfs:subClassOf ois:Guitar_Description }";


        ResultSet res = Queries.getInstance().query(sparql);

        while (res.hasNext()) {
            QuerySolution result = res.next();
            //Literal l1 = result.getLiteral("?color");
            Resource r1 = result.getResource("?guitar");
            Resource r2 = result.getResource("?thing");
            System.out.println(r1.toString()+ "   " + r2.toString());
        }

        // InfModel model = q.getModel();
        //  model.

        //System.out.println(q.queryColour("<http://www.ois.org/guitar-group#GuitarDescription/Fender/Modern_Player_Jazz_Bass%C2%AE%2C_Rosewood_Fingerboard%2C_Olympic_White>"));
        // System.out.println(q.queryBrand("<http://www.ois.org/guitar-group#GuitarDescription/Fender/Modern_Player_Jazz_Bass%C2%AE%2C_Rosewood_Fingerboard%2C_Olympic_White>"));
    }
}
