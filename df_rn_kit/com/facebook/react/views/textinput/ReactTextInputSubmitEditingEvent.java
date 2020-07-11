package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ReactTextInputSubmitEditingEvent extends Event<ReactTextInputSubmitEditingEvent> {
  private String mText;
  
  public ReactTextInputSubmitEditingEvent(int paramInt, String paramString) {
    super(paramInt);
    this.mText = paramString;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("target", getViewTag());
    writableMap.putString("text", this.mText);
    return writableMap;
  }
  
  public boolean canCoalesce() {
    return false;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topSubmitEditing";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactTextInputSubmitEditingEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */