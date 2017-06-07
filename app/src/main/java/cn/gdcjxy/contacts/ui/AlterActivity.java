package cn.gdcjxy.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.gdcjxy.contacts.MainActivity;
import cn.gdcjxy.contacts.R;
import cn.gdcjxy.contacts.bean.Contact;
import cn.gdcjxy.contacts.dao.ContactDao;

public class AlterActivity extends AppCompatActivity {

    private Long id;
    private String name;
    private String tel;

    private EditText editText_altername;
    private EditText editText_altertel;
    private Button button_alter;
    private Button button_del;
    // 数据库增删改查操作类
    private ContactDao dao;

    private static final String TAG = "AlterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);
        dao = new ContactDao(this);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        name = intent.getStringExtra("name");
        tel = intent.getStringExtra("tel");
        initView();
    }

    private void initView() {
        editText_altername = (EditText) findViewById(R.id.editText_altername);
        editText_altertel = (EditText) findViewById(R.id.editText_altertel);
        editText_altername.setText(name);
        editText_altertel.setText(tel);
        button_alter = (Button) findViewById(R.id.button_alter);
        button_del = (Button) findViewById(R.id.button_del);
        button_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact c = new Contact(id,editText_altername.getText().toString(),editText_altertel.getText().toString());
                Log.i(TAG, "onClick: "+c.toString());
                dao.update(c); // 更新数据库
                Toast.makeText(AlterActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AlterActivity.this, MainActivity.class));
            }
        });
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.delete(id);
                Toast.makeText(AlterActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AlterActivity.this, MainActivity.class));
            }
        });
    }
}
