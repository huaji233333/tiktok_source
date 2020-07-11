package com.tt.miniapp.manager;

import android.content.Context;
import android.content.SharedPreferences;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.storage.path.AppbrandPathManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import org.json.JSONObject;

public class StorageManager {
  private static SharedPreferences getMiniAppStorageInfoSP() {
    return KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), getSpName());
  }
  
  public static String getSpName() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(HostDependManager.getInst().getSpPrefixPath());
    stringBuilder.append("mini_app_storage");
    return stringBuilder.toString();
  }
  
  public static void reportDiskOccupy() {
    long l1 = getMiniAppStorageInfoSP().getLong("mini_app_last_report_time", 0L);
    long l2 = System.currentTimeMillis();
    if (l2 - l1 > 86400000L) {
      getMiniAppStorageInfoSP().edit().putLong("mini_app_last_report_time", l2).apply();
      Observable.create(new Action() {
            public final void act() {
              long l1 = System.currentTimeMillis();
              long l2 = AppbrandPathManager.getInstance().getTotalSize();
              long l3 = AppbrandPathManager.getInstance().getDownloadPath().getTotalSize();
              long l4 = AppbrandPathManager.getInstance().getUserPath().getTotalSize();
              long l5 = AppbrandPathManager.getInstance().getSpPath().getTotalSize();
              long l6 = System.currentTimeMillis();
              JSONObject jSONObject = new JSONObject();
              try {
                jSONObject.put("total_size", l2);
                jSONObject.put("pkg_size", l3);
                jSONObject.put("storage_size", l5);
                jSONObject.put("user_size", l4);
                jSONObject.put("calculate_time", l6 - l1);
                jSONObject.put("file_amount", 0);
                AppBrandLogger.d("StorageManager", new Object[] { jSONObject.toString() });
                InnerEventHelper.mpDiskOccupy(jSONObject);
                return;
              } catch (Exception exception) {
                AppBrandLogger.e("StorageManager", new Object[] { exception });
                return;
              } 
            }
          }).schudleOn(Schedulers.longIO()).subscribeSimple();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\StorageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */