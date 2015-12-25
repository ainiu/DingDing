package com.baidu.dingding.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackDBHelper extends SQLiteOpenHelper {

	public BlackDBHelper(Context context) {
		super(context, BlackDB.DATABASE_NAME, null, BlackDB.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表
		db.execSQL(BlackDB.TableAddress.DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
