package com.tt.miniapp.process.core;

import android.text.TextUtils;
import com.storage.async.Action;
import com.tt.miniapp.process.caller.CrossProcessActionCaller;
import com.tt.miniapp.process.handler.CrossProcessCallHandler;
import com.tt.miniapp.process.interceptor.ISyncCallInterceptor;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.callback.IpcCallbackManagerProxy;
import com.tt.miniapphost.process.core.IProcessCallControl;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.data.CrossProcessInformation;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;

public class ProcessCallControl implements IProcessCallControl {
  private ISyncCallInterceptor mSyncCallInterceptor;
  
  public void callProcessAsync(final CrossProcessCallEntity crossProcessCallEntity, final IpcCallback ipcCallback) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            String str = crossProcessCallEntity.getCallProcessIdentify();
            try {
              if (TextUtils.equals(str, "hostProcess")) {
                if (TextUtils.equals(str, crossProcessCallEntity.getCallerProcessIdentify())) {
                  int i = 0;
                  if (ipcCallback != null) {
                    ipcCallback.setCallProcessIdentify(crossProcessCallEntity.getCallProcessIdentify());
                    IpcCallbackManagerProxy.getInstance().registerIpcCallback(ipcCallback);
                    i = ipcCallback.getCallbackId();
                  } 
                  ProcessCallControl.this.handleAsyncCall(crossProcessCallEntity, i);
                  return;
                } 
                CrossProcessActionCaller.callHostProcessAsync(crossProcessCallEntity, ipcCallback);
                return;
              } 
              CrossProcessActionCaller.callMiniAppProcessAsync(crossProcessCallEntity, ipcCallback);
              return;
            } catch (Exception exception) {
              AppBrandLogger.eWithThrowable("ProcessCallControl", crossProcessCallEntity.toString(), exception);
              return;
            } 
          }
        }ThreadPools.defaults(), false);
  }
  
  public CrossProcessDataEntity callProcessSync(CrossProcessCallEntity paramCrossProcessCallEntity) {
    String str = paramCrossProcessCallEntity.getCallProcessIdentify();
    try {
      if (TextUtils.equals(str, "hostProcess"))
        return TextUtils.equals(str, paramCrossProcessCallEntity.getCallerProcessIdentify()) ? handleSyncCall(paramCrossProcessCallEntity) : CrossProcessActionCaller.callHostProcessSync(paramCrossProcessCallEntity, this.mSyncCallInterceptor); 
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("ProcessCallControl", paramCrossProcessCallEntity.toString(), exception);
    } 
    return null;
  }
  
  public void callback(CrossProcessInformation.CallerProcess paramCallerProcess, CrossProcessDataEntity paramCrossProcessDataEntity, boolean paramBoolean) {
    CrossProcessCallEntity crossProcessCallEntity;
    String str = paramCallerProcess.getCallerProcessIdentify();
    CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().put("callbackId", Integer.valueOf(paramCallerProcess.getCallerProcessCallbackId())).put("finishCallBack", Boolean.valueOf(paramBoolean)).build();
    if (TextUtils.equals(str, "hostProcess")) {
      crossProcessCallEntity = new CrossProcessCallEntity(str, "hostProcess_callback", paramCrossProcessDataEntity, crossProcessDataEntity);
    } else {
      crossProcessCallEntity = new CrossProcessCallEntity(str, "miniAppProcess_callback", paramCrossProcessDataEntity, (CrossProcessDataEntity)crossProcessCallEntity);
    } 
    callProcessAsync(crossProcessCallEntity, null);
  }
  
  public void handleAsyncCall(CrossProcessCallEntity paramCrossProcessCallEntity, int paramInt) {
    String str1 = paramCrossProcessCallEntity.getCallProcessIdentify();
    String str2 = paramCrossProcessCallEntity.getCallerProcessIdentify();
    try {
      CrossProcessInformation.CallerProcess callerProcess = new CrossProcessInformation.CallerProcess(str2, paramInt);
      if (TextUtils.equals(str1, "hostProcess")) {
        CrossProcessCallHandler.handleHostProcessReceivedAsyncCall(paramCrossProcessCallEntity, new AsyncIpcHandler(callerProcess));
        return;
      } 
      CrossProcessCallHandler.handleMiniAppReceivedAsyncCall(paramCrossProcessCallEntity, new AsyncIpcHandler(callerProcess));
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("ProcessCallControl", paramCrossProcessCallEntity.toString(), exception);
      return;
    } 
  }
  
  public CrossProcessDataEntity handleSyncCall(CrossProcessCallEntity paramCrossProcessCallEntity) {
    String str = paramCrossProcessCallEntity.getCallProcessIdentify();
    try {
      if (TextUtils.equals(str, "hostProcess"))
        return CrossProcessCallHandler.handleHostProcessReceivedSyncCall(paramCrossProcessCallEntity); 
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("ProcessCallControl", paramCrossProcessCallEntity.toString(), exception);
    } 
    return null;
  }
  
  public void setSyncInterceptor(ISyncCallInterceptor paramISyncCallInterceptor) {
    this.mSyncCallInterceptor = paramISyncCallInterceptor;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\core\ProcessCallControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */