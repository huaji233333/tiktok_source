package com.facebook.react.views.slider;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactSliderEvent extends Event<ReactSliderEvent> {
  private final boolean mFromUser;
  
  private final double mValue;
  
  public ReactSliderEvent(int paramInt, double paramDouble, boolean paramBoolean) {
    super(paramInt);
    this.mValue = paramDouble;
    this.mFromUser = paramBoolean;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("target", getViewTag());
    writableMap.putDouble("value", getValue());
    writableMap.putBoolean("fromUser", isFromUser());
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
  
  public double getValue() {
    return this.mValue;
  }
  
  public boolean isFromUser() {
    return this.mFromUser;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\slider\ReactSliderEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */