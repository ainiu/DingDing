package com.baidu.dingding.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2015/12/9.
 */
public class AddressDao {
    private BlackDBHelper mHelper;

    public AddressDao(Context context) {
        mHelper = new BlackDBHelper(context);
    }
    /**
     * 插入数据
     * @return 如果为true 插入成功
     */
    public boolean add(String id, String name ,String pid, String level) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("name",name);
        values.put("pid",pid);
        values.put("level", level);
        long insert = db.insert(BlackDB.TableAddress.DATABASE_TABLE, null, values);
        db.close();

        return insert != -1;
    }
}
