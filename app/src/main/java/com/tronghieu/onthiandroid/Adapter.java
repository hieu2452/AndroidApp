package com.tronghieu.onthiandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter extends BaseAdapter implements Filterable {

    private ArrayList<Taxi> data;
    private ArrayList<Taxi> dataBackUp;
    private Activity context;
    private LayoutInflater inflater;
    private float total;

    public Adapter(){

    }

    public Adapter(ArrayList<Taxi> data, Activity context){
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null)
            v = inflater.inflate(R.layout.taxicontent, viewGroup , false);
        total = data.get(i).getPrice() * (100 - data.get(i).getDiscount())/100;
        TextView plate =v.findViewById(R.id.txtPlate1);
        plate.setText(data.get(i).getPlate());
        TextView road =v.findViewById(R.id.txtRoad1);
        road.setText(data.get(i).getRoad());
        TextView price =v.findViewById(R.id.txtPrice1);
        price.setText(Float.toString(total));
        return v;
    }

    @Override
    public Filter getFilter() {
//        if(data!=null){
//            dataBackUp= new ArrayList<>(data);
//        }
        Filter f= new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults fr= new FilterResults();
                //Backup du lieu :luu tam data vao data backup

                if(dataBackUp==null) {
                    dataBackUp = new ArrayList<>(data);
                }
                //neu chuoi de filter la rong thi khoi phuc du lieu
                if(constraint==null || constraint.length()==0){
                    fr.count=dataBackUp.size();
                    fr.values=dataBackUp;
                }
                //neu chuoi khong rong thi thuc hien filter
                else {
                    float inputValue = Float.parseFloat(constraint.toString());

                    ArrayList<Taxi> newdata= new ArrayList<>();
                    for (Taxi c:dataBackUp) {
                        try{
                            if (c.getTotal()>inputValue)
                                newdata.add(c);
                        }catch (NullPointerException e){
                            if (c.getPlate().toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT)))
                                newdata.add(c);
                        }

                    }
                    fr.count=newdata.size();
                    fr.values= newdata;
                }

                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data= new ArrayList<Taxi>();
                ArrayList<Taxi> tmp= (ArrayList<Taxi>)results.values;
                for (Taxi c:tmp) {
                    data.add(c);
                }
                notifyDataSetChanged();

            }
        };
        return f;
    }
}
