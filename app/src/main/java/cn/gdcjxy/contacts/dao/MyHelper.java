package cn.gdcjxy.contacts.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyHelper extends SQLiteOpenHelper {
    // 由于父类没有无参构造函数, 所以子类必须指定调用父类哪个有参的构造函数
    public MyHelper(Context context) {
        super(context, "gdcjxy.db", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "email VARCHAR(50)," + // email
                "password VARCHAR(50))"); // 手机号码列
        db.execSQL("CREATE TABLE contact(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR(20)," + // 姓名列
                "tel VARCHAR(11))"); // 手机号码列
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpgrade");
    }
}
