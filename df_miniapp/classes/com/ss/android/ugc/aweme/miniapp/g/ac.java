package com.ss.android.ugc.aweme.miniapp.g;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.ss.android.ugc.aweme.miniapp.pay.a;
import com.ss.android.ugc.aweme.miniapp.pay.a.a;
import com.ss.android.ugc.aweme.miniapp.pay.a.b;
import com.ss.android.ugc.aweme.miniapp.views.MainProcessProxyActivity;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.AppLaunchInfo;
import com.tt.miniapphost.entity.GamePayResultEntity;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.d;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ac extends d {
  public boolean bindPhoneNumber(d.a parama) {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      parama.onBindPhoneResult(false);
      return true;
    } 
    Intent intent = new Intent((Context)miniappHostBase, MainProcessProxyActivity.class);
    intent.putExtra("proxy_type", 5);
    ProcessUtil.fillCrossProcessCallbackIntent(intent, new IpcCallback(this, parama) {
          public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            finishListenIpcCallback();
            if (param1CrossProcessDataEntity == null) {
              this.a.onBindPhoneResult(false);
              return;
            } 
            this.a.onBindPhoneResult(param1CrossProcessDataEntity.getBoolean("bindPhoneNumberResult"));
          }
        });
    miniappHostBase.startActivity(intent);
    return true;
  }
  
  public boolean gamePay(Activity paramActivity, JSONObject paramJSONObject, String paramString) {
    a a1 = a.a.a;
    String str1 = paramJSONObject.optString("app_id");
    String str2 = paramJSONObject.optString("trade_no");
    String str3 = paramJSONObject.optString("merchant_id");
    String str4 = paramJSONObject.optString("_mark");
    b.a a = new b.a();
    a.b = str1;
    a.c = str3;
    a.a = str2;
    a.d = str4;
    b b = new b();
    b.statusBarColor = a.i;
    b.loadingBgColor = a.j;
    b.appId = a.b;
    b.logParams = a.f;
    b.merchantId = a.c;
    b.orderId = a.a;
    b.mark = a.d;
    b.statusFontDark = a.h;
    b.statusBarHeight = a.k;
    b.hideNavbar = a.g;
    b.from = a.e;
    Intent intent = new Intent((Context)paramActivity, MainProcessProxyActivity.class);
    StringBuilder stringBuilder = new StringBuilder("http://wallet.snssdk.com/douyin/cashdesk?order_id=");
    stringBuilder.append(b.getOrderId());
    stringBuilder.append("&app_id=");
    stringBuilder.append(b.getAppId());
    stringBuilder.append("&merchant_id=");
    stringBuilder.append(b.getMerchantId());
    stringBuilder.append("&_mark=");
    stringBuilder.append(b.getMark());
    stringBuilder.append("&_from=");
    stringBuilder.append(b.getFrom());
    stringBuilder.append("&_log_params=");
    stringBuilder.append(b.getLogParams());
    stringBuilder.append("&hide_nav_bar=");
    stringBuilder.append(b.getHideNavbar());
    stringBuilder.append("&status_bar_color=");
    stringBuilder.append(b.getStatusBarColor());
    stringBuilder.append("&status_font_dark=");
    stringBuilder.append(b.getStatusFontDark());
    intent.putExtra("game_pay_url", stringBuilder.toString());
    intent.putExtra("proxy_type", 4);
    paramActivity.startActivityForResult(intent, 100);
    return true;
  }
  
  public List<AppLaunchInfo> getAppLaunchInfo() {
    return null;
  }
  
  public JSONObject getTmaFeatureConfig() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("tt_game_center_id", "ttacffda4233d51d45");
      return jSONObject;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "MyHostOptionLowPriorityDepend", jSONException.getStackTrace());
      return jSONObject;
    } 
  }
  
  public GamePayResultEntity handleActivityGamePayResult(int paramInt1, int paramInt2, Intent paramIntent) {
    GamePayResultEntity gamePayResultEntity = new GamePayResultEntity();
    if (paramInt1 == 100) {
      if (paramInt2 == 0) {
        gamePayResultEntity.setShouldHandle(true);
        gamePayResultEntity.setCode(-2);
        gamePayResultEntity.setMessage("cancelled");
        return gamePayResultEntity;
      } 
      if (paramInt2 == -1) {
        gamePayResultEntity.setShouldHandle(true);
        if (paramIntent != null && paramIntent.hasExtra("pay_key_result_params")) {
          a a = (a)paramIntent.getSerializableExtra("pay_key_result_params");
          paramInt1 = a.getCode();
          if (paramInt1 != 0) {
            if (paramInt1 != 1) {
              if (paramInt1 != 2) {
                if (paramInt1 != 3) {
                  if (paramInt1 != 4) {
                    gamePayResultEntity.setCode(a.getCode());
                    gamePayResultEntity.setMessage("unknown error");
                    return gamePayResultEntity;
                  } 
                  gamePayResultEntity.setCode(a.getCode());
                  gamePayResultEntity.setMessage("pay checkout counter net error");
                  return gamePayResultEntity;
                } 
                gamePayResultEntity.setCode(a.getCode());
                gamePayResultEntity.setMessage("pay checkout counter trigger fail");
                return gamePayResultEntity;
              } 
              gamePayResultEntity.setCode(-2);
              gamePayResultEntity.setMessage("cancelled");
              return gamePayResultEntity;
            } 
            gamePayResultEntity.setCode(-1);
            gamePayResultEntity.setMessage("fail");
            return gamePayResultEntity;
          } 
          gamePayResultEntity.setCode(0);
          gamePayResultEntity.setMessage("success");
        } 
      } 
    } 
    return gamePayResultEntity;
  }
  
  public boolean isEnableOpenSchemaAnimation() {
    return true;
  }
  
  public boolean isEnablePermissionSaveTest() {
    return false;
  }
  
  public boolean isEnableWebviewPreload() {
    return true;
  }
  
  public boolean isHideTitleMenuAboutItem() {
    return true;
  }
  
  public boolean isMediaPlaybackRequiresUserGesture() {
    return false;
  }
  
  public boolean isTitlebarMoreMenuVisible() {
    boolean bool;
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null || appInfoEntity.isGame()) {
      bool = false;
    } else {
      bool = true;
    } 
    return !bool;
  }
  
  public String replaceProcessName(String paramString) {
    return super.replaceProcessName(paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\ac.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */