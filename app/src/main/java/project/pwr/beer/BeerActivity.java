package project.pwr.beer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Iterator;

import project.pwr.database.BeerDBHelper;




public class BeerActivity extends Activity {
    Cursor c;
   public  SimpleCursorAdapter adapter;
    public static final String ARRAY_LIST = "project.pwr.beer.position";
    static final String locationTable = BeerDBHelper.Locations.TABLE_NAME;
    static final String locationId = BeerDBHelper.Locations._ID;
    static final String lat = BeerDBHelper.Locations.COLUMN_NAME_LAT;
    static final String lon = BeerDBHelper.Locations.COLUMN_NAME_LON;
    static final String shopName = BeerDBHelper.Locations.COLUMN_NAME_SHOPNAME;
    long val;


    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);
        Button showMap = (Button)findViewById(R.id.show_map);
        Button addBeer = (Button)findViewById(R.id.add_beer);


        Log.d("HERE", "HERE");
        new Thread(
                new Runnable() {

                    final ListView listView = (ListView)findViewById(R.id.beer_list);
                    BeerDBHelper helper = new BeerDBHelper(getApplicationContext());
                    String[] fromColumns = {BeerDBHelper.Beers.COLUMN_NAME_BRAND,
                            BeerDBHelper.Beers.COLUMN_NAME_FLAVOUR,BeerDBHelper.Beers.COLUMN_NAME_TYPE};
                    int[] toViews = {R.id.beer_name, R.id.beer_flavour,R.id.beer_type};

                    @Override
                    public void run() {

                        c = helper.getBeers();

                        adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.beer_row, c, fromColumns, toViews, 0);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(
                                new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if(c.moveToPosition(position)){
                                            val = c.getLong(0);
                                        }

                                    }
                                }
                        );
                    }
                }
        ).start();

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),MapActivity.class);

                intent.putExtra(ARRAY_LIST,val );
                startActivity(intent);
            }
        });

        addBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddBeer.class);
                startActivity(intent);
            }
        });


    }
}
