package com.facebook.jni;

import java.util.Iterator;
import java.util.Map;

public class MapIteratorHelper {
  private final Iterator<Map.Entry> mIterator;
  
  private Object mKey;
  
  private Object mValue;
  
  public MapIteratorHelper(Map paramMap) {
    this.mIterator = paramMap.entrySet().iterator();
  }
  
  boolean hasNext() {
    if (this.mIterator.hasNext()) {
      Map.Entry entry = this.mIterator.next();
      this.mKey = entry.getKey();
      this.mValue = entry.getValue();
      return true;
    } 
    this.mKey = null;
    this.mValue = null;
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\MapIteratorHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */