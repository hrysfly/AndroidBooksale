package com.example.booksale.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksale.FenLeiActivity;
import com.example.booksale.R;

import com.example.booksale.recycleView.FenLei;
import com.example.booksale.recycleView.FenLeiAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment /*extends BaseFragment*/ extends Fragment  {

    private List<FenLei> fenLeis = new ArrayList<FenLei>();
    private View view;

    /*public static CategoryFragment newInstance(String param) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args",param);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_category,container,false);
        initData();
        initRecylcView();

        return view;
    }

    private void initRecylcView() {
        RecyclerView recyclerView = view.findViewById(R.id.category_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        FenLeiAdapter fenLeiAdapter = new FenLeiAdapter(fenLeis,getActivity());
        fenLeiAdapter.setOnItemClickListener(new FenLeiAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, FenLei data) {
                Toast.makeText(getActivity(),"这是"+data.getFenlei(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), FenLeiActivity.class);
                intent.putExtra("type",data);
                startActivity(intent);

            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(fenLeiAdapter);
    }

    private void initData() {
        fenLeis.add(new FenLei("玄幻"));
        fenLeis.add(new FenLei("科幻"));
        fenLeis.add(new FenLei("修仙"));
        fenLeis.add(new FenLei("武侠"));
        fenLeis.add(new FenLei("爱情"));
        fenLeis.add(new FenLei("都市"));
        fenLeis.add(new FenLei("灵异"));
    }

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        Bundle bundle = getArguments();
        String args = bundle.getString("args");
        TextView textView = view.findViewById(R.id.text_category);
        textView.setText(args);
        return view;
    }*/

    /*private boolean isPrepared;
    *//**
     * 是否已被加载过一次，第二次就不再去请求数据了
     *//*
    private boolean mHasLoadedOnce;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_category, container, false);
            initView();
            isPrepared = true;
//        实现懒加载
            lazyLoad();
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }
    *//**
     * 初始化控件
     *//*
    private void initView() {

    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;
    }*/
}
