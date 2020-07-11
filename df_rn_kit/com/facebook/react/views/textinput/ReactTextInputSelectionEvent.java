package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ReactTextInputSelectionEvent extends Event<ReactTextInputSelectionEvent> {
  private int mSelectionEnd;
  
  private int mSelectionStart;
  
  public ReactTextInputSelectionEvent(int paramInt1, int paramInt2, int paramInt3) {
    super(paramInt1);
    this.mSelectionStart = paramInt2;
    this.mSelectionEnd = paramInt3;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap1 = Arguments.createMap();
    WritableMap writableMap2 = Arguments.createMap();
    writableMap2.putInt("end", this.mSelectionEnd);
    writableMap2.putInt("start", this.mSelectionStart);
    writableMap1.putMap("selection", writableMap2);
    return writableMap1;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topSelectionChange";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactTextInputSelectionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */