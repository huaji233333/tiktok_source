package com.facebook.react.views.scroll;

public enum ScrollEventType {
  BEGIN_DRAG("topScrollBeginDrag"),
  END_DRAG("topScrollEndDrag"),
  MOMENTUM_BEGIN("topScrollEndDrag"),
  MOMENTUM_END("topScrollEndDrag"),
  SCROLL("topScroll");
  
  private final String mJSEventName;
  
  static {
    MOMENTUM_BEGIN = new ScrollEventType("MOMENTUM_BEGIN", 3, "topMomentumScrollBegin");
    MOMENTUM_END = new ScrollEventType("MOMENTUM_END", 4, "topMomentumScrollEnd");
    $VALUES = new ScrollEventType[] { BEGIN_DRAG, END_DRAG, SCROLL, MOMENTUM_BEGIN, MOMENTUM_END };
  }
  
  ScrollEventType(String paramString1) {
    this.mJSEventName = paramString1;
  }
  
  public final String getJSEventName() {
    return this.mJSEventName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ScrollEventType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */