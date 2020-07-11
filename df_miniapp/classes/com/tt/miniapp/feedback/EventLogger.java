package com.tt.miniapp.feedback;

import android.content.Context;
import com.tt.miniapp.event.Event;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.StorageUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class EventLogger implements IFeedbackLogger {
  public static final String PATH;
  
  public BufferedWriter eventBW;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()));
    stringBuilder.append("/TT/feedback/eventLog.txt");
    PATH = stringBuilder.toString();
  }
  
  public void init() {
    try {
      File file = new File(PATH);
      if (file.exists()) {
        this.eventBW = new BufferedWriter(new FileWriter(file));
        return;
      } 
      if (file.createNewFile())
        this.eventBW = new BufferedWriter(new FileWriter(file)); 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_EventLogger", iOException.getStackTrace());
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
      if (this.eventBW == null)
        return; 
      Event.registerIFeedback(new Event.IFeedback() {
            public void onLogEvent(String param1String, JSONObject param1JSONObject) {
              try {
                EventLogger.this.eventBW.write(FeedbackUtil.createLog(new Object[] { param1String, param1JSONObject.toString() }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_EventLogger", iOException.getStackTrace());
                return;
              } 
            }
          });
    } 
  }
  
  public void stop() {
    try {
      Event.unregisterIFeedback();
      if (this.eventBW != null) {
        this.eventBW.flush();
        this.eventBW.close();
      } 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_EventLogger", iOException.getStackTrace());
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\EventLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */