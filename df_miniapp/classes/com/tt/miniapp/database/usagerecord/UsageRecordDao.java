package com.tt.miniapp.database.usagerecord;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import com.a;
import com.tt.miniapp.database.base.BaseDao;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.List;

public class UsageRecordDao extends BaseDao {
  public UsageRecordDao(SQLiteOpenHelper paramSQLiteOpenHelper) {
    super(paramSQLiteOpenHelper);
  }
  
  public void deleteExpiredUsageRecordInfos() {
    beginTrans();
    String str = a.a("delete from %s where startTime<=?", new Object[] { "TB_USAGE_RECODR" });
    this.db.execSQL(str, new Object[] { Long.valueOf(System.currentTimeMillis() - 172800000L) });
    commit();
  }
  
  public List<UsageRecordInfo> getAvailUsageRecordInfoList(List<String> paramList) {
    ArrayList<UsageRecordInfo> arrayList = new ArrayList();
    if (paramList != null) {
      if (paramList.isEmpty())
        return arrayList; 
      try {
        this.db = this.helper.getReadableDatabase();
        String str = a.a("select appID,startTime,duration,scene,subScene from %s where startTime>?", new Object[] { "TB_USAGE_RECODR" });
        this.cursor = this.db.rawQuery(str, new String[] { String.valueOf(System.currentTimeMillis() - 172800000L) });
        while (this.cursor.moveToNext()) {
          str = this.cursor.getString(this.cursor.getColumnIndex("appID"));
          if (paramList.contains(str)) {
            UsageRecordInfo usageRecordInfo = new UsageRecordInfo();
            usageRecordInfo.appID = str;
            usageRecordInfo.startTime = Long.valueOf(this.cursor.getLong(this.cursor.getColumnIndex("startTime")));
            usageRecordInfo.duration = Long.valueOf(this.cursor.getLong(this.cursor.getColumnIndex("duration")));
            usageRecordInfo.scene = this.cursor.getString(this.cursor.getColumnIndex("scene"));
            usageRecordInfo.subScene = this.cursor.getString(this.cursor.getColumnIndex("subScene"));
            arrayList.add(usageRecordInfo);
          } 
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("UsageRecordDao", new Object[] { exception });
      } finally {}
      closeCursor();
      this.db.close();
      return arrayList;
    } 
    return arrayList;
  }
  
  public void insert(UsageRecordInfo paramUsageRecordInfo) {
    beginTrans();
    ContentValues contentValues = new ContentValues();
    if (paramUsageRecordInfo != null) {
      contentValues.put("appID", paramUsageRecordInfo.appID);
      contentValues.put("startTime", paramUsageRecordInfo.startTime);
      contentValues.put("duration", paramUsageRecordInfo.duration);
      contentValues.put("scene", paramUsageRecordInfo.scene);
      contentValues.put("subScene", paramUsageRecordInfo.subScene);
    } 
    this.db.insert("TB_USAGE_RECODR", null, contentValues);
    commit();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\databas\\usagerecord\UsageRecordDao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */