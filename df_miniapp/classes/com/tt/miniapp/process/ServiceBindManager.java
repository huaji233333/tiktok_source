package com.tt.miniapp.process;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.process.base.HostCrossProcessCallService;
import com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface;
import com.tt.miniapphost.process.callback.IpcCallbackManagerProxy;
import com.tt.miniapphost.util.DebugUtil;
import java.util.ArrayList;
import java.util.List;

public class ServiceBindManager {
  private static List<HostProcessLifeListener> sHostProcessLifeListenerList = new ArrayList<HostProcessLifeListener>();
  
  public volatile boolean mBoundHostService;
  
  public IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
      public void binderDied() {
        ServiceBindManager.this.rebindHostService();
      }
    };
  
  public final Object mGetHostCrossProcessCallLock = new Object();
  
  public volatile IMiniApp2HostBinderInterface mHostCrossProcessCall;
  
  public final ServiceConnection mHostServiceConnection = new ServiceConnection() {
      public void onNullBinding(ComponentName param1ComponentName) {
        synchronized (ServiceBindManager.this.mGetHostCrossProcessCallLock) {
          AppbrandContext.getInst().getApplicationContext().unbindService(ServiceBindManager.this.mHostServiceConnection);
          ServiceBindManager.this.mPendingBindHostService = false;
          ServiceBindManager.this.mGetHostCrossProcessCallLock.notifyAll();
          AppBrandLogger.e("ServiceBindManager", new Object[] { "onNullBinding" });
          return;
        } 
      }
      
      public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
        AppBrandLogger.i("ServiceBindManager", new Object[] { "iBinder", param1IBinder });
        try {
          String str = param1IBinder.getInterfaceDescriptor();
        } catch (Exception exception) {
          AppBrandLogger.e("ServiceBindManager", new Object[] { "getInterfaceDescriptor", exception });
          exception = null;
        } 
        if (TextUtils.isEmpty((CharSequence)exception) || TextUtils.equals((CharSequence)exception, "com.tt.miniapphost.process.base.EmptyBinder")) {
          DebugUtil.outputError("ServiceBindManager", new Object[] { "绑定了空 Binder interfaceDescriptor:", exception });
          onNullBinding(param1ComponentName);
          return;
        } 
        synchronized (ServiceBindManager.this.mGetHostCrossProcessCallLock) {
          ServiceBindManager.this.mHostCrossProcessCall = IMiniApp2HostBinderInterface.Stub.asInterface(param1IBinder);
          ServiceBindManager.this.mPendingBindHostService = false;
          ServiceBindManager.this.mGetHostCrossProcessCallLock.notifyAll();
          try {
            if (ServiceBindManager.this.mHostCrossProcessCall == null) {
              null = new StringBuilder("onServiceConnected mHostCrossProcessCall == null. iBinder:");
              null.append(param1IBinder);
              DebugUtil.outputError("ServiceBindManager", new Object[] { null.toString() });
              return;
            } 
            ServiceBindManager.this.mHostCrossProcessCall.asBinder().linkToDeath(ServiceBindManager.this.mDeathRecipient, 0);
            ServiceBindManager.this.onBindService(ServiceBindManager.this.mBoundHostService);
            if (!ServiceBindManager.this.mBoundHostService)
              ServiceBindManager.this.mBoundHostService = true; 
            return;
          } catch (Exception null) {
            AppBrandLogger.e("ServiceBindManager", new Object[] { "onServiceConnected", null });
            return;
          } 
        } 
      }
      
      public void onServiceDisconnected(ComponentName param1ComponentName) {}
    };
  
  public volatile boolean mPendingBindHostService;
  
  private ServiceBindManager() {}
  
  public static ServiceBindManager getInstance() {
    return Holder.mInstance;
  }
  
  private void onHostProcessDied() {
    // Byte code:
    //   0: ldc com/tt/miniapp/process/AppProcessManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/process/ServiceBindManager.sHostProcessLifeListenerList : Ljava/util/List;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore_1
    //   12: aload_1
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 38
    //   21: aload_1
    //   22: invokeinterface next : ()Ljava/lang/Object;
    //   27: checkcast com/tt/miniapp/process/ServiceBindManager$HostProcessLifeListener
    //   30: invokeinterface onDied : ()V
    //   35: goto -> 12
    //   38: ldc com/tt/miniapp/process/AppProcessManager
    //   40: monitorexit
    //   41: return
    //   42: astore_1
    //   43: ldc com/tt/miniapp/process/AppProcessManager
    //   45: monitorexit
    //   46: goto -> 51
    //   49: aload_1
    //   50: athrow
    //   51: goto -> 49
    // Exception table:
    //   from	to	target	type
    //   3	12	42	finally
    //   12	35	42	finally
    //   38	41	42	finally
    //   43	46	42	finally
  }
  
  public static void registerHostProcessLifeListener(HostProcessLifeListener paramHostProcessLifeListener) {
    // Byte code:
    //   0: ldc 'ServiceBindManager'
    //   2: iconst_2
    //   3: anewarray java/lang/Object
    //   6: dup
    //   7: iconst_0
    //   8: ldc 'registerHostProcessLifeListener:'
    //   10: aastore
    //   11: dup
    //   12: iconst_1
    //   13: aload_0
    //   14: aastore
    //   15: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   18: aload_0
    //   19: ifnonnull -> 23
    //   22: return
    //   23: ldc com/tt/miniapp/process/AppProcessManager
    //   25: monitorenter
    //   26: getstatic com/tt/miniapp/process/ServiceBindManager.sHostProcessLifeListenerList : Ljava/util/List;
    //   29: aload_0
    //   30: invokeinterface add : (Ljava/lang/Object;)Z
    //   35: pop
    //   36: ldc com/tt/miniapp/process/AppProcessManager
    //   38: monitorexit
    //   39: return
    //   40: astore_0
    //   41: ldc com/tt/miniapp/process/AppProcessManager
    //   43: monitorexit
    //   44: aload_0
    //   45: athrow
    // Exception table:
    //   from	to	target	type
    //   26	39	40	finally
    //   41	44	40	finally
  }
  
  public static void unregisterHostProcessLifeListener(HostProcessLifeListener paramHostProcessLifeListener) {
    // Byte code:
    //   0: ldc 'ServiceBindManager'
    //   2: iconst_2
    //   3: anewarray java/lang/Object
    //   6: dup
    //   7: iconst_0
    //   8: ldc 'unregisterHostProcessLifeListener:'
    //   10: aastore
    //   11: dup
    //   12: iconst_1
    //   13: aload_0
    //   14: aastore
    //   15: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   18: aload_0
    //   19: ifnonnull -> 23
    //   22: return
    //   23: ldc com/tt/miniapp/process/AppProcessManager
    //   25: monitorenter
    //   26: getstatic com/tt/miniapp/process/ServiceBindManager.sHostProcessLifeListenerList : Ljava/util/List;
    //   29: aload_0
    //   30: invokeinterface remove : (Ljava/lang/Object;)Z
    //   35: pop
    //   36: ldc com/tt/miniapp/process/AppProcessManager
    //   38: monitorexit
    //   39: return
    //   40: astore_0
    //   41: ldc com/tt/miniapp/process/AppProcessManager
    //   43: monitorexit
    //   44: aload_0
    //   45: athrow
    // Exception table:
    //   from	to	target	type
    //   26	39	40	finally
    //   41	44	40	finally
  }
  
  public void bindHostService() {
    AppBrandLogger.i("ServiceBindManager", new Object[] { "bindHostService" });
    synchronized (this.mGetHostCrossProcessCallLock) {
      if (this.mHostCrossProcessCall != null)
        return; 
      if (this.mPendingBindHostService)
        return; 
      this.mPendingBindHostService = true;
      null = AppbrandContext.getInst().getApplicationContext();
      _lancet.com_ss_android_ugc_aweme_push_downgrade_StartServiceLancet_bindService((Context)null, new Intent((Context)null, HostCrossProcessCallService.class), this.mHostServiceConnection, 1);
      return;
    } 
  }
  
  public IMiniApp2HostBinderInterface getHostCrossProcessCallSync() {
    if (this.mHostCrossProcessCall != null)
      return this.mHostCrossProcessCall; 
    if (!AppProcessManager.isHostProcessExist((Context)AppbrandContext.getInst().getApplicationContext()))
      synchronized (this.mGetHostCrossProcessCallLock) {
        if (!this.mPendingBindHostService) {
          DebugUtil.outputError("ServiceBindManager", new Object[] { "宿主进程已被杀死，此功能暂不支持" });
          return null;
        } 
      }  
    bindHostService();
    if (Looper.getMainLooper() == Looper.myLooper()) {
      DebugUtil.outputError("ServiceBindManager", new Object[] { "跨进程通信请勿在主线程中执行" });
      return null;
    } 
    Object object = this.mGetHostCrossProcessCallLock;
    /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
    try {
      if (this.mHostCrossProcessCall == null && this.mPendingBindHostService) {
        long l = System.currentTimeMillis();
        this.mGetHostCrossProcessCallLock.wait(10000L);
        l = System.currentTimeMillis() - l;
        if (l >= 10000L && !AppBrandMonitor.isReportByProcessFramework()) {
          DebugUtil.outputError("ServiceBindManager", new Object[] { "bindHostServiceTimeout waitTime:", Long.valueOf(l) });
          StringBuilder stringBuilder = new StringBuilder("waitTime:");
          stringBuilder.append(l);
          AppBrandMonitor.reportError("bindHostServiceTimeout", stringBuilder.toString(), "");
        } 
      } 
    } catch (InterruptedException interruptedException) {
      AppBrandLogger.e("ServiceBindManager", new Object[] { "getHostCrossProcessCallSync", interruptedException });
    } finally {
      Exception exception;
    } 
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
    return this.mHostCrossProcessCall;
  }
  
  public void onBindService(boolean paramBoolean) {
    // Byte code:
    //   0: ldc com/tt/miniapp/process/AppProcessManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/process/ServiceBindManager.sHostProcessLifeListenerList : Ljava/util/List;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 39
    //   21: aload_2
    //   22: invokeinterface next : ()Ljava/lang/Object;
    //   27: checkcast com/tt/miniapp/process/ServiceBindManager$HostProcessLifeListener
    //   30: iload_1
    //   31: invokeinterface onAlive : (Z)V
    //   36: goto -> 12
    //   39: ldc com/tt/miniapp/process/AppProcessManager
    //   41: monitorexit
    //   42: return
    //   43: astore_2
    //   44: ldc com/tt/miniapp/process/AppProcessManager
    //   46: monitorexit
    //   47: goto -> 52
    //   50: aload_2
    //   51: athrow
    //   52: goto -> 50
    // Exception table:
    //   from	to	target	type
    //   3	12	43	finally
    //   12	36	43	finally
    //   39	42	43	finally
    //   44	47	43	finally
  }
  
  public void rebindHostService() {
    AppBrandLogger.i("ServiceBindManager", new Object[] { "rebindHostService" });
    synchronized (this.mGetHostCrossProcessCallLock) {
      if (this.mHostCrossProcessCall == null)
        return; 
      this.mHostCrossProcessCall.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
      this.mHostCrossProcessCall = null;
      if (AppProcessManager.isHostProcessExist((Context)AppbrandContext.getInst().getApplicationContext())) {
        bindHostService();
        return;
      } 
      IpcCallbackManagerProxy.getInstance().onCallProcessDead("hostProcess");
      DebugUtil.outputError("ServiceBindManager", new Object[] { "宿主进程已被杀死" });
      onHostProcessDied();
      return;
    } 
  }
  
  public static class Holder {
    public static ServiceBindManager mInstance = new ServiceBindManager();
  }
  
  public static interface HostProcessLifeListener {
    void onAlive(boolean param1Boolean);
    
    void onDied();
  }
  
  class ServiceBindManager {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\ServiceBindManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */