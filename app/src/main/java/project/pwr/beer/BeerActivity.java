package project.pwr.beer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import project.pwr.database.BeerDBHelper;




public class BeerActivity extends Activity {
    Cursor c;
    SimpleCursorAdapter adapter;
    public static final String ARRAY_LIST = "project.pwr.beer.position";
    static final String locationTable = BeerDBHelper.Locations.TABLE_NAME;
    static final String locationId = BeerDBHelper.Locations._ID;
    static final String lat = BeerDBHelper.Locations.COLUMN_NAME_LAT;
    static final String lon = BeerDBHelper.Locations.COLUMN_NAME_LON;
    static final String shopName = BeerDBHelper.Locations.COLUMN_NAME_SHOPNAME;

    static ArrayList<Object> places;


    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);
        Button showMap = (Button)findViewById(R.id.show_map);

        new Thread(
                new Runnable() {

                    final ListView listView = (ListView)findViewById(R.id.beer_list);
                    BeerDBHelper helper = new BeerDBHelper(getApplicationContext());
                    String[] fromColumns = {BeerDBHelper.Beers.COLUMN_NAME_BRAND,
                            BeerDBHelper.Beers.COLUMN_NAME_FLAVOUR,BeerDBHelper.Beers.COLUMN_NAME_TYPE};
                    int[] toViews = {R.id.beer_name, R.id.beer_flavour,R.id.beer_type};

                    @Override
                    public void run() {

                        places = new ArrayList<Object>();
                        c = helper.getBeers();
                        adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.beer_row, c, fromColumns, toViews, 0);
                        listView.post(new Runnable() {

                            @Override
                            public void run() {
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        c.getLong(position);
                                        String _id = c.getColumnName(0);
                                        String vals = String.valueOf(c.getLong(0));

                                        places.add(vals);
                                    }
                                });
                            }
                        });
                    }
                }
        ).start();

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra(ARRAY_LIST, places);
                startActivity(intent);
            }
        });
    }
}
