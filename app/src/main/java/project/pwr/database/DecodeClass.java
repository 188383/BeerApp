package project.pwr.database;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pawel on 26.05.15.
 */
public class DecodeClass implements Runnable {
    Context context;
    JSONArray countries;
    JSONArray beers;
    boolean success;
    public DecodeClass(Context context){
        this.context = context;
    }

    public void decodeData(String data) throws Exception{
        JSONObject obj = new JSONObject(data);
        success=obj.getBoolean("success");
        if(success==true) {
            this.countries = obj.getJSONArray("countries");
            this.beers = obj.getJSONArray("beers");
            startUpdate();
        }else{
            throw new MyException("USER EXISTS");

        }

    }
    public boolean getSuccess(){
        return this.success;
    }
    public void startUpdate() throws Exception{
        new Thread(this).start();
    }
    public void run(){

        BeerDBHelper helper = new BeerDBHelper(context);
        Country c = null;
        for(int i=0;i<countries.length();i++){
          try{
              c = new Country(countries.getJSONObject(i));
              helper.insertCountry(c.getId(),c.getCountryName(),c.getCountryCode());
          }catch(Exception e){}
        }
        Beer b = null;
        for(int i=0;i<beers.length();i++){
            try{
                b = new Beer(beers.getJSONObject(i));
                helper.insertBeer(b);
            }catch(Exception e){}
        }
    }
}
