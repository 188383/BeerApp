package project.pwr.beer;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.pwr.database.BeerDBHelper;
import project.pwr.database.DataProc;
import project.pwr.database.DecodeClass;


public class AddBeer extends Activity {


    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        Button save = (Button)findViewById(R.id.save_beer);
        save.setOnClickListener(new View.OnClickListener() {
            EditText brand = (EditText)findViewById(R.id.brand);
            EditText flavour = (EditText)findViewById(R.id.flavour);
            EditText type = (EditText)findViewById(R.id.type);
            EditText brewer = (EditText)findViewById(R.id.brewer);
            EditText country = (EditText)findViewById(R.id.country);
            @Override
            public void onClick(View v) {
                new SaveBeer().execute(new String[]{
                   "3",
                   brand.getText().toString(),
                   flavour.getText().toString(),
                   type.getText().toString(),
                   brewer.getText().toString(),
                   country.getText().toString(),
                   MainActivity.PASS
                });

            }
        });
    }
    private class SaveBeer extends AsyncTask<String,Void,String>{

        SaveBeer(){
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            String answer=null;
            String post = null;
            try{
                BeerDBHelper helper = new BeerDBHelper(getApplicationContext());
                helper.insertBeer(params[1],params[2],params[3],params[4],params[5]);
                DataProc proc = new DataProc();
                post = proc.buildUpdateBeer(params);
                answer = proc.postData(post);
                DecodeClass dc = new DecodeClass(getApplicationContext());
                dc.decodeData(answer);

            }catch(Exception e){
                answer = e.getMessage();
                Log.d("Error", e.getMessage());
            }

            return answer;

        }

        protected void onPostExecute(String result){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, result, duration);
            toast.show();
            //BeerActivity.adapter.notifyDataSetChanged();
            finish();
        }
    }
}
