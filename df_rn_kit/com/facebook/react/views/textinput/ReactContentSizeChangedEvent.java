package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactContentSizeChangedEvent extends Event<ReactTextChangedEvent> {
  private float mContentHeight;
  
  private float mContentWidth;
  
  public ReactContentSizeChangedEvent(int paramInt, float paramFloat1, float paramFloat2) {
    super(paramInt);
    this.mContentWidth = paramFloat1;
    this.mContentHeight = paramFloat2;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap1 = Arguments.createMap();
    WritableMap writableMap2 = Arguments.createMap();
    writableMap2.putDouble("width", this.mContentWidth);
    writableMap2.putDouble("height", this.mContentHeight);
    writableMap1.putMap("contentSize", writableMap2);
    writableMap1.putInt("target", getViewTag());
    return writableMap1;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topContentSizeChange";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactContentSizeChangedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */