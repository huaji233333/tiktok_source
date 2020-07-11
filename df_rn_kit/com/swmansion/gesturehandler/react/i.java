package com.swmansion.gesturehandler.react;

import android.support.v4.f.l;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.swmansion.gesturehandler.b;

public final class i extends Event<i> {
  static final l.c<i> a = new l.c(7);
  
  private WritableMap b;
  
  void a(b paramb, int paramInt1, int paramInt2, c<b> paramc) {
    init(paramb.f.getId());
    this.b = Arguments.createMap();
    if (paramc != null)
      paramc.a(paramb, this.b); 
    this.b.putInt("handlerTag", paramb.e);
    this.b.putInt("state", paramInt1);
    this.b.putInt("oldState", paramInt2);
  }
  
  public final boolean canCoalesce() {
    return false;
  }
  
  public final void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), "onGestureHandlerStateChange", this.b);
  }
  
  public final short getCoalescingKey() {
    return 0;
  }
  
  public final String getEventName() {
    return "onGestureHandlerStateChange";
  }
  
  public final void onDispose() {
    this.b = null;
    a.release(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */