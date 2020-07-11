package com.tt.miniapp.monitor;

import java.util.LinkedList;

public class RouterMonitorTask {
  private static long sLastRouterTime;
  
  private static long sRenderStartTime;
  
  private static String sRouterName;
  
  private static long sRouterStartTime;
  
  private static final LinkedList<Long> sRouterTimeList = new LinkedList<Long>();
  
  static {
    sRouterName = null;
    sRouterStartTime = -1L;
    sLastRouterTime = -1L;
    sRenderStartTime = -1L;
  }
  
  public static void completedPageRouter(String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   2: monitorenter
    //   3: invokestatic currentTimeMillis : ()J
    //   6: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterStartTime : J
    //   9: lsub
    //   10: lstore_1
    //   11: ldc 'tma_RouterMonitorTask'
    //   13: iconst_4
    //   14: anewarray java/lang/Object
    //   17: dup
    //   18: iconst_0
    //   19: ldc 'completedPageRouter '
    //   21: aastore
    //   22: dup
    //   23: iconst_1
    //   24: aload_0
    //   25: aastore
    //   26: dup
    //   27: iconst_2
    //   28: ldc ' '
    //   30: aastore
    //   31: dup
    //   32: iconst_3
    //   33: lload_1
    //   34: invokestatic valueOf : (J)Ljava/lang/Long;
    //   37: aastore
    //   38: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   41: aload_0
    //   42: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterName : Ljava/lang/String;
    //   45: invokevirtual equals : (Ljava/lang/Object;)Z
    //   48: ifeq -> 84
    //   51: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   54: lload_1
    //   55: invokestatic valueOf : (J)Ljava/lang/Long;
    //   58: invokevirtual offer : (Ljava/lang/Object;)Z
    //   61: pop
    //   62: lload_1
    //   63: putstatic com/tt/miniapp/monitor/RouterMonitorTask.sLastRouterTime : J
    //   66: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   69: invokevirtual size : ()I
    //   72: bipush #10
    //   74: if_icmple -> 84
    //   77: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   80: invokevirtual pollFirst : ()Ljava/lang/Object;
    //   83: pop
    //   84: aconst_null
    //   85: putstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterName : Ljava/lang/String;
    //   88: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   90: monitorexit
    //   91: return
    //   92: astore_0
    //   93: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   95: monitorexit
    //   96: aload_0
    //   97: athrow
    // Exception table:
    //   from	to	target	type
    //   3	84	92	finally
    //   84	88	92	finally
  }
  
  public static long optAvgRouterTime() {
    // Byte code:
    //   0: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   6: invokevirtual size : ()I
    //   9: istore_0
    //   10: lconst_0
    //   11: lstore_1
    //   12: iload_0
    //   13: ifne -> 21
    //   16: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   18: monitorexit
    //   19: lconst_0
    //   20: lreturn
    //   21: iconst_0
    //   22: istore_0
    //   23: iload_0
    //   24: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   27: invokevirtual size : ()I
    //   30: if_icmpge -> 56
    //   33: lload_1
    //   34: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   37: iload_0
    //   38: invokevirtual get : (I)Ljava/lang/Object;
    //   41: checkcast java/lang/Long
    //   44: invokevirtual longValue : ()J
    //   47: ladd
    //   48: lstore_1
    //   49: iload_0
    //   50: iconst_1
    //   51: iadd
    //   52: istore_0
    //   53: goto -> 23
    //   56: lload_1
    //   57: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   60: invokevirtual size : ()I
    //   63: i2l
    //   64: ldiv
    //   65: lstore_1
    //   66: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterTimeList : Ljava/util/LinkedList;
    //   69: invokevirtual clear : ()V
    //   72: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   74: monitorexit
    //   75: lload_1
    //   76: lreturn
    //   77: astore_3
    //   78: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   80: monitorexit
    //   81: goto -> 86
    //   84: aload_3
    //   85: athrow
    //   86: goto -> 84
    // Exception table:
    //   from	to	target	type
    //   3	10	77	finally
    //   23	49	77	finally
    //   56	72	77	finally
  }
  
  public static long optLastRouterTime() {
    // Byte code:
    //   0: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/monitor/RouterMonitorTask.sLastRouterTime : J
    //   6: lstore_0
    //   7: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   9: monitorexit
    //   10: lload_0
    //   11: lreturn
    //   12: astore_2
    //   13: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   15: monitorexit
    //   16: aload_2
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	12	finally
  }
  
  public static void startPageRender() {
    // Byte code:
    //   0: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   2: monitorenter
    //   3: invokestatic currentTimeMillis : ()J
    //   6: putstatic com/tt/miniapp/monitor/RouterMonitorTask.sRenderStartTime : J
    //   9: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   11: monitorexit
    //   12: return
    //   13: astore_0
    //   14: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   16: monitorexit
    //   17: aload_0
    //   18: athrow
    // Exception table:
    //   from	to	target	type
    //   3	9	13	finally
  }
  
  public static void startPageRouter(String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   2: monitorenter
    //   3: aload_0
    //   4: putstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterName : Ljava/lang/String;
    //   7: invokestatic currentTimeMillis : ()J
    //   10: putstatic com/tt/miniapp/monitor/RouterMonitorTask.sRouterStartTime : J
    //   13: new java/lang/StringBuilder
    //   16: dup
    //   17: ldc 'startPageRouter '
    //   19: invokespecial <init> : (Ljava/lang/String;)V
    //   22: astore_1
    //   23: aload_1
    //   24: aload_0
    //   25: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: ldc 'tma_RouterMonitorTask'
    //   31: iconst_1
    //   32: anewarray java/lang/Object
    //   35: dup
    //   36: iconst_0
    //   37: aload_1
    //   38: invokevirtual toString : ()Ljava/lang/String;
    //   41: aastore
    //   42: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   45: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   47: monitorexit
    //   48: return
    //   49: astore_0
    //   50: ldc com/tt/miniapp/monitor/RouterMonitorTask
    //   52: monitorexit
    //   53: aload_0
    //   54: athrow
    // Exception table:
    //   from	to	target	type
    //   3	45	49	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\RouterMonitorTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */