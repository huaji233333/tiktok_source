package com.facebook.react.uimanager.events;

import android.view.MotionEvent;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;

class TouchesHelper {
  private static WritableArray createsPointersArray(int paramInt, TouchEvent paramTouchEvent) {
    WritableArray writableArray = Arguments.createArray();
    MotionEvent motionEvent = paramTouchEvent.getMotionEvent();
    float f1 = motionEvent.getX();
    float f2 = paramTouchEvent.getViewX();
    float f3 = motionEvent.getY();
    float f4 = paramTouchEvent.getViewY();
    int i;
    for (i = 0; i < motionEvent.getPointerCount(); i++) {
      WritableMap writableMap = Arguments.createMap();
      writableMap.putDouble("pageX", PixelUtil.toDIPFromPixel(motionEvent.getX(i)));
      writableMap.putDouble("pageY", PixelUtil.toDIPFromPixel(motionEvent.getY(i)));
      float f5 = motionEvent.getX(i);
      float f6 = motionEvent.getY(i);
      writableMap.putDouble("locationX", PixelUtil.toDIPFromPixel(f5 - f1 - f2));
      writableMap.putDouble("locationY", PixelUtil.toDIPFromPixel(f6 - f3 - f4));
      writableMap.putInt("target", paramInt);
      writableMap.putDouble("timestamp", paramTouchEvent.getTimestampMs());
      writableMap.putDouble("identifier", motionEvent.getPointerId(i));
      writableArray.pushMap(writableMap);
    } 
    return writableArray;
  }
  
  public static void sendTouchEvent(RCTEventEmitter paramRCTEventEmitter, TouchEventType paramTouchEventType, int paramInt, TouchEvent paramTouchEvent) {
    StringBuilder stringBuilder;
    WritableArray writableArray1 = createsPointersArray(paramInt, paramTouchEvent);
    MotionEvent motionEvent = paramTouchEvent.getMotionEvent();
    WritableArray writableArray2 = Arguments.createArray();
    if (paramTouchEventType == TouchEventType.MOVE || paramTouchEventType == TouchEventType.CANCEL) {
      for (paramInt = 0; paramInt < motionEvent.getPointerCount(); paramInt++)
        writableArray2.pushInt(paramInt); 
    } else if (paramTouchEventType == TouchEventType.START || paramTouchEventType == TouchEventType.END) {
      writableArray2.pushInt(motionEvent.getActionIndex());
    } else {
      stringBuilder = new StringBuilder("Unknown touch type: ");
      stringBuilder.append(paramTouchEventType);
      throw new RuntimeException(stringBuilder.toString());
    } 
    stringBuilder.receiveTouches(paramTouchEventType.getJSEventName(), writableArray1, writableArray2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\TouchesHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */