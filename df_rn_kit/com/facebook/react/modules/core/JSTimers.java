package com.facebook.react.modules.core;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableArray;

public interface JSTimers extends JavaScriptModule {
  void callIdleCallbacks(double paramDouble);
  
  void callTimers(WritableArray paramWritableArray);
  
  void emitTimeDriftWarning(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\JSTimers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */