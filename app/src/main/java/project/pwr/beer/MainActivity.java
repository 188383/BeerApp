package project.pwr.beer;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Scanner;

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
                }else{
                   // new RegisterClass().execute(new String[]{"http://192.168.1.11:8080/BeerServer/list"});
                   //create an update
                }
            }
        }).start();

        new RegisterClass().execute(new String[]{"fatima","pawel@otrebski.org"});

    }
    /*
        This is the async task that will check the server for updates

        There will be another async task like this one that will actually hold the
        POST method for posting information to the server.

        This one will be handling the GET method
     */
    private class RegisterClass extends AsyncTask<String,Void,String>{

        private String readValues(InputStream is)throws Exception{
            BufferedReader scan = new BufferedReader(new InputStreamReader(is));
            String input;
            String buffer = "";
            if(scan.ready()){
                while((input=scan.readLine())!=null){
                    buffer = buffer.concat(input);
                }
            }
            return buffer;
        }
        private String buildPost(String... names) throws Exception{
            JSONObject object = new JSONObject();
            object.put("username",names[0]);
            object.put("email",names[1]);

            return object.toString();
        }


        protected String doInBackground(String... urls){
            InputStream is = null;
            OutputStream os =null;
            String post = null;
            String answer = null;
            try {
                post = buildPost(urls);

                URL newURL = new URL("http://192.168.1.11:8080/BeerServer/list");
                HttpURLConnection connection = (HttpURLConnection)newURL.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();
                String query = "data="+post;
                os = connection.getOutputStream();
                os.write(query.getBytes());
                is = connection.getInputStream();
                answer = readValues(is);
                connection.disconnect();
            }catch(Exception e){post = "data=\"\"";}
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
