package com.tt.miniapp.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.errorcode.Flow;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.miniapphost.util.UIUtils;
import org.json.JSONObject;

public class LoadHelper {
  public static void handleHostProcessFail(Context paramContext, String paramString) {
    handleHostProcessFail(paramContext, paramString, true);
  }
  
  public static void handleHostProcessFail(final Context context, String paramString, boolean paramBoolean) {
    if (!ProcessUtil.isMainProcess(context))
      return; 
    AppBrandLogger.e("LoadHelper", new Object[] { "showLoadFailDialog fail", new Throwable() });
    monitorErrorEvent(paramString, "");
    if (paramBoolean) {
      final StringBuilder errMsg = new StringBuilder();
      stringBuilder.append(UIUtils.getString(2097741936));
      stringBuilder.append(paramString);
      AppbrandContext.mainHandler.post(new Runnable() {
            public final void run() {
              Context context = context;
              if (context == null)
                return; 
              if (context instanceof Activity && !((Activity)context).isFinishing()) {
                HostDependManager.getInst().showModal((Activity)context, null, null, errMsg.toString(), false, null, null, UIUtils.getString(2097741884), null, new NativeModule.NativeModuleCallback<Integer>() {
                      public void onNativeModuleCall(Integer param2Integer) {}
                    });
                return;
              } 
              ToastManager.showToast(context, errMsg.toString(), 0L, null);
            }
          });
    } 
  }
  
  public static void handleMiniProcessFail(String paramString) {
    handleMiniProcessFail(paramString, true);
  }
  
  public static void handleMiniProcessFail(final String errorCode, boolean paramBoolean) {
    if (!ProcessUtil.isMiniappProcess())
      return; 
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return; 
    StringBuilder stringBuilder = new StringBuilder("stack: ");
    stringBuilder.append(Log.getStackTraceString(new Throwable()));
    monitorErrorEvent(errorCode, stringBuilder.toString());
    if (AppbrandApplication.getInst().getAppInfo() != null && !TextUtils.isEmpty(AppbrandApplication.getInst().getSchema()) && HostDependManager.getInst().handleAppbrandDisablePage((Context)miniappHostBase, AppbrandApplication.getInst().getSchema())) {
      if (!TextUtils.isEmpty(errorCode) && errorCode.startsWith(Flow.Meta.getCode())) {
        ToolUtils.onActivityExit((Activity)miniappHostBase, 3);
        return;
      } 
      ToolUtils.onActivityExit((Activity)miniappHostBase, 4);
      return;
    } 
    if (paramBoolean) {
      int i;
      final StringBuilder errMsg = new StringBuilder();
      if (AppbrandContext.getInst().isGame()) {
        i = 2097741950;
      } else {
        i = 2097741949;
      } 
      String str2 = UIUtils.getString(i);
      String str3 = HostDependManager.getInst().replaceMicroAppCallName();
      String str1 = str2;
      if (!AppbrandContext.getInst().isGame()) {
        str1 = str2;
        if (!TextUtils.isEmpty(str3))
          str1 = str3; 
      } 
      if (!NetUtil.isNetworkAvailable((Context)miniappHostBase)) {
        stringBuilder1.append(UIUtils.getString(2097741958));
      } else if (TextUtils.equals(errorCode, ErrorCode.META.PERMISSION_DENY.getCode())) {
        stringBuilder1.append(UIUtils.getString(2097741939));
      } else if (TextUtils.equals(errorCode, ErrorCode.META.QRCODE_EXPIRED.getCode())) {
        stringBuilder1.append(UIUtils.getString(2097741940));
      } else {
        stringBuilder1.append(str1);
        stringBuilder1.append(UIUtils.getString(2097741938));
      } 
      if (DebugUtil.debug()) {
        stringBuilder1.append("(");
        stringBuilder1.append(errorCode);
        stringBuilder1.append(")");
      } 
      ((RenderSnapShotManager)AppbrandApplicationImpl.getInst().getService(RenderSnapShotManager.class)).onLoadResultFail(stringBuilder1.toString());
      AppBrandLogger.e("LoadHelper", new Object[] { "showLoadFailDialog fail", new Throwable() });
      AppbrandContext.mainHandler.post(new Runnable() {
            public final void run() {
              boolean bool = activity.isFinishing();
              boolean bool2 = true;
              boolean bool1 = bool2;
              if (!bool) {
                IActivityProxy iActivityProxy = activity.getActivityProxy();
                bool1 = bool2;
                if (iActivityProxy != null)
                  if (!iActivityProxy.showLoadFailMessage(errMsg.toString(), finalCanRetry)) {
                    bool1 = bool2;
                  } else {
                    bool1 = false;
                  }  
              } 
              if (bool1)
                LoadHelper.realShowLoadFailDialog((Activity)activity, errMsg.toString(), errorCode); 
            }
          });
    } 
    stringBuilder = new StringBuilder("code: ");
    stringBuilder.append(errorCode);
    errorCode = stringBuilder.toString();
    InnerEventHelper.mpLoadResultInner(LoadStateManager.getIns().getDuration(), "fail", errorCode, LoadStateManager.getIns().getOpenDuration(), LoadStateManager.getIns().getTotalDuration(), LoadStateManager.getIns().getLoadState());
    stringBuilder = new StringBuilder("load fail! ");
    stringBuilder.append(errorCode);
    AppBrandLogger.d("LoadHelper", new Object[] { stringBuilder.toString() });
  }
  
  public static void monitorErrorEvent(String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errCode", paramString1);
      jSONObject.put("errMsg", paramString2);
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("LoadHelper", "openMiniAppActivity", exception);
    } 
    AppBrandMonitor.statusRate("mp_start_error", 5000, jSONObject);
  }
  
  public static void realShowLoadFailDialog(final Activity activity, String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder(paramString1);
    if (DebugUtil.debug()) {
      stringBuilder.append(":");
      stringBuilder.append(paramString2);
    } 
    HostDependManager.getInst().showModal(activity, null, null, stringBuilder.toString(), false, null, null, UIUtils.getString(2097741877), null, new NativeModule.NativeModuleCallback<Integer>() {
          public final void onNativeModuleCall(Integer param1Integer) {
            if (param1Integer.intValue() == 1) {
              EventParamsValue.PARAMS_EXIT_TYPE = "others";
              EventParamsValue.IS_OTHER_FLAG = true;
              ToolUtils.onActivityExit(activity, 1);
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dialog\LoadHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */