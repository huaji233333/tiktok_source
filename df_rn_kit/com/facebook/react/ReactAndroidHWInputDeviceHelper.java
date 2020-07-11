package com.facebook.react;

import android.view.KeyEvent;
import android.view.View;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;
import java.util.Map;

public class ReactAndroidHWInputDeviceHelper {
  private static final Map<Integer, String> KEY_EVENTS_ACTIONS = MapBuilder.of(Integer.valueOf(23), "select", Integer.valueOf(66), "select", Integer.valueOf(62), "select", Integer.valueOf(85), "playPause", Integer.valueOf(89), "rewind", Integer.valueOf(90), "fastForward");
  
  private int mLastFocusedViewId = -1;
  
  private final ReactRootView mReactRootView;
  
  ReactAndroidHWInputDeviceHelper(ReactRootView paramReactRootView) {
    this.mReactRootView = paramReactRootView;
  }
  
  private void dispatchEvent(String paramString, int paramInt) {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    writableNativeMap.putString("eventType", paramString);
    if (paramInt != -1)
      writableNativeMap.putInt("tag", paramInt); 
    this.mReactRootView.sendEvent("onHWKeyEvent", (WritableMap)writableNativeMap);
  }
  
  public void clearFocus() {
    int i = this.mLastFocusedViewId;
    if (i != -1)
      dispatchEvent("blur", i); 
    this.mLastFocusedViewId = -1;
  }
  
  public void handleKeyEvent(KeyEvent paramKeyEvent) {
    int i = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() == 1 && KEY_EVENTS_ACTIONS.containsKey(Integer.valueOf(i)))
      dispatchEvent(KEY_EVENTS_ACTIONS.get(Integer.valueOf(i)), this.mLastFocusedViewId); 
  }
  
  public void onFocusChanged(View paramView) {
    if (this.mLastFocusedViewId == paramView.getId())
      return; 
    int i = this.mLastFocusedViewId;
    if (i != -1)
      dispatchEvent("blur", i); 
    this.mLastFocusedViewId = paramView.getId();
    dispatchEvent("focus", paramView.getId());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactAndroidHWInputDeviceHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */