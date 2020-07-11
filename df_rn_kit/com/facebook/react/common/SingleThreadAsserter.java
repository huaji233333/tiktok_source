package com.facebook.react.common;

import com.facebook.i.a.a;

public class SingleThreadAsserter {
  private Thread mThread;
  
  public void assertNow() {
    boolean bool;
    Thread thread = Thread.currentThread();
    if (this.mThread == null)
      this.mThread = thread; 
    if (this.mThread == thread) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\SingleThreadAsserter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */