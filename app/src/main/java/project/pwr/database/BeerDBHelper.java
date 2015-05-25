package project.pwr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

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

        /*
            The set up methods that are used to create the database

         */
    private static final String CREATE_COUNTRIES = "CREATE TABLE "+Countries.TABLE_NAME+"("+
        Countries.COLUMN_NAME_ID +" INT NOT NULL PRIMARY KEY,"+
        Countries.COLUMN_NAME_COUNTRY_NAME+" TEXT NOT NULL,"+
        Countries.COLUMN_NAME_COUNTRY_CODE+ "TEXT NOT NULL)";

    private static final String CREATE_BEERS = "CREATE TABLE "+Beers.TABLE_NAME+"("+
        Beers.COLUMN_NAME_ID+" INT NOT NULL PRIMARY KEY,"+
        Beers.COLUMN_NAME_BRAND+" TEXT NOT NULL,"+
        Beers.COLUMN_NAME_FLAVOUR+" TEXT NOT NULL," +
        Beers.COLUMN_NAME_TYPE + " TEXT DEFAULT NULL," +
        Beers.COLUMN_NAME_BREWER + " TEXT NOT NULL," +
        Beers.COLUMN_NAME_COUNTRY + " INT NOT NULL,"+
        "FOREIGN KEY("+ Beers.COLUMN_NAME_COUNTRY+")REFERENCES " +Countries.TABLE_NAME+"("+
        Countries.COLUMN_NAME_ID+"))";

    private static final String CREATE_USERS = "CREATE TABLE "+Users.TABLE_NAME+"("+
         Users.COLUMN_NAME_ID + " INT NOT NULL PRIMARY KEY,"+
         Users.COLUMN_NAME_USERNAME+ " TEXT NOT NULL,"+
         Users.COLUMN_NAME_EMAIL+" TEXT NOT NULL)";


    public static final int DB_VERSION=1;
    public static final String DB_NAME="beerdb.db";
    public BeerDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL(CREATE_COUNTRIES);
        db.execSQL(CREATE_COUNTRIES);
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_BEERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}