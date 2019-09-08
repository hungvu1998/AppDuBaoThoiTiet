package com.example.admin.baitaplonweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomMain2Adapter extends BaseAdapter {
    Context context;
    ArrayList<ThoitietObj> arrayList;
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =inflater.inflate(R.layout.dong_listview_main1,null);
        TextView txtDay=(TextView)view.findViewById(R.id.textviewNgay);
        TextView txtStatus=(TextView)view.findViewById(R.id.textviewTrangthai);
        TextView txtMaxTemp=(TextView)view.findViewById(R.id.textviewMaxTemp);
        TextView txtMinTemp=(TextView)view.findViewById(R.id.textviewMinTemp);
        ImageView imgStatus =(ImageView)view.findViewById(R.id.imageviewTrangthai);
        //TextView txtTentp =(TextView)view.findViewById(R.id.textviewTenTP);

        ThoitietObj main2 = arrayList.get(i);

        txtDay.setText(main2.Day);
       // txtTentp.setText(main2.tentp);
        txtStatus.setText(main2.Status);
        txtMaxTemp.setText(main2.MaxTemp+"º C");
        txtMinTemp.setText(main2.MinTemp+"º C");

        Picasso.with(context).load("http://openweathermap.org/img/w/"+main2.Image+".png").into(imgStatus);
        return view;
    }
}
