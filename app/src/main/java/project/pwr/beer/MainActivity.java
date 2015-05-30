package project.pwr.beer;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;




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
