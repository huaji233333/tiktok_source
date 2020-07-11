package com.facebook.react.uimanager.events;

import android.util.SparseIntArray;

public class TouchEventCoalescingKeyHelper {
  private final SparseIntArray mDownTimeToCoalescingKey = new SparseIntArray();
  
  public void addCoalescingKey(long paramLong) {
    this.mDownTimeToCoalescingKey.put((int)paramLong, 0);
  }
  
  public short getCoalescingKey(long paramLong) {
    int i = this.mDownTimeToCoalescingKey.get((int)paramLong, -1);
    if (i != -1)
      return (short)(0xFFFF & i); 
    throw new RuntimeException("Tried to get non-existent cookie");
  }
  
  public boolean hasCoalescingKey(long paramLong) {
    return !(this.mDownTimeToCoalescingKey.get((int)paramLong, -1) == -1);
  }
  
  public void incrementCoalescingKey(long paramLong) {
    SparseIntArray sparseIntArray = this.mDownTimeToCoalescingKey;
    int i = (int)paramLong;
    int j = sparseIntArray.get(i, -1);
    if (j != -1) {
      this.mDownTimeToCoalescingKey.put(i, j + 1);
      return;
    } 
    throw new RuntimeException("Tried to increment non-existent cookie");
  }
  
  public void removeCoalescingKey(long paramLong) {
    this.mDownTimeToCoalescingKey.delete((int)paramLong);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\TouchEventCoalescingKeyHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */