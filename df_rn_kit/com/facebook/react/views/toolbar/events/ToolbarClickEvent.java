package com.facebook.react.views.toolbar.events;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ToolbarClickEvent extends Event<ToolbarClickEvent> {
  private final int position;
  
  public ToolbarClickEvent(int paramInt1, int paramInt2) {
    super(paramInt1);
    this.position = paramInt2;
  }
  
  public boolean canCoalesce() {
    return false;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    writableNativeMap.putInt("position", getPosition());
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), (WritableMap)writableNativeMap);
  }
  
  public String getEventName() {
    return "topSelect";
  }
  
  public int getPosition() {
    return this.position;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\toolbar\events\ToolbarClickEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */