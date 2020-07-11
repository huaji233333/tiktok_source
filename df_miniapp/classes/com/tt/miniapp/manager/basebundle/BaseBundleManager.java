package com.tt.miniapp.manager.basebundle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapp.manager.basebundle.handler.BaseBundleHandler;
import com.tt.miniapp.manager.basebundle.handler.BundleHandlerParam;
import com.tt.miniapp.manager.basebundle.handler.CheckBuildInBaseBundleHandler;
import com.tt.miniapp.manager.basebundle.handler.CheckPushBaseBundleHandler;
import com.tt.miniapp.manager.basebundle.handler.ClearNotUsedBaseBundleHandler;
import com.tt.miniapp.manager.basebundle.handler.DownloadBaseBundleHandler;
import com.tt.miniapp.manager.basebundle.handler.ResolveDownloadHandler;
import com.tt.miniapp.manager.basebundle.handler.UpdateSettingsHandler;
import com.tt.miniapp.settings.data.SettingsManager;
import com.tt.miniapp.settings.data.SettingsUpdateListener;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.dynamic.IBaseBundleManager;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.TimeMeter;

public class BaseBundleManager implements IBaseBundleManager {
  public String TAG = "tma_BundleManager";
  
  private BaseBundleVersionEntity getBaseBundleEntity() {
    BaseBundleVersionEntity baseBundleVersionEntity = BaseBundleVersionEntity.getInstance();
    if (TextUtils.isEmpty(baseBundleVersionEntity.getJssdkFourVerUpdateStr())) {
      long l = BaseBundleFileManager.getLatestBaseBundleVersion();
      if (l > 0L)
        baseBundleVersionEntity.setJssdkFourVerUpdateStr(AppbrandUtil.convertVersionCodeToStr(l)); 
    } 
    if (TextUtils.isEmpty(baseBundleVersionEntity.getJssdkThreeVerStr()) && !TextUtils.isEmpty(baseBundleVersionEntity.getJssdkFourVerUpdateStr()))
      baseBundleVersionEntity.setJssdkThreeVerStr(AppbrandUtil.convertFourToThreeVersion(baseBundleVersionEntity.getJssdkFourVerUpdateStr())); 
    AppBrandLogger.d(this.TAG, new Object[] { baseBundleVersionEntity.toLogString() });
    return baseBundleVersionEntity;
  }
  
  public static BaseBundleManager getInst() {
    return Holder.inst;
  }
  
  public void checkUpdateBaseBundle(Context paramContext, boolean paramBoolean) {
    checkUpdateBaseBundle(paramContext, paramBoolean, null);
  }
  
  public void checkUpdateBaseBundle(final Context context, boolean paramBoolean, final BaseBundleUpdateListener listener) {
    Observable.create(new Action() {
          public void act() {
            baseBundleEvent.appendLog("start request remote basebundle version");
            SettingsManager.getInstance().updateSettings(new SettingsUpdateListener() {
                  public void onUpdateComplete() {
                    boolean bool;
                    UpdateSettingsHandler updateSettingsHandler = new UpdateSettingsHandler();
                    BundleHandlerParam bundleHandlerParam = new BundleHandlerParam();
                    bundleHandlerParam.timeMeter = beginTime;
                    if (ChannelUtil.isLocalTest() && !BaseBundleDAO.isLocalTestBundleSwitch(context) && IOUtils.isAssetsFileExist(context, "__dev__.zip")) {
                      bool = true;
                    } else {
                      bool = false;
                    } 
                    bundleHandlerParam.isIgnoreTask = bool;
                    bundleHandlerParam.baseBundleEvent = baseBundleEvent;
                    updateSettingsHandler.setInitialParam(context, bundleHandlerParam);
                    DownloadBaseBundleHandler downloadBaseBundleHandler = new DownloadBaseBundleHandler();
                    updateSettingsHandler.setNextHandler((BaseBundleHandler)downloadBaseBundleHandler);
                    ResolveDownloadHandler resolveDownloadHandler = new ResolveDownloadHandler();
                    downloadBaseBundleHandler.setNextHandler((BaseBundleHandler)resolveDownloadHandler);
                    SetCurrentProcessBundleVersionHandler setCurrentProcessBundleVersionHandler = new SetCurrentProcessBundleVersionHandler();
                    resolveDownloadHandler.setNextHandler(setCurrentProcessBundleVersionHandler);
                    setCurrentProcessBundleVersionHandler.finish();
                    if (listener != null)
                      listener.onBaseBundleUpdate(); 
                  }
                });
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public String getSdkCurrentVersionStr(Context paramContext) {
    String str2 = getBaseBundleEntity().getJssdkFourVerUpdateStr();
    String str1 = str2;
    if (TextUtils.isEmpty(str2))
      str1 = AppbrandUtil.getUpdateVersionStr("1700001"); 
    return str1;
  }
  
  public void handleBaseBundleWhenRestart() {
    Observable.create(new Action() {
          public void act() {
            Application application = AppbrandContext.getInst().getApplicationContext();
            ClearNotUsedBaseBundleHandler clearNotUsedBaseBundleHandler = new ClearNotUsedBaseBundleHandler();
            BundleHandlerParam bundleHandlerParam = new BundleHandlerParam();
            bundleHandlerParam.baseBundleEvent = BaseBundleEventHelper.createEvent("handleBaseBundleWhenRestart", "restart", String.valueOf(BaseBundleFileManager.getLatestBaseBundleVersion()));
            clearNotUsedBaseBundleHandler.setInitialParam((Context)application, bundleHandlerParam);
            CheckBuildInBaseBundleHandler checkBuildInBaseBundleHandler = new CheckBuildInBaseBundleHandler();
            clearNotUsedBaseBundleHandler.setNextHandler((BaseBundleHandler)checkBuildInBaseBundleHandler);
            CheckPushBaseBundleHandler checkPushBaseBundleHandler = new CheckPushBaseBundleHandler();
            checkBuildInBaseBundleHandler.setNextHandler((BaseBundleHandler)checkPushBaseBundleHandler);
            SetCurrentProcessBundleVersionHandler setCurrentProcessBundleVersionHandler = new SetCurrentProcessBundleVersionHandler();
            checkPushBaseBundleHandler.setNextHandler(setCurrentProcessBundleVersionHandler);
            setCurrentProcessBundleVersionHandler.finish();
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public boolean handleTmaTest(final Context context, final Uri uri) {
    Observable.create(new Action() {
          public void act() {
            Uri uri = Uri.parse(Uri.decode(uri.toString()));
            if (TextUtils.equals(uri.getQueryParameter("action"), "sdkUpdate")) {
              String str1 = uri.getQueryParameter("sdkUpdateVersion");
              String str2 = uri.getQueryParameter("sdkVersion");
              String str3 = uri.getQueryParameter("latestSDKUrl");
              StringBuilder stringBuilder = new StringBuilder("sdkUpdateVersion ");
              stringBuilder.append(str1);
              stringBuilder.append(" latestSDKUrl ");
              stringBuilder.append(str3);
              AppBrandLogger.d(stringBuilder.toString(), new Object[0]);
              SharedPreferences.Editor editor = BaseBundleDAO.getJsSdkSP(context).edit();
              editor.putString("sdk_update_version", str1).apply();
              editor.putString("sdk_version", str2).apply();
              editor.putString("latest_sdk_url", str3).apply();
              DownloadBaseBundleHandler downloadBaseBundleHandler = new DownloadBaseBundleHandler();
              BundleHandlerParam bundleHandlerParam = new BundleHandlerParam();
              bundleHandlerParam.timeMeter = TimeMeter.newAndStart();
              bundleHandlerParam.baseBundleEvent = BaseBundleEventHelper.createEvent("handleBaseBundleWhenRestart", "restart", String.valueOf(BaseBundleFileManager.getLatestBaseBundleVersion()));
              downloadBaseBundleHandler.setInitialParam(context, bundleHandlerParam);
              ResolveDownloadHandler resolveDownloadHandler = new ResolveDownloadHandler();
              downloadBaseBundleHandler.setNextHandler((BaseBundleHandler)resolveDownloadHandler);
              SetCurrentProcessBundleVersionHandler setCurrentProcessBundleVersionHandler = new SetCurrentProcessBundleVersionHandler();
              resolveDownloadHandler.setNextHandler(setCurrentProcessBundleVersionHandler);
              setCurrentProcessBundleVersionHandler.finish();
              if (AppbrandConstants.getProcessManager() != null)
                AppbrandConstants.getProcessManager().killAllProcess(); 
              if (!bundleHandlerParam.isIgnoreTask) {
                Context context = context;
                ToastManager.showToast(context, context.getString(2097741864), 0L);
              } 
            } 
          }
        }).subscribeSimple();
    return false;
  }
  
  public boolean isRealBaseBundleReadyNow() {
    return (BaseBundleFileManager.getLatestBaseBundleVersion() > 0L);
  }
  
  public void preload(Context paramContext) {
    getBaseBundleEntity();
  }
  
  void setBaseBundleEntityVersion(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial getBaseBundleEntity : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleVersionEntity;
    //   6: aload_1
    //   7: invokevirtual setJssdkFourVerUpdateStr : (Ljava/lang/String;)V
    //   10: aload_0
    //   11: invokespecial getBaseBundleEntity : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleVersionEntity;
    //   14: aload_2
    //   15: invokevirtual setJssdkThreeVerStr : (Ljava/lang/String;)V
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: astore_1
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_1
    //   25: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	21	finally
  }
  
  public static interface BaseBundleUpdateListener {
    void onBaseBundleUpdate();
  }
  
  static class Holder {
    static BaseBundleManager inst = new BaseBundleManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\BaseBundleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */