package project.pwr.beer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


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
                //add checking for name and email address
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
