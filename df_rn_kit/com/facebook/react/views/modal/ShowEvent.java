package com.facebook.react.views.modal;

import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class ShowEvent extends Event<ShowEvent> {
  protected ShowEvent(int paramInt) {
    super(paramInt);
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), null);
  }
  
  public String getEventName() {
    return "topShow";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\ShowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */