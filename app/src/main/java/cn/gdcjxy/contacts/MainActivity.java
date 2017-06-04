package cn.gdcjxy.contacts;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.gdcjxy.contacts.fragment.AlterFragment;
import cn.gdcjxy.contacts.fragment.ContactsFragment;
import cn.gdcjxy.contacts.fragment.DelFragment;
import cn.gdcjxy.contacts.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String>mTitle;
    private List<Fragment>mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();        
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("通讯录");
        mTitle.add("修改");
        mTitle.add("查找");
        mTitle.add("删除");

        mFragment = new ArrayList<>();
        mFragment.add(new ContactsFragment());
        mFragment.add(new AlterFragment());
        mFragment.add(new SearchFragment());
        mFragment.add(new DelFragment());
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPage);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

           //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        });
    }

}
