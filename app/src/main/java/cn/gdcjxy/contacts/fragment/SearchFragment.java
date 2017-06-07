package cn.gdcjxy.contacts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.gdcjxy.contacts.R;
import cn.gdcjxy.contacts.bean.Contact;
import cn.gdcjxy.contacts.dao.ContactDao;
import cn.gdcjxy.contacts.ui.AlterActivity;
import cn.gdcjxy.contacts.ui.ReFlashListView;

public class SearchFragment extends Fragment {

    private View view;
    private ReFlashListView contacts_list;
    private MyAdapter adapter;
    private EditText editText_searchname;
    private EditText editText_searchtel;
    private Button button_searchname;
    private Button button_searchtel;


    // 需要适配的数据集合
    private List<Contact> list;
    // 数据库增删改查操作类
    private ContactDao dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container, false);
        if(view!=null){
            initView();
            dao = new ContactDao(getContext());
            // 从数据库查询出所有数据
//            list = dao.queryAll();
//            adapter = new MyAdapter();
//            contacts_list.setAdapter(adapter);// 给ListView添加适配器(自动把数据生成条目)
        }
        return view;
    }

    private void initView() {
        contacts_list = (ReFlashListView) view.findViewById(R.id.contacts_search_list);
        contacts_list.setOnItemClickListener(new MyOnItemClickListener());
        editText_searchname = (EditText) view.findViewById(R.id.editText_searchname);
        editText_searchtel = (EditText) view.findViewById(R.id.editText_searchtel);
        button_searchname = (Button) view.findViewById(R.id.button_searchname);
        button_searchtel = (Button) view.findViewById(R.id.button_searchtel);
        button_searchname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_searchtel.setText("");
                String name=editText_searchname.getText().toString();
                if(!"".equals(name)){
                    list = dao.queryByName(name);
                    adapter = new MyAdapter();
                    contacts_list.setAdapter(adapter);// 给ListView添加适配器(自动把数据生成条目)
                }else {
                    editText_searchname.setError(getString(R.string.error_field_required));
                    editText_searchname.requestFocus();
                }
            }
        });
        button_searchtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_searchname.setText("");
                String tel=editText_searchtel.getText().toString();
                if(!"".equals(tel)){
                    list = dao.queryByTel(tel);
                    adapter = new MyAdapter();
                    contacts_list.setAdapter(adapter);// 给ListView添加适配器(自动把数据生成条目)
                }else {
                    editText_searchtel.setError(getString(R.string.error_field_required));
                    editText_searchtel.requestFocus();
                }

            }
        });

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
            contacts_id.setText(a.getId()+"");
            contacts_name.setText(a.getName()+"");
            contacts_tel.setText(a.getTel()+"");
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
