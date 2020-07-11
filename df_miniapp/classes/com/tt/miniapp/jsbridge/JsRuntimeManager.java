package com.tt.miniapp.jsbridge;

import android.content.ContextWrapper;
import com.he.v8_inspect.Inspect;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.JsRuntime;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapphost.AppBrandLogger;
import java.util.HashSet;

public class JsRuntimeManager extends AppbrandServiceManager.ServiceBase {
  private volatile JsRuntime mCurrentRuntime;
  
  private PreloadedJsContext preloadedJsContext;
  
  private final HashSet<JsRuntimeReadyListener> sReadyListeners = new HashSet<JsRuntimeReadyListener>();
  
  private JsRuntimeManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private void checkCurrent(boolean paramBoolean) {
    if (this.mCurrentRuntime == null)
      return; 
    if (this.mCurrentRuntime instanceof JsTMARuntime == paramBoolean && this.mCurrentRuntime.getJsSdkLoadState() != 1)
      return; 
    StringBuilder stringBuilder = new StringBuilder("release ");
    stringBuilder.append(this.mCurrentRuntime);
    AppBrandLogger.i("tma_JsRuntimeManager", new Object[] { stringBuilder.toString() });
    if ((DebugManager.getInst()).mTmaDebugOpen) {
      Inspect.onDispose("0");
      (DebugManager.getInst()).mTmaDebugOpen = false;
    } 
    this.mCurrentRuntime.release();
    this.mCurrentRuntime = null;
  }
  
  public void addJsRuntimeReadyListener(JsRuntimeReadyListener paramJsRuntimeReadyListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 23
    //   6: aload_0
    //   7: getfield sReadyListeners : Ljava/util/HashSet;
    //   10: aload_1
    //   11: invokevirtual add : (Ljava/lang/Object;)Z
    //   14: pop
    //   15: goto -> 23
    //   18: astore_1
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_1
    //   22: athrow
    //   23: aload_0
    //   24: monitorexit
    //   25: return
    // Exception table:
    //   from	to	target	type
    //   6	15	18	finally
  }
  
  public JsRuntime getCurrentRuntime() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   6: astore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: aload_1
    //   10: areturn
    //   11: astore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_1
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public j getJsBridge() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   6: ifnull -> 21
    //   9: aload_0
    //   10: getfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   13: invokevirtual getJsBridge : ()Lcom/tt/frontendapiinterface/j;
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: areturn
    //   21: aload_0
    //   22: monitorexit
    //   23: aconst_null
    //   24: areturn
    //   25: astore_1
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_1
    //   29: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	25	finally
  }
  
  public void initTMARuntime(ContextWrapper paramContextWrapper) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_1
    //   4: invokespecial checkCurrent : (Z)V
    //   7: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   10: ldc com/tt/miniapp/util/timeline/MpTimeLineReporter
    //   12: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   15: checkcast com/tt/miniapp/util/timeline/MpTimeLineReporter
    //   18: astore_2
    //   19: aload_2
    //   20: ldc 'create_jsEngine_begin'
    //   22: invokevirtual addPoint : (Ljava/lang/String;)Z
    //   25: pop
    //   26: aload_0
    //   27: getfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   30: ifnonnull -> 49
    //   33: aload_0
    //   34: new com/tt/miniapp/jsbridge/JsTMARuntime
    //   37: dup
    //   38: aload_1
    //   39: aload_0
    //   40: getfield preloadedJsContext : Lcom/tt/miniapp/jsbridge/PreloadedJsContext;
    //   43: invokespecial <init> : (Landroid/content/ContextWrapper;Lcom/tt/miniapp/jsbridge/PreloadedJsContext;)V
    //   46: putfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   49: aload_2
    //   50: ldc 'create_jsEngine_end'
    //   52: invokevirtual addPoint : (Ljava/lang/String;)Z
    //   55: pop
    //   56: aload_0
    //   57: getfield sReadyListeners : Ljava/util/HashSet;
    //   60: invokevirtual iterator : ()Ljava/util/Iterator;
    //   63: astore_1
    //   64: aload_1
    //   65: invokeinterface hasNext : ()Z
    //   70: ifeq -> 96
    //   73: aload_1
    //   74: invokeinterface next : ()Ljava/lang/Object;
    //   79: checkcast com/tt/miniapp/jsbridge/JsRuntimeManager$JsRuntimeReadyListener
    //   82: astore_2
    //   83: aload_2
    //   84: ifnull -> 64
    //   87: aload_2
    //   88: invokeinterface onJsRuntimeReady : ()V
    //   93: goto -> 64
    //   96: aload_0
    //   97: getfield sReadyListeners : Ljava/util/HashSet;
    //   100: invokevirtual clear : ()V
    //   103: aload_0
    //   104: monitorexit
    //   105: return
    //   106: astore_1
    //   107: aload_0
    //   108: monitorexit
    //   109: goto -> 114
    //   112: aload_1
    //   113: athrow
    //   114: goto -> 112
    // Exception table:
    //   from	to	target	type
    //   2	49	106	finally
    //   49	64	106	finally
    //   64	83	106	finally
    //   87	93	106	finally
    //   96	103	106	finally
  }
  
  public void initTMGRuntime(JSRuntimeFactory paramJSRuntimeFactory) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_0
    //   4: invokespecial checkCurrent : (Z)V
    //   7: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   10: ldc com/tt/miniapp/util/timeline/MpTimeLineReporter
    //   12: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   15: checkcast com/tt/miniapp/util/timeline/MpTimeLineReporter
    //   18: astore_2
    //   19: aload_2
    //   20: ldc 'create_jsEngine_begin'
    //   22: invokevirtual addPoint : (Ljava/lang/String;)Z
    //   25: pop
    //   26: aload_0
    //   27: getfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   30: ifnonnull -> 47
    //   33: aload_0
    //   34: aload_1
    //   35: aload_0
    //   36: getfield preloadedJsContext : Lcom/tt/miniapp/jsbridge/PreloadedJsContext;
    //   39: invokeinterface create : (Lcom/tt/miniapp/jsbridge/PreloadedJsContext;)Lcom/tt/miniapp/JsRuntime;
    //   44: putfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   47: aload_2
    //   48: ldc 'create_jsEngine_end'
    //   50: invokevirtual addPoint : (Ljava/lang/String;)Z
    //   53: pop
    //   54: aload_0
    //   55: monitorexit
    //   56: return
    //   57: astore_1
    //   58: aload_0
    //   59: monitorexit
    //   60: aload_1
    //   61: athrow
    // Exception table:
    //   from	to	target	type
    //   2	47	57	finally
    //   47	54	57	finally
  }
  
  public void preloadTMARuntime(ContextWrapper paramContextWrapper) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_1
    //   15: iconst_0
    //   16: iconst_2
    //   17: anewarray java/lang/Enum
    //   20: dup
    //   21: iconst_0
    //   22: getstatic com/tt/miniapp/settings/keys/Settings.TT_TMA_SWITCH : Lcom/tt/miniapp/settings/keys/Settings;
    //   25: aastore
    //   26: dup
    //   27: iconst_1
    //   28: getstatic com/tt/miniapp/settings/keys/Settings$TmaSwitch.PRELOAD_TMG : Lcom/tt/miniapp/settings/keys/Settings$TmaSwitch;
    //   31: aastore
    //   32: invokestatic getBoolean : (Landroid/content/Context;Z[Ljava/lang/Enum;)Z
    //   35: ifeq -> 62
    //   38: invokestatic getInst : ()Lcom/tt/miniapp/debug/DebugManager;
    //   41: getfield mIsDebugOpen : Z
    //   44: ifne -> 62
    //   47: aload_0
    //   48: new com/tt/miniapp/jsbridge/PreloadedJsContext
    //   51: dup
    //   52: aload_1
    //   53: invokespecial <init> : (Landroid/content/ContextWrapper;)V
    //   56: putfield preloadedJsContext : Lcom/tt/miniapp/jsbridge/PreloadedJsContext;
    //   59: aload_0
    //   60: monitorexit
    //   61: return
    //   62: aload_0
    //   63: new com/tt/miniapp/jsbridge/JsTMARuntime
    //   66: dup
    //   67: aload_1
    //   68: aconst_null
    //   69: invokespecial <init> : (Landroid/content/ContextWrapper;Lcom/tt/miniapp/jsbridge/PreloadedJsContext;)V
    //   72: putfield mCurrentRuntime : Lcom/tt/miniapp/JsRuntime;
    //   75: aload_0
    //   76: monitorexit
    //   77: return
    //   78: astore_1
    //   79: aload_0
    //   80: monitorexit
    //   81: aload_1
    //   82: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	78	finally
    //   14	59	78	finally
    //   62	75	78	finally
  }
  
  public static interface JSRuntimeFactory {
    JsRuntime create(PreloadedJsContext param1PreloadedJsContext);
  }
  
  public static interface JsRuntimeReadyListener {
    void onJsRuntimeReady();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\JsRuntimeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */