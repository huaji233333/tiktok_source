package com.tt.option;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import com.tt.miniapp.msg.ApiRequestPayCtrl;
import com.tt.miniapp.msg.ApiWXRequestPayCtrl;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapphost.entity.AppLaunchInfo;
import com.tt.miniapphost.entity.DisableStateEntity;
import com.tt.miniapphost.entity.GamePayResultEntity;
import java.util.List;
import org.json.JSONObject;

public class d {
  public boolean bindPhoneNumber(a parama) {
    return false;
  }
  
  public DisableStateEntity checkExtraAppbrandDisableState(int paramInt) {
    return null;
  }
  
  public b createBlockLoadingCallback() {
    return new b(this) {
      
      };
  }
  
  public boolean gamePay(Activity paramActivity, JSONObject paramJSONObject, String paramString) {
    return false;
  }
  
  public List<AppLaunchInfo> getAppLaunchInfo() {
    return null;
  }
  
  public <T> T getHostData(int paramInt, T paramT) {
    return paramT;
  }
  
  public com.tt.c.a getMiniAppLifeCycleInstance() {
    return null;
  }
  
  public NestWebView getNestWebView(Context paramContext) {
    return new NestWebView(paramContext);
  }
  
  public boolean getPayEnable() {
    return true;
  }
  
  public ApiRequestPayCtrl.IPayExecutor getPayExecutor(Activity paramActivity) {
    return null;
  }
  
  public List<AppLaunchInfo> getRecommendList() {
    return null;
  }
  
  public long getSettingsRequestDelayTime() {
    return 3000L;
  }
  
  public JSONObject getTmaFeatureConfig() {
    return null;
  }
  
  public ApiWXRequestPayCtrl.IWxPayExecutor getWxPayExecutor(Activity paramActivity) {
    return null;
  }
  
  public GamePayResultEntity handleActivityGamePayResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return new GamePayResultEntity();
  }
  
  public boolean isCheckSafeDomain(boolean paramBoolean, String paramString1, String paramString2) {
    return paramBoolean;
  }
  
  public boolean isEnableDownlaodFileApiRewrite() {
    return false;
  }
  
  public boolean isEnableOpenSchemaAnimation() {
    return false;
  }
  
  public boolean isEnablePermissionSaveTest() {
    return true;
  }
  
  public boolean isEnableWebAppPreload() {
    return false;
  }
  
  public boolean isEnableWebviewPreload() {
    return true;
  }
  
  public boolean isHideTitleMenuAboutItem() {
    return false;
  }
  
  public boolean isMediaPlaybackRequiresUserGesture() {
    return true;
  }
  
  public boolean isReturnDeviceIdInSystemInfo() {
    return false;
  }
  
  public boolean isSlideActivity(String paramString) {
    return false;
  }
  
  public boolean isSupportExitEntirely() {
    return false;
  }
  
  public boolean isTitlebarMoreMenuVisible() {
    return true;
  }
  
  public void loadSoInHost(String paramString, c paramc) {}
  
  public boolean loadSoInHost(String paramString) {
    return false;
  }
  
  public boolean needSettingTitleMenuItem() {
    return true;
  }
  
  public void onWebViewComponentDestroyed(WebView paramWebView) {}
  
  public List<Object> replaceMenuItems(List<Object> paramList) {
    return paramList;
  }
  
  public String replaceProcessName(String paramString) {
    return paramString;
  }
  
  public boolean shouldCheckPermissionBeforeCallhostmethod() {
    return true;
  }
  
  public boolean startAboutActivity(Activity paramActivity) {
    return false;
  }
  
  public boolean supportRequestCommonParamsInChildProcess() {
    return false;
  }
  
  public void syncWebViewLoginCookie(String paramString) {}
  
  public static interface a {
    void onBindPhoneResult(boolean param1Boolean);
  }
  
  public static interface b {}
  
  public static interface c {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */