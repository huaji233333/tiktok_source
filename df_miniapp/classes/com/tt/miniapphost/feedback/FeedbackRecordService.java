package com.tt.miniapphost.feedback;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class FeedbackRecordService extends Service {
  IFeedbackRecordAIDL.Stub mIBinder = new IFeedbackRecordAIDL.Stub() {
      public void start(IFeedbackRecordCallback param1IFeedbackRecordCallback) throws RemoteException {
        FeedbackRecordBridge.start(param1IFeedbackRecordCallback);
      }
      
      public void stop(IFeedbackRecordCallback param1IFeedbackRecordCallback) throws RemoteException {
        FeedbackRecordBridge.stop(param1IFeedbackRecordCallback);
      }
    };
  
  public IBinder onBind(Intent paramIntent) {
    return (IBinder)this.mIBinder;
  }
  
  public boolean onUnbind(Intent paramIntent) {
    return super.onUnbind(paramIntent);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\feedback\FeedbackRecordService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */