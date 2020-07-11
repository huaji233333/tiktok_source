package com.tt.miniapp.process.caller;

import android.os.Looper;
import android.os.RemoteException;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.ServiceBindManager;
import com.tt.miniapp.process.interceptor.ISyncCallInterceptor;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.callback.IpcCallbackManagerProxy;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.DebugUtil;

public class CrossProcessActionCaller {
  public static void callHostProcessAsync(CrossProcessCallEntity paramCrossProcessCallEntity, IpcCallback paramIpcCallback) throws RemoteException {
    IMiniApp2HostBinderInterface iMiniApp2HostBinderInterface = ServiceBindManager.getInstance().getHostCrossProcessCallSync();
    int i = 0;
    if (iMiniApp2HostBinderInterface == null) {
      if (paramIpcCallback != null)
        paramIpcCallback.onIpcConnectError(); 
      AppBrandLogger.e("CrossProcessActionCaller", new Object[] { "主进程被杀死或绑定主进程服务异常. CallType:", paramCrossProcessCallEntity.getCallType() });
      return;
    } 
    if (paramIpcCallback != null) {
      paramIpcCallback.setCallProcessIdentify(paramCrossProcessCallEntity.getCallProcessIdentify());
      IpcCallbackManagerProxy.getInstance().registerIpcCallback(paramIpcCallback);
    } 
    if (paramIpcCallback != null)
      i = paramIpcCallback.getCallbackId(); 
    iMiniApp2HostBinderInterface.asyncCall(paramCrossProcessCallEntity, i);
  }
  
  public static CrossProcessDataEntity callHostProcessSync(CrossProcessCallEntity paramCrossProcessCallEntity, ISyncCallInterceptor paramISyncCallInterceptor) throws RemoteException {
    AppBrandLogger.i("CrossProcessActionCaller", new Object[] { "before_getHostCrossProcessCallSync", "CallType:", paramCrossProcessCallEntity.getCallType() });
    IMiniApp2HostBinderInterface iMiniApp2HostBinderInterface = ServiceBindManager.getInstance().getHostCrossProcessCallSync();
    AppBrandLogger.i("CrossProcessActionCaller", new Object[] { "after_getHostCrossProcessCallSync", String.valueOf(iMiniApp2HostBinderInterface) });
    String str = paramCrossProcessCallEntity.getCallType();
    if (iMiniApp2HostBinderInterface == null) {
      if (Looper.getMainLooper() == Looper.myLooper()) {
        AppBrandLogger.e("CrossProcessActionCaller", new Object[] { "同步跨进程通信请勿在主线程中执行. CallType:", str });
      } else {
        AppBrandLogger.e("CrossProcessActionCaller", new Object[] { "主进程被杀死或绑定主进程服务异常. CallType:", str });
      } 
      paramCrossProcessCallEntity = null;
      if (paramISyncCallInterceptor != null)
        crossProcessDataEntity1 = paramISyncCallInterceptor.interceptSyncCallResult(str, null, false); 
      return crossProcessDataEntity1;
    } 
    CrossProcessDataEntity crossProcessDataEntity2 = iMiniApp2HostBinderInterface.syncCall((CrossProcessCallEntity)crossProcessDataEntity1);
    CrossProcessDataEntity crossProcessDataEntity1 = crossProcessDataEntity2;
    if (paramISyncCallInterceptor != null)
      crossProcessDataEntity1 = paramISyncCallInterceptor.interceptSyncCallResult(str, crossProcessDataEntity2, true); 
    return crossProcessDataEntity1;
  }
  
  public static void callMiniAppProcessAsync(CrossProcessCallEntity paramCrossProcessCallEntity, IpcCallback paramIpcCallback) {
    StringBuilder stringBuilder;
    AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByProcessIdentity(paramCrossProcessCallEntity.getCallProcessIdentify());
    if (processInfo == null) {
      stringBuilder = new StringBuilder("processInfo is null! ");
      stringBuilder.append(paramCrossProcessCallEntity.getCallerProcessIdentify());
      DebugUtil.logOrThrow("CrossProcessActionCaller", new Object[] { stringBuilder.toString() });
      return;
    } 
    if (stringBuilder != null) {
      stringBuilder.setCallProcessIdentify(paramCrossProcessCallEntity.getCallProcessIdentify());
      IpcCallbackManagerProxy.getInstance().registerIpcCallback((IpcCallback)stringBuilder);
    } 
    processInfo.mMiniProcessMonitor.asyncCallMiniAppProcess(paramCrossProcessCallEntity, (IpcCallback)stringBuilder);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\caller\CrossProcessActionCaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */