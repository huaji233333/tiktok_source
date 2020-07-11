package com.he;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsScopedContext;
import java.util.concurrent.atomic.AtomicInteger;

public final class JsRunLoop extends Thread {
  private static final Delegate delegate = new Delegate();
  
  private static final AtomicInteger thread_id = new AtomicInteger(0);
  
  private Handler handler;
  
  private JsContext mJsContext;
  
  private Runnable posted_task;
  
  private SetupCallback setupCallback;
  
  private RuntimeException thrown;
  
  public JsRunLoop() {
    super(stringBuilder.toString());
  }
  
  public final Handler getHandler() {
    // Byte code:
    //   0: aload_0
    //   1: getfield handler : Landroid/os/Handler;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 11
    //   9: aload_1
    //   10: areturn
    //   11: aload_0
    //   12: monitorenter
    //   13: aload_0
    //   14: getfield handler : Landroid/os/Handler;
    //   17: ifnonnull -> 41
    //   20: aload_0
    //   21: getfield thrown : Ljava/lang/RuntimeException;
    //   24: astore_1
    //   25: aload_1
    //   26: ifnonnull -> 36
    //   29: aload_0
    //   30: invokevirtual wait : ()V
    //   33: goto -> 13
    //   36: aload_0
    //   37: getfield thrown : Ljava/lang/RuntimeException;
    //   40: athrow
    //   41: aload_0
    //   42: monitorexit
    //   43: aload_0
    //   44: getfield handler : Landroid/os/Handler;
    //   47: areturn
    //   48: astore_1
    //   49: aload_0
    //   50: monitorexit
    //   51: goto -> 56
    //   54: aload_1
    //   55: athrow
    //   56: goto -> 54
    //   59: astore_1
    //   60: goto -> 13
    // Exception table:
    //   from	to	target	type
    //   13	25	48	finally
    //   29	33	59	java/lang/InterruptedException
    //   29	33	48	finally
    //   36	41	48	finally
    //   41	43	48	finally
    //   49	51	48	finally
  }
  
  public final JsContext getJsContext() {
    return this.mJsContext;
  }
  
  public final void post(Runnable paramRunnable) {
    synchronized (delegate) {
      if (this.setupCallback == null) {
        this.posted_task = paramRunnable;
        delegate.notify();
        return;
      } 
      getHandler().post(paramRunnable);
      return;
    } 
  }
  
  public final void quit() {
    this.handler.getLooper().quitSafely();
  }
  
  public final void run() {
    // Byte code:
    //   0: new com/he/jsbinding/JsContext
    //   3: dup
    //   4: new com/he/jsbinding/JsEngine
    //   7: dup
    //   8: invokespecial <init> : ()V
    //   11: invokespecial <init> : (Lcom/he/jsbinding/JsEngine;)V
    //   14: astore_2
    //   15: aload_0
    //   16: aload_2
    //   17: putfield mJsContext : Lcom/he/jsbinding/JsContext;
    //   20: invokestatic prepare : ()V
    //   23: aload_0
    //   24: new android/os/Handler
    //   27: dup
    //   28: getstatic com/he/JsRunLoop.delegate : Lcom/he/JsRunLoop$Delegate;
    //   31: invokespecial <init> : (Landroid/os/Handler$Callback;)V
    //   34: putfield handler : Landroid/os/Handler;
    //   37: aload_0
    //   38: monitorenter
    //   39: aload_0
    //   40: invokevirtual notifyAll : ()V
    //   43: aload_0
    //   44: monitorexit
    //   45: getstatic com/he/JsRunLoop.delegate : Lcom/he/JsRunLoop$Delegate;
    //   48: astore_1
    //   49: aload_1
    //   50: monitorenter
    //   51: aload_0
    //   52: getfield setupCallback : Lcom/he/JsRunLoop$SetupCallback;
    //   55: ifnonnull -> 88
    //   58: aload_0
    //   59: getfield posted_task : Ljava/lang/Runnable;
    //   62: ifnull -> 79
    //   65: aload_0
    //   66: getfield posted_task : Ljava/lang/Runnable;
    //   69: invokeinterface run : ()V
    //   74: aload_0
    //   75: aconst_null
    //   76: putfield posted_task : Ljava/lang/Runnable;
    //   79: getstatic com/he/JsRunLoop.delegate : Lcom/he/JsRunLoop$Delegate;
    //   82: invokevirtual wait : ()V
    //   85: goto -> 51
    //   88: aload_1
    //   89: monitorexit
    //   90: aload_2
    //   91: aload_0
    //   92: getfield setupCallback : Lcom/he/JsRunLoop$SetupCallback;
    //   95: invokevirtual run : (Lcom/he/jsbinding/JsContext$ScopeCallback;)V
    //   98: aload_2
    //   99: getstatic com/he/JsRunLoop.delegate : Lcom/he/JsRunLoop$Delegate;
    //   102: invokevirtual run : (Lcom/he/jsbinding/JsContext$ScopeCallback;)V
    //   105: aload_0
    //   106: getfield setupCallback : Lcom/he/JsRunLoop$SetupCallback;
    //   109: invokeinterface cleanup : ()V
    //   114: aload_2
    //   115: invokevirtual getEngine : ()Lcom/he/jsbinding/JsEngine;
    //   118: invokevirtual dispose : ()V
    //   121: return
    //   122: astore_2
    //   123: aload_1
    //   124: monitorexit
    //   125: aload_2
    //   126: athrow
    //   127: astore_1
    //   128: aload_0
    //   129: monitorexit
    //   130: aload_1
    //   131: athrow
    //   132: astore_1
    //   133: aload_0
    //   134: aload_1
    //   135: putfield thrown : Ljava/lang/RuntimeException;
    //   138: aload_0
    //   139: monitorenter
    //   140: aload_0
    //   141: invokevirtual notifyAll : ()V
    //   144: aload_0
    //   145: monitorexit
    //   146: return
    //   147: astore_1
    //   148: aload_0
    //   149: monitorexit
    //   150: goto -> 155
    //   153: aload_1
    //   154: athrow
    //   155: goto -> 153
    //   158: astore_3
    //   159: goto -> 51
    // Exception table:
    //   from	to	target	type
    //   0	20	132	java/lang/RuntimeException
    //   39	45	127	finally
    //   51	79	122	finally
    //   79	85	158	java/lang/InterruptedException
    //   79	85	122	finally
    //   88	90	122	finally
    //   123	125	122	finally
    //   128	130	127	finally
    //   140	146	147	finally
    //   148	150	147	finally
  }
  
  public final void setup(SetupCallback paramSetupCallback) {
    this.setupCallback = paramSetupCallback;
    synchronized (delegate) {
      delegate.notify();
      return;
    } 
  }
  
  static class Delegate implements Handler.Callback, JsContext.ScopeCallback {
    private Delegate() {}
    
    public boolean handleMessage(Message param1Message) {
      return false;
    }
    
    public void run(JsScopedContext param1JsScopedContext) {
      Looper.loop();
    }
  }
  
  public static interface SetupCallback extends JsContext.ScopeCallback {
    void cleanup();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\JsRunLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */