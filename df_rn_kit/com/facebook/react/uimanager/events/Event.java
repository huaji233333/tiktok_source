package com.facebook.react.uimanager.events;

import com.facebook.react.common.SystemClock;

public abstract class Event<T extends Event> {
  private static int sUniqueID;
  
  private boolean mInitialized;
  
  private long mTimestampMs;
  
  private int mUniqueID;
  
  private int mViewTag;
  
  public Event() {
    int i = sUniqueID;
    sUniqueID = i + 1;
    this.mUniqueID = i;
  }
  
  public Event(int paramInt) {
    int i = sUniqueID;
    sUniqueID = i + 1;
    this.mUniqueID = i;
    init(paramInt);
  }
  
  public boolean canCoalesce() {
    return true;
  }
  
  public T coalesce(T paramT) {
    return (T)((getTimestampMs() >= paramT.getTimestampMs()) ? this : (Object)paramT);
  }
  
  public abstract void dispatch(RCTEventEmitter paramRCTEventEmitter);
  
  final void dispose() {
    this.mInitialized = false;
    onDispose();
  }
  
  public short getCoalescingKey() {
    return 0;
  }
  
  public abstract String getEventName();
  
  public final long getTimestampMs() {
    return this.mTimestampMs;
  }
  
  public int getUniqueID() {
    return this.mUniqueID;
  }
  
  public final int getViewTag() {
    return this.mViewTag;
  }
  
  public void init(int paramInt) {
    this.mViewTag = paramInt;
    this.mTimestampMs = SystemClock.uptimeMillis();
    this.mInitialized = true;
  }
  
  boolean isInitialized() {
    return this.mInitialized;
  }
  
  public void onDispose() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\Event.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */