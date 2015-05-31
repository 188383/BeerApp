package project.pwr.database;

import org.json.JSONObject;

/**
 * Created by pawel on 25.05.15.
 */
class MyException extends Exception{
    String message;
    public MyException(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
public class Country {
    private JSONObject country;
    private int id;
    private String countryName;
    private String countryCode;

    public Country(){}
    public Country(JSONObject country)throws MyException,Exception{
        this.id = (int)country.getInt("id");
        this.countryName = (String)country.getString("country_name");
        this.countryCode = (String)country.getString("country_code");
    }
    public Country(int id, String name, String code){
        this.id = id;
        this.countryName = name;
        this.countryCode = code;
    }
    public int getId(){
        return this.id;
    }
    public String getCountryCode(){
        return this.countryCode;
    }
    public String getCountryName(){
        return this.countryName;
    }

}