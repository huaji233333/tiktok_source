package com.facebook.react.views.image;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ImageLoadEvent extends Event<ImageLoadEvent> {
  private final int mEventType;
  
  private final int mHeight;
  
  private final String mImageUri;
  
  private final int mWidth;
  
  public ImageLoadEvent(int paramInt1, int paramInt2) {
    this(paramInt1, paramInt2, null);
  }
  
  public ImageLoadEvent(int paramInt1, int paramInt2, String paramString) {
    this(paramInt1, paramInt2, paramString, 0, 0);
  }
  
  public ImageLoadEvent(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4) {
    super(paramInt1);
    this.mEventType = paramInt2;
    this.mImageUri = paramString;
    this.mWidth = paramInt3;
    this.mHeight = paramInt4;
  }
  
  public static String eventNameForType(int paramInt) {
    if (paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt != 3) {
          if (paramInt != 4) {
            if (paramInt == 5)
              return "topProgress"; 
            StringBuilder stringBuilder = new StringBuilder("Invalid image event: ");
            stringBuilder.append(Integer.toString(paramInt));
            throw new IllegalStateException(stringBuilder.toString());
          } 
          return "topLoadStart";
        } 
        return "topLoadEnd";
      } 
      return "topLoad";
    } 
    return "topError";
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    WritableMap writableMap;
    if (this.mImageUri != null || this.mEventType == 2) {
      WritableMap writableMap1 = Arguments.createMap();
      String str = this.mImageUri;
      if (str != null)
        writableMap1.putString("uri", str); 
      writableMap = writableMap1;
      if (this.mEventType == 2) {
        writableMap = Arguments.createMap();
        writableMap.putDouble("width", this.mWidth);
        writableMap.putDouble("height", this.mHeight);
        String str1 = this.mImageUri;
        if (str1 != null)
          writableMap.putString("url", str1); 
        writableMap1.putMap("source", writableMap);
        writableMap = writableMap1;
      } 
    } else {
      writableMap = null;
    } 
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), writableMap);
  }
  
  public short getCoalescingKey() {
    return (short)this.mEventType;
  }
  
  public String getEventName() {
    return eventNameForType(this.mEventType);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\image\ImageLoadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */