package com.example.admin.baitaplonweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomThoiTietObjAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoitietObj> arrayList;
    LayoutInflater inflater;


    public CustomThoiTietObjAdapter(Context context, ArrayList<ThoitietObj> arrayList) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        view = inflater.inflate(R.layout.dong_listview_main1,null);
        TextView txtDay=(TextView)view.findViewById(R.id.textviewNgay);
        TextView txtStatus=(TextView)view.findViewById(R.id.textviewTrangthai);
        TextView txtMaxTemp=(TextView)view.findViewById(R.id.textviewMaxTemp);
        TextView txtMinTemp=(TextView)view.findViewById(R.id.textviewMinTemp);
        ImageView imgStatus =(ImageView)view.findViewById(R.id.imageviewTrangthai);

        ThoitietObj thoitiet = arrayList.get(i);

        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.Status);
        txtMaxTemp.setText(thoitiet.MaxTemp+"º C");
        txtMinTemp.setText(thoitiet.MinTemp+"º C");

        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet.Image+".png").into(imgStatus);
        return view;
    }
}
