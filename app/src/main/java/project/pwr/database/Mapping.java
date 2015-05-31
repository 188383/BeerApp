package project.pwr.database;

import org.json.JSONObject;

/**
 * Created by pawel on 31.05.15.
 */
public class Mapping {
    private String brand;
    private String flavour;
    private String shop;
    private String lat;
    private String lon;

    public Mapping(JSONObject obj) throws Exception{
        this.setBrand(obj.getString("brand"));
        this.setFlavour(obj.getString("flavour"));
        this.setShop(obj.getString("shopname"));
        this.setLat(obj.getString("lat"));
        this.setLon(obj.getString("lon"));

    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
