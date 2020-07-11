package com.tt.miniapp.database.recentapps;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.a;
import com.tt.miniapphost.AppBrandLogger;

public class RecentAppsDbOpenHelper extends SQLiteOpenHelper {
  public RecentAppsDbOpenHelper(Context paramContext) {
    super(paramContext, "DB_NAME_RECENT_APPS", null, 1);
  }
  
  private void createTable(SQLiteDatabase paramSQLiteDatabase) {
    String str = a.a("create table %s(appID text,appName text, ttid text,icon text,type integer,orientation integer,mark integer,minJssdk text,schema text,state integer,summary text,timestamp Long,UNIQUE(appID));", new Object[] { "TB_RECENT_APPS" });
    try {
      paramSQLiteDatabase.execSQL(str);
      return;
    } catch (SQLException sQLException) {
      AppBrandLogger.e("RecentAppsDbOpenHelper", new Object[] { sQLException });
      return;
    } 
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
    createTable(paramSQLiteDatabase);
  }
  
  public void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    if (paramInt1 > paramInt2)
      paramSQLiteDatabase.execSQL(a.a("drop table if exists %s;", new Object[] { "TB_RECENT_APPS" })); 
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\database\recentapps\RecentAppsDbOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */