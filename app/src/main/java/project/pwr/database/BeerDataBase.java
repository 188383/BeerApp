package project.pwr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by pawel on 22.05.15.
 *
 */

public class BeerDataBase{

    public BeerDataBase(){}
    /*
        Beers table
     */
    private static abstract class Beers implements BaseColumns{
        private static final String TABLE_NAME = "Beers";
        private static final String COLUMN_NAME_ID = "id";
        private static final String COLUMN_NAME_BRAND = "brand";
        private static final String COLUMN_NAME_FLAVOUR = "flavour";
        private static final String COLUMN_NAME_TYPE = "type";
        private static final String COLUMN_NAME_BREWER = "brewer";
        private static final String COLUMN_NAME_COUNTRY = "country";
    }
    /*
        countries table
     */
    private static abstract class Countries implements BaseColumns{
        private static final String TABLE_NAME = "Countries";
        private static final String COLUMN_NAME_ID = "id";
        private static final String COLUMN_NAME_COUNTRY_NAME = "country_name";
        private static final String COLUMN_NAME_COUNTRY_CODE = "country_code";
    }
    /*
        users table
     */
    private static abstract class Users implements BaseColumns{
        private static final String TABLE_NAME = "Users";
        private static final String COLUMN_NAME_ID = "id";
        private static final String COLUMN_NAME_USERNAME = "username";
        private static final String COLUMN_NAME_EMAIL= "email";
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
            Beers.COLUMN_NAME_TYPE + " TEXT," +
            Beers.COLUMN_NAME_BREWER + " TEXT NOT NULL," +
            Beers.COLUMN_NAME_COUNTRY + " INT REFERENCES" +Countries.TABLE_NAME+"("+
            Countries.COLUMN_NAME_ID+"))";

    private static String CREATE_USERS = "CREATE TABLE "+Users.TABLE_NAME+"("+
            Users.COLUMN_NAME_ID + " INT NOT NULL PRIMARY KEY,"+
            Users.COLUMN_NAME_USERNAME+ " TEXT NOT NULL,"+
            Users.COLUMN_NAME_EMAIL+" TEXT NOT NULL)";

    public class BeerDBHelper extends SQLiteOpenHelper{

        public static final int DB_VERSION=1;
        public static final String DB_NAME="beerdb.db";
        public BeerDBHelper(Context context){
            super(context,DB_NAME,null,DB_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_COUNTRIES);
            db.execSQL(CREATE_BEERS);
            db.execSQL(CREATE_USERS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
