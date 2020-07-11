package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactTextChangedEvent extends Event<ReactTextChangedEvent> {
  private int mEventCount;
  
  private String mText;
  
  public ReactTextChangedEvent(int paramInt1, String paramString, int paramInt2) {
    super(paramInt1);
    this.mText = paramString;
    this.mEventCount = paramInt2;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putString("text", this.mText);
    writableMap.putInt("eventCount", this.mEventCount);
    writableMap.putInt("target", getViewTag());
    return writableMap;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topChange";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactTextChangedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */