package project.pwr.beer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
* Created by Kamil on 2015-05-28.
*/
public class MapActivity extends Activity{

    GoogleMap googleMap;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


    }

//    private void createMapView(){
//        /**
//         * Catch the null pointer exception that
//         * may be thrown when initialising the map
//         */
//        try {
//            if(null == googleMap){
//                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
//                        R.id.map)).getMap();
//
//                /**
//                 * If the map is still null after attempted initialisation,
//                 * show an error to the user
//                 */
//                if(null == googleMap) {
//                    Toast.makeText(getApplicationContext(),
//                            "Error creating map", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (NullPointerException exception){
//            Log.e("mapApp", exception.toString());
//        }
//    }


}
