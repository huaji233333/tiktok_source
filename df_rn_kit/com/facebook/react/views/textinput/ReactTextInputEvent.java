package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactTextInputEvent extends Event<ReactTextInputEvent> {
  private String mPreviousText;
  
  private int mRangeEnd;
  
  private int mRangeStart;
  
  private String mText;
  
  public ReactTextInputEvent(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3) {
    super(paramInt1);
    this.mText = paramString1;
    this.mPreviousText = paramString2;
    this.mRangeStart = paramInt2;
    this.mRangeEnd = paramInt3;
  }
  
  private WritableMap serializeEventData() {
    WritableMap writableMap1 = Arguments.createMap();
    WritableMap writableMap2 = Arguments.createMap();
    writableMap2.putDouble("start", this.mRangeStart);
    writableMap2.putDouble("end", this.mRangeEnd);
    writableMap1.putString("text", this.mText);
    writableMap1.putString("previousText", this.mPreviousText);
    writableMap1.putMap("range", writableMap2);
    writableMap1.putInt("target", getViewTag());
    return writableMap1;
  }
  
  public boolean canCoalesce() {
    return false;
  }
  
  public void dispatch(RCTEventEmitter paramRCTEventEmitter) {
    paramRCTEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }
  
  public String getEventName() {
    return "topTextInput";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactTextInputEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */