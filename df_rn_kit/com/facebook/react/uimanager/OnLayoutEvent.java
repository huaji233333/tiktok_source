package com.facebook.react.uimanager;

import android.support.v4.f.l;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class OnLayoutEvent extends Event<OnLayoutEvent> {
  private static final l.c<OnLayoutEvent> EVENTS_POOL = new l.c(20);
  
  private int mHeight;
  
  private int mWidth;
  
  private int mX;
  
  private int mY;
  
  public static OnLayoutEvent obtain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    OnLayoutEvent onLayoutEvent2 = (OnLayoutEvent)EVENTS_POOL.acquire();
    OnLayoutEvent onLayoutEvent1 = onLayoutEvent2;
    if (onLayoutEvent2 == null)
      onLayoutEvent1 = new OnLayoutEvent(); 
    onLayoutEvent1.init(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    return onLayoutEvent1;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    WritableMap writableMap1 = Arguments.createMap();
    writableMap1.putDouble("x", PixelUtil.toDIPFromPixel(this.mX));
    writableMap1.putDouble("y", PixelUtil.toDIPFromPixel(this.mY));
    writableMap1.putDouble("width", PixelUtil.toDIPFromPixel(this.mWidth));
    writableMap1.putDouble("height", PixelUtil.toDIPFromPixel(this.mHeight));
    WritableMap writableMap2 = Arguments.createMap();
    writableMap2.putMap("layout", writableMap1);
    writableMap2.putInt("target", getViewTag());
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), writableMap2);
  }
  
  public String getEventName() {
    return "topLayout";
  }
  
  protected void init(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    init(paramInt1);
    this.mX = paramInt2;
    this.mY = paramInt3;
    this.mWidth = paramInt4;
    this.mHeight = paramInt5;
  }
  
  public void onDispose() {
    EVENTS_POOL.release(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\OnLayoutEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */