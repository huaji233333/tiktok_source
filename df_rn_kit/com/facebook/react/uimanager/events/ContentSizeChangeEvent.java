package com.facebook.react.uimanager.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;

public class ContentSizeChangeEvent extends Event<ContentSizeChangeEvent> {
  private final int mHeight;
  
  private final int mWidth;
  
  public ContentSizeChangeEvent(int paramInt1, int paramInt2, int paramInt3) {
    super(paramInt1);
    this.mWidth = paramInt2;
    this.mHeight = paramInt3;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putDouble("width", PixelUtil.toDIPFromPixel(this.mWidth));
    writableMap.putDouble("height", PixelUtil.toDIPFromPixel(this.mHeight));
    paramRCTEventEmitter.receiveEvent(getViewTag(), "topContentSizeChange", writableMap);
  }
  
  public String getEventName() {
    return "topContentSizeChange";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\ContentSizeChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */