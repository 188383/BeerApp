package project.pwr.beer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class OptionsActivity extends Activity {
   // public static final String USER = "com.user";
  //  public static final String PASS = "com.pass";

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
                String name = txt.getText().toString();
                txt = (EditText)findViewById(R.id.email_text);
                String email = txt.getText().toString();
                SharedPreferences shared = ctx.getSharedPreferences(getString(R.string.credentials),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString(MainActivity.USER,name);
                editor.putString(MainActivity.PASS,email);
                editor.commit();
                Intent intent = new Intent();
                intent.putExtra(MainActivity.USER,name);
                intent.putExtra(MainActivity.PASS,email);
                finish();
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
}
