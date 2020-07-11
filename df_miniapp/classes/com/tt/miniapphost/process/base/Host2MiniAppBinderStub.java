package com.tt.miniapphost.process.base;

import android.os.RemoteException;
import com.storage.async.Action;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;

public class Host2MiniAppBinderStub extends IHost2MiniAppBinderInterface.Stub {
  public void asyncCallMiniProcess(final CrossProcessCallEntity crossProcessCallEntity, final int callbackId) throws RemoteException {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            ProcessCallControlBridge.handleAsyncCall(crossProcessCallEntity, callbackId);
          }
        }ThreadPools.defaults());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\base\Host2MiniAppBinderStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */