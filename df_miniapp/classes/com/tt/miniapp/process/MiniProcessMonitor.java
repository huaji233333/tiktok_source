package com.tt.miniapp.process;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Pair;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.base.Host2MiniAppBinderStub;
import com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.util.DebugUtil;
import java.util.LinkedList;

public class MiniProcessMonitor {
  private boolean mBoundService;
  
  public IBinder.DeathRecipient mDeathRecipient;
  
  public final Object mLock = new Object();
  
  private LinkedList<Pair<CrossProcessCallEntity, IpcCallback>> mPendingCallList = new LinkedList<Pair<CrossProcessCallEntity, IpcCallback>>();
  
  private final AppProcessManager.ProcessInfo mProcessInfo;
  
  private final ProcessLifeListener mProcessLifeListener;
  
  public volatile IHost2MiniAppBinderInterface mRealMonitor;
  
  private ServiceConnection mServiceConnection;
  
  MiniProcessMonitor(AppProcessManager.ProcessInfo paramProcessInfo, ProcessLifeListener paramProcessLifeListener) {
    this.mProcessInfo = paramProcessInfo;
    this.mProcessLifeListener = paramProcessLifeListener;
    this.mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
          StringBuilder stringBuilder = new StringBuilder("49411_onServiceConnected: ");
          stringBuilder.append(param1ComponentName);
          stringBuilder.append(", ");
          stringBuilder.append(param1IBinder);
          AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { stringBuilder.toString() });
          synchronized (MiniProcessMonitor.this.mLock) {
            MiniProcessMonitor.this.mRealMonitor = Host2MiniAppBinderStub.asInterface(param1IBinder);
            if (MiniProcessMonitor.this.mRealMonitor == null) {
              null = new StringBuilder("onServiceConnected mRealMonitor == null. iBinder:");
              null.append(param1IBinder);
              AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { null.toString() });
              MiniProcessMonitor.this.onProcessDied();
              return;
            } 
            try {
              MiniProcessMonitor.this.mRealMonitor.asBinder().linkToDeath(MiniProcessMonitor.this.mDeathRecipient, 0);
              MiniProcessMonitor.this.onProcessAlive();
              return;
            } catch (RemoteException null) {
              synchronized (MiniProcessMonitor.this.mLock) {
                MiniProcessMonitor.this.mRealMonitor = null;
                MiniProcessMonitor.this.onProcessDied();
                AppBrandLogger.eWithThrowable("tma_MiniProcessMonitor", "49411_link2death exp!", (Throwable)null);
                return;
              } 
            } 
          } 
        }
        
        public void onServiceDisconnected(ComponentName param1ComponentName) {
          StringBuilder stringBuilder = new StringBuilder("49411_onServiceDisconnected: ");
          stringBuilder.append(Thread.currentThread().getName());
          AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { stringBuilder.toString() });
        }
      };
    this.mDeathRecipient = new IBinder.DeathRecipient() {
        public void binderDied() {
          StringBuilder stringBuilder = new StringBuilder("49411_binderDied: ");
          stringBuilder.append(Thread.currentThread().getName());
          AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { stringBuilder.toString() });
          synchronized (MiniProcessMonitor.this.mLock) {
            if (MiniProcessMonitor.this.mRealMonitor == null)
              return; 
            MiniProcessMonitor.this.mRealMonitor.asBinder().unlinkToDeath(MiniProcessMonitor.this.mDeathRecipient, 0);
            MiniProcessMonitor.this.mRealMonitor = null;
            MiniProcessMonitor.this.onProcessDied();
            return;
          } 
        }
      };
  }
  
  private boolean rebindIfCan() {
    if (AppProcessManager.isProcessExist((Context)AppbrandContext.getInst().getApplicationContext(), this.mProcessInfo.mProcessName)) {
      startMonitorMiniAppProcess();
      return this.mBoundService;
    } 
    return false;
  }
  
  private void stopMonitorMiniAppProcess() {
    synchronized (this.mLock) {
      if (this.mBoundService) {
        StringBuilder stringBuilder = new StringBuilder("stopMonitorMiniAppProcess unbindService processName: ");
        stringBuilder.append(this.mProcessInfo.mProcessName);
        AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { stringBuilder.toString() });
        try {
          AppbrandContext.getInst().getApplicationContext().unbindService(this.mServiceConnection);
        } catch (Exception exception) {
          StringBuilder stringBuilder1 = new StringBuilder("monitor stopMonitorMiniAppProcess error: ");
          stringBuilder1.append(exception);
          DebugUtil.outputError("tma_MiniProcessMonitor", new Object[] { stringBuilder1.toString() });
        } 
        this.mBoundService = false;
      } 
      return;
    } 
  }
  
  public void asyncCallMiniAppProcess(CrossProcessCallEntity paramCrossProcessCallEntity, IpcCallback paramIpcCallback) {
    IHost2MiniAppBinderInterface iHost2MiniAppBinderInterface = this.mRealMonitor;
    if (iHost2MiniAppBinderInterface == null) {
      synchronized (this.mLock) {
        if (this.mRealMonitor == null) {
          if (this.mBoundService) {
            this.mPendingCallList.addLast(new Pair(paramCrossProcessCallEntity, paramIpcCallback));
          } else {
            boolean bool1 = true;
          } 
        } else {
          iHost2MiniAppBinderInterface = this.mRealMonitor;
        } 
      } 
    } else {
      int i = 0;
      if (iHost2MiniAppBinderInterface != null) {
        if (paramIpcCallback != null)
          try {
            i = paramIpcCallback.getCallbackId();
            iHost2MiniAppBinderInterface.asyncCallMiniProcess(paramCrossProcessCallEntity, i);
            return;
          } catch (RemoteException remoteException) {
            AppBrandLogger.e("tma_MiniProcessMonitor", new Object[] { remoteException });
            return;
          }  
      } else {
        if (i != 0) {
          if (rebindIfCan()) {
            asyncCallMiniAppProcess((CrossProcessCallEntity)remoteException, paramIpcCallback);
            return;
          } 
          if (paramIpcCallback != null) {
            paramIpcCallback.finishListenIpcCallback();
            paramIpcCallback.onIpcConnectError();
          } 
        } 
        return;
      } 
    } 
    boolean bool = false;
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_5} */
  }
  
  public String getProcessName() {
    return this.mProcessInfo.mProcessName;
  }
  
  public boolean isAlive() {
    return (this.mRealMonitor != null);
  }
  
  public void onProcessAlive() {
    null = new StringBuilder("onProcessAlive: ");
    null.append(this.mProcessInfo.mProcessName);
    AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { null.toString() });
    this.mProcessLifeListener.onAlive(this.mProcessInfo);
    synchronized (this.mLock) {
      if (!this.mPendingCallList.isEmpty()) {
        LinkedList<Pair<CrossProcessCallEntity, IpcCallback>> linkedList = this.mPendingCallList;
        this.mPendingCallList = new LinkedList<Pair<CrossProcessCallEntity, IpcCallback>>();
      } else {
        null = null;
      } 
      if (null != null)
        while (!null.isEmpty()) {
          null = null.removeFirst();
          asyncCallMiniAppProcess((CrossProcessCallEntity)((Pair)null).first, (IpcCallback)((Pair)null).second);
        }  
      return;
    } 
  }
  
  public void onProcessDied() {
    null = new StringBuilder("onProcessDied: ");
    null.append(this.mProcessInfo.mProcessName);
    AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { null.toString() });
    synchronized (this.mLock) {
      if (!this.mPendingCallList.isEmpty()) {
        LinkedList<Pair<CrossProcessCallEntity, IpcCallback>> linkedList = this.mPendingCallList;
        this.mPendingCallList = new LinkedList<Pair<CrossProcessCallEntity, IpcCallback>>();
      } else {
        null = null;
      } 
      if (null != null)
        while (!null.isEmpty()) {
          null = null.removeFirst();
          if (((Pair)null).second != null) {
            ((IpcCallback)((Pair)null).second).onIpcConnectError();
            ((IpcCallback)((Pair)null).second).finishListenIpcCallback();
          } 
        }  
      stopMonitorMiniAppProcess();
      this.mProcessLifeListener.onDied(this.mProcessInfo);
      return;
    } 
  }
  
  void startMonitorMiniAppProcess() {
    synchronized (this.mLock) {
      if (this.mRealMonitor != null) {
        AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { "mRealMonitor != null" });
        return;
      } 
      if (this.mBoundService) {
        AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { "mPendingBindMiniAppService" });
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("startMonitorMiniAppProcess bindService processName: ");
      stringBuilder.append(this.mProcessInfo.mProcessName);
      AppBrandLogger.d("tma_MiniProcessMonitor", new Object[] { stringBuilder.toString() });
      Application application = AppbrandContext.getInst().getApplicationContext();
      this.mBoundService = _lancet.com_ss_android_ugc_aweme_push_downgrade_StartServiceLancet_bindService((Context)application, new Intent((Context)application, this.mProcessInfo.mPreloadServiceClass), this.mServiceConnection, 1);
      return;
    } 
  }
  
  public static interface ProcessLifeListener {
    void onAlive(AppProcessManager.ProcessInfo param1ProcessInfo);
    
    void onDied(AppProcessManager.ProcessInfo param1ProcessInfo);
  }
  
  class MiniProcessMonitor {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\MiniProcessMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */