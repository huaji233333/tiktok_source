package com.facebook.react.uimanager;

import com.facebook.react.common.ClearableSynchronizedPool;
import com.facebook.yoga.YogaNode;

public class YogaNodePool {
  private static final Object sInitLock = new Object();
  
  private static ClearableSynchronizedPool<YogaNode> sPool;
  
  public static ClearableSynchronizedPool<YogaNode> get() {
    ClearableSynchronizedPool<YogaNode> clearableSynchronizedPool = sPool;
    if (clearableSynchronizedPool != null)
      return clearableSynchronizedPool; 
    synchronized (sInitLock) {
      if (sPool == null)
        sPool = new ClearableSynchronizedPool(1024); 
      return sPool;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\YogaNodePool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */