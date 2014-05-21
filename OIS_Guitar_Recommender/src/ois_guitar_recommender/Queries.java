package ois_guitar_recommender;

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

public class Queries {

    private InfModel model;
    private final static String prefixes = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
            + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "PREFIX ois: <http://www.ois.org/guitar-group#>\n";

    public Queries() {
        String schema = "http://wilma.vub.ac.be/~yajadoul/guitars/ontology.rdf";
        String indiv = "http://wilma.vub.ac.be/~yajadoul/guitars/instances.rdf";

        OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        OntModel individuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        base.read(schema, "RDF/XML");
        base.read(indiv, "RDF/XML");
        Model union = ModelFactory.createUnion(base, individuals);
        String rules = "http://wilma.vub.ac.be/~yajadoul/guitars/rules";

        Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(rules));
        reasoner.setDerivationLogging(true);
        model = ModelFactory.createInfModel(reasoner, union);
    }

    private ResultSet query(String query) {
        ////JENA MAGIC////
        Query q = QueryFactory.create(prefixes + query);
        ResultSet results = QueryExecutionFactory.create(q, model).execSelect();

        return results;
    }

    public String queryColor(String desc) {
        String queryString = "SELECT ?color WHERE {"
                + desc + " ois:has_global_look ?look."
                + "?look ois:has_color ?color.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Resource color = result.getResource("?color");
            return color.toString();
        } else {
            return null;
        }
        ////START JENA MAGIC////
//        String color;
//        if (desc.equals("a")) {
//            color = "white";
//        } else if (desc.equals("b")) {
//            color = "white";
//        } else if (desc.equals("c")) {
//            color = "cherry";
//        } else if (desc.equals("d")) {
//            color = "brown";
//        } else if (desc.equals("e")) {
//            color = "white";
//        } else if (desc.equals("f")) {
//            color = "cherry";
//        } else {
//            color = "unknown";
//        }
        ////END JENA MAGIC////
    }

    public static String queryBrand(String desc) {
        String query = "SELECT ?brand WHERE {" + desc + " <http://www.ois.org/guitar.owl#has_brand> ?brand.}";

        ////START JENA MAGIC////
        String brand = "Fender";
        ////END JENA MAGIC////

        return brand;
    }

    public static String[] queryGuitarDescriptions() {
        String query = "SELECT ?name WHERE {?guitar ns:has_product_name ?name.}";

        ////START JENA MAGIC////
        String[] desc = new String[]{"a", "b", "c", "d", "e", "f", "g"};
        ////END JENA MAGIC////

        return desc;
    }
}
