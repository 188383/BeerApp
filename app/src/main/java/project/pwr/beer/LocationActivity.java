package project.pwr.beer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import project.pwr.database.BeerDBHelper;

class SaveLocation implements Runnable{
    private String name;
    private double lat;
    private double lon;
    private Context context;

    public SaveLocation(String name, double lat, double lon,Context context){
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public void run() {
        try {
            BeerDBHelper db = new BeerDBHelper(context);
            db.addLocation(this.name, this.lat, this.lon);
            db.close();
        }catch(Exception e){

        }
    }
}

public class LocationActivity extends Activity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    protected GoogleApiClient mGoogleAppClient;
    protected Location mLastLocation;

    protected double latitude;
    protected double longitude;

   protected synchronized void buildGoogleApiClient(){
        mGoogleAppClient = new GoogleApiClient.Builder(this).
        addOnConnectionFailedListener(this).
        addConnectionCallbacks(this).
        addApi(LocationServices.API).build();
   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        buildGoogleApiClient();
        Button save = (Button)findViewById(R.id.save_loc);


    }

    @Override
    public void onConnected(Bundle bundle) {
        //mGoogleAppClient.connect();
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleAppClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Context context = getApplicationContext();
        CharSequence text = String.valueOf(connectionResult.getErrorCode());
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        mGoogleAppClient.connect();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleAppClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleAppClient.isConnected()) {
            mGoogleAppClient.disconnect();
        }
    }

}
