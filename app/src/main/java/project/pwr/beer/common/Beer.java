package project.pwr.beer.common;

/**
 * Created by Kamil on 2015-05-22.
 */
public class Beer {
    private int id ;
    private String beerName;
    private String company;
    private String beetType;
    private double beerPower;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBeerName() {
        return beerName;
    }
    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getBeerType() {
        return beetType;
    }
    public void setBeetType(String beetType) {
        this.beetType = beetType;
    }
    public double getBeerPower() {
        return beerPower;
    }
    public void setBeerPower(double beerPower) {
        this.beerPower = beerPower;
    }
}
