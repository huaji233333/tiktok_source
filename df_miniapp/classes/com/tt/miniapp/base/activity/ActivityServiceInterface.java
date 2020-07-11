package com.tt.miniapp.base.activity;

import android.app.Activity;
import android.os.Bundle;
import com.bytedance.sandboxapp.b.b;

public interface ActivityServiceInterface extends b {
  void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks paramActivityLifecycleCallbacks);
  
  void registerActivityResultHandler(IActivityResultHandler paramIActivityResultHandler);
  
  void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks paramActivityLifecycleCallbacks);
  
  void unregisterActivityResultHandler(IActivityResultHandler paramIActivityResultHandler);
  
  public static abstract class AbsActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
    public void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {}
    
    public void onActivityDestroyed(Activity param1Activity) {}
    
    public void onActivityPaused(Activity param1Activity) {}
    
    public void onActivityResumed(Activity param1Activity) {}
    
    public void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {}
    
    public void onActivityStarted(Activity param1Activity) {}
    
    public void onActivityStopped(Activity param1Activity) {}
    
    public boolean shouldUnregister() {
      return false;
    }
    
    public Object uniquelyIdentify() {
      return null;
    }
  }
  
  public static interface ActivityLifecycleCallbacks {
    void onActivityCreated(Activity param1Activity, Bundle param1Bundle);
    
    void onActivityDestroyed(Activity param1Activity);
    
    void onActivityPaused(Activity param1Activity);
    
    void onActivityResumed(Activity param1Activity);
    
    void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle);
    
    void onActivityStarted(Activity param1Activity);
    
    void onActivityStopped(Activity param1Activity);
    
    boolean shouldUnregister();
    
    Object uniquelyIdentify();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\activity\ActivityServiceInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */