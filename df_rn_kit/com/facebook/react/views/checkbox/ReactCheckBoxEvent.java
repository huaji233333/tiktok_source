package com.facebook.react.views.checkbox;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ReactCheckBoxEvent extends Event<ReactCheckBoxEvent> {
  private final boolean mIsChecked;
  
  public ReactCheckBoxEvent(int paramInt, boolean paramBoolean) {
    super(paramInt);
    this.mIsChecked = paramBoolean;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("target", getViewTag());
    writableMap.putBoolean("value", getIsChecked());
    return writableMap;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public String getEventName() {
    return "topChange";
  }
  
  public boolean getIsChecked() {
    return this.mIsChecked;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\checkbox\ReactCheckBoxEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */