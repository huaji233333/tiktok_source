package com.tt.miniapp.manager;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import com.tt.miniapp.database.recentapps.RecentAppsDao;
import com.tt.miniapp.database.recentapps.RecentAppsDbOpenHelper;
import com.tt.miniapp.database.usagerecord.UsageRecordDao;
import com.tt.miniapp.database.usagerecord.UsageRecordDbOpenHelper;
import com.tt.miniapphost.AppbrandContext;

public class DbManager {
  private RecentAppsDao mRecentAppsDao;
  
  private UsageRecordDao mUsageRecordDao;
  
  private DbManager() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    this.mUsageRecordDao = new UsageRecordDao((SQLiteOpenHelper)new UsageRecordDbOpenHelper((Context)application));
    this.mUsageRecordDao.deleteExpiredUsageRecordInfos();
    this.mRecentAppsDao = new RecentAppsDao((SQLiteOpenHelper)new RecentAppsDbOpenHelper((Context)application));
  }
  
  public static DbManager getInstance() {
    return Holder.mInstance;
  }
  
  public RecentAppsDao getRecentAppsDao() {
    return this.mRecentAppsDao;
  }
  
  public UsageRecordDao getUsageRecordDao() {
    return this.mUsageRecordDao;
  }
  
  static class Holder {
    static final DbManager mInstance = new DbManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\DbManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */