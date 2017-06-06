package cn.gdcjxy.contacts.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.gdcjxy.contacts.bean.User;

public class UserDao {
    private MyHelper helper;
    public UserDao(Context context) {
        helper = new MyHelper(context); // 创建Dao时, 创建Helper
    }
    public void insert(User user) {
        SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库对象
        // 用来装载要插入的数据的 Map<列名, 列的值>
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        long id = db.insert("user", null, values); // 向contact表插入数据values,
        user.setId(id);  // 得到id
        db.close();         // 关闭数据库
    }
    //根据id 删除数据
    public int delete(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // 按条件删除指定表中的数据, 返回受影响的行数
        int count = db.delete("user", "_id=?", new String[] { id + "" });
        db.close();
        return count;
    }
    //更新数据
    public int update(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues(); // 要修改的数据
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        int count = db.update("user", values, "_id=?",
                new String[] { user.getId() + "" }); // 更新并得到行数
        db.close();
        return count;
    }
    //查询所有数据
    public List<User> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("user", null, null, null, null, null,
                "_id DESC");
//        Cursor c = db.rawQuery("select * from user",null);
        List<User> list = new ArrayList<User>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
            String email = c.getString(1);
            String password = c.getString(2);
            list.add(new User(id, email, password));
        }
        c.close();
        db.close();
        return list;
    }
    //登录验证
    public  boolean login(String lemail,String lpassword){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from user where email='"+lemail+"' and password='"+lpassword+"'",null);
        while(c.moveToNext()){
            if((c.getString(1).equals(lemail))&&(c.getString(2).equals(lpassword))){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
