package com.tt.miniapp.feedback.screenrecord;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.feedback.FeedbackRecordService;
import com.tt.miniapphost.feedback.IFeedbackRecordAIDL;
import com.tt.miniapphost.feedback.IFeedbackRecordCallback;

public class ScreenRecordServiceBindManager {
  public IFeedbackRecordCallback mCallback;
  
  public IFeedbackRecordAIDL mIFeedback;
  
  private final ServiceConnection mServiceConnection = new ServiceConnection() {
      public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
        ScreenRecordServiceBindManager.this.mIFeedback = IFeedbackRecordAIDL.Stub.asInterface(param1IBinder);
        if (ScreenRecordServiceBindManager.this.mCallback != null) {
          ScreenRecordServiceBindManager.this.startRecord();
          return;
        } 
        ScreenRecordServiceBindManager.this.stopRecord();
      }
      
      public void onServiceDisconnected(ComponentName param1ComponentName) {
        ScreenRecordServiceBindManager.this.mIFeedback = null;
      }
    };
  
  private ScreenRecordServiceBindManager() {}
  
  public static ScreenRecordServiceBindManager getInstance() {
    return Holder.mInstance;
  }
  
  public void bindHostService(IFeedbackRecordCallback paramIFeedbackRecordCallback) {
    if (this.mIFeedback == null) {
      Application application = AppbrandContext.getInst().getApplicationContext();
      _lancet.com_ss_android_ugc_aweme_push_downgrade_StartServiceLancet_bindService((Context)application, new Intent((Context)application, FeedbackRecordService.class), this.mServiceConnection, 1);
      this.mCallback = paramIFeedbackRecordCallback;
      return;
    } 
    startRecord();
  }
  
  public void startRecord() {
    try {
      this.mIFeedback.start((IFeedbackRecordCallback)new IFeedbackRecordCallback.Stub() {
            public void onFail(String param1String) throws RemoteException {
              if (ScreenRecordServiceBindManager.this.mCallback != null)
                ScreenRecordServiceBindManager.this.mCallback.onFail(param1String); 
            }
            
            public void onSuccess(String param1String) throws RemoteException {
              if (ScreenRecordServiceBindManager.this.mCallback != null)
                ScreenRecordServiceBindManager.this.mCallback.onSuccess(param1String); 
            }
          });
      return;
    } catch (RemoteException remoteException) {
      AppBrandLogger.stacktrace(6, "ScreenRecordServiceBindManager", remoteException.getStackTrace());
      return;
    } 
  }
  
  public void stopRecord() {
    try {
      this.mIFeedback.stop((IFeedbackRecordCallback)new IFeedbackRecordCallback.Stub() {
            public void onFail(String param1String) throws RemoteException {}
            
            public void onSuccess(String param1String) throws RemoteException {}
          });
    } catch (RemoteException remoteException) {
      AppBrandLogger.stacktrace(6, "ScreenRecordServiceBindManager", remoteException.getStackTrace());
    } 
    AppbrandContext.getInst().getApplicationContext().unbindService(this.mServiceConnection);
  }
  
  public void unBindService(IFeedbackRecordCallback paramIFeedbackRecordCallback) {
    if (this.mIFeedback == null) {
      bindHostService(null);
      return;
    } 
    stopRecord();
  }
  
  static class Holder {
    public static ScreenRecordServiceBindManager mInstance = new ScreenRecordServiceBindManager();
  }
  
  class ScreenRecordServiceBindManager {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\ScreenRecordServiceBindManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */