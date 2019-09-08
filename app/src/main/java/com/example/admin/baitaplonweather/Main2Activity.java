package com.example.admin.baitaplonweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    ImageView imgback;
    EditText edtSearch;
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhXa();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();


                String a=city.replaceAll(" ", "");

                Intent intent =new Intent(Main2Activity.this,MainActivity.class);

                intent.putExtra("name",a);
                startActivity(intent);

                /*Intent inKQ = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", city);
                //add doi tuong bundle vao intent
                inKQ.putExtra("add",bundle);
                setResult(RESULT_OK, inKQ);
                finish();*/
            }
        });
    }


    void AnhXa(){
        imgback=(ImageView) findViewById(R.id.imgBack);
        edtSearch = (EditText) findViewById(R.id.editTextMonHoc);
        btnSearch=(Button) findViewById(R.id.buttonThem);
    }
}
