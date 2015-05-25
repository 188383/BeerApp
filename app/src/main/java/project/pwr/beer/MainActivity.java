package project.pwr.beer;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.*;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import project.pwr.database.BeerDBHelper;
import project.pwr.database.DataProc;

import static android.content.Context.*;

public class MainActivity extends Activity {
    private String USER_NAME=null;
    private String EMAIL=null;
    private boolean registered =false;
    public static final String USER = "com.beer.drinker";
    public static final String PASS = "com.beer.pass";
    static final int REGISTER_USER = 1;
    BeerDBHelper helper = null;
    SQLiteDatabase db =null;
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

        boolean exists = sharedPreferences.contains("com.answer");
        CharSequence text = sharedPreferences.getString("com.answer","no value");
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        new RegisterClass().execute(new String[]{"1",USER_NAME,EMAIL});
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                //db = helper.getReadableDatabase();
                db = openOrCreateDatabase("beerdb.db",MODE_PRIVATE,null);
                helper = new BeerDBHelper(getApplicationContext());
                helper.onCreate(db);
            }

        }).start();

        Button listBeers = (Button)findViewById(R.id.list_beers);
        Button location = (Button)findViewById(R.id.location);



    }

    private class RegisterClass extends AsyncTask<String,Void,String>{


        protected String doInBackground(String... urls){
            int action  = Integer.parseInt(urls[0]);
            DataProc proc = new DataProc();
            String post = null;
            String answer = null;
            try {
              post = proc.buildPost(urls);
              answer = proc.postData(post);
            }catch(Exception e){answer = null;}
            return answer;
        }

        protected void onPostExecute(String result){
            Context context = getApplicationContext();
            CharSequence text = result;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
