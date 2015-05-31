package project.pwr.beer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import project.pwr.database.Beer;
import project.pwr.database.BeerDBHelper;


public class BeerListActivity extends Activity implements View.OnClickListener{

    private ListView listView;
    private List<Beer> beerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_list_activity);

        Button location = (Button)findViewById(R.id.location);
        location = (Button) findViewById(R.id.location);
        location.setOnClickListener(this);

        init();
    }


    public void init()
    {
        createBeerList();


        listView = (ListView) findViewById(R.id.list);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                //TODO
                return true;
            }
        });

        CustomList adapter = new
                CustomList(this, beerList);//, imageId);

        listView.setAdapter(adapter);


//        arrayList = new ArrayList<Beer>();
//        // Konfigurujemy obiekt adaptera. Jako parametry podajemy mu Activity,
//        // adres pliku zawierającego definicję pojedyńczego elementu i nazwę naszego obiektu listy tablicowej.
//        adapter=new ArrayAdapter<Beer>(this, R.layout.list_single, arrayList);
//
//        listView.setAdapter(adapter);
    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.location:
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }



    public void createBeerList()// throws Exception// throws Exception
     {
        beerList = new ArrayList<Beer>();

        BeerDBHelper dbHelper = new BeerDBHelper(getApplicationContext());

         SharedPreferences settings = getSharedPreferences(Memory.APLICATION_NAME, 0);
         SharedPreferences.Editor editor = settings.edit();
         editor.putBoolean(Memory.DB_CREATED, true);
         editor.commit();

         dbHelper.insertCountry(1,"po","ad");
         dbHelper.insertBeer(2,"tyskie2","gronie2","jasn2e","kampania2","polska");


         beerList = dbHelper.getBeerList();

    }
}
