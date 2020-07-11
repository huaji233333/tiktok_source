package com.facebook.react.uimanager;

public class ReactRootViewTagGenerator {
  private static int sNextRootViewTag = 1;
  
  public static int getNextRootViewTag() {
    // Byte code:
    //   0: ldc com/facebook/react/uimanager/ReactRootViewTagGenerator
    //   2: monitorenter
    //   3: getstatic com/facebook/react/uimanager/ReactRootViewTagGenerator.sNextRootViewTag : I
    //   6: istore_0
    //   7: getstatic com/facebook/react/uimanager/ReactRootViewTagGenerator.sNextRootViewTag : I
    //   10: bipush #10
    //   12: iadd
    //   13: putstatic com/facebook/react/uimanager/ReactRootViewTagGenerator.sNextRootViewTag : I
    //   16: ldc com/facebook/react/uimanager/ReactRootViewTagGenerator
    //   18: monitorexit
    //   19: iload_0
    //   20: ireturn
    //   21: astore_1
    //   22: ldc com/facebook/react/uimanager/ReactRootViewTagGenerator
    //   24: monitorexit
    //   25: aload_1
    //   26: athrow
    // Exception table:
    //   from	to	target	type
    //   3	16	21	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ReactRootViewTagGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */