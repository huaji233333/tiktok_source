package com.ss.android.ugc.aweme.miniapp.f.a;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;

public final class d implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null) {
      AppBrandLogger.e("TmaLifecyleHandler", new Object[] { "callData == null" });
      return null;
    } 
    AppbrandContext.mainHandler.post(new Runnable(this, paramCrossProcessDataEntity) {
          public final void run() {
            try {
              Application application = AppbrandContext.getInst().getApplicationContext();
              String str1 = this.a.getString("activityLifecycle");
              String str2 = this.a.getString("activityName");
              int i = this.a.getInt("hashcode");
              if (!TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2) && application != null) {
                StringBuilder stringBuilder = new StringBuilder("lifecycle ");
                stringBuilder.append(str1);
                stringBuilder.append(" ");
                stringBuilder.append(str2);
                AppBrandLogger.d("TmaLifecyleHandler", new Object[] { stringBuilder.toString() });
                if (TextUtils.equals("onResume", str1)) {
                  MiniAppService.inst().getMonitorDepend().b((Context)application, str2, i);
                  return;
                } 
                if (TextUtils.equals("onPause", str1)) {
                  MiniAppService.inst().getMonitorDepend().a((Context)application, str2, i);
                  return;
                } 
                if (TextUtils.equals("onCreate", str1))
                  MiniAppService.inst().getMonitorDepend().a(str2); 
              } 
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("TmaLifecyleHandler", new Object[] { exception });
              return;
            } 
          }
        });
    return null;
  }
  
  public final String getType() {
    return "tmaLifecycle";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */