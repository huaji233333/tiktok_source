package com.facebook.react.uimanager.events;

import android.support.v4.f.l;
import android.view.MotionEvent;
import com.facebook.i.a.a;
import com.facebook.react.bridge.SoftAssertions;

public class TouchEvent extends Event<TouchEvent> {
  private static final l.c<TouchEvent> EVENTS_POOL = new l.c(3);
  
  private short mCoalescingKey;
  
  private MotionEvent mMotionEvent;
  
  private TouchEventType mTouchEventType;
  
  private float mViewX;
  
  private float mViewY;
  
  private void init(int paramInt, TouchEventType paramTouchEventType, MotionEvent paramMotionEvent, long paramLong, float paramFloat1, float paramFloat2, TouchEventCoalescingKeyHelper paramTouchEventCoalescingKeyHelper) {
    StringBuilder stringBuilder;
    boolean bool;
    init(paramInt);
    short s = 0;
    if (paramLong != Long.MIN_VALUE) {
      bool = true;
    } else {
      bool = false;
    } 
    SoftAssertions.assertCondition(bool, "Gesture start time must be initialized");
    paramInt = paramMotionEvent.getAction() & 0xFF;
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3) {
            if (paramInt == 5 || paramInt == 6) {
              paramTouchEventCoalescingKeyHelper.incrementCoalescingKey(paramLong);
            } else {
              stringBuilder = new StringBuilder("Unhandled MotionEvent action: ");
              stringBuilder.append(paramInt);
              throw new RuntimeException(stringBuilder.toString());
            } 
          } else {
            paramTouchEventCoalescingKeyHelper.removeCoalescingKey(paramLong);
          } 
        } else {
          s = paramTouchEventCoalescingKeyHelper.getCoalescingKey(paramLong);
        } 
      } else {
        paramTouchEventCoalescingKeyHelper.removeCoalescingKey(paramLong);
      } 
    } else {
      paramTouchEventCoalescingKeyHelper.addCoalescingKey(paramLong);
    } 
    this.mTouchEventType = (TouchEventType)stringBuilder;
    this.mMotionEvent = MotionEvent.obtain(paramMotionEvent);
    this.mCoalescingKey = s;
    this.mViewX = paramFloat1;
    this.mViewY = paramFloat2;
  }
  
  public static TouchEvent obtain(int paramInt, TouchEventType paramTouchEventType, MotionEvent paramMotionEvent, long paramLong, float paramFloat1, float paramFloat2, TouchEventCoalescingKeyHelper paramTouchEventCoalescingKeyHelper) {
    TouchEvent touchEvent2 = (TouchEvent)EVENTS_POOL.acquire();
    TouchEvent touchEvent1 = touchEvent2;
    if (touchEvent2 == null)
      touchEvent1 = new TouchEvent(); 
    touchEvent1.init(paramInt, paramTouchEventType, paramMotionEvent, paramLong, paramFloat1, paramFloat2, paramTouchEventCoalescingKeyHelper);
    return touchEvent1;
  }
  
  public boolean canCoalesce() {
    int i = null.$SwitchMap$com$facebook$react$uimanager$events$TouchEventType[((TouchEventType)a.b(this.mTouchEventType)).ordinal()];
    if (i != 1 && i != 2 && i != 3) {
      if (i == 4)
        return true; 
      StringBuilder stringBuilder = new StringBuilder("Unknown touch event type: ");
      stringBuilder.append(this.mTouchEventType);
      throw new RuntimeException(stringBuilder.toString());
    } 
    return false;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    TouchesHelper.sendTouchEvent(paramRCTEventEmitter, (TouchEventType)a.b(this.mTouchEventType), getViewTag(), this);
  }
  
  public short getCoalescingKey() {
    return this.mCoalescingKey;
  }
  
  public String getEventName() {
    return ((TouchEventType)a.b(this.mTouchEventType)).getJSEventName();
  }
  
  public MotionEvent getMotionEvent() {
    a.b(this.mMotionEvent);
    return this.mMotionEvent;
  }
  
  public float getViewX() {
    return this.mViewX;
  }
  
  public float getViewY() {
    return this.mViewY;
  }
  
  public void onDispose() {
    ((MotionEvent)a.b(this.mMotionEvent)).recycle();
    this.mMotionEvent = null;
    EVENTS_POOL.release(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\TouchEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */