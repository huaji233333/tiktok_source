package com.tt.miniapp.database.base;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDao {
  protected Cursor cursor;
  
  protected SQLiteDatabase db;
  
  protected SQLiteOpenHelper helper;
  
  public BaseDao(SQLiteOpenHelper paramSQLiteOpenHelper) {
    this.helper = paramSQLiteOpenHelper;
  }
  
  public void beginTrans() {
    this.db = this.helper.getWritableDatabase();
    this.db.beginTransaction();
  }
  
  public void closeCursor() {
    Cursor cursor = this.cursor;
    if (cursor != null) {
      cursor.close();
      this.cursor = null;
    } 
  }
  
  public void commit() {
    this.db.setTransactionSuccessful();
    this.db.endTransaction();
    this.db.close();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\database\base\BaseDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */