package cn.gdcjxy.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.gdcjxy.contacts.fragment.ContactsFragment;
import cn.gdcjxy.contacts.fragment.SearchFragment;
import cn.gdcjxy.contacts.ui.AddActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitle;
    private List<Fragment> mFragment;
    private FloatingActionButton fab_add;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setElevation(0);

        initData();
        initView();
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("通讯录");
        mTitle.add("查找");

        mFragment = new ArrayList<>();
        mFragment.add(new ContactsFragment());
        mFragment.add(new SearchFragment());
    }

    private void initView() {
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);
        //默认显示
        fab_add.setVisibility(View.VISIBLE);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPage);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());


        //ViewPager的滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    fab_add.setVisibility(View.VISIBLE);
                }else {
                    fab_add.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add:
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                break;
        }
    }
}
