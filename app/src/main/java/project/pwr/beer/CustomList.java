package project.pwr.beer;

/**
 * Created by Kamil on 2015-04-17.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.pwr.beer.common.Beer;

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
        textView.setText(beerList.get(position).getBeerName());

        TextView textView2 = (TextView)  rowView.findViewById(R.id.textView2);
        textView2.setText( beerList.get(position).getBeerType());

        TextView textView3 = (TextView)  rowView.findViewById(R.id.textView3);
        Double finalresult = new Double(beerList.get(position).getBeerPower());
        textView3.setText(finalresult.toString());
//        Button b1 = (Button) rowView.findViewById(R.id.button);
//        b1.setText(beerList.get(position).getBeerName());
//
//        Button b2 = (Button) rowView.findViewById(R.id.button3);
//        b2.setText(""+beerList.get(position).getBeerPower());

//        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
//        txtTitle.setText(web[position]);
//        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}