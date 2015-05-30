package project.pwr.beer;

import android.app.Activity;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import project.pwr.database.BeerDBHelper;

class SaveLocation extends AsyncTask<String,Void,String> {
    Context context;
    SaveLocation(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        BeerDBHelper db = new BeerDBHelper(context);
        String success;
        try {
            db.addLocation(params[0],params[1],params[2]);
            success = "Location Added";
        } catch (Exception e) {
            success = "Error!";
        }
        return success;
    }

    protected void onPostExecute(String result){

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, result, duration);
        toast.show();
    }
}

public class LocationActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        LocationManager lm;
        Location loc;
        Button save = (Button)findViewById(R.id.save_loc);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                Location loc = lm.getLastKnownLocation(lm.GPS_PROVIDER);

                if(loc==null){
                    loc = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
                }else{
                    EditText editText = (EditText)findViewById(R.id.shop_name);
                    String name = editText.getText().toString();
                    String lat = String.valueOf(loc.getLatitude());
                    String lon = String.valueOf(loc.getLongitude());
                    SaveLocation sl = new SaveLocation(getApplicationContext());
                    sl.execute(name,lat,lon);
                }


            }
        });

    }


}
