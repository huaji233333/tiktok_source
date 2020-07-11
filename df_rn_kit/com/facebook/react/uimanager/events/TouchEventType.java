package com.facebook.react.uimanager.events;

public enum TouchEventType {
  CANCEL,
  END,
  MOVE,
  START("topTouchStart");
  
  private final String mJSEventName;
  
  static {
    END = new TouchEventType("END", 1, "topTouchEnd");
    MOVE = new TouchEventType("MOVE", 2, "topTouchMove");
    CANCEL = new TouchEventType("CANCEL", 3, "topTouchCancel");
    $VALUES = new TouchEventType[] { START, END, MOVE, CANCEL };
  }
  
  TouchEventType(String paramString1) {
    this.mJSEventName = paramString1;
  }
  
  public final String getJSEventName() {
    return this.mJSEventName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\TouchEventType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */