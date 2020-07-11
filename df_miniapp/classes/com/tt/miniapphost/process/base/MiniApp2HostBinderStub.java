package com.tt.miniapphost.process.base;

import com.storage.async.Action;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public class MiniApp2HostBinderStub extends IMiniApp2HostBinderInterface.Stub {
  public void asyncCall(final CrossProcessCallEntity crossProcessCallEntity, final int callbackId) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            ProcessCallControlBridge.handleAsyncCall(crossProcessCallEntity, callbackId);
          }
        }ThreadPools.longIO());
  }
  
  public CrossProcessDataEntity syncCall(CrossProcessCallEntity paramCrossProcessCallEntity) {
    return ProcessCallControlBridge.handleSyncCall(paramCrossProcessCallEntity);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\base\MiniApp2HostBinderStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */