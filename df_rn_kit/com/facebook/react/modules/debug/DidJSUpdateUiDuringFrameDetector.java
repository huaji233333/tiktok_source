package com.facebook.react.modules.debug;

import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.common.LongArray;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;

public class DidJSUpdateUiDuringFrameDetector implements NotThreadSafeBridgeIdleDebugListener, NotThreadSafeViewHierarchyUpdateDebugListener {
  private final LongArray mTransitionToBusyEvents = LongArray.createWithInitialCapacity(20);
  
  private final LongArray mTransitionToIdleEvents = LongArray.createWithInitialCapacity(20);
  
  private final LongArray mViewHierarchyUpdateEnqueuedEvents = LongArray.createWithInitialCapacity(20);
  
  private final LongArray mViewHierarchyUpdateFinishedEvents = LongArray.createWithInitialCapacity(20);
  
  private volatile boolean mWasIdleAtEndOfLastFrame = true;
  
  private static void cleanUp(LongArray paramLongArray, long paramLong) {
    int k = paramLongArray.size();
    boolean bool = false;
    int j = 0;
    int i;
    for (i = 0; j < k; i = m) {
      int m = i;
      if (paramLongArray.get(j) < paramLong)
        m = i + 1; 
      j++;
    } 
    if (i > 0) {
      for (j = bool; j < k - i; j++)
        paramLongArray.set(j, paramLongArray.get(j + i)); 
      paramLongArray.dropTail(i);
    } 
  }
  
  private boolean didEndFrameIdle(long paramLong1, long paramLong2) {
    long l = getLastEventBetweenTimestamps(this.mTransitionToIdleEvents, paramLong1, paramLong2);
    paramLong1 = getLastEventBetweenTimestamps(this.mTransitionToBusyEvents, paramLong1, paramLong2);
    return (l == -1L && paramLong1 == -1L) ? this.mWasIdleAtEndOfLastFrame : ((l > paramLong1));
  }
  
  private static long getLastEventBetweenTimestamps(LongArray paramLongArray, long paramLong1, long paramLong2) {
    long l = -1L;
    int i;
    for (i = 0; i < paramLongArray.size(); i++) {
      long l1 = paramLongArray.get(i);
      if (l1 >= paramLong1 && l1 < paramLong2) {
        l = l1;
        continue;
      } 
      if (l1 < paramLong2)
        continue; 
    } 
    return l;
  }
  
  private static boolean hasEventBetweenTimestamps(LongArray paramLongArray, long paramLong1, long paramLong2) {
    int i;
    for (i = 0; i < paramLongArray.size(); i++) {
      long l = paramLongArray.get(i);
      if (l >= paramLong1 && l < paramLong2)
        return true; 
    } 
    return false;
  }
  
  public boolean getDidJSHitFrameAndCleanup(long paramLong1, long paramLong2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mViewHierarchyUpdateFinishedEvents : Lcom/facebook/react/common/LongArray;
    //   6: lload_1
    //   7: lload_3
    //   8: invokestatic hasEventBetweenTimestamps : (Lcom/facebook/react/common/LongArray;JJ)Z
    //   11: istore #7
    //   13: aload_0
    //   14: lload_1
    //   15: lload_3
    //   16: invokespecial didEndFrameIdle : (JJ)Z
    //   19: istore #6
    //   21: iconst_1
    //   22: istore #5
    //   24: iload #7
    //   26: ifeq -> 32
    //   29: goto -> 52
    //   32: iload #6
    //   34: ifeq -> 102
    //   37: aload_0
    //   38: getfield mViewHierarchyUpdateEnqueuedEvents : Lcom/facebook/react/common/LongArray;
    //   41: lload_1
    //   42: lload_3
    //   43: invokestatic hasEventBetweenTimestamps : (Lcom/facebook/react/common/LongArray;JJ)Z
    //   46: ifne -> 102
    //   49: goto -> 52
    //   52: aload_0
    //   53: getfield mTransitionToIdleEvents : Lcom/facebook/react/common/LongArray;
    //   56: lload_3
    //   57: invokestatic cleanUp : (Lcom/facebook/react/common/LongArray;J)V
    //   60: aload_0
    //   61: getfield mTransitionToBusyEvents : Lcom/facebook/react/common/LongArray;
    //   64: lload_3
    //   65: invokestatic cleanUp : (Lcom/facebook/react/common/LongArray;J)V
    //   68: aload_0
    //   69: getfield mViewHierarchyUpdateEnqueuedEvents : Lcom/facebook/react/common/LongArray;
    //   72: lload_3
    //   73: invokestatic cleanUp : (Lcom/facebook/react/common/LongArray;J)V
    //   76: aload_0
    //   77: getfield mViewHierarchyUpdateFinishedEvents : Lcom/facebook/react/common/LongArray;
    //   80: lload_3
    //   81: invokestatic cleanUp : (Lcom/facebook/react/common/LongArray;J)V
    //   84: aload_0
    //   85: iload #6
    //   87: putfield mWasIdleAtEndOfLastFrame : Z
    //   90: aload_0
    //   91: monitorexit
    //   92: iload #5
    //   94: ireturn
    //   95: astore #8
    //   97: aload_0
    //   98: monitorexit
    //   99: aload #8
    //   101: athrow
    //   102: iconst_0
    //   103: istore #5
    //   105: goto -> 52
    // Exception table:
    //   from	to	target	type
    //   2	21	95	finally
    //   37	49	95	finally
    //   52	90	95	finally
  }
  
  public void onTransitionToBridgeBusy() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mTransitionToBusyEvents : Lcom/facebook/react/common/LongArray;
    //   6: invokestatic nanoTime : ()J
    //   9: invokevirtual add : (J)V
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	15	finally
  }
  
  public void onTransitionToBridgeIdle() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mTransitionToIdleEvents : Lcom/facebook/react/common/LongArray;
    //   6: invokestatic nanoTime : ()J
    //   9: invokevirtual add : (J)V
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	15	finally
  }
  
  public void onViewHierarchyUpdateEnqueued() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mViewHierarchyUpdateEnqueuedEvents : Lcom/facebook/react/common/LongArray;
    //   6: invokestatic nanoTime : ()J
    //   9: invokevirtual add : (J)V
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	15	finally
  }
  
  public void onViewHierarchyUpdateFinished() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mViewHierarchyUpdateFinishedEvents : Lcom/facebook/react/common/LongArray;
    //   6: invokestatic nanoTime : ()J
    //   9: invokevirtual add : (J)V
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	15	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\debug\DidJSUpdateUiDuringFrameDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */