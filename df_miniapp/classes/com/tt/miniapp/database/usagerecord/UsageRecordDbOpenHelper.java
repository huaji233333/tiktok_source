package com.tt.miniapp.database.usagerecord;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.a;
import com.tt.miniapphost.AppBrandLogger;

public class UsageRecordDbOpenHelper extends SQLiteOpenHelper {
  public UsageRecordDbOpenHelper(Context paramContext) {
    super(paramContext, "DB_NAME_USAGE_RECORD", null, 2);
  }
  
  private void createTable(SQLiteDatabase paramSQLiteDatabase) {
    String str = a.a("create table %s(_id integer primary key autoincrement,appID text,startTime long,duration long,scene text,subScene text);", new Object[] { "TB_USAGE_RECODR" });
    try {
      paramSQLiteDatabase.execSQL(str);
      return;
    } catch (SQLException sQLException) {
      AppBrandLogger.e("UsageRecordDbOpenHelper", new Object[] { sQLException });
      return;
    } 
  }
  
  private void updateScene(SQLiteDatabase paramSQLiteDatabase) {
    try {
      paramSQLiteDatabase.execSQL(a.a("create table %s(_id integer primary key autoincrement,appID text,startTime long,duration long,scene text,subScene text);", new Object[] { "TB_TEMP_USAGE_RECODR" }));
      paramSQLiteDatabase.execSQL(a.a("insert into %s select * from %s;", new Object[] { "TB_TEMP_USAGE_RECODR", "TB_USAGE_RECODR" }));
      paramSQLiteDatabase.execSQL(a.a("drop table if exists %s;", new Object[] { "TB_USAGE_RECODR" }));
      paramSQLiteDatabase.execSQL(a.a("alter table %s rename to %s;", new Object[] { "TB_TEMP_USAGE_RECODR", "TB_USAGE_RECODR" }));
      return;
    } catch (SQLException sQLException) {
      AppBrandLogger.e("UsageRecordDbOpenHelper", new Object[] { sQLException });
      return;
    } 
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
    createTable(paramSQLiteDatabase);
  }
  
  public void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    if (paramInt1 > paramInt2) {
      paramSQLiteDatabase.execSQL(a.a("drop table if exists %s;", new Object[] { "TB_USAGE_RECODR" }));
      createTable(paramSQLiteDatabase);
    } 
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    if (paramInt1 < paramInt2 && 2 == paramInt2 && paramInt1 == 1)
      updateScene(paramSQLiteDatabase); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\databas\\usagerecord\UsageRecordDbOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */