package com.tt.miniapp.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class AppbrandBroadcastService extends AppbrandServiceManager.ServiceBase {
  public static final LightBroadcastObj[] mBroadCastObjs = new LightBroadcastObj[] { new LightBroadcastObj(0, "android.intent.action.TIME_SET", 500), new LightBroadcastObj(1, "android.intent.action.TIMEZONE_CHANGED", 1000) };
  
  public AppbrandBroadcastService(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private LightBroadcastObj getBroadcastObjForType(int paramInt) {
    for (LightBroadcastObj lightBroadcastObj : mBroadCastObjs) {
      if (lightBroadcastObj.mType == paramInt)
        return lightBroadcastObj; 
    } 
    return null;
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_CREATE})
  public void onAppCreate() {
    IntentFilter intentFilter = new IntentFilter();
    LightBroadcastObj[] arrayOfLightBroadcastObj = mBroadCastObjs;
    int j = arrayOfLightBroadcastObj.length;
    for (int i = 0; i < j; i++)
      intentFilter.addAction((arrayOfLightBroadcastObj[i]).mAction); 
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context param1Context, Intent param1Intent) {
          String str = param1Intent.getAction();
          for (AppbrandBroadcastService.LightBroadcastObj lightBroadcastObj : AppbrandBroadcastService.mBroadCastObjs) {
            if (TextUtils.equals(lightBroadcastObj.mAction, str))
              lightBroadcastObj.onReceive(param1Context, param1Intent); 
          } 
        }
      };
    AppbrandContext.getInst().getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
  }
  
  public void registerReceiver(int paramInt, LightBroadcastReceiver paramLightBroadcastReceiver) {
    LightBroadcastObj lightBroadcastObj = getBroadcastObjForType(paramInt);
    if (lightBroadcastObj != null) {
      lightBroadcastObj.addReceiver(paramLightBroadcastReceiver);
      return;
    } 
    AppBrandLogger.e("AppbrandBroadcastService", new Object[] { "LightBroadcastObj is null at register: ", Integer.valueOf(paramInt) });
  }
  
  public void unregisterReceiver(int paramInt, LightBroadcastReceiver paramLightBroadcastReceiver) {
    LightBroadcastObj lightBroadcastObj = getBroadcastObjForType(paramInt);
    if (lightBroadcastObj != null) {
      lightBroadcastObj.removeReceiver(paramLightBroadcastReceiver);
      return;
    } 
    AppBrandLogger.e("AppbrandBroadcastService", new Object[] { "LightBroadcastObj is null at unregister: ", Integer.valueOf(paramInt) });
  }
  
  static class LightBroadcastObj {
    public final String mAction;
    
    private final long mInterval;
    
    private long mLastReceiveTimeStamp;
    
    private volatile CopyOnWriteArrayList<AppbrandBroadcastService.LightBroadcastReceiver> mReceiverList;
    
    public final int mType;
    
    LightBroadcastObj(int param1Int1, String param1String, int param1Int2) {
      this.mType = param1Int1;
      this.mAction = param1String;
      this.mInterval = param1Int2;
    }
    
    public void addReceiver(AppbrandBroadcastService.LightBroadcastReceiver param1LightBroadcastReceiver) {
      // Byte code:
      //   0: aload_0
      //   1: getfield mReceiverList : Ljava/util/concurrent/CopyOnWriteArrayList;
      //   4: ifnonnull -> 37
      //   7: aload_0
      //   8: monitorenter
      //   9: aload_0
      //   10: getfield mReceiverList : Ljava/util/concurrent/CopyOnWriteArrayList;
      //   13: ifnonnull -> 27
      //   16: aload_0
      //   17: new java/util/concurrent/CopyOnWriteArrayList
      //   20: dup
      //   21: invokespecial <init> : ()V
      //   24: putfield mReceiverList : Ljava/util/concurrent/CopyOnWriteArrayList;
      //   27: aload_0
      //   28: monitorexit
      //   29: goto -> 37
      //   32: astore_1
      //   33: aload_0
      //   34: monitorexit
      //   35: aload_1
      //   36: athrow
      //   37: aload_0
      //   38: getfield mReceiverList : Ljava/util/concurrent/CopyOnWriteArrayList;
      //   41: aload_1
      //   42: invokevirtual addIfAbsent : (Ljava/lang/Object;)Z
      //   45: pop
      //   46: return
      // Exception table:
      //   from	to	target	type
      //   9	27	32	finally
      //   27	29	32	finally
      //   33	35	32	finally
    }
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      if (this.mReceiverList == null)
        return; 
      long l = SystemClock.uptimeMillis();
      if (l < this.mLastReceiveTimeStamp + this.mInterval)
        return; 
      this.mLastReceiveTimeStamp = l;
      AppBrandLogger.i("AppbrandBroadcastService", new Object[] { "receive broadcast", param1Intent.getAction() });
      Iterator<AppbrandBroadcastService.LightBroadcastReceiver> iterator = this.mReceiverList.iterator();
      while (iterator.hasNext())
        ((AppbrandBroadcastService.LightBroadcastReceiver)iterator.next()).onReceive(this.mType, param1Context, param1Intent); 
    }
    
    public void removeReceiver(AppbrandBroadcastService.LightBroadcastReceiver param1LightBroadcastReceiver) {
      if (this.mReceiverList == null)
        return; 
      this.mReceiverList.remove(param1LightBroadcastReceiver);
    }
  }
  
  public static interface LightBroadcastReceiver {
    void onReceive(int param1Int, Context param1Context, Intent param1Intent);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\AppbrandBroadcastService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */