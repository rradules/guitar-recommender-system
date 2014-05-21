package ois_guitar_recommender;

public class Queries {
    public static String queryColor(String desc) {
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
        String[] desc = new String[] {"a", "b", "c", "d", "e", "f", "g"};
        ////END JENA MAGIC////
        
        return desc;
    }
}
