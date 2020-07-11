package com.swmansion.gesturehandler.react;

import android.support.v4.f.l;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public final class b extends Event<b> {
  private static final l.c<b> a = new l.c(7);
  
  private WritableMap b;
  
  public static b a(com.swmansion.gesturehandler.b paramb, c<com.swmansion.gesturehandler.b> paramc) {
    b b2 = (b)a.acquire();
    b b1 = b2;
    if (b2 == null)
      b1 = new b(); 
    b1.init(paramb.f.getId());
    b1.b = Arguments.createMap();
    if (paramc != null)
      paramc.a(paramb, b1.b); 
    b1.b.putInt("handlerTag", paramb.e);
    b1.b.putInt("state", paramb.g);
    return b1;
  }
  
  public final boolean canCoalesce() {
    return false;
  }
  
  public final void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), "onGestureHandlerEvent", this.b);
  }
  
  public final short getCoalescingKey() {
    return 0;
  }
  
  public final String getEventName() {
    return "onGestureHandlerEvent";
  }
  
  public final void onDispose() {
    this.b = null;
    a.release(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */