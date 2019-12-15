package com.example.booksale;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class REGinfo extends AppCompatActivity {

    private Dao dao;
    private SQLiteDatabase db;
    private MyHelper myHelper = new MyHelper(this);

    /*以上是数据库操作封装类的定义*/

    private EditText username;
    private EditText passwords;
    private EditText sure_passwords;
    private Button btn_regist_reg;
    private Button btn_regist_back;
    /*以上是定义控件的成员变量*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reginfo);
        /*dao引用并创建数据库*/
        dao = new Dao(this);
        /*对成员变量进行引用*/
        username = findViewById(R.id.et_regist_username);
        passwords = findViewById(R.id.et_regist_passwords);
        sure_passwords = findViewById(R.id.et_regist_sure_passwords);
        btn_regist_reg = findViewById(R.id.btn_regist_reg);
        btn_regist_back = findViewById(R.id.btn_regist_back);
        /*设置注册按钮的响应*/
        btn_regist_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*将从EditText获取的文件转为String类型，并赋给String数组*/
                String[] usernames = {

                        String.valueOf(username.getText())
                };
                /*先做判断，判断输入两次的密码是否正确*/
                if (String.valueOf(passwords.getText()).trim().equals(String.valueOf(sure_passwords.getText()).trim())){
                    /*查询数据库中是否已经拥有了该用户，如果拥有就不再插入*/
                    db = myHelper.getReadableDatabase();
                    Cursor cursor = db.query(Constans.USER_TABLE,null,"Username=?",usernames,null,null,null,null);
                    if (cursor.moveToNext()){
                        Toast.makeText(REGinfo.this,"该用户已存在",Toast.LENGTH_SHORT).show();
                    }else {
                        ContentValues values = loadUserData(String.valueOf(username.getText()),String.valueOf(passwords.getText()));
                        dao.insert(Constans.USER_TABLE,values);
                    }
                    cursor.close();
                    db.close();
                    Toast.makeText(REGinfo.this,"注册成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(REGinfo.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_regist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public ContentValues loadUserData(String username,String password){
        /*将用户名和密码写入ContentValues  返回 values*/
        ContentValues values = new ContentValues();
        values.put("Username",username);
        values.put("Passward",password);
        return values;
    }




}
