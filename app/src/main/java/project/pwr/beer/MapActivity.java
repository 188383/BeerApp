package project.pwr.beer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.logging.Handler;

import project.pwr.database.BeerDBHelper;


public class MapActivity extends Activity implements GoogleMap.OnMapLoadedCallback, OnMapReadyCallback {
    private GoogleMap map;
    ArrayList<String> list;
    Cursor c;

    public void onBackPressed(){
        super.onBackPressed();finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        list = intent.getStringArrayListExtra(BeerActivity.ARRAY_LIST);

        MapFragment mf = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);
    }



    @Override
    public void onMapLoaded() {
                String t;
                double lat;
                double lon;
                BeerDBHelper helper = new BeerDBHelper(getApplicationContext());
               c =  helper.getMapping(1);
               if(c.moveToNext()){
                   t = c.getString(0);
                   lat = Double.parseDouble(c.getString(1));
                   lon = Double.parseDouble(c.getString(2));
               }else{t=" d";lat = 0.0;lon=0.0;}

               map.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(t));



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.setOnMapLoadedCallback(this);
    }
}
