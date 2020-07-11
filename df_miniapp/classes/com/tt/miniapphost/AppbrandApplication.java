package com.tt.miniapphost;

public final class AppbrandApplication {
  private static IAppbrandApplication sRealAppbrandApplication;
  
  public static IAppbrandApplication getInst() {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/AppbrandApplication.sRealAppbrandApplication : Lcom/tt/miniapphost/IAppbrandApplication;
    //   3: ifnonnull -> 33
    //   6: ldc com/tt/miniapphost/AppbrandApplication
    //   8: monitorenter
    //   9: getstatic com/tt/miniapphost/AppbrandApplication.sRealAppbrandApplication : Lcom/tt/miniapphost/IAppbrandApplication;
    //   12: ifnonnull -> 21
    //   15: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   18: putstatic com/tt/miniapphost/AppbrandApplication.sRealAppbrandApplication : Lcom/tt/miniapphost/IAppbrandApplication;
    //   21: ldc com/tt/miniapphost/AppbrandApplication
    //   23: monitorexit
    //   24: goto -> 33
    //   27: astore_0
    //   28: ldc com/tt/miniapphost/AppbrandApplication
    //   30: monitorexit
    //   31: aload_0
    //   32: athrow
    //   33: getstatic com/tt/miniapphost/AppbrandApplication.sRealAppbrandApplication : Lcom/tt/miniapphost/IAppbrandApplication;
    //   36: areturn
    // Exception table:
    //   from	to	target	type
    //   9	21	27	finally
    //   21	24	27	finally
    //   28	31	27	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\AppbrandApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */