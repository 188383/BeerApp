package project.pwr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.pwr.beer.Memory;

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

        public static final String[] allColumns = { COLUMN_NAME_ID,
                COLUMN_NAME_BRAND,
                COLUMN_NAME_FLAVOUR,
                COLUMN_NAME_TYPE,
                COLUMN_NAME_BREWER,
                COLUMN_NAME_COUNTRY};
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

    /*
        The set up methods that are used to create the database
     */
    private static final String CREATE_COUNTRIES = "CREATE TABLE "+Countries.TABLE_NAME+"("+
            Countries.COLUMN_NAME_ID +" INT NOT NULL PRIMARY KEY,"+
            Countries.COLUMN_NAME_COUNTRY_NAME+" TEXT NOT NULL,"+
            Countries.COLUMN_NAME_COUNTRY_CODE+ " TEXT NOT NULL)";

    private static final String CREATE_BEERS = "CREATE TABLE "+Beers.TABLE_NAME+"("+
            Beers.COLUMN_NAME_ID+" INT NOT NULL PRIMARY KEY,"+
            Beers.COLUMN_NAME_BRAND+" TEXT NOT NULL,"+
            Beers.COLUMN_NAME_FLAVOUR+" TEXT NOT NULL," +
            Beers.COLUMN_NAME_TYPE + " TEXT DEFAULT NULL," +
            Beers.COLUMN_NAME_BREWER + " TEXT NOT NULL," +
            Beers.COLUMN_NAME_COUNTRY + " TEXT NOT NULL,"+
            "FOREIGN KEY ( "+ Beers.COLUMN_NAME_COUNTRY+" ) REFERENCES " +Countries.TABLE_NAME+"("+
            Countries.COLUMN_NAME_COUNTRY_NAME+"))";

    private static final String CREATE_USERS = "CREATE TABLE "+Users.TABLE_NAME+"("+
            Users.COLUMN_NAME_ID + " INT NOT NULL PRIMARY KEY,"+
            Users.COLUMN_NAME_USERNAME+ " TEXT NOT NULL,"+
            Users.COLUMN_NAME_EMAIL+" TEXT NOT NULL)";


    public static final int DB_VERSION=1;
    public static final String DB_NAME="Beer.db";
    public BeerDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL(CREATE_COUNTRIES);

        if(Memory.getInstance().isDatabaseCreated())
        {
            db.execSQL(CREATE_COUNTRIES);
            db.execSQL(CREATE_USERS);
            db.execSQL(CREATE_BEERS);
        }
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
        c.put("id",country.getId());
        c.put("country_name",country.getCountryName());
        c.put("country_code",country.getCountryName());
        db.insert(Countries.TABLE_NAME,null,c);
        db.close();
    }
    public void insertCountry(int id, String name, String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("id",id);
        c.put("country_name",name);
        c.put("country_code",code);
        db.insert(Countries.TABLE_NAME,null,c);
        db.close();
    }

    public void insertBeer(Beer beer)//throws Exception
    {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues co = new ContentValues();
        co.put(Beers.COLUMN_NAME_ID,beer.getId());
        co.put(Beers.COLUMN_NAME_BRAND,beer.getBrand());
        co.put(Beers.COLUMN_NAME_FLAVOUR,beer.getFlavour());
        co.put(Beers.COLUMN_NAME_TYPE,beer.getType());
        co.put(Beers.COLUMN_NAME_BREWER,beer.getBrewer());
        co.put(Beers.COLUMN_NAME_COUNTRY,beer.getCountry());

        db.insert(Beers.TABLE_NAME,null,co);
    }
    public void insertBeer(int id,String brand,String flavour, String type,String brewer,String country)//throws Exception
    {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues co = new ContentValues();
        co.put(Beers.COLUMN_NAME_ID,id);
        co.put(Beers.COLUMN_NAME_BRAND,brand);
        co.put(Beers.COLUMN_NAME_FLAVOUR,flavour);
        co.put(Beers.COLUMN_NAME_TYPE,type);
        co.put(Beers.COLUMN_NAME_BREWER,brewer);
        co.put(Beers.COLUMN_NAME_COUNTRY,country);

        db.insert(Beers.TABLE_NAME,null,co);
        db.close();
    }


    public List<Beer> getBeerList()
    {
        List<Beer> result = new ArrayList<Beer>();
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        Cursor cursor = db.query(Beers.TABLE_NAME,
                Beers.allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Beer comment = cursorToBeer(cursor);
            result.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return result;
    }

    private Beer cursorToBeer(Cursor cursor) {
        Beer beer = new Beer(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
//        comment.setId(cursor.getLong(0));
//        comment.setComment(cursor.getString(1));
        return beer;
    }
}