package com.facebook.react.modules.appregistry;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableMap;

public interface AppRegistry extends JavaScriptModule {
  void runApplication(String paramString, WritableMap paramWritableMap);
  
  void startHeadlessTask(int paramInt, String paramString, WritableMap paramWritableMap);
  
  void unmountApplicationComponentAtRootTag(int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\appregistry\AppRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */