package com.tt.miniapp.feedback;

import android.content.Context;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.StorageUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FeedbackALogger implements IFeedbackLogger {
  public static final String PATH;
  
  public BufferedWriter feedbackALogBW;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()));
    stringBuilder.append("/TT/feedback/feedbackALogger.txt");
    PATH = stringBuilder.toString();
  }
  
  public void init() {
    try {
      File file = new File(PATH);
      if (file.exists()) {
        this.feedbackALogBW = new BufferedWriter(new FileWriter(file));
        return;
      } 
      if (file.createNewFile())
        this.feedbackALogBW = new BufferedWriter(new FileWriter(file)); 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_FeedbackALogger", iOException.getStackTrace());
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
      if (this.feedbackALogBW == null)
        return; 
      AppBrandLogger.registerFeedbackLogger(new AppBrandLogger.ILogger() {
            public void flush() {}
            
            public void logD(String param1String1, String param1String2) {
              try {
                FeedbackALogger.this.feedbackALogBW.write(FeedbackUtil.createLog(new Object[] { "debug: ", param1String1, param1String2 }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_FeedbackALogger", iOException.getStackTrace());
                return;
              } 
            }
            
            public void logE(String param1String1, String param1String2) {
              try {
                FeedbackALogger.this.feedbackALogBW.write(FeedbackUtil.createLog(new Object[] { "error: ", param1String1, param1String2 }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_FeedbackALogger", iOException.getStackTrace());
                return;
              } 
            }
            
            public void logE(String param1String1, String param1String2, Throwable param1Throwable) {
              try {
                FeedbackALogger.this.feedbackALogBW.write(FeedbackUtil.createLog(new Object[] { "error: ", param1String1, param1String2, param1Throwable.getStackTrace() }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_FeedbackALogger", iOException.getStackTrace());
                return;
              } 
            }
            
            public void logI(String param1String1, String param1String2) {
              try {
                FeedbackALogger.this.feedbackALogBW.write(FeedbackUtil.createLog(new Object[] { "info: ", param1String1, param1String2 }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_FeedbackALogger", iOException.getStackTrace());
                return;
              } 
            }
            
            public void logW(String param1String1, String param1String2) {
              try {
                FeedbackALogger.this.feedbackALogBW.write(FeedbackUtil.createLog(new Object[] { "warn: ", param1String1, param1String2 }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_FeedbackALogger", iOException.getStackTrace());
                return;
              } 
            }
          });
    } 
  }
  
  public void stop() {
    try {
      AppBrandLogger.registerFeedbackLogger(null);
      if (this.feedbackALogBW != null) {
        this.feedbackALogBW.flush();
        this.feedbackALogBW.close();
      } 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_FeedbackALogger", iOException.getStackTrace());
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\FeedbackALogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */