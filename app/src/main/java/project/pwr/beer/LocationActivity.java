package project.pwr.beer;

import android.app.Activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.pwr.database.Beer;
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

   SimpleCursorAdapter adapter;
    Cursor c;


    public void onBackPressed(){
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        LocationManager lm;
        Location loc;
        Button save = (Button)findViewById(R.id.save_loc);

        new Thread(
            new Runnable() {
                String table = BeerDBHelper.Locations.TABLE_NAME;
                String id = BeerDBHelper.Locations._ID;
                String lat = BeerDBHelper.Locations.COLUMN_NAME_LAT;
                String lon = BeerDBHelper.Locations.COLUMN_NAME_LON;
                String name = BeerDBHelper.Locations.COLUMN_NAME_SHOPNAME;
                final ListView listview = (ListView)findViewById(R.id.list_view);
                String[] fromColumns = {name,
                        lat,lon};
                int[] toViews = {R.id.name, R.id.lat,R.id.lon};
                //Cursor c;
                @Override
                public void run() {
                    BeerDBHelper helper = new BeerDBHelper(getApplicationContext());
                    SQLiteDatabase db = helper.getReadableDatabase();
                    c = db.query(table,new String[]{id,name,lat,lon},null,null,null,null,null);

                    adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.list_row, c, fromColumns, toViews, 0);
                    listview.post(new Runnable() {
                        @Override
                        public void run() {
                            listview.setAdapter(adapter);
                        }
                    });
                }

            }
        ).start();


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
