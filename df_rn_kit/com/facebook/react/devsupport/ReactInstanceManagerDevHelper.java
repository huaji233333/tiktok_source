package com.facebook.react.devsupport;

import android.app.Activity;
import com.facebook.react.bridge.JavaJSExecutor;

public interface ReactInstanceManagerDevHelper {
  Activity getCurrentActivity();
  
  void onJSBundleLoadedFromServer();
  
  void onReloadWithJSDebugger(JavaJSExecutor.Factory paramFactory);
  
  void toggleElementInspector();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\ReactInstanceManagerDevHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */