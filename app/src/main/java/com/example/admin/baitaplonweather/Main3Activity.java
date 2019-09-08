package com.example.admin.baitaplonweather;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    ListView lsttt;
    customtp customAdapter;

    private final String fileName="tggg.txt";
    int intCount=0;
     ArrayList<String> list;


    BufferedReader bufferedReaderCounter;
    String[] my_country_list;


    BufferedReader bufferedReaderLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Anhxa();
         docfile();
         list = new ArrayList<String>(Arrays.asList(my_country_list));
        customAdapter=new customtp(this,list);
        lsttt.setAdapter(customAdapter);

       /* btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem=0;

                list2 = new ArrayList<String>(Arrays.asList(my_country_list));
                for(int i=0;i<list2.size();i++){
                    if(list2.get(i).equals("Saigon")){
                        dem=i;
                        list2.remove(i);
                    }

                }
                deletefile();
                saveData(list2);
                docfile();
                list.remove(dem);
                customAdapter.notifyDataSetChanged();



            }
        });*/
        lsttt.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                list.remove(i);
                customAdapter.notifyDataSetChanged();
                return false;
            }
        });


        lsttt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = my_country_list[position];
                Intent intent =new Intent(Main3Activity.this,MainActivity.class);

                intent.putExtra("name2",city);

                startActivity(intent);


            }
        });


    }

    void  Anhxa(){
       lsttt = (ListView) findViewById(R.id.listviewtp);

   }



     void docfile(){
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
   }



}
