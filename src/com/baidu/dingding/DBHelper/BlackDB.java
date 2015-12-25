package com.baidu.dingding.DBHelper;

/**
 * 黑名单的数据库
 * 
 */
public interface BlackDB {

	String NAME = "black.db";
	int VERSION = 1;

	/**
	 * 黑名单对应的表
	 * 
	 */
	 interface TableBlack {
		String TABLE_NAME = "black";

		String COLUMN_ID = "_id";
		String COLUMN_NUMBER = "number";
		String COLUMN_TYPE = "type";//

		String CREATE_TABLE_SQL =
				"create table " + TABLE_NAME + "("
				+ COLUMN_ID + " integer primary key autoincrement,"
				+ COLUMN_NUMBER + " text unique," + COLUMN_TYPE + " integer "
				+ ")";

	}

	/**地址数据库*/
	 String DATABASE_NAME = "MyDB.db";
	 int DATABASE_VERSION = 1;

	interface  TableAddress {
		 String KEY_ROWID = "_id";     //id
		 String KEY_PID = "pid";     //��id
		 String KEY_LEVEL = "level";  //����
		 String KEY_CITY = "city";    //����
		 String DATABASE_TABLE = "contacts";
		 String DATABASE_CREATE =
				"create table "+ DATABASE_TABLE +"(" +
						KEY_ROWID+"text Primary key," +
						KEY_PID+"text ," +
						KEY_LEVEL+" text ," +
						KEY_CITY+" text );";
	}
}
