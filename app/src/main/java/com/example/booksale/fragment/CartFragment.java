package com.example.booksale.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksale.Constans;
import com.example.booksale.Dao;
import com.example.booksale.MyHelper;
import com.example.booksale.R;
import com.example.booksale.recycleView.CartAdapter;
import com.example.booksale.recycleView.CartAttribute;

import java.util.ArrayList;
import java.util.List;

public class CartFragment /*extends BaseFragment*/ extends Fragment {
    private View view ;
    private Boolean aBoolean = false;
    private List<CartAttribute> cartAttributes = new ArrayList<CartAttribute>();
    private CheckBox checkBox;
    private  List<Boolean> booleans = new ArrayList<Boolean>();
    private TextView subnum;
    private Button delete;
    private Button buy;
    List<Integer> list=new ArrayList<Integer>();

    private List<Integer> index = new ArrayList<Integer>();
    private boolean reload = false;

    private Dao dao;
    private SQLiteDatabase db;
    private SQLiteOpenHelper myHelper;
    private String user;
    private CartAdapter cartAdapter;
    private int subnums;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart,container,false);

        /*直接从MainActivity获取数据*/
        user = getActivity().getIntent().getStringExtra("username");





        checkBox = view.findViewById(R.id.main_check_all);
        subnum = view.findViewById(R.id.main_price);
        delete= view.findViewById(R.id.btn_delete);
        buy = view.findViewById(R.id.btn_buy);

        initData();
        initCount();
        initBoolearns();
        initRecylcView();


        return view;
    }
/*
*
*
*
*
* 切换时重新加载----------------------------------------------------------------------------------------*/

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            clearview();
        }else {
            initData();
            initCount();
            initBoolearns();
            initRecylcView();
        }
    }

    public void clearview(){
        for (int i=0;i<cartAttributes.size();i++){
            if (cartAttributes.get(i)!=null ){
                cartAttributes.remove(i);
                list.remove(i);
                booleans.remove(i);
                i--;
            }
        }
        cartAdapter.notifyDataSetChanged();
    }

    private void initCount() {

        for (int i=0;i<cartAttributes.size();i++){

            list.add(1);
            list.set(i,1);
        }
    }

    private void initBoolearns() {
        for (int i=0;i<cartAttributes.size();i++){
            booleans.add(false);
        }

    }


    private void initData() {
        myHelper=new MyHelper(getActivity());
        db = myHelper.getReadableDatabase();
        String [] usernames = {
                user
        };
        Cursor cursor = db.query(Constans.CART_TABLE,null,"Username=?",usernames,null,null,null,null);
        while(cursor.moveToNext()){

            String temp_count = cursor.getString(cursor.getColumnIndex("Number"));
            int bookcount = Integer.valueOf(temp_count);
            String bookname = cursor.getString(cursor.getColumnIndex("Bookname"));
            String temp_price = cursor.getString(cursor.getColumnIndex("Cost"));
            int bookprice = Integer.valueOf(temp_price);
            String temp_picid = cursor.getString(cursor.getColumnIndex("Picture"));
            int bookpicid = getResource(temp_picid);
            System.out.println("0");
            cartAttributes.add(new CartAttribute(bookname,bookpicid,bookprice,bookcount,aBoolean));
        }
        cursor.close();
        db.close();
    }

    private void changemoney(List<Integer> list, List<Boolean> booleans){
        subnums = 0;
        for (int i=0;i<list.size();i++){
            if (booleans.get(i)){
                subnums += cartAttributes.get(i).getBookprice()*list.get(i);
            }
        }
        subnum.setText(subnums +"");
    }


    private void initRecylcView() {
        RecyclerView recyclerView = view.findViewById(R.id.cart_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        cartAdapter = new CartAdapter(cartAttributes,getActivity());
        cartAdapter.setOnItemClickListener(new CartAdapter.OnCheckboxClickListener() {
            @Override
            public void OnCheckboxClick(View view, CartAttribute data,Boolean flag) {
          /*      Toast.makeText(getActivity(),"选中index"+view.getTag(),Toast.LENGTH_SHORT).show();*/
                if (flag==true){
                    Toast.makeText(getActivity(),"选中index"+view.getTag(),Toast.LENGTH_SHORT).show();
                    int position = (int) view.getTag();
                    data.setCheck(true);
                    booleans.set(position,true);
                }
                else {
                    Toast.makeText(getActivity(),"取消index"+view.getTag(),Toast.LENGTH_SHORT).show();
                    int position = (int) view.getTag();
                    booleans.set(position,false);
                    data.setCheck(false);
                    checkBox.setChecked(false);
                }
                changemoney(list,booleans);
                cartAdapter.notifyDataSetChanged();

            }
        });




        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    for (int i = 0; i <cartAttributes.size(); i++) {
                        cartAttributes.get(i).setCheck(true);
                        booleans.set(i,true);
                    }

                }
                if (checkBox.isChecked() == false) {
                    for (int i = 0; i <cartAttributes.size(); i++) {
                        cartAttributes.get(i).setCheck(false);
                        booleans.set(i,false);
                    }

                }
                changemoney(list,booleans);
                cartAdapter.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = 0;
                for (int i=0;i<cartAttributes.size();i++){
                    if (cartAttributes.get(i)!=null && cartAttributes.get(i).isCheck()){
                        db = myHelper.getWritableDatabase();
                        String sql = "DELETE FROM "+Constans.CART_TABLE+" WHERE Username = '"+user+"' AND Bookname = '"+cartAttributes.get(i).getBookname()+"'";
                        db.execSQL(sql);



                        cartAttributes.remove(i);
                        list.remove(i);
                        booleans.remove(i);
                        i--;
                        d++;
                    }
                }
                cartAdapter.notifyDataSetChanged();
                if (d==0){
                    Toast.makeText(getActivity(),"没有选择",Toast.LENGTH_SHORT).show();
                }
                changemoney(list,booleans);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<cartAttributes.size();i++){
                    if (cartAttributes.get(i)!=null && cartAttributes.get(i).isCheck()){
                        db = myHelper.getWritableDatabase();
                        String sql = "DELETE FROM "+Constans.CART_TABLE+" WHERE Username = '"+user+"' AND Bookname = '"+cartAttributes.get(i).getBookname()+"'";
                        db.execSQL(sql);

                        String [] values = {
                                user,cartAttributes.get(i).getBookname()
                        };

                        Cursor cursor = db.query(Constans.HISTORY_TABLE,null,"Username=? and Bookname=?",values,null,null,null,null);
                        if (cursor.moveToNext()){
                            String number = cursor.getString(cursor.getColumnIndex("Number"));
                            int temp = Integer.parseInt(number);
                            temp =temp+1;
                            String cnum = String.valueOf(temp);
                            String sql1 = "UPDATE "+Constans.HISTORY_TABLE+" SET Number = '"+cnum+"' WHERE Username = '"+user+"' AND Bookname = '"+cartAttributes.get(i).getBookname()+"'";
                            db.execSQL(sql1);
                        }else {

                            ContentValues buyzhi = new ContentValues();
                            buyzhi.put("Username",user);
                            buyzhi.put("Bookname",cartAttributes.get(i).getBookname());
                            buyzhi.put("Number",list.get(i)+"");
                            buyzhi.put("Cost",String.valueOf(cartAttributes.get(i).getBookprice()*list.get(i)));
                            db.insert(Constans.HISTORY_TABLE,null,buyzhi);
                        }
                        cursor.close();
                        db.close();

                        cartAttributes.remove(i);
                        list.remove(i);
                        booleans.remove(i);
                        i--;
                    }
                }
                Toast.makeText(getActivity(),"你已经购买成功了",Toast.LENGTH_SHORT).show();
                cartAdapter.notifyDataSetChanged();
            }
        });








        cartAdapter.setOnNumAddClickListener(new CartAdapter.OnNumAddClickListener() {
            @Override
            public void OnNumAddClick(View view, CartAttribute data) {
                int newcount = data.getCount()+1;
                cartAttributes.get((Integer) view.getTag()).setCount(newcount);
                /*count[(int) view.getTag()] = newcount;*/
                list.set((Integer) view.getTag(),newcount);
                changemoney(list,booleans);
                cartAdapter.notifyItemChanged((Integer) view.getTag());
            }
        });

        cartAdapter.setOnNumReduceClickListener(new CartAdapter.OnNumReduceClickListener() {
            @Override
            public void OnNumReduceClick(View view, CartAttribute data) {
                if (data.getCount()==1){
                    Toast.makeText(getActivity(),"不能再少了",Toast.LENGTH_SHORT).show();
                }else {
                    int newcount = data.getCount()-1;
                    cartAttributes.get((Integer) view.getTag()).setCount(newcount);
                    /*count[(int) view.getTag()] = newcount;*/
                    list.set((Integer) view.getTag(),newcount);
                    changemoney(list,booleans);
                    cartAdapter.notifyItemChanged((Integer) view.getTag());
                }


            }
        });



        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);
    }


    private int  getResource(String imageName){
            Context ctx = getActivity();

            int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
            //如果没有在"mipmap"下找到imageName,将会返回0
            return resId;


    }




}
