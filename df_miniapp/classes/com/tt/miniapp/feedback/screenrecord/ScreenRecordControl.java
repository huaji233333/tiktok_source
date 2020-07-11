package com.tt.miniapp.feedback.screenrecord;

import android.content.Context;
import android.os.RemoteException;
import com.tt.miniapp.feedback.FeedbackRecordActivity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.feedback.IFeedbackRecordCallback;
import com.tt.miniapphost.feedback.IFeedbackRecordControl;

public class ScreenRecordControl implements IFeedbackRecordControl {
  public void start(IFeedbackRecordCallback paramIFeedbackRecordCallback) {
    if (AppbrandContext.getInst().getApplicationContext() != null)
      FeedbackRecordActivity.start((Context)AppbrandContext.getInst().getApplicationContext(), paramIFeedbackRecordCallback); 
  }
  
  public void stop(IFeedbackRecordCallback paramIFeedbackRecordCallback) {
    ScreenRecorderManager screenRecorderManager = ScreenRecorderManager.getInstance();
    if (screenRecorderManager != null && screenRecorderManager.quit())
      try {
        paramIFeedbackRecordCallback.onSuccess("ok");
        return;
      } catch (RemoteException remoteException) {
        AppBrandLogger.stacktrace(6, "ScreenRecordControl", remoteException.getStackTrace());
        return;
      }  
    try {
      remoteException.onSuccess("fail");
      return;
    } catch (RemoteException remoteException1) {
      AppBrandLogger.stacktrace(6, "ScreenRecordControl", remoteException1.getStackTrace());
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\ScreenRecordControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */