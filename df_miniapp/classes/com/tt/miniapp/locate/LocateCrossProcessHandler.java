package com.tt.miniapp.locate;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.storage.async.Action;
import com.tt.miniapp.maplocate.ILocator;
import com.tt.miniapp.maplocate.TMALocation;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import java.util.ArrayList;
import java.util.Iterator;

public class LocateCrossProcessHandler implements Handler.Callback {
  private static LocateCrossProcessHandler sInst;
  
  private Handler handler;
  
  private boolean isRequestingLocate = false;
  
  private ILocator.ILocationListener listener;
  
  private ILocator mLocationManager;
  
  private ArrayList<AsyncIpcHandler> mWaitingListenerList = new ArrayList<AsyncIpcHandler>();
  
  private LocateCrossProcessHandler(Context paramContext) {
    this.mLocationManager = HostDependManager.getInst().createLocateInstance(paramContext);
    if (this.mLocationManager == null) {
      AppBrandLogger.d("no lcoate instance,return", new Object[0]);
      return;
    } 
    this.listener = new ILocator.ILocationListener() {
        public void onLocationChanged(TMALocation param1TMALocation) {
          LocateCrossProcessHandler.this.onLocationGot(param1TMALocation);
        }
      };
    this.handler = new Handler(Looper.getMainLooper(), this);
  }
  
  private static CrossProcessDataEntity createCrossEntity(TMALocation paramTMALocation) {
    return (paramTMALocation == null) ? null : ((paramTMALocation.getStatusCode() != 0) ? CrossProcessDataEntity.Builder.create().put("code", Integer.valueOf(-1)).put("locationResult", paramTMALocation.toJson()).build() : CrossProcessDataEntity.Builder.create().put("code", Integer.valueOf(1)).put("locationResult", paramTMALocation.toJson()).build());
  }
  
  private void dispatchResultAndClearWaiting(TMALocation paramTMALocation) {
    if (paramTMALocation == null)
      return; 
    CrossProcessDataEntity crossProcessDataEntity = createCrossEntity(paramTMALocation);
    if (crossProcessDataEntity == null)
      return; 
    Iterator<AsyncIpcHandler> iterator = this.mWaitingListenerList.iterator();
    while (iterator.hasNext())
      ((AsyncIpcHandler)iterator.next()).callback(crossProcessDataEntity); 
    this.mWaitingListenerList.clear();
  }
  
  public static LocateCrossProcessHandler inst(Context paramContext) {
    // Byte code:
    //   0: getstatic com/tt/miniapp/locate/LocateCrossProcessHandler.sInst : Lcom/tt/miniapp/locate/LocateCrossProcessHandler;
    //   3: ifnonnull -> 38
    //   6: ldc com/tt/miniapp/locate/LocateCrossProcessHandler
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/locate/LocateCrossProcessHandler.sInst : Lcom/tt/miniapp/locate/LocateCrossProcessHandler;
    //   12: ifnonnull -> 26
    //   15: new com/tt/miniapp/locate/LocateCrossProcessHandler
    //   18: dup
    //   19: aload_0
    //   20: invokespecial <init> : (Landroid/content/Context;)V
    //   23: putstatic com/tt/miniapp/locate/LocateCrossProcessHandler.sInst : Lcom/tt/miniapp/locate/LocateCrossProcessHandler;
    //   26: ldc com/tt/miniapp/locate/LocateCrossProcessHandler
    //   28: monitorexit
    //   29: goto -> 38
    //   32: astore_0
    //   33: ldc com/tt/miniapp/locate/LocateCrossProcessHandler
    //   35: monitorexit
    //   36: aload_0
    //   37: athrow
    //   38: getstatic com/tt/miniapp/locate/LocateCrossProcessHandler.sInst : Lcom/tt/miniapp/locate/LocateCrossProcessHandler;
    //   41: areturn
    // Exception table:
    //   from	to	target	type
    //   9	26	32	finally
    //   26	29	32	finally
    //   33	36	32	finally
  }
  
  public void getLocation(AsyncIpcHandler paramAsyncIpcHandler) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 'LocateCrossProcessHandler'
    //   4: iconst_1
    //   5: anewarray java/lang/Object
    //   8: dup
    //   9: iconst_0
    //   10: ldc 'getLocation'
    //   12: aastore
    //   13: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   16: aload_0
    //   17: getfield mLocationManager : Lcom/tt/miniapp/maplocate/ILocator;
    //   20: invokeinterface getLastKnwonLocation : ()Lcom/tt/miniapp/maplocate/TMALocation;
    //   25: astore_2
    //   26: aload_2
    //   27: ifnull -> 86
    //   30: aload_2
    //   31: invokevirtual getStatusCode : ()I
    //   34: ifne -> 86
    //   37: invokestatic currentTimeMillis : ()J
    //   40: aload_2
    //   41: invokevirtual getTime : ()J
    //   44: lsub
    //   45: ldc2_w 60000
    //   48: lcmp
    //   49: ifge -> 86
    //   52: ldc 'LocateCrossProcessHandler'
    //   54: iconst_1
    //   55: anewarray java/lang/Object
    //   58: dup
    //   59: iconst_0
    //   60: ldc 'call back lastknown'
    //   62: aastore
    //   63: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   66: aload_2
    //   67: invokestatic createCrossEntity : (Lcom/tt/miniapp/maplocate/TMALocation;)Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;
    //   70: astore_2
    //   71: aload_2
    //   72: ifnonnull -> 78
    //   75: aload_0
    //   76: monitorexit
    //   77: return
    //   78: aload_1
    //   79: aload_2
    //   80: invokevirtual callback : (Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;)V
    //   83: aload_0
    //   84: monitorexit
    //   85: return
    //   86: aload_0
    //   87: getfield mWaitingListenerList : Ljava/util/ArrayList;
    //   90: aload_1
    //   91: invokevirtual add : (Ljava/lang/Object;)Z
    //   94: pop
    //   95: ldc 'LocateCrossProcessHandler'
    //   97: iconst_1
    //   98: anewarray java/lang/Object
    //   101: dup
    //   102: iconst_0
    //   103: ldc 'add listener'
    //   105: aastore
    //   106: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   109: aload_0
    //   110: getfield isRequestingLocate : Z
    //   113: ifne -> 167
    //   116: aload_0
    //   117: getfield handler : Landroid/os/Handler;
    //   120: iconst_1
    //   121: ldc2_w 7000
    //   124: invokevirtual sendEmptyMessageDelayed : (IJ)Z
    //   127: pop
    //   128: new com/tt/miniapp/maplocate/ILocator$LocateConfig
    //   131: dup
    //   132: invokespecial <init> : ()V
    //   135: astore_1
    //   136: aload_1
    //   137: iconst_0
    //   138: putfield isUseGps : Z
    //   141: aload_1
    //   142: ldc2_w 7000
    //   145: putfield timeout : J
    //   148: aload_0
    //   149: getfield mLocationManager : Lcom/tt/miniapp/maplocate/ILocator;
    //   152: aload_1
    //   153: aload_0
    //   154: getfield listener : Lcom/tt/miniapp/maplocate/ILocator$ILocationListener;
    //   157: invokeinterface startLocate : (Lcom/tt/miniapp/maplocate/ILocator$LocateConfig;Lcom/tt/miniapp/maplocate/ILocator$ILocationListener;)V
    //   162: aload_0
    //   163: iconst_1
    //   164: putfield isRequestingLocate : Z
    //   167: aload_0
    //   168: monitorexit
    //   169: return
    //   170: astore_1
    //   171: aload_0
    //   172: monitorexit
    //   173: aload_1
    //   174: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	170	finally
    //   30	71	170	finally
    //   78	83	170	finally
    //   86	167	170	finally
  }
  
  public boolean handleMessage(Message paramMessage) {
    if (paramMessage.what == 1) {
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              LocateCrossProcessHandler.this.onLocationGot(new TMALocation(2));
            }
          },  ThreadPools.defaults());
      return true;
    } 
    return false;
  }
  
  public void onLocationGot(TMALocation paramTMALocation) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield handler : Landroid/os/Handler;
    //   6: iconst_1
    //   7: invokevirtual removeMessages : (I)V
    //   10: aload_0
    //   11: iconst_0
    //   12: putfield isRequestingLocate : Z
    //   15: aload_0
    //   16: getfield mLocationManager : Lcom/tt/miniapp/maplocate/ILocator;
    //   19: aload_0
    //   20: getfield listener : Lcom/tt/miniapp/maplocate/ILocator$ILocationListener;
    //   23: invokeinterface stopLocate : (Lcom/tt/miniapp/maplocate/ILocator$ILocationListener;)V
    //   28: aload_1
    //   29: invokestatic isSuccess : (Lcom/tt/miniapp/maplocate/TMALocation;)Z
    //   32: ifeq -> 57
    //   35: ldc 'LocateCrossProcessHandler'
    //   37: iconst_1
    //   38: anewarray java/lang/Object
    //   41: dup
    //   42: iconst_0
    //   43: ldc 'onLocationGot success'
    //   45: aastore
    //   46: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   49: aload_0
    //   50: aload_1
    //   51: invokespecial dispatchResultAndClearWaiting : (Lcom/tt/miniapp/maplocate/TMALocation;)V
    //   54: aload_0
    //   55: monitorexit
    //   56: return
    //   57: aload_0
    //   58: getfield mLocationManager : Lcom/tt/miniapp/maplocate/ILocator;
    //   61: invokeinterface getLastKnwonLocation : ()Lcom/tt/miniapp/maplocate/TMALocation;
    //   66: astore_2
    //   67: aload_2
    //   68: ifnull -> 93
    //   71: aload_0
    //   72: aload_2
    //   73: invokespecial dispatchResultAndClearWaiting : (Lcom/tt/miniapp/maplocate/TMALocation;)V
    //   76: ldc 'LocateCrossProcessHandler'
    //   78: iconst_1
    //   79: anewarray java/lang/Object
    //   82: dup
    //   83: iconst_0
    //   84: ldc 'onLocationGot failed,call back cache'
    //   86: aastore
    //   87: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   90: aload_0
    //   91: monitorexit
    //   92: return
    //   93: ldc 'LocateCrossProcessHandler'
    //   95: iconst_1
    //   96: anewarray java/lang/Object
    //   99: dup
    //   100: iconst_0
    //   101: ldc 'onLocationGot callback failed'
    //   103: aastore
    //   104: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   107: aload_0
    //   108: aload_1
    //   109: invokespecial dispatchResultAndClearWaiting : (Lcom/tt/miniapp/maplocate/TMALocation;)V
    //   112: aload_0
    //   113: monitorexit
    //   114: return
    //   115: astore_1
    //   116: aload_0
    //   117: monitorexit
    //   118: aload_1
    //   119: athrow
    // Exception table:
    //   from	to	target	type
    //   2	54	115	finally
    //   57	67	115	finally
    //   71	90	115	finally
    //   93	112	115	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\locate\LocateCrossProcessHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */