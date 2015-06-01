package project.pwr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

/**
 * Created by pawel on 25.05.15.
 */
public class BeerDBHelper extends SQLiteOpenHelper {

    public static abstract class Beers implements BaseColumns {
        public static final String TABLE_NAME = "Beers";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_BRAND = "brand";
        public static final String COLUMN_NAME_FLAVOUR = "flavour";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_BREWER = "brewer";
        public static final String COLUMN_NAME_COUNTRY = "country";
    }
        /*
            countries table
         */
    public static abstract class Countries implements BaseColumns{
        public static final String TABLE_NAME = "Countries";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_COUNTRY_NAME = "country_name";
        public static final String COLUMN_NAME_COUNTRY_CODE = "country_code";
    }
        /*
            users table
         */
    public static abstract class Users implements BaseColumns{
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL= "email";
    }

    public static abstract class Locations implements BaseColumns{
        public static final String TABLE_NAME = "Locations";
        public static final String COLUMN_NAME_SHOPNAME = "shopname";
        public static final String COLUMN_NAME_LAT = "latitude";
        public static final String COLUMN_NAME_LON = "longitude";
       // public static final String COLUMN_NAME_ID = "id";
    }

    public static abstract class Mapping implements BaseColumns{
        public static final String TABLE_NAME = "Mapping";
        public static final String COLUMN_NAME_BEER_ID = "id_beer";
        public static final String COLUMN_NAME_SHOP_ID = "id_location";
    }

        /*
            The set up methods that are used to create the database

         */
    private static final String CREATE_COUNTRIES = "CREATE TABLE "+Countries.TABLE_NAME+"("+
        Countries._ID +" INTEGER NOT NULL PRIMARY KEY,"+
        Countries.COLUMN_NAME_COUNTRY_NAME+" TEXT NOT NULL,"+
        Countries.COLUMN_NAME_COUNTRY_CODE+ " TEXT NOT NULL)";

    private static final String CREATE_BEERS = "CREATE TABLE "+Beers.TABLE_NAME+"("+
        Beers._ID+" INTEGER NOT NULL PRIMARY KEY,"+
        Beers.COLUMN_NAME_BRAND+" TEXT NOT NULL,"+
        Beers.COLUMN_NAME_FLAVOUR+" TEXT NOT NULL," +
        Beers.COLUMN_NAME_TYPE + " TEXT DEFAULT NULL," +
        Beers.COLUMN_NAME_BREWER + " TEXT NOT NULL," +
        Beers.COLUMN_NAME_COUNTRY + " TEXT NOT NULL,"+
        "FOREIGN KEY ( "+ Beers.COLUMN_NAME_COUNTRY+" ) REFERENCES " +Countries.TABLE_NAME+"("+
        Countries.COLUMN_NAME_COUNTRY_NAME+"))";

    private static final String CREATE_USERS = "CREATE TABLE "+Users.TABLE_NAME+"("+
         Users._ID + " INTEGER NOT NULL PRIMARY KEY,"+
         Users.COLUMN_NAME_USERNAME+ " TEXT NOT NULL,"+
         Users.COLUMN_NAME_EMAIL+" TEXT NOT NULL)";

    private static final String CREATE_LOCATIONS = "CREATE TABLE "+ Locations.TABLE_NAME+"("+
            Locations._ID+" INTEGER PRIMARY KEY,"+
            Locations.COLUMN_NAME_SHOPNAME+" TEXT NOT NULL," +
            Locations.COLUMN_NAME_LAT+" TEXT NOT NULL," +
            Locations.COLUMN_NAME_LON+" TEXT NOT NULL, UNIQUE("+
            Locations.COLUMN_NAME_SHOPNAME+","+Locations.COLUMN_NAME_LAT+","+
            Locations.COLUMN_NAME_LON+"))";

    private static final String CREATE_MAPPING = "CREATE TABLE "+ Mapping.TABLE_NAME+"("+
            Mapping._ID + " INTEGER PRIMARY KEY,"+
            Mapping.COLUMN_NAME_BEER_ID + " INT NOT NULL,"+
            Mapping.COLUMN_NAME_SHOP_ID + " INT NOT NULL)";



    public static final int DB_VERSION=1;
    public static final String DB_NAME="beerdb.db";
    public BeerDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL(CREATE_COUNTRIES);
        db.execSQL(CREATE_COUNTRIES);
        db.execSQL(CREATE_MAPPING);
        db.execSQL(CREATE_BEERS);
        db.execSQL(CREATE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    public void insertCountry(Country country){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
       // c.put("id",country.getId());
        c.put("country_name",country.getCountryName());
        c.put("country_code",country.getCountryName());
        db.insert(Countries.TABLE_NAME,null,c);
        db.close();
    }
    public void insertCountry(int id, String name, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
       // c.put("id",id);
        c.put("country_name",name);
        c.put("country_code",code);
        db.insert(Countries.TABLE_NAME,null,c);
        db.close();
    }

    public void insertBeer(Beer beer)throws Exception{
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues co = new ContentValues();
       // co.put(Beers.COLUMN_NAME_ID,beer.getId());
        co.put(Beers.COLUMN_NAME_BRAND,beer.getBrand());
        co.put(Beers.COLUMN_NAME_FLAVOUR,beer.getFlavour());
        co.put(Beers.COLUMN_NAME_TYPE,beer.getType());
        co.put(Beers.COLUMN_NAME_BREWER,beer.getBrewer());
        co.put(Beers.COLUMN_NAME_COUNTRY,beer.getCountry());

        db.insert(Beers.TABLE_NAME,null,co);
        db.close();

    }

    public void insertLocation(String name,String lat, String lon)throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Locations.COLUMN_NAME_SHOPNAME,name);
        cv.put(Locations.COLUMN_NAME_LAT,lat);
        cv.put(Locations.COLUMN_NAME_LON,lon);
        db.insert(Locations.TABLE_NAME,null,cv);
        db.close();
    }

    public void insertMapping(String brand,String flavour,String shop,String lat, String lon)throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("INSERT INTO "+Mapping.TABLE_NAME+ "("+Mapping.COLUMN_NAME_BEER_ID+","+
               Mapping.COLUMN_NAME_SHOP_ID+") VALUES((SELECT "+ Beers._ID +" FROM "+Beers.TABLE_NAME+" WHERE "+
                Beers.COLUMN_NAME_BRAND +" = '" + brand + "' and "+ Beers.COLUMN_NAME_FLAVOUR + "= '"+
              flavour+"'),(SELECT "+Locations._ID+" FROM "+Locations.TABLE_NAME + " WHERE "+
               Locations.COLUMN_NAME_SHOPNAME + " = '"+ shop + "' and "+ Locations.COLUMN_NAME_LAT + " = '" +
              lat + "' and "+ Locations.COLUMN_NAME_LON + " = '" + lon +"'))");
        db.close();

    }

    public Cursor getBeers(){
        Cursor cursor;
        String[]project = {Beers._ID,Beers.COLUMN_NAME_BRAND,Beers.COLUMN_NAME_FLAVOUR,Beers.COLUMN_NAME_TYPE};
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query(Beers.TABLE_NAME,project,null,null,null,null,null,null);
        //db.close();
        return cursor;
    }

    public Cursor getMapping(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

       // cursor = db.query(Locations.TABLE_NAME,new String[]{
        //                Locations.COLUMN_NAME_SHOPNAME,Locations.COLUMN_NAME_LAT,Locations.COLUMN_NAME_LON},
       //    null,null,null,null,null);
        cursor = db.rawQuery("SELECT shopname,latitude,longitude FROM Locations,Mapping WHERE " +
                "Mapping.id_beer=?",new String[]{String.valueOf(id)});
        return cursor;

    }
}