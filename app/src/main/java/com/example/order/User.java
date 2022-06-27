package com.example.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class User extends AppCompatActivity {
    private TextView tv,tv1;
    private EditText et_u,et_p,et_p1,et_c;
    private Button bt;
    private File f;
    private FileOutputStream fos;
    private FileInputStream fis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        init();//对控件的初始化
        isRegister();//判断是否已经注册过
    }
    private void init(){
        et_u=findViewById(R.id.et_u_f);
        et_p=findViewById(R.id.et_p_f);
        et_p1=findViewById(R.id.et_p_f1);
        et_c=findViewById(R.id.et_city);
        bt=findViewById(R.id.button);
        tv=findViewById(R.id.textView2);
        tv1=findViewById(R.id.textView8);
    }
    private void isRegister(){
        f = new File("/data/user/0/com.example.order/shared_prefs/login.xml");
        //f = new File("/data/user/0/com.example.sharepreference/file");
        boolean it=f.exists();
        if(!f.exists())
        {
            tv.setVisibility(View.VISIBLE);
            et_p1.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            et_c.setVisibility(View.VISIBLE);
            Toast.makeText(User.this, "初次运行,请设置用户名、密码", Toast.LENGTH_SHORT).show();
//            try {
//                fos = openFileOutput("f", MODE_PRIVATE);
//                //fos.write(f.getBytes());
//                fos.close();
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
            bt.setText("注册");
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = et_u.getText().toString().trim();
                    String pwd = et_p.getText().toString().trim();
                    String city = et_c.getText().toString().trim();
                    String pwd1 = et_p1.getText().toString().trim();
                    SharedPreferences data=getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=data.edit();
                    if(user.length()>5&pwd.length()>5&pwd1.equals(pwd)) {
                        editor.putString("username",user);
                        editor.putString("password",pwd);
                        editor.putString("city",city);
                        editor.commit();
                        Toast.makeText(User.this, "设置成功,请退出登录", Toast.LENGTH_LONG).show();//注意此处要添加.show()方法,才能将提示信息显示出
                    }
                    else
                    {
                        et_p.setText("");
                        et_u.setText("");
                        et_p1.setText("");
                        et_c.setText("");
                        Toast.makeText(User.this, "设置不成功,请重新设置", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            bt.setText("登录");
            Toast toast = Toast.makeText(User.this, "欢迎回来,请输入用户名密码登录", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,200);
            toast.show();
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                SharedPreferences data = getSharedPreferences("login",MODE_PRIVATE);
                String user0=et_u.getText().toString().trim();
                String pwd0=et_p.getText().toString().trim();
                if(user0.equals(data.getString("username",""))&pwd0.equals(data.getString("password",""))) {
                    Toast.makeText(User.this, "欢迎登录", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(User.this, weather.class);
                    startActivity(it);
                }
                else
                    Toast.makeText(User.this,"用户名或密码错误,请重新输入",Toast.LENGTH_LONG).show();
            }
        });

        }

    }

}
