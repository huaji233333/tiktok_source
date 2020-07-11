package com.tt.miniapp.msg;

import android.app.Activity;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.guide.ReenterGuideHelper;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiExitMiniProgramCtrl extends b {
  public ApiExitMiniProgramCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    boolean bool = false;
    try {
      boolean bool1 = (new JSONObject(this.mArgs)).optBoolean("isFullExit");
      bool = bool1;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiHandler", new Object[] { jSONException });
    } 
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    EventParamsValue.PARAMS_EXIT_TYPE = "others";
    EventParamsValue.IS_OTHER_FLAG = true;
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            Activity activity = activity;
            if (activity != null) {
              ReenterGuideHelper.checkReenterGuideTip(activity, callbackRun);
              return;
            } 
            ApiExitMiniProgramCtrl.this.callbackFail("activity is null");
          }
        });
    if (bool && HostDependManager.getInst().isSupportExitEntirely()) {
      ToolUtils.onActivityExit((Activity)miniappHostBase, 1);
    } else {
      ToolUtils.onActivityExit((Activity)miniappHostBase, 9);
    } 
    callbackDefaultMsg(true);
  }
  
  public String getActionName() {
    return "exitMiniProgram";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiExitMiniProgramCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */