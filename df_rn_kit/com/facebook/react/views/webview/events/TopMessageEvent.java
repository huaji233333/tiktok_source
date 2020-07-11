package com.facebook.react.views.webview.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class TopMessageEvent extends Event<TopMessageEvent> {
  private final String mData;
  
  public TopMessageEvent(int paramInt, String paramString) {
    super(paramInt);
    this.mData = paramString;
  }
  
  public boolean canCoalesce() {
    return false;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putString("data", this.mData);
    paramRCTEventEmitter.receiveEvent(getViewTag(), "topMessage", writableMap);
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public String getEventName() {
    return "topMessage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\webview\events\TopMessageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */