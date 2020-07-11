package com.tt.miniapp.shortcut;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.shortcut.handler.ShortcutRequest;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.util.ArrayList;
import java.util.List;

public class ShortcutService extends AppbrandServiceManager.ServiceBase {
  public Context mContext;
  
  private boolean mLastResult;
  
  private boolean mOpenSettingPage;
  
  private BroadcastReceiver mResultReceiver;
  
  private List<ShortcutRequest> mShortcutRequestPool = new ArrayList<ShortcutRequest>();
  
  private boolean mShouldShowDialog = true;
  
  public ShortcutService(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private void addShortcutAuto() {
    final AppInfoEntity appInfo = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null)
      return; 
    AppbrandContext.mainHandler.postDelayed(new Runnable() {
          public void run() {
            ShortcutEventReporter.reportClick("user");
            ShortcutEntity shortcutEntity = (new ShortcutEntity.Builder()).setAppId(appInfo.appId).setIcon(appInfo.icon).setLabel(appInfo.appName).setAppType(appInfo.type).build();
            ShortcutService shortcutService = ShortcutService.this;
            shortcutService.tryToAddShortcut((Activity)shortcutService.mContext, shortcutEntity);
          }
        }300L);
  }
  
  private ShortcutRequest getPendingRequest(String paramString) {
    for (ShortcutRequest shortcutRequest : this.mShortcutRequestPool) {
      if (TextUtils.equals(paramString, shortcutRequest.id))
        return shortcutRequest; 
    } 
    return null;
  }
  
  private void registerIntentCallback(String paramString) {
    if (this.mResultReceiver == null) {
      this.mResultReceiver = new ShortcutResultReceiver();
      IntentFilter intentFilter = new IntentFilter();
      StringBuilder stringBuilder = new StringBuilder("com.tt.appbrand.shorcut.");
      stringBuilder.append(paramString);
      intentFilter.addAction(stringBuilder.toString());
      AppbrandContext.getInst().getApplicationContext().registerReceiver(this.mResultReceiver, intentFilter);
    } 
  }
  
  public List<ShortcutRequest> getPendingList() {
    return this.mShortcutRequestPool;
  }
  
  public boolean isShowDialog() {
    return this.mShouldShowDialog;
  }
  
  public void onActivityPause() {
    this.mShouldShowDialog = false;
  }
  
  public void onActivityResume() {
    this.mShouldShowDialog = true;
    if (this.mOpenSettingPage) {
      this.mOpenSettingPage = false;
      if (!this.mLastResult)
        addShortcutAuto(); 
    } 
  }
  
  public void onResult(String paramString) {
    ShortcutRequest shortcutRequest = getPendingRequest(paramString);
    if (shortcutRequest == null)
      return; 
    shortcutRequest.callback(new ShortcutResult(ShortcutResult.Result.NEED_CHECK, null));
  }
  
  public void setOpenSettingPage(boolean paramBoolean) {
    this.mOpenSettingPage = paramBoolean;
  }
  
  public void setShortcutState(boolean paramBoolean) {
    this.mLastResult = paramBoolean;
  }
  
  public void tryToAddShortcut(Activity paramActivity, ShortcutEntity paramShortcutEntity) {
    if (paramActivity == null || paramShortcutEntity == null) {
      AppBrandLogger.i("ShortcutService", new Object[] { "tryToAddShortCut is called, but params is null" });
      ShortcutEventReporter.reportResult("no", "add shortcut params error");
      return;
    } 
    this.mContext = (Context)paramActivity;
    this.mLastResult = false;
    this.mShouldShowDialog = true;
    ShortcutRequest shortcutRequest = new ShortcutRequest(paramActivity, paramShortcutEntity);
    shortcutRequest.request();
    this.mShortcutRequestPool.add(shortcutRequest);
    registerIntentCallback(paramShortcutEntity.getAppId());
  }
  
  public void unregisterIntentCallback() {
    if (this.mResultReceiver != null) {
      AppbrandContext.getInst().getApplicationContext().unregisterReceiver(this.mResultReceiver);
      this.mResultReceiver = null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\ShortcutService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */