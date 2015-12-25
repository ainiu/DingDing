package com.baidu.dingding.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlackDao {

	private BlackDBHelper mHelper;

	public BlackDao(Context context) {
		mHelper = new BlackDBHelper(context);
	}

	/**
	 * 插入数据
	 * 
	 * @param number
	 * @param type
	 * @return 如果为true 插入成功
	 */
	public boolean add(String number, int type) {
		SQLiteDatabase db = mHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BlackDB.TableBlack.COLUMN_NUMBER, number);
		values.put(BlackDB.TableBlack.COLUMN_TYPE, type);
		long insert = db.insert(BlackDB.TableBlack.TABLE_NAME, null, values);

		db.close();

		return insert != -1;
	}


	/**
	 * 通过号码改类型
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	public boolean update(String number, int type) {

		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BlackDB.TableBlack.COLUMN_TYPE, type);

		String whereClause = BlackDB.TableBlack.COLUMN_NUMBER + "=?";
		String[] whereArgs = new String[] { number };
		int update = db.update(BlackDB.TableBlack.TABLE_NAME, values,
				whereClause, whereArgs);

		db.close();

		return update != 0;
	}

	/**
	 * 删除的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean delete(String number) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = BlackDB.TableBlack.COLUMN_NUMBER + "=?";
		String[] whereArgs = new String[] { number };
		int delete = db.delete(BlackDB.TableBlack.TABLE_NAME, whereClause,
				whereArgs);

		db.close();

		return delete != 0;
	}

	/**
	 * 根据号码查类型
	 * 
	 * @param number
	 * @return
	 */
	public int findType(String number) {

		SQLiteDatabase db = mHelper.getReadableDatabase();

		String sql = "select " + BlackDB.TableBlack.COLUMN_TYPE + " from "
				+ BlackDB.TableBlack.TABLE_NAME + " where "
				+ BlackDB.TableBlack.COLUMN_NUMBER + "=?";
		Cursor cursor = db.rawQuery(sql, new String[] { number });

		int type = -1;
		if (cursor != null) {
			if (cursor.moveToNext()) {
				type = cursor.getInt(0);
			}

			cursor.close();
		}
		db.close();

		return type;
	}

	/**
	 * 查询所有的数据
	 * 
	 * @return
	 */
	/*public List<BlackBean> findAll() {
		SQLiteDatabase db = mHelper.getReadableDatabase();

		List<BlackBean> list = new ArrayList<BlackBean>();

		String sql = "select " + BlackDB.TableBlack.COLUMN_NUMBER + ","
				+ BlackDB.TableBlack.COLUMN_TYPE + " from "
				+ BlackDB.TableBlack.TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				String number = cursor.getString(0);
				int type = cursor.getInt(1);

				BlackBean bean = new BlackBean();
				bean.number = number;
				bean.type = type;
				list.add(bean);
			}
			cursor.close();
		}
		db.close();

		return list;
	}*/

	/**
	 * 
	 * @param index
	 *            :从那条记录开始查询
	 * @param size
	 *            :要查多少条记录
	 * @return
	 */
	/*public List<BlackBean> findPart(int index, int size) {
		SQLiteDatabase db = mHelper.getReadableDatabase();

		// limit : 要查询的数量
		// offet : 从什么位置开始查询
		String sql = "select " + BlackDB.TableBlack.COLUMN_NUMBER + ","
				+ BlackDB.TableBlack.COLUMN_TYPE + " from "
				+ BlackDB.TableBlack.TABLE_NAME + " limit " + size + " offset "
				+ index;
		Cursor cursor = db.rawQuery(sql, null);

		List<BlackBean> list = new ArrayList<BlackBean>();

		if (cursor != null) {
			while (cursor.moveToNext()) {
				String number = cursor.getString(0);
				int type = cursor.getInt(1);

				BlackBean bean = new BlackBean();
				bean.number = number;
				bean.type = type;
				list.add(bean);
			}

			cursor.close();
		}
		db.close();

		return list;
	}*/
}
