package com.example.booksale;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booksale.Banner.GlideImageLoader;
import com.example.booksale.recycleView.BookAttribute;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;


public class GoodsFragment extends AppCompatActivity {



    private ImageButton back;
    private BookAttribute bookAttribute;
    private List<Integer> imglist = new ArrayList<>();
    private Banner banner;
    private Button btn1;
    private Button btn2;

    private TextView goods_name;
    private TextView goods_author;
    private TextView goods_price;
    private TextView goods_publish;
    private TextView goods_description;
    private ImageView goods_photo;

    private Dao dao;
    private SQLiteDatabase db;
    private SQLiteOpenHelper myHelper;
    private String username;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodspage_layout);
        bookAttribute = (BookAttribute) getIntent().getSerializableExtra("book");
        username = getIntent().getStringExtra("username");


        System.out.println("0");


        myHelper = new MyHelper(this);

        /*给予一个可写入权限，便于插入*/


        goods_price=findViewById(R.id.goods_price);
        goods_publish=findViewById(R.id.goods_publish);
        goods_name=findViewById(R.id.goods_name);
        goods_author=findViewById(R.id.goods_author);
        goods_description=findViewById(R.id.goods_description);
        goods_photo=findViewById(R.id.goods_photo);

        back=findViewById(R.id.tb_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn1=findViewById(R.id.btn_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GoodsFragment.this,"已添加至购物车",Toast.LENGTH_SHORT).show();
                /*bookattribute
                * 插入该数据到购物车表*/
                String[] selects = {
                        username,bookAttribute.getBookName()
                };
                db = myHelper.getWritableDatabase();
                Cursor cursor = db.query(Constans.CART_TABLE,null,"Username=? and Bookname=?",selects,null,null,null,null);
                /*先做一个查询操作，看购物车表中是否有该物品存在，若存在将数量加一。若不存在则将该物品插入，数量置一。*/
                if (cursor.moveToNext()){
                    String number = cursor.getString(cursor.getColumnIndex("Number"));
                    int temp = Integer.parseInt(number);
                    temp =temp+1;
                    String cnum = String.valueOf(temp);
                    String sql = "UPDATE "+Constans.CART_TABLE+" SET Number = '"+cnum+"' WHERE Username = '"+username+"' AND Bookname = '"+bookAttribute.getBookName()+"'";
                    db.execSQL(sql);
                }
                else {
                    String picname = getpicname(bookAttribute.getBookID());
                    ContentValues values = new ContentValues();
                    values.put("Username",username);
                    values.put("Picture",picname);
                    values.put("Bookname",bookAttribute.getBookName());
                    values.put("Number","1");
                    values.put("Cost",bookAttribute.getBookPrice());
                    db.insert(Constans.CART_TABLE,null,values);
                }
                /*添加到购物车按钮*/

                cursor.close();
                db.close();
            }
        });

        /*btn2=findViewById(R.id.btn_2);*/





        goods_author.setText(bookAttribute.getBookAuthor());
        goods_description.setText(bookAttribute.getBookDescription());
        goods_name.setText(bookAttribute.getBookName());
        goods_photo.setImageResource(bookAttribute.getBookImage());
        goods_publish.setText(bookAttribute.getBookPublish());
        goods_price.setText(bookAttribute.getBookPrice());


        initImagelist();
        initBanner();







    }


    /*private ContentValues initvalues(String picname){
        ContentValues values = new ContentValues();
        values.put("Username",username);
        values.put("Picture",picname);
        values.put("Bookname",bookAttribute.getBookName());
        values.put("Number","1");
        values.put("Cost",bookAttribute.getBookPrice());
        return values;
    }*/
    private String getpicname(String bookid){
        String picname = "a"+bookid;


        System.out.println(picname);


        return picname;
    }

    private void initBanner() {
        banner = findViewById(R.id.banner_goods);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setImages(imglist)
                .setBannerAnimation(Transformer.DepthPage)
                .isAutoPlay(true)
                .setDelayTime(1500)
                .start();
    }

    private void initImagelist() {
        imglist.add(bookAttribute.getBookImage());
    }
}

