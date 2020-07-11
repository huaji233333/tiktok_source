package com.facebook.react.modules.core;

import com.facebook.i.a.a;
import com.facebook.react.bridge.UiThreadUtil;
import java.util.ArrayDeque;

public class ReactChoreographer {
  private static ReactChoreographer sInstance;
  
  public final ArrayDeque<ChoreographerCompat.FrameCallback>[] mCallbackQueues = (ArrayDeque<ChoreographerCompat.FrameCallback>[])new ArrayDeque[(CallbackType.values()).length];
  
  private final ChoreographerCompat mChoreographer = ChoreographerCompat.getInstance();
  
  public boolean mHasPostedCallback;
  
  private final ReactChoreographerDispatcher mReactChoreographerDispatcher = new ReactChoreographerDispatcher();
  
  public int mTotalCallbacks;
  
  private ReactChoreographer() {
    int i = 0;
    while (true) {
      ArrayDeque<ChoreographerCompat.FrameCallback>[] arrayOfArrayDeque = this.mCallbackQueues;
      if (i < arrayOfArrayDeque.length) {
        arrayOfArrayDeque[i] = new ArrayDeque<ChoreographerCompat.FrameCallback>();
        i++;
        continue;
      } 
      break;
    } 
  }
  
  public static ReactChoreographer getInstance() {
    a.a(sInstance, "ReactChoreographer needs to be initialized.");
    return sInstance;
  }
  
  public static void initialize() {
    if (sInstance == null) {
      UiThreadUtil.assertOnUiThread();
      sInstance = new ReactChoreographer();
    } 
  }
  
  public void maybeRemoveFrameCallback() {
    boolean bool;
    if (this.mTotalCallbacks >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool);
    if (this.mTotalCallbacks == 0 && this.mHasPostedCallback) {
      this.mChoreographer.removeFrameCallback(this.mReactChoreographerDispatcher);
      this.mHasPostedCallback = false;
    } 
  }
  
  public void postFrameCallback(CallbackType paramCallbackType, ChoreographerCompat.FrameCallback paramFrameCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCallbackQueues : [Ljava/util/ArrayDeque;
    //   6: aload_1
    //   7: invokevirtual getOrder : ()I
    //   10: aaload
    //   11: aload_2
    //   12: invokevirtual addLast : (Ljava/lang/Object;)V
    //   15: aload_0
    //   16: aload_0
    //   17: getfield mTotalCallbacks : I
    //   20: iconst_1
    //   21: iadd
    //   22: putfield mTotalCallbacks : I
    //   25: aload_0
    //   26: getfield mTotalCallbacks : I
    //   29: ifle -> 72
    //   32: iconst_1
    //   33: istore_3
    //   34: goto -> 37
    //   37: iload_3
    //   38: invokestatic a : (Z)V
    //   41: aload_0
    //   42: getfield mHasPostedCallback : Z
    //   45: ifne -> 64
    //   48: aload_0
    //   49: getfield mChoreographer : Lcom/facebook/react/modules/core/ChoreographerCompat;
    //   52: aload_0
    //   53: getfield mReactChoreographerDispatcher : Lcom/facebook/react/modules/core/ReactChoreographer$ReactChoreographerDispatcher;
    //   56: invokevirtual postFrameCallback : (Lcom/facebook/react/modules/core/ChoreographerCompat$FrameCallback;)V
    //   59: aload_0
    //   60: iconst_1
    //   61: putfield mHasPostedCallback : Z
    //   64: aload_0
    //   65: monitorexit
    //   66: return
    //   67: astore_1
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_1
    //   71: athrow
    //   72: iconst_0
    //   73: istore_3
    //   74: goto -> 37
    // Exception table:
    //   from	to	target	type
    //   2	32	67	finally
    //   37	64	67	finally
  }
  
  public void removeFrameCallback(CallbackType paramCallbackType, ChoreographerCompat.FrameCallback paramFrameCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCallbackQueues : [Ljava/util/ArrayDeque;
    //   6: aload_1
    //   7: invokevirtual getOrder : ()I
    //   10: aaload
    //   11: aload_2
    //   12: invokevirtual removeFirstOccurrence : (Ljava/lang/Object;)Z
    //   15: ifeq -> 35
    //   18: aload_0
    //   19: aload_0
    //   20: getfield mTotalCallbacks : I
    //   23: iconst_1
    //   24: isub
    //   25: putfield mTotalCallbacks : I
    //   28: aload_0
    //   29: invokevirtual maybeRemoveFrameCallback : ()V
    //   32: aload_0
    //   33: monitorexit
    //   34: return
    //   35: ldc 'ReactNative'
    //   37: ldc 'Tried to remove non-existent frame callback'
    //   39: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
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
    //   2	32	45	finally
    //   35	42	45	finally
  }
  
  public enum CallbackType {
    DISPATCH_UI,
    IDLE_EVENT,
    NATIVE_ANIMATED_MODULE,
    PERF_MARKERS(0),
    TIMERS_EVENTS(0);
    
    private final int mOrder;
    
    static {
      IDLE_EVENT = new CallbackType("IDLE_EVENT", 4, 4);
      $VALUES = new CallbackType[] { PERF_MARKERS, DISPATCH_UI, NATIVE_ANIMATED_MODULE, TIMERS_EVENTS, IDLE_EVENT };
    }
    
    CallbackType(int param1Int1) {
      this.mOrder = param1Int1;
    }
    
    final int getOrder() {
      return this.mOrder;
    }
  }
  
  class ReactChoreographerDispatcher extends ChoreographerCompat.FrameCallback {
    private ReactChoreographerDispatcher() {}
    
    public void doFrame(long param1Long) {
      synchronized (ReactChoreographer.this) {
        ReactChoreographer.this.mHasPostedCallback = false;
        for (int i = 0;; i++) {
          if (i < ReactChoreographer.this.mCallbackQueues.length) {
            int k = ReactChoreographer.this.mCallbackQueues[i].size();
            int j;
            for (j = 0; j < k; j++) {
              ((ChoreographerCompat.FrameCallback)ReactChoreographer.this.mCallbackQueues[i].removeFirst()).doFrame(param1Long);
              ReactChoreographer reactChoreographer = ReactChoreographer.this;
              reactChoreographer.mTotalCallbacks--;
            } 
          } else {
            ReactChoreographer.this.maybeRemoveFrameCallback();
            return;
          } 
        } 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\ReactChoreographer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */