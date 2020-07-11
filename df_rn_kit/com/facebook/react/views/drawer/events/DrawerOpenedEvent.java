package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class DrawerOpenedEvent extends Event<DrawerOpenedEvent> {
  public DrawerOpenedEvent(int paramInt) {
    super(paramInt);
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), Arguments.createMap());
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public String getEventName() {
    return "topDrawerOpened";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\drawer\events\DrawerOpenedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */