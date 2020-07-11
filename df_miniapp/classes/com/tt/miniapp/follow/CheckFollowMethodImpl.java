package com.tt.miniapp.follow;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Function;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.q.d;
import com.tt.option.q.i;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckFollowMethodImpl {
  public static void callbackError(FollowResultCallback paramFollowResultCallback, String paramString) {
    if (paramFollowResultCallback == null)
      return; 
    paramFollowResultCallback.failed(new Exception(paramString));
  }
  
  public static void requestFollowState(FollowResultCallback paramFollowResultCallback) {
    AppBrandLogger.d("CheckFollowMethodImpl", new Object[] { "requestFollowState" });
    i i = new i(AppbrandConstant.OpenApi.getInst().getCHECK_FOLLOW_URL(), "POST");
    if (!d.a((Context)AppbrandContext.getInst().getApplicationContext())) {
      paramFollowResultCallback.failed(new Throwable(FollowErrorMsgBuilder.buildCodeInfo(3, "no network")));
      return;
    } 
    try {
      String str1 = (AppbrandApplication.getInst().getAppInfo()).appId;
      String str2 = InnerHostProcessBridge.getPlatformSession(str1);
      long l1 = Long.valueOf(d.a()).longValue();
      long l2 = Long.valueOf(AppbrandContext.getInst().getInitParams().getAppId()).longValue();
      i.a("session", str2);
      i.a("device_id", Long.valueOf(l1));
      i.a("aid", Long.valueOf(l2));
      return;
    } finally {
      i = null;
      callbackError(paramFollowResultCallback, "request build error");
      AppBrandLogger.eWithThrowable("CheckFollowMethodImpl", "param error", (Throwable)i);
    } 
  }
  
  public static interface FollowResultCallback {
    void failed(Throwable param1Throwable);
    
    void success(boolean param1Boolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\follow\CheckFollowMethodImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */