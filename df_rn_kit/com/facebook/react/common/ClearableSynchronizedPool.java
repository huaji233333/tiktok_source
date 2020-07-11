package com.facebook.react.common;

import android.support.v4.f.l;

public class ClearableSynchronizedPool<T> implements l.a<T> {
  private final Object[] mPool;
  
  private int mSize;
  
  public ClearableSynchronizedPool(int paramInt) {
    this.mPool = new Object[paramInt];
  }
  
  public T acquire() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mSize : I
    //   6: istore_1
    //   7: iload_1
    //   8: ifne -> 15
    //   11: aload_0
    //   12: monitorexit
    //   13: aconst_null
    //   14: areturn
    //   15: aload_0
    //   16: aload_0
    //   17: getfield mSize : I
    //   20: iconst_1
    //   21: isub
    //   22: putfield mSize : I
    //   25: aload_0
    //   26: getfield mSize : I
    //   29: istore_1
    //   30: aload_0
    //   31: getfield mPool : [Ljava/lang/Object;
    //   34: iload_1
    //   35: aaload
    //   36: astore_2
    //   37: aload_0
    //   38: getfield mPool : [Ljava/lang/Object;
    //   41: iload_1
    //   42: aconst_null
    //   43: aastore
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_2
    //   47: areturn
    //   48: astore_2
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_2
    //   52: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	48	finally
    //   15	44	48	finally
  }
  
  public void clear() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_0
    //   3: istore_1
    //   4: iload_1
    //   5: aload_0
    //   6: getfield mSize : I
    //   9: if_icmpge -> 26
    //   12: aload_0
    //   13: getfield mPool : [Ljava/lang/Object;
    //   16: iload_1
    //   17: aconst_null
    //   18: aastore
    //   19: iload_1
    //   20: iconst_1
    //   21: iadd
    //   22: istore_1
    //   23: goto -> 4
    //   26: aload_0
    //   27: iconst_0
    //   28: putfield mSize : I
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: astore_2
    //   35: aload_0
    //   36: monitorexit
    //   37: goto -> 42
    //   40: aload_2
    //   41: athrow
    //   42: goto -> 40
    // Exception table:
    //   from	to	target	type
    //   4	19	34	finally
    //   26	31	34	finally
  }
  
  public boolean release(T paramT) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mSize : I
    //   6: istore_2
    //   7: aload_0
    //   8: getfield mPool : [Ljava/lang/Object;
    //   11: arraylength
    //   12: istore_3
    //   13: iload_2
    //   14: iload_3
    //   15: if_icmpne -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: iconst_0
    //   21: ireturn
    //   22: aload_0
    //   23: getfield mPool : [Ljava/lang/Object;
    //   26: aload_0
    //   27: getfield mSize : I
    //   30: aload_1
    //   31: aastore
    //   32: aload_0
    //   33: aload_0
    //   34: getfield mSize : I
    //   37: iconst_1
    //   38: iadd
    //   39: putfield mSize : I
    //   42: aload_0
    //   43: monitorexit
    //   44: iconst_1
    //   45: ireturn
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	46	finally
    //   22	42	46	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\ClearableSynchronizedPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */