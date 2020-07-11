package com.facebook.react.views.scroll;

import android.support.v4.f.l;
import com.facebook.i.a.a;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ScrollEvent extends Event<ScrollEvent> {
  private static final l.c<ScrollEvent> EVENTS_POOL = new l.c(3);
  
  private int mContentHeight;
  
  private int mContentWidth;
  
  private ScrollEventType mScrollEventType;
  
  private int mScrollViewHeight;
  
  private int mScrollViewWidth;
  
  private int mScrollX;
  
  private int mScrollY;
  
  private double mXVelocity;
  
  private double mYVelocity;
  
  private void init(int paramInt1, ScrollEventType paramScrollEventType, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
    init(paramInt1);
    this.mScrollEventType = paramScrollEventType;
    this.mScrollX = paramInt2;
    this.mScrollY = paramInt3;
    this.mXVelocity = paramFloat1;
    this.mYVelocity = paramFloat2;
    this.mContentWidth = paramInt4;
    this.mContentHeight = paramInt5;
    this.mScrollViewWidth = paramInt6;
    this.mScrollViewHeight = paramInt7;
  }
  
  public static ScrollEvent obtain(int paramInt1, ScrollEventType paramScrollEventType, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
    ScrollEvent scrollEvent2 = (ScrollEvent)EVENTS_POOL.acquire();
    ScrollEvent scrollEvent1 = scrollEvent2;
    if (scrollEvent2 == null)
      scrollEvent1 = new ScrollEvent(); 
    scrollEvent1.init(paramInt1, paramScrollEventType, paramInt2, paramInt3, paramFloat1, paramFloat2, paramInt4, paramInt5, paramInt6, paramInt7);
    return scrollEvent1;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap1 = Arguments.createMap();
    writableMap1.putDouble("top", 0.0D);
    writableMap1.putDouble("bottom", 0.0D);
    writableMap1.putDouble("left", 0.0D);
    writableMap1.putDouble("right", 0.0D);
    WritableMap writableMap2 = Arguments.createMap();
    writableMap2.putDouble("x", PixelUtil.toDIPFromPixel(this.mScrollX));
    writableMap2.putDouble("y", PixelUtil.toDIPFromPixel(this.mScrollY));
    WritableMap writableMap3 = Arguments.createMap();
    writableMap3.putDouble("width", PixelUtil.toDIPFromPixel(this.mContentWidth));
    writableMap3.putDouble("height", PixelUtil.toDIPFromPixel(this.mContentHeight));
    WritableMap writableMap4 = Arguments.createMap();
    writableMap4.putDouble("width", PixelUtil.toDIPFromPixel(this.mScrollViewWidth));
    writableMap4.putDouble("height", PixelUtil.toDIPFromPixel(this.mScrollViewHeight));
    WritableMap writableMap5 = Arguments.createMap();
    writableMap5.putDouble("x", this.mXVelocity);
    writableMap5.putDouble("y", this.mYVelocity);
    WritableMap writableMap6 = Arguments.createMap();
    writableMap6.putMap("contentInset", writableMap1);
    writableMap6.putMap("contentOffset", writableMap2);
    writableMap6.putMap("contentSize", writableMap3);
    writableMap6.putMap("layoutMeasurement", writableMap4);
    writableMap6.putMap("velocity", writableMap5);
    writableMap6.putInt("target", getViewTag());
    writableMap6.putBoolean("responderIgnoreScroll", true);
    return writableMap6;
  }
  
  public boolean canCoalesce() {
    return (this.mScrollEventType == ScrollEventType.SCROLL);
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public String getEventName() {
    return ((ScrollEventType)a.b(this.mScrollEventType)).getJSEventName();
  }
  
  public void onDispose() {
    EVENTS_POOL.release(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ScrollEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */