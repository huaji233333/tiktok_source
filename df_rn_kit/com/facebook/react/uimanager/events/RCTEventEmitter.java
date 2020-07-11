package com.facebook.react.uimanager.events;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public interface RCTEventEmitter extends JavaScriptModule {
  void receiveEvent(int paramInt, String paramString, WritableMap paramWritableMap);
  
  void receiveTouches(String paramString, WritableArray paramWritableArray1, WritableArray paramWritableArray2);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\RCTEventEmitter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */