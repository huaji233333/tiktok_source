package com.ss.android.ugc.aweme.miniapp.f;

import android.app.Activity;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.d;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import org.json.JSONObject;

public final class h implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null) {
      AppBrandLogger.e("MiniAppLifecycleHandler", new Object[] { "callData == null)" });
      return null;
    } 
    String str = paramCrossProcessDataEntity.getString("miniAppLifecycle");
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("MiniAppLifecycleHandler", new Object[] { "TextUtils.isEmpty(miniAppLifecycle)" });
      return null;
    } 
    byte b = -1;
    try {
      Activity activity;
      int i = str.hashCode();
      if (i != 3417674) {
        if (i == 94756344 && str.equals("close"))
          b = 1; 
      } else {
        boolean bool = str.equals("open");
        if (bool)
          b = 0; 
      } 
      if (b != 0) {
        if (b != 1)
          return null; 
        if (TextUtils.equals(paramCrossProcessDataEntity.getJSONObject("jsonData").getString("miniAppStopReason"), "click_close_btn") && MiniAppService.inst().getRouterDepend().d()) {
          activity = MiniAppService.inst().getRouterDepend().c();
          if (activity != null) {
            MiniAppService.inst().getRouterDepend().a(activity);
            MiniAppService.inst().getRouterDepend().e();
          } 
        } 
        if (g.a) {
          MiniAppService.inst().getBaseLibDepend();
          return null;
        } 
      } else {
        JSONObject jSONObject = activity.getJSONObject("jsonData");
        boolean bool = jSONObject.getBoolean("isGame");
        d.a("", jSONObject.getString("miniAppId"), bool, "");
        return null;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("MiniAppLifecycleHandler", new Object[] { "action", exception });
    } 
    return null;
  }
  
  public final String getType() {
    return "miniAppLifecycle";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */