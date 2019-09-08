package com.example.admin.baitaplonweather;

import android.content.Context;
import android.os.Environment;
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
import java.util.ArrayList;
import java.util.Arrays;

public class customtp extends BaseAdapter {
    Context context;
    TextView txtTP;
    LayoutInflater inflater;
    private final String fileName = "tggg.txt";
    int intCount = 0;
    ArrayList<String> list;
    ArrayList<String> list2;
    BufferedReader bufferedReaderCounter;
    String[] my_country_list;
    BufferedReader bufferedReaderLoader;


    public customtp(Context context, ArrayList<String> y) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        list = y;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int ps, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.dong_main3, null);
        txtTP = (TextView) view.findViewById(R.id.textviewMaxTemp);
        txtTP.setText(list.get(ps));

        Button btn = (Button) view.findViewById(R.id.btnXoaa);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Xóa thành công thành phố \t "+list.get(ps), Toast.LENGTH_SHORT).show();
                list.remove(ps);
                xoa();

                saveData(list);
                notifyDataSetChanged();





                //notifyDataSetChanged();
            }
        });
        return view;
    }
    public void saveData(ArrayList<String> x){


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


                BufferedWriter bw =new BufferedWriter(osw);
                for(int i=0;i<x.size();++i){
                    bw.write(x.get(i));
                    bw.newLine();
                }
                bw.close();










            osw.close();

            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void xoa(){
        File file =new File(Environment.getExternalStorageDirectory(),fileName);
        if(file.exists()){
            file.delete();
        }
        else {
            Toast.makeText(context, "Không tồn tại file", Toast.LENGTH_SHORT).show();
        }
    }
}
