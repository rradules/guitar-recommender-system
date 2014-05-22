/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import ois_guitar_recommender.Queries;

/**
 *
 * @author Roxana
 */
public class Guitar_Description {

    private String desc;
    private String name;
    private String color_material;
    private String type;
    private String size;
    private URL imageURL;

    public Guitar_Description(String desc) {
        this.desc = desc;
        name = Queries.getInstance().queryProductName(desc);
        color_material = Queries.getInstance().queryColour(desc) + " "
                + Queries.getInstance().queryMaterial(desc);
        type = Queries.getInstance().queryType(desc);
        size = Queries.getInstance().querySizeString(desc);
        try {
            imageURL = new URL(Queries.getInstance().queryImage(desc));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Guitar_Description.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getColor_material() {
        return color_material;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public URL getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "Guitar_Description{" + "name=" + name + ", color_material=" + color_material + ", type=" + type + '}';
    }
}
