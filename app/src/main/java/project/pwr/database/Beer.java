package project.pwr.database;

import org.json.JSONObject;

/**
 * Created by pawel on 25.05.15.
 */
public class Beer {
    private int id;
    private String brand;
    private String flavour;
    private String type;
    private String brewer;
    private String country;

    public Beer(JSONObject beer)throws Exception{
      //  id = beer.getInt("id");
        brand = beer.getString("brand");
        flavour = beer.getString("flavour");
        type = beer.getString("type");
        brewer = beer.getString("brewer");
        country = beer.getString("country");
    }

    public int getId(){
        return this.id;
    }

    public String getBrand(){
        return this.brand;
    }

    public String getFlavour(){
        return this.flavour;
    }

    public String getType(){
        return this.type;
    }

    public String getBrewer(){
        return this.brewer;
    }

    public String getCountry(){
        return this.country;
    }
}
