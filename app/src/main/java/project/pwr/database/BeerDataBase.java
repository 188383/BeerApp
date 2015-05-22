package project.pwr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pawel on 22.05.15.
 *
 */

public class BeerDataBase extends SQLiteOpenHelper{

    public BeerDataBase(Context context){
        super(context,null,null,0);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
