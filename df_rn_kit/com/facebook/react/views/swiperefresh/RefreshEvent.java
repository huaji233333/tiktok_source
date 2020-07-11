package com.facebook.react.views.swiperefresh;

import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RefreshEvent extends Event<RefreshEvent> {
  protected RefreshEvent(int paramInt) {
    super(paramInt);
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), null);
  }
  
  public String getEventName() {
    return "topRefresh";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\swiperefresh\RefreshEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */