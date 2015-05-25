package project.pwr.beer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.pwr.database.BeerDBHelper;

import project.pwr.database.DataProc;

/*
    This is the options activity, that becomes active if
    the user is not registered.

    If there is a name value pair for the user that have a username and email
    then the user is considered registered. Due to time constraints cannot add
    full user verification at this time.
 */

public class OptionsActivity extends Activity {
   // public static final String USER = "com.user";
  //  public static final String PASS = "com.pass";

    SQLiteDatabase db;
    BeerDBHelper mHelper;

    String name = null;
    String email = null;
    public void onPause(){
        super.onPause();
    }

    public void onResume(){
        super.onResume();
    }

    public void onBackPressed(){
        Intent intent = new Intent();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button save = (Button)findViewById(R.id.save_but);
        Button cancel = (Button)findViewById(R.id.cancel_but);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = getApplicationContext();
                EditText txt =  (EditText)findViewById(R.id.name_text);
                name = txt.getText().toString();
                txt = (EditText)findViewById(R.id.email_text);
                email = txt.getText().toString();
                SharedPreferences shared = ctx.getSharedPreferences(getString(R.string.credentials),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString(MainActivity.USER,name);
                editor.putString(MainActivity.PASS,email);
                editor.commit();
                RegisterUser s = new RegisterUser();
                s.execute("0", name, email);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return from registration
                //with warning that exit to register
            }
        });
    }

    private class RegisterUser extends AsyncTask<String,Void,String> {

        protected String doInBackground(String... urls){
            int action  = Integer.parseInt(urls[0]);
            DataProc proc = new DataProc();
            String post = null;
            String answer = null;
            Context context = getApplicationContext();
            try {
                post = proc.buildPost(urls);
                 answer = proc.postData(post);
                 Context ctx = getApplicationContext();
                 SharedPreferences shared = ctx.getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = shared.edit();
                 editor.putString("com.answer", "connected");
                 editor.commit();


            }catch(Exception e){answer = e.getMessage();}
            return answer;
        }

        protected void onPostExecute(String string){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            String text = string;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
