package com.example.booksale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import com.example.booksale.fragment.CartFragment;
import com.example.booksale.fragment.CategoryFragment;
import com.example.booksale.fragment.HomeFragment;
import com.example.booksale.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private FragmentManager mfragmentManager;
    private BottomNavigationBar bottomNavigationBar;


    private int lastSelectedPosition = 0;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private CartFragment cartFragment;
    private MineFragment mineFragment;

    private Toolbar toolbar;

    private String TAG = MainActivity.class.getSimpleName();
    private Bundle bundle;
    private String user;

    //FragmentTransaction 一个事物只能被提交一次，不可以重复提交，可以使用局部变量进行提交，尽量避免使用成员变量。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mfragmentManager = getSupportFragmentManager();
        if (savedInstanceState!=null){
            homeFragment = (HomeFragment) mfragmentManager.findFragmentByTag("home");
            cartFragment = (CartFragment) mfragmentManager.findFragmentByTag("cart");
            categoryFragment = (CategoryFragment) mfragmentManager.findFragmentByTag("category");
            mineFragment = (MineFragment) mfragmentManager.findFragmentByTag("mine");

            /*使用此方法避免重影*/
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = getIntent().getStringExtra("username");
        /*获取到Login传递过来的用户名值*/
        /*bundle = new Bundle();
        bundle.putString("username", user);
        System.out.println("0");*/

        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);


        init();

    }

    private void init() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        //设计按钮模式
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.home,"主页"))
                            .addItem(new BottomNavigationItem(R.mipmap.comments,"分类"))
                            .addItem(new BottomNavigationItem(R.mipmap.cart,"购物车")).setActiveColor(R.color.red)
                            .addItem(new BottomNavigationItem(R.mipmap.account,"我的"))
                            .setFirstSelectedPosition(lastSelectedPosition)
                            .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        mfragmentManager = getSupportFragmentManager();
        FragmentTransaction mft = mfragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        mft.replace(R.id.fragment_content,homeFragment /*HomeFragment.newInstance("主页")*/);
        mft.commit();
    }


    @Override
    public void onTabSelected(int position) {
        /*lastSelectedPosition  =  position;*/
        mfragmentManager = getSupportFragmentManager();
        FragmentTransaction mft ;
        mft = mfragmentManager.beginTransaction();
        hideALL();
        switch (position){
            case 0 :
                if (homeFragment == null){
                    //homeFragment = HomeFragment.newInstance("主页");
                    homeFragment = new HomeFragment();
                    /*homeFragment.setArguments(bundle);*/
                    mft.add(R.id.fragment_content,homeFragment,"home");
                }

                    mft.show(homeFragment);

                //mTransaction.replace(R.id.fragment_content,homeFragment);
                break;
            case 1:
                if (categoryFragment == null){
                    //categoryFragment=CategoryFragment.newInstance("分类");
                    categoryFragment = new CategoryFragment();
                    mft.add(R.id.fragment_content,categoryFragment,"category");
                }

                mft.show(categoryFragment);
                //mTransaction.replace(R.id.fragment_content,categoryFragment);
                break;
            case 2:
                if (cartFragment == null){
                    //cartFragment= CartFragment.newInstance("购物车");
                    cartFragment = new CartFragment();
                    mft.add(R.id.fragment_content,cartFragment,"cart");
                }

                mft.show(cartFragment);



               // mTransaction.replace(R.id.fragment_content,cartFragment);
                break;
            case 3:
                if (mineFragment == null) {
                    //mineFragment=MineFragment.newInstance("我的");
                    mineFragment = new MineFragment();
                    mft.add(R.id.fragment_content,mineFragment,"mine");
                }
                mft.show(mineFragment);


                //mTransaction.replace(R.id.fragment_content,mineFragment);
                break;
            default:
                    break;
        }
        mft.commit();
    }
    private void hideALL(){
        FragmentTransaction mft;
        mft = mfragmentManager.beginTransaction();
        if (homeFragment != null){
            mft.hide(homeFragment);
        }
        if (cartFragment != null){
            mft.hide(cartFragment);
        }
        if (categoryFragment != null){
            mft.hide(categoryFragment);
        }
        if (mineFragment != null){
            mft.hide(mineFragment);
        }
        mft.commit();
    }
    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");


    }

    @Override
    public void onTabReselected(int position) {

    }

}
