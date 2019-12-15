package com.example.booksale;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private Button btn_reg;
    private EditText username;
    private EditText password;
    /*对控件进行引用*/

    private Dao dao;
    private SQLiteDatabase db;
    private MyHelper myHelper = new MyHelper(this);
    /*
    * 初始化数据库的全局变量*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_passwords);
        btn_login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String user = String.valueOf(username.getText()).trim();
                String jy_password = String.valueOf(password.getText()).trim();
                String[] usernames = {
                        String.valueOf(username.getText()).trim()
                };
                db = myHelper.getReadableDatabase();
                Cursor cursor = db.query(Constans.USER_TABLE,null,"Username=?",usernames,null,null,null,null);
                if (cursor.moveToNext()){
                    if (cursor.getString(cursor.getColumnIndex("Passward")).equals(jy_password)){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("username",user);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"该账号不存在",Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                db.close();
            }
        });


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,REGinfo.class);
                startActivity(intent);
            }
        });
    }
}
