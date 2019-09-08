package com.example.admin.baitaplonweather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    ListView lsttt;
    TextView txtName,txtCountry,txtTemp,txtStatus,txtCloud,txtWind,txtDay,txtHumidity;
    ImageView imgIcon;
    ImageButton imgBack,imgChuyen;
    int intCount=0;
    String tenthanhpho="";
    CustomThoiTietObjAdapter customAdapter;
    ArrayList<ThoitietObj> mangthoitiet;
    private final String fileName="tggg.txt";
    InputStream inputStreamCounter;
    BufferedReader bufferedReaderCounter;
    String[] my_country_list;
    InputStream inputStreamLoader;
    BufferedReader bufferedReaderLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //GetCurrentWeatherData("SaiGon");
       //Get7DaysData("HaNoi");
        AnhXa();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        final Intent intent=getIntent();
        final String city = intent.getStringExtra("name");
        final String city2 = intent.getStringExtra("name2");




        if (city==null && city2==null) {
            Get7DaysData("Hanoi");
            tenthanhpho="Hanoi";
            GetCurrentWeatherData("Hanoi");
        }
        else if(city2==null){
            tenthanhpho=city;
            Get7DaysData(tenthanhpho);
            GetCurrentWeatherData(tenthanhpho);
        }
        else if(city==null){
            tenthanhpho=city2;
            Get7DaysData(tenthanhpho);
            GetCurrentWeatherData(tenthanhpho);
        }








       imgChuyen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               /**/
               Intent intent1=new Intent(MainActivity.this,Main2Activity.class);
               startActivity(intent1);
           }
       });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city2=txtName.getText().toString();
                Intent intent1=new Intent(MainActivity.this,Main3Activity.class);
                intent1.putExtra("name",city2);
                startActivity(intent1);
            }
        });
    }

    public boolean kiemtra(String xx){
        File file=null;
        file = new File(Environment.getExternalStorageDirectory(),fileName);

        try {
            bufferedReaderCounter=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            bufferedReaderLoader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try{
            while (bufferedReaderCounter.readLine()!=null){
                intCount++;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        my_country_list=  new String[intCount];
        try{
            for(int i=0;i<intCount;i++){
                my_country_list[i]=bufferedReaderLoader.readLine();
            }
        }



        catch (Exception ex){
            ex.printStackTrace();
        }
        for(int i=2;i<my_country_list.length;i++){
            if(my_country_list[i].equals(xx)) return true;
            else return false;
        }
        return false;
    }
    public void saveData(String x){


        try {
            File file =new File(Environment.getExternalStorageDirectory(),fileName);
            FileOutputStream fileOutputStream=new FileOutputStream(file,true);
            OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream,"UTF-8");







            try {
                bufferedReaderCounter=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                bufferedReaderLoader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            try{
                while (bufferedReaderCounter.readLine()!=null){
                    intCount++;
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            my_country_list=  new String[intCount];
            try{
                for(int i=0;i<intCount;i++){
                    my_country_list[i]=bufferedReaderLoader.readLine();
                }
            }



            catch (Exception ex){
                ex.printStackTrace();
            }
            boolean xx=false;
            for(int i=0;i<intCount;i++){
                if(my_country_list[i].equals(x)) {
                    xx=true;
                    break;
                }
                else {
                    xx=false;
                }
            }

            if(xx==false){
                BufferedWriter bw =new BufferedWriter(osw);
                bw.write(x);
                  bw.newLine();Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                bw.close();
            }









            osw.close();

            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String URL ="http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=3c11345e98442b840c2e55e818d67cc2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String day= jsonObject.getString("dt");
                            String name=jsonObject.getString("name");
                            txtName.setText(""+name);

                            long l= Long.valueOf(day);
                            Date date = new Date(l*1000L);//Chuyển sang ml giây
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd ");
                            String Day = simpleDateFormat.format(date);
                            txtDay.setText(Day);

                            JSONArray jsonArrayWeather= jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather= jsonArrayWeather.getJSONObject(0);//0 là phần tử đầu tiên trong jsObject
                            String status = jsonObjectWeather.getString("main");
                            txtStatus.setText(status);
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgIcon);


                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtTemp.setText(Nhietdo+" C");
                            txtHumidity.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio =jsonObjectWind.getString("speed");
                            txtWind.setText(gio+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtCloud.setText(may+"%");

                            /*ArrayList<ThanhPho>dsKH =new ArrayList<ThanhPho>();
                            dsKH.add(new ThanhPho(name));*/

                            saveData(name);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Không tồn tại tên thành phố", Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(stringRequest);
    }
    void Get7DaysData(final String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                           // txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");//[] là array {} là Object

                            //khi mở jsArrayList ra có 7 thẻ JSobject
                            // các giá trị trong 7 thẻ này giống nhau => dùng vòng for
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");

                                long l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);//Chuyển sang ml giây
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                                String max = jsonObjectTemp.getString("max");
                                String min = jsonObjectTemp.getString("min");

                                Double a = Double.valueOf(max);
                                String NhietdoMax = String.valueOf(a.intValue());
                                Double b = Double.valueOf(min);
                                String NhietdoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                mangthoitiet.add(new ThoitietObj(Day, status, icon, NhietdoMax, NhietdoMin));



                            }

                            customAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Hiện tại OpenWeather Không có thông tin về thành phố "+data+" trong dữ liêu. Vui Lòng quay lại nhập tên thành phố khác", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    void AnhXa(){
        lsttt = (ListView) findViewById(R.id.list_tt);
        txtName =(TextView) findViewById(R.id.textviewName);
        imgBack=(ImageButton)findViewById(R.id.imgbuttonBack);
        imgChuyen=(ImageButton)findViewById(R.id.imgbuttonChuyen);
        txtTemp=(TextView) findViewById(R.id.textviewTemp);
        txtStatus=(TextView) findViewById(R.id.textviewStatus);
        txtCloud=(TextView) findViewById(R.id.textviewCloud);
        txtWind=(TextView) findViewById(R.id.textviewWind);
        txtDay=(TextView)findViewById(R.id.textviewDay);
        imgIcon =(ImageView) findViewById(R.id.imageIcon);
        txtHumidity=(TextView) findViewById(R.id.textviewHumidity);

        mangthoitiet=new ArrayList<ThoitietObj>();
        customAdapter = new CustomThoiTietObjAdapter(MainActivity.this,mangthoitiet);
        lsttt.setAdapter(customAdapter);
    }


}
