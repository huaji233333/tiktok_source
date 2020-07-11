package com.facebook.react.jstasks;

import android.os.Handler;
import android.util.SparseArray;
import com.facebook.react.bridge.ReactContext;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class HeadlessJsTaskContext {
  private static final WeakHashMap<ReactContext, HeadlessJsTaskContext> INSTANCES = new WeakHashMap<ReactContext, HeadlessJsTaskContext>();
  
  private final Set<Integer> mActiveTasks = new CopyOnWriteArraySet<Integer>();
  
  private final Handler mHandler = new Handler();
  
  public final Set<HeadlessJsTaskEventListener> mHeadlessJsTaskEventListeners = new CopyOnWriteArraySet<HeadlessJsTaskEventListener>();
  
  private final AtomicInteger mLastTaskId = new AtomicInteger(0);
  
  private final WeakReference<ReactContext> mReactContext;
  
  private final SparseArray<Runnable> mTaskTimeouts = new SparseArray();
  
  private HeadlessJsTaskContext(ReactContext paramReactContext) {
    this.mReactContext = new WeakReference<ReactContext>(paramReactContext);
  }
  
  public static HeadlessJsTaskContext getInstance(ReactContext paramReactContext) {
    HeadlessJsTaskContext headlessJsTaskContext2 = INSTANCES.get(paramReactContext);
    HeadlessJsTaskContext headlessJsTaskContext1 = headlessJsTaskContext2;
    if (headlessJsTaskContext2 == null) {
      headlessJsTaskContext1 = new HeadlessJsTaskContext(paramReactContext);
      INSTANCES.put(paramReactContext, headlessJsTaskContext1);
    } 
    return headlessJsTaskContext1;
  }
  
  private void scheduleTaskTimeout(final int taskId, long paramLong) {
    Runnable runnable = new Runnable() {
        public void run() {
          HeadlessJsTaskContext.this.finishTask(taskId);
        }
      };
    this.mTaskTimeouts.append(taskId, runnable);
    this.mHandler.postDelayed(runnable, paramLong);
  }
  
  public void addTaskEventListener(HeadlessJsTaskEventListener paramHeadlessJsTaskEventListener) {
    this.mHeadlessJsTaskEventListeners.add(paramHeadlessJsTaskEventListener);
  }
  
  public void finishTask(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mActiveTasks : Ljava/util/Set;
    //   6: iload_1
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokeinterface remove : (Ljava/lang/Object;)Z
    //   15: istore_2
    //   16: new java/lang/StringBuilder
    //   19: dup
    //   20: ldc 'Tried to finish non-existent task with id '
    //   22: invokespecial <init> : (Ljava/lang/String;)V
    //   25: astore_3
    //   26: aload_3
    //   27: iload_1
    //   28: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   31: pop
    //   32: aload_3
    //   33: ldc '.'
    //   35: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: iload_2
    //   40: aload_3
    //   41: invokevirtual toString : ()Ljava/lang/String;
    //   44: invokestatic a : (ZLjava/lang/String;)V
    //   47: aload_0
    //   48: getfield mTaskTimeouts : Landroid/util/SparseArray;
    //   51: iload_1
    //   52: invokevirtual get : (I)Ljava/lang/Object;
    //   55: checkcast java/lang/Runnable
    //   58: astore_3
    //   59: aload_3
    //   60: ifnull -> 79
    //   63: aload_0
    //   64: getfield mHandler : Landroid/os/Handler;
    //   67: aload_3
    //   68: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
    //   71: aload_0
    //   72: getfield mTaskTimeouts : Landroid/util/SparseArray;
    //   75: iload_1
    //   76: invokevirtual remove : (I)V
    //   79: new com/facebook/react/jstasks/HeadlessJsTaskContext$1
    //   82: dup
    //   83: aload_0
    //   84: iload_1
    //   85: invokespecial <init> : (Lcom/facebook/react/jstasks/HeadlessJsTaskContext;I)V
    //   88: invokestatic runOnUiThread : (Ljava/lang/Runnable;)V
    //   91: aload_0
    //   92: monitorexit
    //   93: return
    //   94: astore_3
    //   95: aload_0
    //   96: monitorexit
    //   97: aload_3
    //   98: athrow
    // Exception table:
    //   from	to	target	type
    //   2	59	94	finally
    //   63	79	94	finally
    //   79	91	94	finally
  }
  
  public boolean hasActiveTasks() {
    return (this.mActiveTasks.size() > 0);
  }
  
  public boolean isTaskRunning(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mActiveTasks : Ljava/util/Set;
    //   6: iload_1
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokeinterface contains : (Ljava/lang/Object;)Z
    //   15: istore_2
    //   16: aload_0
    //   17: monitorexit
    //   18: iload_2
    //   19: ireturn
    //   20: astore_3
    //   21: aload_0
    //   22: monitorexit
    //   23: aload_3
    //   24: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	20	finally
  }
  
  public void removeTaskEventListener(HeadlessJsTaskEventListener paramHeadlessJsTaskEventListener) {
    this.mHeadlessJsTaskEventListeners.remove(paramHeadlessJsTaskEventListener);
  }
  
  public int startTask(HeadlessJsTaskConfig paramHeadlessJsTaskConfig) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic assertOnUiThread : ()V
    //   5: aload_0
    //   6: getfield mReactContext : Ljava/lang/ref/WeakReference;
    //   9: invokevirtual get : ()Ljava/lang/Object;
    //   12: ldc 'Tried to start a task on a react context that has already been destroyed'
    //   14: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   17: checkcast com/facebook/react/bridge/ReactContext
    //   20: astore_3
    //   21: aload_3
    //   22: invokevirtual getLifecycleState : ()Lcom/facebook/react/common/LifecycleState;
    //   25: getstatic com/facebook/react/common/LifecycleState.RESUMED : Lcom/facebook/react/common/LifecycleState;
    //   28: if_acmpne -> 79
    //   31: aload_1
    //   32: invokevirtual isAllowedInForeground : ()Z
    //   35: ifeq -> 41
    //   38: goto -> 79
    //   41: new java/lang/StringBuilder
    //   44: dup
    //   45: ldc 'Tried to start task '
    //   47: invokespecial <init> : (Ljava/lang/String;)V
    //   50: astore_3
    //   51: aload_3
    //   52: aload_1
    //   53: invokevirtual getTaskKey : ()Ljava/lang/String;
    //   56: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload_3
    //   61: ldc ' while in foreground, but this is not allowed.'
    //   63: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: new java/lang/IllegalStateException
    //   70: dup
    //   71: aload_3
    //   72: invokevirtual toString : ()Ljava/lang/String;
    //   75: invokespecial <init> : (Ljava/lang/String;)V
    //   78: athrow
    //   79: aload_0
    //   80: getfield mLastTaskId : Ljava/util/concurrent/atomic/AtomicInteger;
    //   83: invokevirtual incrementAndGet : ()I
    //   86: istore_2
    //   87: aload_0
    //   88: getfield mActiveTasks : Ljava/util/Set;
    //   91: iload_2
    //   92: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   95: invokeinterface add : (Ljava/lang/Object;)Z
    //   100: pop
    //   101: aload_3
    //   102: ldc com/facebook/react/modules/appregistry/AppRegistry
    //   104: invokevirtual getJSModule : (Ljava/lang/Class;)Lcom/facebook/react/bridge/JavaScriptModule;
    //   107: checkcast com/facebook/react/modules/appregistry/AppRegistry
    //   110: iload_2
    //   111: aload_1
    //   112: invokevirtual getTaskKey : ()Ljava/lang/String;
    //   115: aload_1
    //   116: invokevirtual getData : ()Lcom/facebook/react/bridge/WritableMap;
    //   119: invokeinterface startHeadlessTask : (ILjava/lang/String;Lcom/facebook/react/bridge/WritableMap;)V
    //   124: aload_1
    //   125: invokevirtual getTimeout : ()J
    //   128: lconst_0
    //   129: lcmp
    //   130: ifle -> 142
    //   133: aload_0
    //   134: iload_2
    //   135: aload_1
    //   136: invokevirtual getTimeout : ()J
    //   139: invokespecial scheduleTaskTimeout : (IJ)V
    //   142: aload_0
    //   143: getfield mHeadlessJsTaskEventListeners : Ljava/util/Set;
    //   146: invokeinterface iterator : ()Ljava/util/Iterator;
    //   151: astore_1
    //   152: aload_1
    //   153: invokeinterface hasNext : ()Z
    //   158: ifeq -> 179
    //   161: aload_1
    //   162: invokeinterface next : ()Ljava/lang/Object;
    //   167: checkcast com/facebook/react/jstasks/HeadlessJsTaskEventListener
    //   170: iload_2
    //   171: invokeinterface onHeadlessJsTaskStart : (I)V
    //   176: goto -> 152
    //   179: aload_0
    //   180: monitorexit
    //   181: iload_2
    //   182: ireturn
    //   183: astore_1
    //   184: aload_0
    //   185: monitorexit
    //   186: goto -> 191
    //   189: aload_1
    //   190: athrow
    //   191: goto -> 189
    // Exception table:
    //   from	to	target	type
    //   2	38	183	finally
    //   41	79	183	finally
    //   79	142	183	finally
    //   142	152	183	finally
    //   152	176	183	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\jstasks\HeadlessJsTaskContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */