package com.facebook.react.views.slider;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactSlidingCompleteEvent extends Event<ReactSlidingCompleteEvent> {
  private final double mValue;
  
  public ReactSlidingCompleteEvent(int paramInt, double paramDouble) {
    super(paramInt);
    this.mValue = paramDouble;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("target", getViewTag());
    writableMap.putDouble("value", getValue());
    return writableMap;
  }
  
  public boolean canCoalesce() {
    return false;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public String getEventName() {
    return "topSlidingComplete";
  }
  
  public double getValue() {
    return this.mValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\slider\ReactSlidingCompleteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */