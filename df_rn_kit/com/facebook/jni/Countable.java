package com.facebook.jni;

public class Countable {
  private long mInstance;
  
  public native void dispose();
  
  protected void finalize() throws Throwable {
    dispose();
    super.finalize();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\Countable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */