package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class food extends AppCompatActivity implements View.OnClickListener {
    private EditText et_v,et_t,et_p,et_q;
    private Button bt_i,bt_c,bt_r;
    private Integer id_s=-1;
    MyDB m_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food);
        m_db = new MyDB(this,"db.db");
        init();
        Intent it = getIntent();
        String [] infs = it.getStringExtra("infs").split(" ");
        if(infs.length>1){
                id_s=Integer.valueOf(infs[0]);
                et_v.setText(infs[1]);
                et_t.setText(infs[2]);
                et_p.setText(infs[3]);
                et_q.setText(infs[4]);
        }
    }
    private void init(){
        et_v=findViewById(R.id.et_1);
        et_t=findViewById(R.id.et_2);
        et_p=findViewById(R.id.et_3);
        et_q=findViewById(R.id.et_4);
        findViewById(R.id.bt_i).setOnClickListener(this);
        findViewById(R.id.bt_c).setOnClickListener(this);
        findViewById(R.id.bt_r).setOnClickListener(this);
        findViewById(R.id.bt_d).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String name,type;
        int price,quantity;
        SQLiteDatabase db;
        ContentValues values;
        name = et_v.getText().toString();
        type = et_t.getText().toString();
        price = Integer.parseInt(et_p.getText().toString());
        quantity = Integer.parseInt(et_q.getText().toString());
        db = m_db.getWritableDatabase();
        switch (v.getId()){
            case R.id.bt_i:
                values = new ContentValues();
                values.put("name",name);
                values.put("type",type);
                values.put("price",price);
                values.put("quantity",quantity);
                db.insert("information",null,values);
                Toast.makeText(food.this, "信息已添加", Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.bt_c:
//                db = m_db.getWritableDatabase();
//                values = new ContentValues();
//                values.put("name",name=et_v.getText().toString());
//                values.put("type",type=et_t.getText().toString());
//                values.put("price",price=et_p.getText().toString());
//                values.put("quantity",quantity=et_q.getText().toString());
//                db.update("information",values,"name=?",new String[]{});
//                Intent it = new Intent(food.this,MainActivity.class);
//                startActivity(it);
//                Toast.makeText(this, "信息已修改", Toast.LENGTH_SHORT).show();
                db.execSQL("update information set name=?,type=?,price=?,quantity=? where _id=?",new Object[]{name,type,price,quantity,id_s});
                Toast.makeText(this, "信息已修改", Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.bt_r:
                Intent it1 = new Intent(food.this, weather.class);
                startActivity(it1);
                break;
            case R.id.bt_d:
                db.delete("information","_id=?",new String[]{id_s+""});
                Toast.makeText(this, "信息已删除", Toast.LENGTH_SHORT).show();
                db.close();
                break;
        }
    }
}