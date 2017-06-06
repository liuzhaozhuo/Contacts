package cn.gdcjxy.contacts.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.gdcjxy.contacts.bean.Contact;


public class ContactDao {
    private MyHelper helper;

    public ContactDao(Context context) {
        helper = new MyHelper(context); // 创建Dao时, 创建Helper
    }

    public void insert(Contact contact) {
        SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库对象
        // 用来装载要插入的数据的 Map<列名, 列的值>
        ContentValues values = new ContentValues();
        values.put("name", contact.getName());
        values.put("tel", contact.getTel());
        long id = db.insert("contact", null, values); // 向contact表插入数据values,
        contact.setId(id);  // 得到id
        db.close();         // 关闭数据库
    }

    //根据id 删除数据
    public int delete(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // 按条件删除指定表中的数据, 返回受影响的行数
        int count = db.delete("contact", "_id=?", new String[]{id + ""});
        db.close();
        return count;
    }

    //更新数据
    public int update(Contact contact) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues(); // 要修改的数据
        values.put("name", contact.getName());
        values.put("tel", contact.getTel());
        int count = db.update("contact", values, "_id=?",
                new String[]{contact.getId() + ""}); // 更新并得到行数
        db.close();
        return count;
    }

    //查询所有数据按ID升序排列
    public List<Contact> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("contact", null, null, null, null, null,
                "_id");
        List<Contact> list = new ArrayList<Contact>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
            String name = c.getString(1);
            String tel = c.getString(2);
            list.add(new Contact(id, name, tel));
        }
        c.close();
        db.close();
        return list;
    }

    //查询多条数据
    public List<Contact> queryByName(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor c = db.query("contact", null, null, null, null, null,
//                "_id");
        Cursor c = db.rawQuery("select * from contact where name like '%" + name + "%'", null);
        List<Contact> list = new ArrayList<Contact>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
            String sname = c.getString(1);
            String tel = c.getString(2);
            list.add(new Contact(id, sname, tel));
        }
        c.close();
        db.close();
        return list;
    }
    //查询一条数据
    public List<Contact> queryByTel(String tel) {
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor c = db.query("contact", null, null, null, null, null,
//                "_id");
        Cursor c = db.rawQuery("select * from contact where tel='" + tel + "'", null);
        List<Contact> list = new ArrayList<Contact>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
            String name = c.getString(1);
            String stel = c.getString(2);
            list.add(new Contact(id, name, stel));
        }
        c.close();
        db.close();
        return list;
    }
}
