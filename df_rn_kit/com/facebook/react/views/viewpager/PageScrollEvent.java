package com.facebook.react.views.viewpager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

class PageScrollEvent extends Event<PageScrollEvent> {
  private final float mOffset;
  
  private final int mPosition;
  
  protected PageScrollEvent(int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokespecial <init> : (I)V
    //   5: aload_0
    //   6: iload_2
    //   7: putfield mPosition : I
    //   10: fload_3
    //   11: invokestatic isInfinite : (F)Z
    //   14: ifne -> 27
    //   17: fload_3
    //   18: fstore #4
    //   20: fload_3
    //   21: invokestatic isNaN : (F)Z
    //   24: ifeq -> 30
    //   27: fconst_0
    //   28: fstore #4
    //   30: aload_0
    //   31: fload #4
    //   33: putfield mOffset : F
    //   36: return
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("position", this.mPosition);
    writableMap.putDouble("offset", this.mOffset);
    return writableMap;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topPageScroll";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\viewpager\PageScrollEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */