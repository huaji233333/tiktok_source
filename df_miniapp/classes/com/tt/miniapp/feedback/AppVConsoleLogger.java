package com.tt.miniapp.feedback;

import android.content.Context;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.StorageUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppVConsoleLogger implements IFeedbackLogger {
  public static final String PATH;
  
  public BufferedWriter appConsoleBW;
  
  private WebViewManager mWebViewManager;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()));
    stringBuilder.append("/TT/feedback/appVConsole.txt");
    PATH = stringBuilder.toString();
  }
  
  public void init() {
    try {
      File file = new File(PATH);
      if (file.exists()) {
        this.appConsoleBW = new BufferedWriter(new FileWriter(file));
        return;
      } 
      if (file.createNewFile())
        this.appConsoleBW = new BufferedWriter(new FileWriter(file)); 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_AppVConsoleLogger", iOException.getStackTrace());
      return;
    } 
  }
  
  public void log() {
    boolean bool;
    if (FeedbackLogHandler.getInstance() != null && FeedbackLogHandler.getInstance().getSwitch()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      if (this.appConsoleBW == null)
        return; 
      this.mWebViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
      WebViewManager webViewManager = this.mWebViewManager;
      if (webViewManager == null)
        return; 
      webViewManager.registerFeedback(new WebViewManager.IFeedback() {
            public void onPublish(int param1Int, String param1String1, String param1String2) {
              try {
                AppVConsoleLogger.this.appConsoleBW.write(FeedbackUtil.createLog(new Object[] { Integer.valueOf(param1Int), param1String1, param1String2 }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_AppVConsoleLogger", iOException.getStackTrace());
                return;
              } 
            }
          });
    } 
  }
  
  public void stop() {
    try {
      if (this.mWebViewManager != null)
        this.mWebViewManager.unRegisterFeedback(); 
      if (this.appConsoleBW != null) {
        this.appConsoleBW.flush();
        this.appConsoleBW.close();
      } 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_AppVConsoleLogger", iOException.getStackTrace());
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\AppVConsoleLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */