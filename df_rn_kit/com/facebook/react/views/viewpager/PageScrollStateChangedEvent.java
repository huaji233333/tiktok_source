package com.facebook.react.views.viewpager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class PageScrollStateChangedEvent extends Event<PageScrollStateChangedEvent> {
  private final String mPageScrollState;
  
  protected PageScrollStateChangedEvent(int paramInt, String paramString) {
    super(paramInt);
    this.mPageScrollState = paramString;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putString("pageScrollState", this.mPageScrollState);
    return writableMap;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topPageScrollStateChanged";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\viewpager\PageScrollStateChangedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */