package project.pwr.database;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pawel on 26.05.15.
 */
public class DecodeClass implements Runnable {
    Context context;
    JSONArray countries;
    JSONArray beers;
    JSONArray locations;
    JSONArray mapping;
    boolean success;
    public DecodeClass(Context context){
        this.context = context;
    }

    public void decodeData(String data) throws Exception{
        JSONObject obj = new JSONObject(data);

        success=obj.getBoolean("success");
        if(success==true) {
            if(obj.has("countries")) {
                this.countries = obj.getJSONArray("countries");
                Log.d("Countries","YES");
            }else{
                this.countries = new JSONArray();
            }
            this.beers = obj.getJSONArray("beers");
            this.locations=obj.getJSONArray("locations");
            this.mapping = obj.getJSONArray("mapping");
            startUpdate();
        }else{
            throw new MyException("USER EXISTS");

        }

    }
    public boolean getSuccess(){
        return this.success;
    }
    public void startUpdate() throws Exception{
        Log.d("Position","before start");
        new Thread(this).start();
    }
    public void run(){
        Log.d("RUn","IN RUN");
        BeerDBHelper helper = new BeerDBHelper(context);
        Country c = null;
        for(int i=0;i<countries.length();i++){
          try{
              c = new Country(countries.getJSONObject(i));
              helper.insertCountry(c.getId(),c.getCountryName(),c.getCountryCode());
          }catch(Exception e){}
        }
        Log.d("RUn","IN RUN");
        Beer b = null;
        for(int i=0;i<beers.length();i++){
            try{
                Log.d("RUn","IN RUN");
                b = new Beer(beers.getJSONObject(i));
                helper.insertBeer(b);
                Log.d("Location","Beers");
            }catch(Exception e){ Log.d("beers",e.getMessage());}
        }
        Log.d("RUn","IN RUN");
        Location l= null;
        for(int i=0;i<locations.length();i++){
            try{
                l = new Location(locations.getJSONObject(i));
                helper.insertLocation(l.getName(),l.getLat(),l.getLon());

            }catch(Exception e){ Log.d("locations",e.getMessage());}
        }
        Log.d("RUn","IN RUN");
        Mapping m = null;
        for(int i=0;i<mapping.length();i++){
            try{
                m = new Mapping(mapping.getJSONObject(i));
                helper.insertMapping(m.getBrand(),m.getFlavour(),m.getShop(),m.getLat(),m.getLon());

            }catch(Exception e){
                Log.d("Mapping",e.getMessage());
            }
        }
        helper.close();
    }
}
