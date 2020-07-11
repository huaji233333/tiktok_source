package com.facebook.react.views.webview.events;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class TopLoadingStartEvent extends Event<TopLoadingStartEvent> {
  private WritableMap mEventData;
  
  public TopLoadingStartEvent(int paramInt, WritableMap paramWritableMap) {
    super(paramInt);
    this.mEventData = paramWritableMap;
  }
  
  public boolean canCoalesce() {
    return false;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), this.mEventData);
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public String getEventName() {
    return "topLoadingStart";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\webview\events\TopLoadingStartEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */