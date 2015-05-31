package project.pwr.beer;

/**
 * Created by Kamil on 2015-04-17.
 */
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import project.pwr.database.Beer;


public class CustomList extends ArrayAdapter<Beer>{
    private final Activity context;

    private List<Beer> beerList;

    public CustomList(Activity context, List<Beer> beerList) {
        super(context, R.layout.list_single, beerList);
        this.context = context;
        this.beerList = beerList;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView textView = (TextView)  rowView.findViewById(R.id.textView);
        textView.setText(beerList.get(position).getBrand());

        TextView textView2 = (TextView)  rowView.findViewById(R.id.textView2);
        textView2.setText( beerList.get(position).getFlavour());

        TextView textView3 = (TextView)  rowView.findViewById(R.id.textView3);
        textView3.setText( beerList.get(position).getType());

//        CheckBox checkBox = (CheckBox)  rowView.findViewById(R.id.checkbox);
        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);

        //listener dla checkboxsa
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Log.d("ch","ch");
                }
                else
                {
                    Log.d("aa","aa");
                }
            }
        });

//
        return rowView;
    }
}