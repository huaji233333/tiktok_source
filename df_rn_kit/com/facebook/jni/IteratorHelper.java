package com.facebook.jni;

import java.util.Iterator;

public class IteratorHelper {
  private Object mElement;
  
  private final Iterator mIterator;
  
  public IteratorHelper(Iterable paramIterable) {
    this.mIterator = paramIterable.iterator();
  }
  
  public IteratorHelper(Iterator paramIterator) {
    this.mIterator = paramIterator;
  }
  
  boolean hasNext() {
    if (this.mIterator.hasNext()) {
      this.mElement = this.mIterator.next();
      return true;
    } 
    this.mElement = null;
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\IteratorHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */