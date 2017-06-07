package cn.gdcjxy.contacts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.gdcjxy.contacts.R;
import cn.gdcjxy.contacts.bean.Contact;
import cn.gdcjxy.contacts.dao.ContactDao;
import cn.gdcjxy.contacts.ui.AlterActivity;
import cn.gdcjxy.contacts.ui.ReFlashListView;


public class ContactsFragment extends Fragment implements ReFlashListView.IReflashListener {

    private View view;
    private ReFlashListView contacts_list;
    private MyAdapter adapter;

    // 需要适配的数据集合
    private List<Contact> list;
    // 数据库增删改查操作类
    private ContactDao dao;

//    @Override
//    public void onStart() {
//        //刷新列表
//        list = dao.queryAll();
//        adapter = new MyAdapter();
//        contacts_list.setAdapter(adapter);// 给ListView添加适配器(自动把数据生成条目)
//        super.onStart();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        if (view != null) {
            initView();
            dao = new ContactDao(getContext());
            // 从数据库查询出所有数据
            list = dao.queryAll();
            adapter = new MyAdapter();
            contacts_list.setAdapter(adapter);// 给ListView添加适配器(自动把数据生成条目)
        }

        return view;
    }

    private void initView() {
        contacts_list = (ReFlashListView) view.findViewById(R.id.contacts_list);
        contacts_list.setOnItemClickListener(new MyOnItemClickListener());
        contacts_list.setInterface(this);
    }

    //刷新列表
    @Override
    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list = dao.queryAll();
                adapter = new MyAdapter();
                contacts_list.setAdapter(adapter);// 给ListView添加适配器(自动把数据生成条目)
                contacts_list.reflashComplete();
            }
        }, 2000);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 重用convertView
            convertView = convertView != null ? convertView : View.inflate(getActivity(), R.layout.contacts_item, null);
            // 获取该视图中的TextView
            TextView contacts_id = (TextView) convertView.findViewById(R.id.contacts_id);
            TextView contacts_name = (TextView) convertView.findViewById(R.id.contacts_name);
            TextView contacts_tel = (TextView) convertView.findViewById(R.id.contacts_tel);
            // 根据当前位置获取Account对象
            final Contact a = list.get(position);
            // 把Account对象中的数据放到TextView中
            contacts_id.setText(a.getId() + "");
            contacts_name.setText(a.getName() + "");
            contacts_tel.setText(a.getTel() + "");
            return convertView;
        }

    }

    //ListView的Item点击事件
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // 获取点击位置上的数据
            Contact c = (Contact) parent.getItemAtPosition(position);
            Intent intent = new Intent(getContext(), AlterActivity.class);
            intent.putExtra("id", c.getId());
            intent.putExtra("name", c.getName());
            intent.putExtra("tel", c.getTel());
            startActivity(intent);
        }
    }
}

