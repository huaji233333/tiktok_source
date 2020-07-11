package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class DrawerStateChangedEvent extends Event<DrawerStateChangedEvent> {
  private final int mDrawerState;
  
  public DrawerStateChangedEvent(int paramInt1, int paramInt2) {
    super(paramInt1);
    this.mDrawerState = paramInt2;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putDouble("drawerState", getDrawerState());
    return writableMap;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public int getDrawerState() {
    return this.mDrawerState;
  }
  
  public String getEventName() {
    return "topDrawerStateChanged";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\drawer\events\DrawerStateChangedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */