package com.tt.miniapp.liveplayer.dns;

import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapphost.AppBrandLogger;
import d.f.b.g;

public final class LivePlayerDnsManager {
  public static final Companion Companion = new Companion(null);
  
  public static volatile LivePlayerDnsManager instance;
  
  public ILivePlayerDns mDnsOptimizer;
  
  private LivePlayerDnsManager() {}
  
  public final void bindLifeCycle(ILivePlayerDns paramILivePlayerDns) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'dnsOptimizer'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: monitorenter
    //   8: aload_0
    //   9: getfield mDnsOptimizer : Lcom/tt/miniapp/liveplayer/dns/ILivePlayerDns;
    //   12: astore_2
    //   13: aload_2
    //   14: ifnull -> 20
    //   17: aload_0
    //   18: monitorexit
    //   19: return
    //   20: aload_0
    //   21: aload_1
    //   22: putfield mDnsOptimizer : Lcom/tt/miniapp/liveplayer/dns/ILivePlayerDns;
    //   25: aload_0
    //   26: monitorexit
    //   27: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   30: astore_1
    //   31: aload_1
    //   32: ldc 'AppbrandApplicationImpl.getInst()'
    //   34: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   37: aload_1
    //   38: invokevirtual getForeBackgroundManager : ()Lcom/tt/miniapp/manager/ForeBackgroundManager;
    //   41: astore_1
    //   42: aload_1
    //   43: ifnull -> 61
    //   46: aload_1
    //   47: new com/tt/miniapp/liveplayer/dns/LivePlayerDnsManager$bindLifeCycle$2
    //   50: dup
    //   51: aload_0
    //   52: invokespecial <init> : (Lcom/tt/miniapp/liveplayer/dns/LivePlayerDnsManager;)V
    //   55: checkcast com/tt/miniapp/manager/ForeBackgroundManager$ForeBackgroundListener
    //   58: invokevirtual registerForeBackgroundListener : (Lcom/tt/miniapp/manager/ForeBackgroundManager$ForeBackgroundListener;)V
    //   61: aload_0
    //   62: getfield mDnsOptimizer : Lcom/tt/miniapp/liveplayer/dns/ILivePlayerDns;
    //   65: astore_1
    //   66: aload_1
    //   67: ifnull -> 76
    //   70: aload_1
    //   71: invokeinterface startOptimize : ()V
    //   76: return
    //   77: astore_1
    //   78: aload_0
    //   79: monitorexit
    //   80: aload_1
    //   81: athrow
    // Exception table:
    //   from	to	target	type
    //   8	13	77	finally
    //   20	25	77	finally
  }
  
  public final boolean hasBind() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mDnsOptimizer : Lcom/tt/miniapp/liveplayer/dns/ILivePlayerDns;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull -> 16
    //   11: iconst_1
    //   12: istore_1
    //   13: goto -> 18
    //   16: iconst_0
    //   17: istore_1
    //   18: aload_0
    //   19: monitorexit
    //   20: iload_1
    //   21: ireturn
    //   22: astore_2
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_2
    //   26: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	22	finally
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final LivePlayerDnsManager getInstance() {
      // Byte code:
      //   0: getstatic com/tt/miniapp/liveplayer/dns/LivePlayerDnsManager.instance : Lcom/tt/miniapp/liveplayer/dns/LivePlayerDnsManager;
      //   3: astore_1
      //   4: aload_1
      //   5: ifnonnull -> 42
      //   8: aload_0
      //   9: monitorenter
      //   10: getstatic com/tt/miniapp/liveplayer/dns/LivePlayerDnsManager.instance : Lcom/tt/miniapp/liveplayer/dns/LivePlayerDnsManager;
      //   13: astore_2
      //   14: aload_2
      //   15: astore_1
      //   16: aload_2
      //   17: ifnonnull -> 33
      //   20: new com/tt/miniapp/liveplayer/dns/LivePlayerDnsManager
      //   23: dup
      //   24: aconst_null
      //   25: invokespecial <init> : (Ld/f/b/g;)V
      //   28: astore_1
      //   29: aload_1
      //   30: putstatic com/tt/miniapp/liveplayer/dns/LivePlayerDnsManager.instance : Lcom/tt/miniapp/liveplayer/dns/LivePlayerDnsManager;
      //   33: aload_0
      //   34: monitorexit
      //   35: aload_1
      //   36: areturn
      //   37: astore_1
      //   38: aload_0
      //   39: monitorexit
      //   40: aload_1
      //   41: athrow
      //   42: aload_1
      //   43: areturn
      // Exception table:
      //   from	to	target	type
      //   10	14	37	finally
      //   20	33	37	finally
    }
  }
  
  public static final class LivePlayerDnsManager$bindLifeCycle$2 implements ForeBackgroundManager.ForeBackgroundListener {
    public final void onBackground() {
      ILivePlayerDns iLivePlayerDns = LivePlayerDnsManager.this.mDnsOptimizer;
      if (iLivePlayerDns != null)
        iLivePlayerDns.stopOptimize(); 
    }
    
    public final void onBackgroundOverLimitTime() {
      AppBrandLogger.i("LivePlayerDnsManager", new Object[] { "onBackgroundOverLimitTime" });
    }
    
    public final void onForeground() {
      ILivePlayerDns iLivePlayerDns = LivePlayerDnsManager.this.mDnsOptimizer;
      if (iLivePlayerDns != null)
        iLivePlayerDns.startOptimize(); 
    }
    
    public final void onTriggerHomeOrRecentApp() {
      AppBrandLogger.i("LivePlayerDnsManager", new Object[] { "onTriggerHomeOrRecentApp" });
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\liveplayer\dns\LivePlayerDnsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */