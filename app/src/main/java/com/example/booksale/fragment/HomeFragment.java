package com.example.booksale.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksale.Banner.GlideImageLoader;
import com.example.booksale.Constans;
import com.example.booksale.Dao;
import com.example.booksale.GoodsFragment;
import com.example.booksale.MainActivity;
import com.example.booksale.MyHelper;
import com.example.booksale.R;

import com.example.booksale.recycleView.BookAdapter;
import com.example.booksale.recycleView.BookAttribute;
import com.example.booksale.toolbar.MyToolbar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment /*extends BaseFragment */ extends Fragment {
    private Banner banner;
    private List<Integer> imglist = new ArrayList<>();
    private List<BookAttribute> bookAttributeList = new ArrayList<BookAttribute>();
    private EditText searchview;
    private View view;


    private Dao dao;
    private SQLiteDatabase db;
    private SQLiteOpenHelper myHelper;
    private String user;


  /*  @Override
    public void onStart() {
        super.onStart();

        if (getArguments()!=null){
            user = getArguments().getString("username");
        }

        System.out.println("9");
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        /*直接从MainActivity获取数据*/
        user = getActivity().getIntent().getStringExtra("username");
        System.out.println("1");


        searchview = view.findViewById(R.id.et_toolbar_search);
        searchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"打开搜索框",Toast.LENGTH_SHORT).show();
            }
        });
        imglist.add(R.drawable.banner1);
        imglist.add(R.drawable.banner2);
        imglist.add(R.drawable.banner3);

        banner = view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setImages(imglist)
                .setBannerAnimation(Transformer.DepthPage)
                .isAutoPlay(true)
                .setDelayTime(1500)
                .setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getActivity(), "这是第"+position+"张图", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
        initData();
        initRecycleView();

    return view;
    }

    private void initData() {
        myHelper=new MyHelper(getActivity());
        db = myHelper.getReadableDatabase();
        Cursor cursor = db.query(Constans.BOOK_TABLE,null,null,null,null,null,null,null);
        while(cursor.moveToNext()){

            String bookid = cursor.getString(cursor.getColumnIndex("Booknumber"));
            String bookname = cursor.getString(cursor.getColumnIndex("Bookname"));
            String bookprice = cursor.getString(cursor.getColumnIndex("Price"));
            String bookauthor = cursor.getString(cursor.getColumnIndex("Author"));
            String bookpublish = cursor.getString(cursor.getColumnIndex("Publish"));
            String bookdescription = cursor.getString(cursor.getColumnIndex("Decrible"));
            int bookpicid = getResource(cursor.getString(cursor.getColumnIndex("Picture")));
            String booktype = cursor.getString(cursor.getColumnIndex("Type"));
            bookAttributeList.add(new BookAttribute(bookid,bookname,bookprice,bookauthor,bookpublish,bookdescription,bookpicid,booktype));
        }
        cursor.close();
        db.close();
    }



    private void initRecycleView() {
        RecyclerView recycler_view = view.findViewById(R.id.recycle_view);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getActivity(),2,RecyclerView.VERTICAL,false);
        BookAdapter bookAdapter = new BookAdapter(bookAttributeList,getActivity());
        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, BookAttribute data) {
                Toast.makeText(getActivity(),data.getBookName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GoodsFragment.class);
                intent.putExtra("book",data);
                intent.putExtra("username",user);
                startActivity(intent);
            }
        });
        recycler_view.setLayoutManager(gridLayoutManager);
        recycler_view.setAdapter(bookAdapter);

    }

    public int  getResource(String imageName){
        Context ctx=getActivity();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
