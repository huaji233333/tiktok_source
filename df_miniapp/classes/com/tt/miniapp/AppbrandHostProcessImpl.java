package com.tt.miniapp;

import android.os.IBinder;
import com.tt.miniapphost.process.base.IHostProcessService;
import com.tt.miniapphost.process.base.MiniApp2HostBinderStub;

public class AppbrandHostProcessImpl implements IHostProcessService {
  public IBinder getHostProcessCrossProcessCallBinder() {
    return (new MiniApp2HostBinderStub()).asBinder();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\AppbrandHostProcessImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */