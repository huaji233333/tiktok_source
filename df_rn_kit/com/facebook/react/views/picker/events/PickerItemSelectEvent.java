package com.facebook.react.views.picker.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class PickerItemSelectEvent extends Event<PickerItemSelectEvent> {
  private final int mPosition;
  
  public PickerItemSelectEvent(int paramInt1, int paramInt2) {
    super(paramInt1);
    this.mPosition = paramInt2;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("position", this.mPosition);
    return writableMap;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topSelect";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\picker\events\PickerItemSelectEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */