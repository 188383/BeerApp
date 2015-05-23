package project.pwr.beer;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.URI;
import java.net.URL;

public class MainActivity extends Activity {
    private String USER_NAME=null;
    private String EMAIL=null;

    public static final String USER = "com.beer.drinker";
    public static final String PASS = "com.beer.pass";
    static final int REGISTER_USER = 1;

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REGISTER_USER){
            if(resultCode==RESULT_OK){
                USER_NAME =  data.getStringExtra(USER);
                EMAIL =  data.getStringExtra(PASS);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listBeers = (Button)findViewById(R.id.list_beers);
       
        Button location = (Button)findViewById(R.id.location);
        /*
            Thread checks whether the user has registered.
            If the user has registered then we're good to go, otherwise
            the user has to register a name and send it to the database.
        */

        new Thread(new Runnable() {

            @Override
            public void run() {
                Context ctx = getApplicationContext();
                SharedPreferences shared = ctx.getSharedPreferences(getString(R.string.credentials),Context.MODE_PRIVATE);
                USER_NAME = shared.getString(USER,null);
                EMAIL = shared.getString(PASS,null);
                if(USER_NAME==null||EMAIL==null) {
                    Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                    startActivityForResult(intent,REGISTER_USER);
                }
            }
        }).start();

    }
}
