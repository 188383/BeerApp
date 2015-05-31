package project.pwr.beer;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import project.pwr.database.Beer;
import project.pwr.database.BeerDBHelper;
import project.pwr.database.DataProc;
import project.pwr.database.DecodeClass;
import project.pwr.database.Location;
import project.pwr.database.Mapping;


import static android.content.Context.*;

public class MainActivity extends Activity {
    private String USER_NAME=null;
    private String EMAIL=null;
    private boolean registered =false;
    public static final String USER = "com.beer.drinker";
    public static final String PASS = "com.beer.pass";
    static final int REGISTER_USER = 1;

    public void onSavedInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(USER,USER_NAME);
        savedInstanceState.putString(PASS,EMAIL);
    }

    public void onResume(){
        super.onResume();
        Context context = getApplicationContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.credentials), MODE_PRIVATE);
        USER_NAME = sharedPreferences.getString(USER,null);
        EMAIL = sharedPreferences.getString(PASS,null);
        if(USER_NAME!=null && EMAIL!=null) {
            Log.d("Here",EMAIL);
            new UpdateClass().execute("5", USER_NAME, EMAIL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Context ctx = getApplicationContext();

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(getString(R.string.credentials), MODE_PRIVATE);
        USER_NAME = sharedPreferences.getString(USER,null);
        EMAIL = sharedPreferences.getString(PASS,null);

        if(USER_NAME==null&&EMAIL==null){
            Intent intent = new Intent(this,OptionsActivity.class);
            startActivity(intent);
        }




        Button listBeers = (Button)findViewById(R.id.list_beers);
        Button location = (Button)findViewById(R.id.location);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LocationActivity.class);
                startActivity(intent);
            }
        });


    }

    private class UpdateClass extends AsyncTask<String,Void,String>{

        public UpdateClass(){
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            String answer=null;
            String post = null;
            try{
                DataProc proc = new DataProc();
                post = proc.buildPost(params);
                answer = proc.postData(post);
                DecodeClass dc = new DecodeClass(getApplicationContext());
                dc.decodeData(answer);
              //  BeerDBHelper helper = new BeerDBHelper(getApplicationContext());
               // JSONObject j = new JSONObject(answer);
              //  Location b = new Location((j.getJSONArray("locations")).getJSONObject(0));

              //  helper.insertLocation(b.getName(),b.getLat(),b.getLon());
              //  helper.close();

            }catch(Exception e){
                //answer = null;
                Log.d("Error",e.getMessage());
            }

            return answer;
        }
        protected void onPostExecute(String string) {
            String text = null;// string;
            text = string;
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            //String text = Long.toString(1);
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}
