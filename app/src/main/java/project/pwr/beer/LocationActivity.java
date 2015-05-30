package project.pwr.beer;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.pwr.database.BeerDBHelper;
import project.pwr.database.DataProc;

class SaveLocation extends AsyncTask<String,Void,String> {
    Context context;
    SaveLocation(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        BeerDBHelper db = new BeerDBHelper(context);
        String success;
        String answer;
        try {

            DataProc proc = new DataProc();
            answer = proc.buildUpdateLocation("2",params[0],params[1],params[2],params[3]);
            proc.postData(answer);
            db.insertLocation(params[0],params[1],params[2]);
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

             //   if(loc==null){
              //      loc = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
             //   }
                if(loc==null){
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(getApplicationContext(), "Cannot Locate!", duration);
                    toast.show();
                }
                else{
                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.credentials), MODE_PRIVATE);
                   // String USER_NAME = sharedPreferences.getString(MainActivity.USER,null);
                    String EMAIL = sharedPreferences.getString(MainActivity.PASS,null);
                    EditText editText = (EditText)findViewById(R.id.shop_name);
                    String name = editText.getText().toString();
                    String lat = String.valueOf(loc.getLatitude());
                    String lon = String.valueOf(loc.getLongitude());
                    SaveLocation sl = new SaveLocation(getApplicationContext());
                    sl.execute(name,lat,lon,EMAIL);
                }


            }
        });

    }


}
