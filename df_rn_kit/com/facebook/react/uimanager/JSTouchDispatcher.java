package com.facebook.react.uimanager;

import android.view.MotionEvent;
import android.view.ViewGroup;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.TouchEvent;
import com.facebook.react.uimanager.events.TouchEventCoalescingKeyHelper;
import com.facebook.react.uimanager.events.TouchEventType;

public class JSTouchDispatcher {
  private boolean mChildIsHandlingNativeGesture;
  
  private long mGestureStartTime = Long.MIN_VALUE;
  
  private final ViewGroup mRootViewGroup;
  
  private final float[] mTargetCoordinates = new float[2];
  
  private int mTargetTag = -1;
  
  private final TouchEventCoalescingKeyHelper mTouchEventCoalescingKeyHelper = new TouchEventCoalescingKeyHelper();
  
  public JSTouchDispatcher(ViewGroup paramViewGroup) {
    this.mRootViewGroup = paramViewGroup;
  }
  
  private void dispatchCancelEvent(MotionEvent paramMotionEvent, EventDispatcher paramEventDispatcher) {
    if (this.mTargetTag == -1) {
      a.b("ReactNative", "Can't cancel already finished gesture. Is a child View trying to start a gesture from an UP/CANCEL event?");
      return;
    } 
    a.a(this.mChildIsHandlingNativeGesture ^ true, "Expected to not have already sent a cancel for this gesture");
    paramEventDispatcher = (EventDispatcher)a.b(paramEventDispatcher);
    int i = this.mTargetTag;
    TouchEventType touchEventType = TouchEventType.CANCEL;
    long l = this.mGestureStartTime;
    float[] arrayOfFloat = this.mTargetCoordinates;
    paramEventDispatcher.dispatchEvent((Event)TouchEvent.obtain(i, touchEventType, paramMotionEvent, l, arrayOfFloat[0], arrayOfFloat[1], this.mTouchEventCoalescingKeyHelper));
  }
  
  private int findTargetTagAndSetCoordinates(MotionEvent paramMotionEvent) {
    return TouchTargetHelper.findTargetTagAndCoordinatesForTouch(paramMotionEvent.getX(), paramMotionEvent.getY(), this.mRootViewGroup, this.mTargetCoordinates, null);
  }
  
  public void handleTouchEvent(MotionEvent paramMotionEvent, EventDispatcher paramEventDispatcher) {
    int i = paramMotionEvent.getAction() & 0xFF;
    if (i == 0) {
      if (this.mTargetTag != -1)
        a.c("ReactNative", "Got DOWN touch before receiving UP or CANCEL from last gesture"); 
      this.mChildIsHandlingNativeGesture = false;
      this.mGestureStartTime = paramMotionEvent.getEventTime();
      this.mTargetTag = findTargetTagAndSetCoordinates(paramMotionEvent);
      i = this.mTargetTag;
      TouchEventType touchEventType = TouchEventType.START;
      long l = this.mGestureStartTime;
      float[] arrayOfFloat = this.mTargetCoordinates;
      paramEventDispatcher.dispatchEvent((Event)TouchEvent.obtain(i, touchEventType, paramMotionEvent, l, arrayOfFloat[0], arrayOfFloat[1], this.mTouchEventCoalescingKeyHelper));
      return;
    } 
    if (this.mChildIsHandlingNativeGesture)
      return; 
    int j = this.mTargetTag;
    if (j == -1) {
      a.c("ReactNative", "Unexpected state: received touch event but didn't get starting ACTION_DOWN for this gesture before");
      return;
    } 
    if (i == 1) {
      findTargetTagAndSetCoordinates(paramMotionEvent);
      i = this.mTargetTag;
      TouchEventType touchEventType = TouchEventType.END;
      long l = this.mGestureStartTime;
      float[] arrayOfFloat = this.mTargetCoordinates;
      paramEventDispatcher.dispatchEvent((Event)TouchEvent.obtain(i, touchEventType, paramMotionEvent, l, arrayOfFloat[0], arrayOfFloat[1], this.mTouchEventCoalescingKeyHelper));
      this.mTargetTag = -1;
      this.mGestureStartTime = Long.MIN_VALUE;
      return;
    } 
    if (i == 2) {
      findTargetTagAndSetCoordinates(paramMotionEvent);
      i = this.mTargetTag;
      TouchEventType touchEventType = TouchEventType.MOVE;
      long l = this.mGestureStartTime;
      float[] arrayOfFloat = this.mTargetCoordinates;
      paramEventDispatcher.dispatchEvent((Event)TouchEvent.obtain(i, touchEventType, paramMotionEvent, l, arrayOfFloat[0], arrayOfFloat[1], this.mTouchEventCoalescingKeyHelper));
      return;
    } 
    if (i == 5) {
      TouchEventType touchEventType = TouchEventType.START;
      long l = this.mGestureStartTime;
      float[] arrayOfFloat = this.mTargetCoordinates;
      paramEventDispatcher.dispatchEvent((Event)TouchEvent.obtain(j, touchEventType, paramMotionEvent, l, arrayOfFloat[0], arrayOfFloat[1], this.mTouchEventCoalescingKeyHelper));
      return;
    } 
    if (i == 6) {
      TouchEventType touchEventType = TouchEventType.END;
      long l = this.mGestureStartTime;
      float[] arrayOfFloat = this.mTargetCoordinates;
      paramEventDispatcher.dispatchEvent((Event)TouchEvent.obtain(j, touchEventType, paramMotionEvent, l, arrayOfFloat[0], arrayOfFloat[1], this.mTouchEventCoalescingKeyHelper));
      return;
    } 
    if (i == 3) {
      if (this.mTouchEventCoalescingKeyHelper.hasCoalescingKey(paramMotionEvent.getDownTime())) {
        dispatchCancelEvent(paramMotionEvent, paramEventDispatcher);
      } else {
        a.c("ReactNative", "Received an ACTION_CANCEL touch event for which we have no corresponding ACTION_DOWN");
      } 
      this.mTargetTag = -1;
      this.mGestureStartTime = Long.MIN_VALUE;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Warning : touch event was ignored. Action=");
    stringBuilder.append(i);
    stringBuilder.append(" Target=");
    stringBuilder.append(this.mTargetTag);
    a.b("ReactNative", stringBuilder.toString());
  }
  
  public void onChildStartedNativeGesture(MotionEvent paramMotionEvent, EventDispatcher paramEventDispatcher) {
    if (this.mChildIsHandlingNativeGesture)
      return; 
    dispatchCancelEvent(paramMotionEvent, paramEventDispatcher);
    this.mChildIsHandlingNativeGesture = true;
    this.mTargetTag = -1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\JSTouchDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */