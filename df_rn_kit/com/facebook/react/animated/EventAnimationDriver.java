package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.List;

class EventAnimationDriver implements RCTEventEmitter {
  private List<String> mEventPath;
  
  ValueAnimatedNode mValueNode;
  
  public EventAnimationDriver(List<String> paramList, ValueAnimatedNode paramValueAnimatedNode) {
    this.mEventPath = paramList;
    this.mValueNode = paramValueAnimatedNode;
  }
  
  public void receiveEvent(int paramInt, String paramString, WritableMap paramWritableMap) {
    if (paramWritableMap != null) {
      ReadableMap readableMap;
      for (paramInt = 0; paramInt < this.mEventPath.size() - 1; paramInt++)
        readableMap = paramWritableMap.getMap(this.mEventPath.get(paramInt)); 
      ValueAnimatedNode valueAnimatedNode = this.mValueNode;
      List<String> list = this.mEventPath;
      valueAnimatedNode.mValue = readableMap.getDouble(list.get(list.size() - 1));
      return;
    } 
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Native animated events must have event data.");
    throw illegalArgumentException;
  }
  
  public void receiveTouches(String paramString, WritableArray paramWritableArray1, WritableArray paramWritableArray2) {
    throw new RuntimeException("receiveTouches is not support by native animated events");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\EventAnimationDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */