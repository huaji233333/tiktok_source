package com.tt.miniapp.feedback;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.StorageUtil;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackUploadHandler {
  public static final String PATH;
  
  public static String TAG = "tma_FeedbackUploadHandler";
  
  public Context mContext;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()));
    stringBuilder.append("/TT/feedback/");
    PATH = stringBuilder.toString();
  }
  
  private FeedbackUploadHandler() {
    Application application;
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      application = AppbrandContext.getInst().getApplicationContext(); 
    this.mContext = (Context)application;
  }
  
  private boolean deleteDir(File paramFile) {
    if (paramFile.isDirectory()) {
      String[] arrayOfString = paramFile.list();
      for (int i = 0; i < arrayOfString.length; i++) {
        if (!deleteDir(new File(paramFile, arrayOfString[i])))
          return false; 
      } 
    } 
    return paramFile.delete();
  }
  
  public static FeedbackUploadHandler getInstance() {
    return Holder.INSTANCE;
  }
  
  private void startUploadLog() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("console", AppVConsoleLogger.PATH);
      jSONObject.put("alog", FeedbackALogger.PATH);
      jSONObject.put("performance", PerformanceLogger.PATH);
      jSONObject.put("event", EventLogger.PATH);
    } catch (JSONException jSONException) {}
    HostProcessBridge.uploadFeedback("log", jSONObject, new IpcCallback() {
          public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            if (param1CrossProcessDataEntity == null)
              return; 
            String str1 = param1CrossProcessDataEntity.getString("errorMsg");
            if (str1 != null && str1.equals("ok")) {
              AppBrandLogger.d(FeedbackUploadHandler.TAG, new Object[] { "upLoad feedback success" });
              return;
            } 
            String str2 = FeedbackUploadHandler.TAG;
            StringBuilder stringBuilder = new StringBuilder("upLoad feedback fail:");
            stringBuilder.append(str1);
            AppBrandLogger.d(str2, new Object[] { stringBuilder.toString() });
          }
          
          public void onIpcConnectError() {}
        });
  }
  
  private void startUploadVideo() {
    JSONObject jSONObject = new JSONObject();
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(PATH);
      stringBuilder.append("ScreenCapture.mp4");
      jSONObject.put("video", stringBuilder.toString());
    } catch (JSONException jSONException) {}
    HostProcessBridge.uploadFeedback("video", jSONObject, new IpcCallback() {
          public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            if (param1CrossProcessDataEntity == null) {
              FeedbackUploadHandler.this.showErrorDialog();
              return;
            } 
            HostDependManager.getInst().hideToast();
            String str = param1CrossProcessDataEntity.getString("errorMsg");
            if (str != null && str.equals("ok")) {
              FeedbackUploadHandler.this.showSuccessDialog();
              return;
            } 
            FeedbackUploadHandler.this.showErrorDialog();
          }
          
          public void onIpcConnectError() {
            FeedbackUploadHandler.this.showErrorDialog();
          }
        });
  }
  
  void deleteLogFile() {
    deleteDir(new File(PATH));
  }
  
  public void showErrorDialog() {
    final MiniappHostBase currentActivity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            HostDependManager.getInst().showModal(currentActivity, null, "", FeedbackUploadHandler.this.mContext.getString(2097742045), true, FeedbackUploadHandler.this.mContext.getString(2097741944), "", FeedbackUploadHandler.this.mContext.getString(2097741877), "", new NativeModule.NativeModuleCallback<Integer>() {
                  public void onNativeModuleCall(Integer param2Integer) {
                    HostDependManager.getInst().hideToast();
                    if (param2Integer.intValue() == 1) {
                      FeedbackUploadHandler.this.startUpload();
                      return;
                    } 
                    FeedbackUploadHandler.this.deleteLogFile();
                  }
                });
          }
        });
  }
  
  public void showSuccessDialog() {
    final MiniappHostBase currentActivity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            HostDependManager.getInst().showModal(currentActivity, null, "", FeedbackUploadHandler.this.mContext.getString(2097742046), false, "", "", FeedbackUploadHandler.this.mContext.getString(2097741877), "", new NativeModule.NativeModuleCallback<Integer>() {
                  public void onNativeModuleCall(Integer param2Integer) {
                    HostDependManager.getInst().hideToast();
                    FeedbackUploadHandler.this.deleteLogFile();
                  }
                });
          }
        });
  }
  
  public void showUploadingConfirmDialog() {
    if (this.mContext == null)
      return; 
    final MiniappHostBase currentActivity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            HostDependManager.getInst().showModal(currentActivity, null, "", FeedbackUploadHandler.this.mContext.getString(2097741878), false, FeedbackUploadHandler.this.mContext.getString(2097741944), "", FeedbackUploadHandler.this.mContext.getString(2097741877), "", new NativeModule.NativeModuleCallback<Integer>() {
                  public void onNativeModuleCall(Integer param2Integer) {
                    if (FeedbackLogHandler.getInstance() != null)
                      FeedbackLogHandler.getInstance().setFeedbackSwitchOn((Context)AppbrandContext.getInst().getApplicationContext(), false); 
                    HostDependManager.getInst().hideToast();
                    if (param2Integer.intValue() == 1) {
                      FeedbackUploadHandler.this.startUpload();
                      return;
                    } 
                    FeedbackUploadHandler.this.deleteLogFile();
                  }
                });
          }
        });
  }
  
  public void startUpload() {
    HostDependManager.getInst().showToast(this.mContext, null, "", 30000L, "loading");
    startUploadLog();
    startUploadVideo();
  }
  
  void upload() {
    showUploadingConfirmDialog();
  }
  
  static class Holder {
    public static FeedbackUploadHandler INSTANCE = new FeedbackUploadHandler();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\FeedbackUploadHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */