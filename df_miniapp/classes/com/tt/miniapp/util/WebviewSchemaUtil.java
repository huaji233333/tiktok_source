package com.tt.miniapp.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.f.a;
import android.text.TextUtils;
import com.bytedance.sandboxapp.protocol.service.k.a;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.call.PhoneCallHelper;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import java.util.List;
import java.util.Locale;

public class WebviewSchemaUtil {
  private static boolean actOtherSchema(String paramString) {
    try {
      Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      intent.setFlags(268435456);
      MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (miniappHostBase != null)
        miniappHostBase.startActivity(intent); 
      if (paramString.startsWith("weixin://wap/pay"))
        ThreadUtil.runOnWorkThread(new Action() {
              public final void act() {
                ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).reportPayInformation();
              }
            },  Schedulers.shortIO()); 
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("WebviewSchemaUtil", new Object[] { exception });
      return false;
    } 
  }
  
  public static String getSchema(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    Uri uri = Uri.parse(paramString);
    if (uri == null)
      return ""; 
    String str = uri.getScheme();
    return TextUtils.isEmpty(str) ? "" : str;
  }
  
  private static boolean isDefaultBusinessSchema(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    paramString = paramString.toLowerCase();
    return (paramString.equals("tel") || paramString.equals("mailto") || paramString.equals("sms"));
  }
  
  public static boolean isDefaultSchema(String paramString) {
    null = TextUtils.isEmpty(paramString);
    boolean bool = false;
    if (null)
      return false; 
    paramString = paramString.toLowerCase();
    if (paramString.equals("wss") || paramString.equals("https"))
      bool = true; 
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity != null && (!appInfoEntity.isLocalTest() || appInfoEntity.isAudit())) {
      null = bool;
      return isLark() ? (bool | paramString.equals("http")) : null;
    } 
    return bool | paramString.equals("http");
  }
  
  public static boolean isLark() {
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      String str = initParamsEntity.getAppId();
      return ("1161".equals(str) || "1664".equals(str));
    } 
    return false;
  }
  
  public static boolean isWhiteList(String paramString1, String paramString2) {
    AppBrandLogger.d("WebviewSchemaUtil", new Object[] { "isWhiteList =  url ", paramString2 });
    if (isDefaultBusinessSchema(paramString2))
      return true; 
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity != null) {
      if (TextUtils.isEmpty(paramString2))
        return false; 
      a a = appInfoEntity.domainMap;
      if (a != null) {
        if (a.isEmpty())
          return false; 
        if (a.containsKey(paramString1) && a.get(paramString1) != null && ((List)a.get(paramString1)).contains(paramString2))
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean openSchema(String paramString1, String paramString2) {
    paramString1 = paramString1.toLowerCase(Locale.getDefault());
    if (paramString1.startsWith("tel"))
      try {
        PhoneCallHelper.markPhoneCall((Context)AppbrandContext.getInst().getCurrentActivity(), paramString2);
        return true;
      } catch (Exception exception) {
        AppBrandLogger.e("WebviewSchemaUtil", new Object[] { "openSchema tel", exception });
        return false;
      }  
    return exception.startsWith("mailto") ? sendMail(paramString2) : actOtherSchema(paramString2);
  }
  
  private static boolean sendMail(String paramString) {
    try {
      Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse(paramString));
      intent.setFlags(268435456);
      MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (miniappHostBase != null)
        miniappHostBase.startActivity(intent); 
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("WebviewSchemaUtil", new Object[] { exception });
      return false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\WebviewSchemaUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */