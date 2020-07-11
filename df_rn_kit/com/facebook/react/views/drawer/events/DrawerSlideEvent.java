package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class DrawerSlideEvent extends Event<DrawerSlideEvent> {
  private final float mOffset;
  
  public DrawerSlideEvent(int paramInt, float paramFloat) {
    super(paramInt);
    this.mOffset = paramFloat;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putDouble("offset", getOffset());
    return writableMap;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public String getEventName() {
    return "topDrawerSlide";
  }
  
  public float getOffset() {
    return this.mOffset;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\drawer\events\DrawerSlideEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */