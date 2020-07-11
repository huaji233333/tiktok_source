package com.facebook.react;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import com.facebook.react.bridge.MemoryPressureListener;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class MemoryPressureRouter implements ComponentCallbacks2 {
  private final Set<MemoryPressureListener> mListeners = Collections.synchronizedSet(new LinkedHashSet<MemoryPressureListener>());
  
  MemoryPressureRouter(Context paramContext) {
    paramContext.getApplicationContext().registerComponentCallbacks((ComponentCallbacks)this);
  }
  
  private void dispatchMemoryPressure(int paramInt) {
    Set<MemoryPressureListener> set = this.mListeners;
    MemoryPressureListener[] arrayOfMemoryPressureListener = set.<MemoryPressureListener>toArray(new MemoryPressureListener[set.size()]);
    int j = arrayOfMemoryPressureListener.length;
    for (int i = 0; i < j; i++)
      arrayOfMemoryPressureListener[i].handleMemoryPressure(paramInt); 
  }
  
  public void addMemoryPressureListener(MemoryPressureListener paramMemoryPressureListener) {
    this.mListeners.add(paramMemoryPressureListener);
  }
  
  public void destroy(Context paramContext) {
    paramContext.getApplicationContext().unregisterComponentCallbacks((ComponentCallbacks)this);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {}
  
  public void onLowMemory() {}
  
  public void onTrimMemory(int paramInt) {
    dispatchMemoryPressure(paramInt);
  }
  
  public void removeMemoryPressureListener(MemoryPressureListener paramMemoryPressureListener) {
    this.mListeners.remove(paramMemoryPressureListener);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\MemoryPressureRouter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */