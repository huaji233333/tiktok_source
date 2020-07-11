package com.tt.miniapphost.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.callback.IpcCallbackManagerProxy;
import com.tt.miniapphost.process.data.CrossProcessInformation;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class ProcessUtil {
  public static void fillCrossProcessCallbackIntent(Intent paramIntent, IpcCallback paramIpcCallback) {
    IpcCallbackManagerProxy.getInstance().registerIpcCallback(paramIpcCallback);
    paramIntent.putExtra("ma_callerProcessIdentify", getProcessIdentify());
    paramIntent.putExtra("ma_callbackId", paramIpcCallback.getCallbackId());
  }
  
  public static void fillCrossProcessCallbackJSONObject(JSONObject paramJSONObject, IpcCallback paramIpcCallback) {
    IpcCallbackManagerProxy.getInstance().registerIpcCallback(paramIpcCallback);
    try {
      paramJSONObject.put("ma_callerProcessIdentify", getProcessIdentify());
      paramJSONObject.put("ma_callbackId", paramIpcCallback.getCallbackId());
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ProcessUtil", new Object[] { "fillCrossProcessCallbackInformation fail", jSONException });
      return;
    } 
  }
  
  public static void fillCrossProcessCallbackUri(Uri.Builder paramBuilder, IpcCallback paramIpcCallback) {
    IpcCallbackManagerProxy.getInstance().registerIpcCallback(paramIpcCallback);
    paramBuilder.appendQueryParameter("ma_callerProcessIdentify", getProcessIdentify());
    paramBuilder.appendQueryParameter("ma_callbackId", String.valueOf(paramIpcCallback.getCallbackId()));
  }
  
  public static AsyncIpcHandler generateAsyncIpcHandlerFromBundle(Bundle paramBundle) {
    String str = paramBundle.getString("ma_callerProcessIdentify");
    int i = paramBundle.getInt("ma_callbackId", 0);
    if (TextUtils.isEmpty(str) || i == 0) {
      StringBuilder stringBuilder = new StringBuilder("generateAsyncIpcHandlerFromBundle error. processIdentify: ");
      stringBuilder.append(str);
      stringBuilder.append(" callbackId: ");
      stringBuilder.append(i);
      DebugUtil.outputError("ProcessUtil", new Object[] { stringBuilder.toString() });
      return null;
    } 
    return new AsyncIpcHandler(new CrossProcessInformation.CallerProcess(str, i));
  }
  
  public static AsyncIpcHandler generateAsyncIpcHandlerFromIntent(Intent paramIntent) {
    String str = paramIntent.getStringExtra("ma_callerProcessIdentify");
    int i = paramIntent.getIntExtra("ma_callbackId", 0);
    if (TextUtils.isEmpty(str) || i == 0) {
      StringBuilder stringBuilder = new StringBuilder("generateAsyncIpcHandlerFromIntent error. processIdentify: ");
      stringBuilder.append(str);
      stringBuilder.append(" callbackId: ");
      stringBuilder.append(i);
      DebugUtil.outputError("ProcessUtil", new Object[] { stringBuilder.toString() });
      return null;
    } 
    return new AsyncIpcHandler(new CrossProcessInformation.CallerProcess(str, i));
  }
  
  public static AsyncIpcHandler generateAsyncIpcHandlerFromJSONObject(JSONObject paramJSONObject) {
    String str = paramJSONObject.optString("ma_callerProcessIdentify");
    int i = paramJSONObject.optInt("ma_callbackId", 0);
    if (TextUtils.isEmpty(str) || i == 0) {
      StringBuilder stringBuilder = new StringBuilder("generateAsyncIpcHandlerFromIntent error. processIdentify: ");
      stringBuilder.append(str);
      stringBuilder.append(" callbackId: ");
      stringBuilder.append(i);
      DebugUtil.outputError("ProcessUtil", new Object[] { stringBuilder.toString() });
      return null;
    } 
    return new AsyncIpcHandler(new CrossProcessInformation.CallerProcess(str, i));
  }
  
  public static AsyncIpcHandler generateAsyncIpcHandlerFromUri(Uri paramUri) {
    boolean bool;
    String str = paramUri.getQueryParameter("ma_callerProcessIdentify");
    try {
      bool = Integer.valueOf(paramUri.getQueryParameter("ma_callbackId")).intValue();
    } catch (Exception exception) {
      AppBrandLogger.e("ProcessUtil", new Object[] { "generateAsyncIpcHandlerFromUri", exception });
      bool = false;
    } 
    if (TextUtils.isEmpty(str) || !bool) {
      StringBuilder stringBuilder = new StringBuilder("generateAsyncIpcHandlerFromIntent error. processIdentify: ");
      stringBuilder.append(str);
      stringBuilder.append(" callbackId: ");
      stringBuilder.append(bool);
      DebugUtil.outputError("ProcessUtil", new Object[] { stringBuilder.toString() });
      return null;
    } 
    return new AsyncIpcHandler(new CrossProcessInformation.CallerProcess(str, bool));
  }
  
  public static String getCurProcessName(Context paramContext) {
    return MiniAppProcessUtils.getCurProcessName(paramContext);
  }
  
  public static String getProcessIdentify() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (isMainProcess((Context)application))
      return "hostProcess"; 
    AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByProcessName(getCurProcessName((Context)application));
    return (processInfo != null) ? processInfo.mProcessIdentity : "";
  }
  
  public static boolean isBdpProcess() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (MiniAppProcessUtils.isMiniAppProcess((Context)application) || MiniAppProcessUtils.getCurProcessName((Context)application).contains(":unitycontainer"));
  }
  
  public static boolean isMainProcess(Context paramContext) {
    return MiniAppProcessUtils.isMainProcess(paramContext);
  }
  
  public static boolean isMiniappProcess() {
    return MiniAppProcessUtils.isMiniAppProcess((Context)AppbrandContext.getInst().getApplicationContext());
  }
  
  public static void killCurrentMiniAppProcess(Context paramContext) {
    StringBuilder stringBuilder = new StringBuilder("Killing Process: ");
    stringBuilder.append(getCurProcessName(paramContext));
    stringBuilder.append("\n");
    stringBuilder.append(Log.getStackTraceString(new Throwable()));
    InnerEventHelper.mpTechnologyMsg(stringBuilder.toString());
    if (MiniAppProcessUtils.isMiniAppProcess(paramContext)) {
      try {
        Thread.sleep(200L);
      } catch (InterruptedException interruptedException) {
        AppBrandLogger.e("ProcessUtil", new Object[] { interruptedException });
      } 
      Process.killProcess(Process.myPid());
      System.exit(0);
    } 
  }
  
  public static void transferAsyncIpcHandlerInIntent(AsyncIpcHandler paramAsyncIpcHandler, Intent paramIntent) {
    CrossProcessInformation.CallerProcess callerProcess = paramAsyncIpcHandler.getCallerProcess();
    if (callerProcess == null)
      return; 
    paramIntent.putExtra("ma_callerProcessIdentify", callerProcess.getCallerProcessIdentify());
    paramIntent.putExtra("ma_callbackId", callerProcess.getCallerProcessCallbackId());
  }
  
  public static void transferAsyncIpcHandlerInJSONObject(AsyncIpcHandler paramAsyncIpcHandler, JSONObject paramJSONObject) {
    CrossProcessInformation.CallerProcess callerProcess = paramAsyncIpcHandler.getCallerProcess();
    if (callerProcess == null)
      return; 
    try {
      paramJSONObject.put("ma_callerProcessIdentify", callerProcess.getCallerProcessIdentify());
      paramJSONObject.put("ma_callbackId", callerProcess.getCallerProcessCallbackId());
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ProcessUtil", new Object[] { "transferAsyncIpcHandlerInJSONObject fail", jSONException });
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\ProcessUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */