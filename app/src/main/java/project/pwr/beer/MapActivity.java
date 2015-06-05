package project.pwr.beer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Handler;

import project.pwr.database.BeerDBHelper;


public class MapActivity extends Activity implements GoogleMap.OnMapLoadedCallback, OnMapReadyCallback {
    private GoogleMap map;
    long val;
    Cursor c;

    public void onBackPressed(){
        super.onBackPressed();finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        val = intent.getLongExtra(BeerActivity.ARRAY_LIST,(long)-1);

        MapFragment mf = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);
    }



    @Override
    public void onMapLoaded() {



                            Cursor t;
                            String name = "";
                            double lat=0.0;
                            double lon=0.0;
                            BeerDBHelper helper = new BeerDBHelper(getApplicationContext());



                                    try {
                                        t = helper.getMapping(val);
                                        while(t.moveToNext()) {
                                            name = t.getString(0);
                                            lat = Double.parseDouble(t.getString(1));
                                            lon = Double.parseDouble(t.getString(2));


                                            map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(name));

                                        }
                                    }catch (Exception e){
                                       Log.d("READING", e.getMessage());
                                    }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.setOnMapLoadedCallback(this);
    }
}
