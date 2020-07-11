package com.tt.miniapp.manager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.ArrayList;
import java.util.List;

public class NetStateManager {
  private static NetStateManager mInst;
  
  private NetStateBroadcastReceiver mNetStateBroadcastReceiver;
  
  private List<NetStateChangeListener> mNetStateChangeListenerList = new ArrayList<NetStateChangeListener>();
  
  private NetworkType mNetworkType = NetworkType.UNKNOWN;
  
  public static NetStateManager getInst() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/manager/NetStateManager.mInst : Lcom/tt/miniapp/manager/NetStateManager;
    //   3: ifnonnull -> 37
    //   6: ldc com/tt/miniapp/manager/NetStateManager
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/manager/NetStateManager.mInst : Lcom/tt/miniapp/manager/NetStateManager;
    //   12: ifnonnull -> 25
    //   15: new com/tt/miniapp/manager/NetStateManager
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/tt/miniapp/manager/NetStateManager.mInst : Lcom/tt/miniapp/manager/NetStateManager;
    //   25: ldc com/tt/miniapp/manager/NetStateManager
    //   27: monitorexit
    //   28: goto -> 37
    //   31: astore_0
    //   32: ldc com/tt/miniapp/manager/NetStateManager
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    //   37: getstatic com/tt/miniapp/manager/NetStateManager.mInst : Lcom/tt/miniapp/manager/NetStateManager;
    //   40: areturn
    // Exception table:
    //   from	to	target	type
    //   9	25	31	finally
    //   25	28	31	finally
    //   32	35	31	finally
  }
  
  private static NetworkType getLatestNetworkType(Context paramContext) {
    try {
      NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramContext.getSystemService("connectivity"));
      if (networkInfo == null || !networkInfo.isAvailable())
        return NetworkType.NONE; 
      int i = networkInfo.getType();
      return NetworkType.MOBILE;
    } finally {
      paramContext = null;
      AppBrandLogger.e("NetStateManager", new Object[] { paramContext });
    } 
  }
  
  private NetworkType getNetworkType() {
    return this.mNetworkType;
  }
  
  private void registerNetStateChangeReceiver() {
    if (this.mNetStateBroadcastReceiver != null)
      return; 
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null) {
      AppBrandLogger.e("NetStateManager", new Object[] { "registerNetStateChangeReceiver context == null " });
      return;
    } 
    this.mNetStateBroadcastReceiver = new NetStateBroadcastReceiver();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    try {
      application.getApplicationContext().registerReceiver(this.mNetStateBroadcastReceiver, intentFilter);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("NetStateManager", new Object[] { "registerNetStateChangeReceiver", exception });
      this.mNetStateBroadcastReceiver = null;
      return;
    } 
  }
  
  public void clear() {
    this.mNetStateChangeListenerList.clear();
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application != null) {
      NetStateBroadcastReceiver netStateBroadcastReceiver = this.mNetStateBroadcastReceiver;
      if (netStateBroadcastReceiver == null)
        return; 
      application.unregisterReceiver(netStateBroadcastReceiver);
      this.mNetStateBroadcastReceiver = null;
    } 
  }
  
  public void registerNetStateChangeReceiver(NetStateChangeListener paramNetStateChangeListener) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield mNetStateBroadcastReceiver : Lcom/tt/miniapp/manager/NetStateManager$NetStateBroadcastReceiver;
    //   11: ifnonnull -> 18
    //   14: aload_0
    //   15: invokespecial registerNetStateChangeReceiver : ()V
    //   18: aload_0
    //   19: getfield mNetStateChangeListenerList : Ljava/util/List;
    //   22: aload_1
    //   23: invokeinterface contains : (Ljava/lang/Object;)Z
    //   28: ifne -> 42
    //   31: aload_0
    //   32: getfield mNetStateChangeListenerList : Ljava/util/List;
    //   35: aload_1
    //   36: invokeinterface add : (Ljava/lang/Object;)Z
    //   41: pop
    //   42: aload_0
    //   43: monitorexit
    //   44: return
    //   45: astore_1
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_1
    //   49: athrow
    // Exception table:
    //   from	to	target	type
    //   7	18	45	finally
    //   18	42	45	finally
    //   42	44	45	finally
    //   46	48	45	finally
  }
  
  public void unregisterNetStateChangeReceiver(NetStateChangeListener paramNetStateChangeListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mNetStateChangeListenerList : Ljava/util/List;
    //   6: aload_1
    //   7: invokeinterface remove : (Ljava/lang/Object;)Z
    //   12: pop
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	16	finally
    //   17	19	16	finally
  }
  
  public void updateNetworkType(Context paramContext) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: aload_0
    //   6: aload_1
    //   7: invokestatic getLatestNetworkType : (Landroid/content/Context;)Lcom/tt/miniapp/manager/NetStateManager$NetworkType;
    //   10: putfield mNetworkType : Lcom/tt/miniapp/manager/NetStateManager$NetworkType;
    //   13: aload_0
    //   14: monitorenter
    //   15: aload_0
    //   16: getfield mNetStateChangeListenerList : Ljava/util/List;
    //   19: invokeinterface iterator : ()Ljava/util/Iterator;
    //   24: astore_1
    //   25: aload_1
    //   26: invokeinterface hasNext : ()Z
    //   31: ifeq -> 55
    //   34: aload_1
    //   35: invokeinterface next : ()Ljava/lang/Object;
    //   40: checkcast com/tt/miniapp/manager/NetStateManager$NetStateChangeListener
    //   43: aload_0
    //   44: getfield mNetworkType : Lcom/tt/miniapp/manager/NetStateManager$NetworkType;
    //   47: invokeinterface onNetStateChange : (Lcom/tt/miniapp/manager/NetStateManager$NetworkType;)V
    //   52: goto -> 25
    //   55: aload_0
    //   56: monitorexit
    //   57: return
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: goto -> 66
    //   64: aload_1
    //   65: athrow
    //   66: goto -> 64
    // Exception table:
    //   from	to	target	type
    //   15	25	58	finally
    //   25	52	58	finally
    //   55	57	58	finally
    //   59	61	58	finally
  }
  
  class NetStateBroadcastReceiver extends BroadcastReceiver {
    private NetStateBroadcastReceiver() {}
    
    public void onReceive(final Context context, Intent param1Intent) {
      if ("android.net.conn.CONNECTIVITY_CHANGE".equals(param1Intent.getAction()))
        ThreadUtil.runOnWorkThread(new Action() {
              public void act() {
                NetStateManager.this.updateNetworkType(context);
              }
            },  Schedulers.shortIO()); 
    }
  }
  
  class null implements Action {
    public void act() {
      NetStateManager.this.updateNetworkType(context);
    }
  }
  
  public static interface NetStateChangeListener {
    void onNetStateChange(NetStateManager.NetworkType param1NetworkType);
  }
  
  public enum NetworkType {
    MOBILE,
    MOBILE_2G,
    MOBILE_3G,
    MOBILE_4G,
    NONE,
    UNKNOWN(-1),
    WIFI(-1);
    
    final int nativeInt;
    
    static {
      MOBILE = new NetworkType("MOBILE", 2, 1);
      MOBILE_2G = new NetworkType("MOBILE_2G", 3, 2);
      MOBILE_3G = new NetworkType("MOBILE_3G", 4, 3);
      WIFI = new NetworkType("WIFI", 5, 4);
      MOBILE_4G = new NetworkType("MOBILE_4G", 6, 5);
      $VALUES = new NetworkType[] { UNKNOWN, NONE, MOBILE, MOBILE_2G, MOBILE_3G, WIFI, MOBILE_4G };
    }
    
    NetworkType(int param1Int1) {
      this.nativeInt = param1Int1;
    }
    
    public final int getValue() {
      return this.nativeInt;
    }
    
    public final boolean isAvailable() {
      return (this != UNKNOWN && this != NONE);
    }
    
    public final boolean isWifi() {
      return (this == WIFI);
    }
  }
  
  class NetStateManager {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\NetStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */