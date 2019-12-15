package com.example.booksale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booksale.recycleView.BookAttribute;
import com.example.booksale.recycleView.FenLei;
import com.example.booksale.recycleView.FenLeiZhanshiAdapter;
import com.example.booksale.toolbar.MyToolbar;

import java.util.ArrayList;
import java.util.List;

public class FenLeiActivity extends AppCompatActivity {
    private List<BookAttribute> bookAttribute = new ArrayList<BookAttribute>();
    private MyToolbar myToolbar;
    private ImageButton back;
    private FenLei fenLei;



    private Dao dao;
    private SQLiteDatabase db;
    private MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_lei);
        myHelper = new MyHelper(this);
        myToolbar = findViewById(R.id.myToolbar_fenlei);
        back=findViewById(R.id.tb_back);
        fenLei = (FenLei) getIntent().getSerializableExtra("type");
        myToolbar.setTitle(fenLei.getFenlei());



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        initRecycleView();
    }

    private void initData() {
        db = myHelper.getReadableDatabase();

        String[] type = {
                String.valueOf(fenLei.getFenlei()).trim()
        };
        Cursor cursor = db.query(Constans.BOOK_TABLE,null,"Type=?",type,null,null,null,null);
        while(cursor.moveToNext()){
            String bookid = cursor.getString(cursor.getColumnIndex("Booknumber"));
            String bookname = cursor.getString(cursor.getColumnIndex("Bookname"));
            String bookprice = cursor.getString(cursor.getColumnIndex("Price"));
            String bookauthor = cursor.getString(cursor.getColumnIndex("Author"));
            String bookpublish = cursor.getString(cursor.getColumnIndex("Publish"));
            String bookdescription = cursor.getString(cursor.getColumnIndex("Decrible"));
            int bookpicid = getResource(cursor.getString(cursor.getColumnIndex("Picture")));
            String booktype = cursor.getString(cursor.getColumnIndex("Type"));
            bookAttribute.add(new BookAttribute(bookid,bookname,bookprice,bookauthor,bookpublish,bookdescription,bookpicid,booktype));
        }
        cursor.close();
        db.close();

    }

    private void initRecycleView() {

        RecyclerView recyclerView = findViewById(R.id.fenleizhanshi_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FenLeiActivity.this,RecyclerView.VERTICAL,false);
        FenLeiZhanshiAdapter fenLeiZhanshiAdapter = new FenLeiZhanshiAdapter(bookAttribute,this);
        fenLeiZhanshiAdapter.setOnItemClickListener(new FenLeiZhanshiAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, BookAttribute data) {
                Toast.makeText(FenLeiActivity.this, "hahhahahah", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(fenLeiZhanshiAdapter);


    }



    public int  getResource(String imageName){
        Context ctx=this;
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
