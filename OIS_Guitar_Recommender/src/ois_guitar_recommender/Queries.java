package ois_guitar_recommender;

import java.util.ArrayList;

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

public class Queries {

    private static Queries singleton = null;
    private InfModel model;
    private final static String prefixes = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
            + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "PREFIX ois: <http://www.ois.org/guitar-group#>\n";

    private Queries() {
        String schema = "http://wilma.vub.ac.be/~yajadoul/guitars/ontology.rdf";
        String indiv = "http://wilma.vub.ac.be/~yajadoul/guitars/instances.rdf";

        OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        OntModel individuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        base.read(schema, "RDF/XML");
        individuals.read(indiv, "RDF/XML");
        Model union = ModelFactory.createUnion(base, individuals);
        String rules = "http://wilma.vub.ac.be/~yajadoul/guitars/rules";

        Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(rules));
        reasoner.setDerivationLogging(true);
        model = ModelFactory.createInfModel(reasoner, union);
    }

    public static Queries getInstance() {
        if (singleton == null) {
            singleton = new Queries();
        }
        return singleton;
    }

    public InfModel getModel() {
        return model;
    }

    public ResultSet query(String query) {
        Query q = QueryFactory.create(prefixes + query);
        ResultSet results = QueryExecutionFactory.create(q, model).execSelect();

        return results;
    }

    public String queryColour(String desc) {
        String queryString = "SELECT ?color WHERE {"
                + desc + " ois:has_global_look ?look."
                + "?look ois:has_colour ?color.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Literal color = result.getLiteral("?color");
            return color.getString();
        } else {
            return null;
        }
    }

    public String queryBrand(String desc) {
        String queryString = "SELECT ?brand WHERE {"
                + desc + " ois:has_brand ?brand.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Literal brand = result.getLiteral("?brand");
            return brand.getString();
        } else {
            return null;
        }
    }

    public String queryType(String desc) {
        String queryString = "SELECT ?type WHERE {"
                + desc + " ois:has_type ?type.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Literal type = result.getLiteral("?type");
            return type.getString();
        } else {
            return null;
        }
    }

    public ArrayList<String> queryGuitarDescriptions() {
        String queryString = "SELECT ?desc WHERE {?desc a ois:Guitar_Description.}";

        ArrayList<String> descriptions = new ArrayList<>();
        ResultSet results = query(queryString);
        while (results.hasNext()) {
            QuerySolution result = results.next();
            Resource desc = result.getResource("?desc");
            descriptions.add("<" + desc.toString() + ">");
        }

        return descriptions;
    }

    public String queryProductName(String desc) {
        String queryString = "SELECT ?name WHERE {"
                + desc + " ois:has_product_name ?name.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Literal name = result.getLiteral("?name");
            return name.getString();
        } else {
            return null;
        }
    }

    public String queryMaterial(String desc) {
        String queryString = "SELECT ?material WHERE {"
                + desc + " ois:has_global_look ?look."
                + "?look ois:has_material ?material.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Literal material = result.getLiteral("?material");
            return material.getString();
        } else {
            return null;
        }
    }
    
    public String queryImage(String desc) {
        String queryString = "SELECT ?image WHERE {"
                + desc + " ois:has_image ?image.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Literal image = result.getLiteral("?image");
            return image.getString();
        } else {
            return null;
        }
    }
    
    public String querySizeString(String desc) {
        String queryString = "SELECT ?type WHERE {"
                + desc + " a ?type."
                + "?type rdfs:subClassOf ois:Guitar_Description.}";

        ResultSet results = query(queryString);
        if (results.hasNext()) {
            QuerySolution result = results.next();
            Resource type = result.getResource("?type");
            String [] size = type.toString().split("_");
            return size[size.length-1];
        } else {
            return null;
        }
    }
}
