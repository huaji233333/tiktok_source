package com.tt.miniapphost;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.dynamic.IBundleManager;
import com.tt.miniapphost.entity.DisableStateEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageUtils;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.miniapphost.util.UIUtils;

public class BundleManager implements IBundleManager {
  public String TAG = "BundleManager";
  
  private boolean isCurrentDevicesSupport(int paramInt) {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null)
      return false; 
    int i = SettingsDAO.getInt((Context)application, 0, new Enum[] { (Enum)Settings.TT_TMA_BLACKLIST, (Enum)Settings.TmaBlackList.DEVICE, (Enum)Settings.TmaBlackList.DeviceBlackList.TMA });
    int j = SettingsDAO.getInt((Context)application, 0, new Enum[] { (Enum)Settings.TT_TMA_BLACKLIST, (Enum)Settings.TmaBlackList.DEVICE, (Enum)Settings.TmaBlackList.DeviceBlackList.TMG });
    return (paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? ((j == 1) ? ((i != 1)) : true) : ((j != 1 && i != 1))) : ((j != 1))) : ((i != 1));
  }
  
  private boolean isSDKSupport() {
    return (Build.VERSION.SDK_INT >= 21);
  }
  
  private boolean isTMAEnable() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (application == null) ? false : ((SettingsDAO.getInt((Context)application, 0, new Enum[] { (Enum)Settings.BDP_SWITCH, (Enum)Settings.BdpSwitch.DISABLE_TMA }) == 0));
  }
  
  private boolean isTMGEnable() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (application == null) ? false : ((SettingsDAO.getInt((Context)application, 0, new Enum[] { (Enum)Settings.BDP_SWITCH, (Enum)Settings.BdpSwitch.DISABLE_TMG }) == 0));
  }
  
  public DisableStateEntity checkMiniAppDisableState(int paramInt) {
    if (paramInt == 1 && !isTMAEnable()) {
      String str = UIUtils.getString(2097741925);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(AppbrandConstant.OpenApi.getInst().getSYSTEMDOWN());
      stringBuilder.append("?");
      stringBuilder.append(LanguageUtils.appendLanguageQueryParam());
      return new DisableStateEntity(str, stringBuilder.toString());
    } 
    if (paramInt == 2 && !isTMGEnable()) {
      String str = UIUtils.getString(2097741922);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(AppbrandConstant.OpenApi.getInst().getSYSTEMDOWN());
      stringBuilder.append("?");
      stringBuilder.append(LanguageUtils.appendLanguageQueryParam());
      return new DisableStateEntity(str, stringBuilder.toString());
    } 
    if (!isCurrentDevicesSupport(paramInt)) {
      String str = UIUtils.getString(2097741880);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(AppbrandConstant.OpenApi.getInst().getUNSUPPORTED_MODEL());
      stringBuilder.append("&");
      stringBuilder.append(LanguageUtils.appendLanguageQueryParam());
      return new DisableStateEntity(str, stringBuilder.toString());
    } 
    if (!isSDKSupport()) {
      String str = UIUtils.getString(2097741881);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(AppbrandConstant.OpenApi.getInst().getUNSUPPORTED_OS());
      stringBuilder.append("&");
      stringBuilder.append(LanguageUtils.appendLanguageQueryParam());
      return new DisableStateEntity(str, stringBuilder.toString());
    } 
    return HostDependManager.getInst().checkExtraAppbrandDisableState(paramInt);
  }
  
  public void checkUpdateBaseBundle(Context paramContext) {
    if (ProcessUtil.isMainProcess(paramContext)) {
      checkUpdateBaseBundleInMainProcess(paramContext);
      return;
    } 
    InnerHostProcessBridge.updateBaseBundle();
  }
  
  public void checkUpdateBaseBundleInMainProcess(final Context context) {
    Observable.create(new Action() {
          public void act() {
            if (ProcessUtil.isMainProcess(context)) {
              BaseBundleManager.getInst().preload(context);
              AppBrandLogger.i(BundleManager.this.TAG, new Object[] { "updateInfo : " });
              BaseBundleManager.getInst().checkUpdateBaseBundle(context, false);
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public boolean handleTmaTest(Context paramContext, Uri paramUri) {
    return BaseBundleManager.getInst().handleTmaTest(paramContext, paramUri);
  }
  
  public boolean isSDKSupport(int paramInt) {
    return (checkMiniAppDisableState(paramInt) == null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\BundleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */