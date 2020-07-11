package com.ss.android.ugc.aweme.miniapp.g;

public final class z {
  private static volatile y a;
  
  public static y a() {
    // Byte code:
    //   0: ldc com/ss/android/ugc/aweme/miniapp/g/z
    //   2: monitorenter
    //   3: getstatic com/ss/android/ugc/aweme/miniapp/g/z.a : Lcom/ss/android/ugc/aweme/miniapp/g/y;
    //   6: ifnonnull -> 40
    //   9: ldc com/ss/android/ugc/aweme/miniapp/g/y
    //   11: monitorenter
    //   12: getstatic com/ss/android/ugc/aweme/miniapp/g/z.a : Lcom/ss/android/ugc/aweme/miniapp/g/y;
    //   15: ifnonnull -> 28
    //   18: new com/ss/android/ugc/aweme/miniapp/g/y
    //   21: dup
    //   22: invokespecial <init> : ()V
    //   25: putstatic com/ss/android/ugc/aweme/miniapp/g/z.a : Lcom/ss/android/ugc/aweme/miniapp/g/y;
    //   28: ldc com/ss/android/ugc/aweme/miniapp/g/y
    //   30: monitorexit
    //   31: goto -> 40
    //   34: astore_0
    //   35: ldc com/ss/android/ugc/aweme/miniapp/g/y
    //   37: monitorexit
    //   38: aload_0
    //   39: athrow
    //   40: getstatic com/ss/android/ugc/aweme/miniapp/g/z.a : Lcom/ss/android/ugc/aweme/miniapp/g/y;
    //   43: astore_0
    //   44: ldc com/ss/android/ugc/aweme/miniapp/g/z
    //   46: monitorexit
    //   47: aload_0
    //   48: areturn
    //   49: astore_0
    //   50: ldc com/ss/android/ugc/aweme/miniapp/g/z
    //   52: monitorexit
    //   53: aload_0
    //   54: athrow
    // Exception table:
    //   from	to	target	type
    //   3	12	49	finally
    //   12	28	34	finally
    //   28	31	49	finally
    //   35	40	49	finally
    //   40	44	49	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\z.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */