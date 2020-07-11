package com.facebook.jni;

public class ThreadScopeSupport {
  private static void runStdFunction(long paramLong) {
    runStdFunctionImpl(paramLong);
  }
  
  private static native void runStdFunctionImpl(long paramLong);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\ThreadScopeSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */