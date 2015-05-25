package project.pwr.beer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import project.pwr.beer.common.Beer;


public class BeerListActivity extends Activity {

    private ListView listView;
//    private ArrayAdapter<Beer> adapter;
    private ArrayList<Beer> beerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_list_activity);

        init();
    }


    public void init()
    {
        createBeerList();


        listView = (ListView) findViewById(R.id.list);
        listView.setLongClickable(true);
// rozbudowac
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



    public void createBeerList()
    {
        beerList = new ArrayList<Beer>();
        Beer beer = new Beer();

        beer.setBeerName("Tyskie");
        beer.setCompany("Kampanie piwowarska");
        beer.setBeerPower(5.5);

        beerList.add(beer);

        beer = new Beer();
        beer.setBeerName("Piast");
        beer.setCompany("Kampanie piwowarska");
        beer.setBeerPower(4.5);

        beerList.add(beer);
    }
}
