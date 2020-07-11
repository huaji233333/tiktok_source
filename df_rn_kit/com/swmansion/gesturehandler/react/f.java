package com.swmansion.gesturehandler.react;

import android.util.SparseArray;
import android.view.View;
import com.swmansion.gesturehandler.b;
import com.swmansion.gesturehandler.e;
import java.util.ArrayList;

public final class f implements e {
  private final SparseArray<b> a = new SparseArray();
  
  private final SparseArray<Integer> b = new SparseArray();
  
  private final SparseArray<ArrayList<b>> c = new SparseArray();
  
  private void a(int paramInt, b paramb) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield b : Landroid/util/SparseArray;
    //   6: aload_2
    //   7: getfield e : I
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: ifnonnull -> 83
    //   16: aload_0
    //   17: getfield b : Landroid/util/SparseArray;
    //   20: aload_2
    //   21: getfield e : I
    //   24: iload_1
    //   25: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   28: invokevirtual put : (ILjava/lang/Object;)V
    //   31: aload_0
    //   32: getfield c : Landroid/util/SparseArray;
    //   35: iload_1
    //   36: invokevirtual get : (I)Ljava/lang/Object;
    //   39: checkcast java/util/ArrayList
    //   42: astore_3
    //   43: aload_3
    //   44: ifnonnull -> 74
    //   47: new java/util/ArrayList
    //   50: dup
    //   51: iconst_1
    //   52: invokespecial <init> : (I)V
    //   55: astore_3
    //   56: aload_3
    //   57: aload_2
    //   58: invokevirtual add : (Ljava/lang/Object;)Z
    //   61: pop
    //   62: aload_0
    //   63: getfield c : Landroid/util/SparseArray;
    //   66: iload_1
    //   67: aload_3
    //   68: invokevirtual put : (ILjava/lang/Object;)V
    //   71: aload_0
    //   72: monitorexit
    //   73: return
    //   74: aload_3
    //   75: aload_2
    //   76: invokevirtual add : (Ljava/lang/Object;)Z
    //   79: pop
    //   80: aload_0
    //   81: monitorexit
    //   82: return
    //   83: new java/lang/StringBuilder
    //   86: dup
    //   87: ldc 'Handler '
    //   89: invokespecial <init> : (Ljava/lang/String;)V
    //   92: astore_3
    //   93: aload_3
    //   94: aload_2
    //   95: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: aload_3
    //   100: ldc ' already attached'
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: new java/lang/IllegalStateException
    //   109: dup
    //   110: aload_3
    //   111: invokevirtual toString : ()Ljava/lang/String;
    //   114: invokespecial <init> : (Ljava/lang/String;)V
    //   117: athrow
    //   118: astore_2
    //   119: aload_0
    //   120: monitorexit
    //   121: aload_2
    //   122: athrow
    // Exception table:
    //   from	to	target	type
    //   2	43	118	finally
    //   47	71	118	finally
    //   74	80	118	finally
    //   83	118	118	finally
  }
  
  private void b(b paramb) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield b : Landroid/util/SparseArray;
    //   6: aload_1
    //   7: getfield e : I
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: checkcast java/lang/Integer
    //   16: astore_2
    //   17: aload_2
    //   18: ifnull -> 75
    //   21: aload_0
    //   22: getfield b : Landroid/util/SparseArray;
    //   25: aload_1
    //   26: getfield e : I
    //   29: invokevirtual remove : (I)V
    //   32: aload_0
    //   33: getfield c : Landroid/util/SparseArray;
    //   36: aload_2
    //   37: invokevirtual intValue : ()I
    //   40: invokevirtual get : (I)Ljava/lang/Object;
    //   43: checkcast java/util/ArrayList
    //   46: astore_3
    //   47: aload_3
    //   48: ifnull -> 75
    //   51: aload_3
    //   52: aload_1
    //   53: invokevirtual remove : (Ljava/lang/Object;)Z
    //   56: pop
    //   57: aload_3
    //   58: invokevirtual size : ()I
    //   61: ifne -> 75
    //   64: aload_0
    //   65: getfield c : Landroid/util/SparseArray;
    //   68: aload_2
    //   69: invokevirtual intValue : ()I
    //   72: invokevirtual remove : (I)V
    //   75: aload_1
    //   76: getfield f : Landroid/view/View;
    //   79: ifnull -> 86
    //   82: aload_1
    //   83: invokevirtual c : ()V
    //   86: aload_0
    //   87: monitorexit
    //   88: return
    //   89: astore_1
    //   90: aload_0
    //   91: monitorexit
    //   92: aload_1
    //   93: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	89	finally
    //   21	47	89	finally
    //   51	75	89	finally
    //   75	86	89	finally
  }
  
  private ArrayList<b> c(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield c : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast java/util/ArrayList
    //   13: astore_2
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_2
    //   17: areturn
    //   18: astore_2
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_2
    //   22: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	18	finally
  }
  
  public final b a(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield a : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/swmansion/gesturehandler/b
    //   13: astore_2
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_2
    //   17: areturn
    //   18: astore_2
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_2
    //   22: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	18	finally
  }
  
  public final ArrayList<b> a(View paramView) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual getId : ()I
    //   7: invokespecial c : (I)Ljava/util/ArrayList;
    //   10: astore_1
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_1
    //   14: areturn
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	15	finally
  }
  
  public final void a() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield a : Landroid/util/SparseArray;
    //   6: invokevirtual clear : ()V
    //   9: aload_0
    //   10: getfield b : Landroid/util/SparseArray;
    //   13: invokevirtual clear : ()V
    //   16: aload_0
    //   17: getfield c : Landroid/util/SparseArray;
    //   20: invokevirtual clear : ()V
    //   23: aload_0
    //   24: monitorexit
    //   25: return
    //   26: astore_1
    //   27: aload_0
    //   28: monitorexit
    //   29: aload_1
    //   30: athrow
    // Exception table:
    //   from	to	target	type
    //   2	23	26	finally
  }
  
  public final void a(b paramb) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield a : Landroid/util/SparseArray;
    //   6: aload_1
    //   7: getfield e : I
    //   10: aload_1
    //   11: invokevirtual put : (ILjava/lang/Object;)V
    //   14: aload_0
    //   15: monitorexit
    //   16: return
    //   17: astore_1
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_1
    //   21: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	17	finally
  }
  
  public final boolean a(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield a : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/swmansion/gesturehandler/b
    //   13: astore_3
    //   14: aload_3
    //   15: ifnull -> 33
    //   18: aload_0
    //   19: aload_3
    //   20: invokespecial b : (Lcom/swmansion/gesturehandler/b;)V
    //   23: aload_0
    //   24: iload_2
    //   25: aload_3
    //   26: invokespecial a : (ILcom/swmansion/gesturehandler/b;)V
    //   29: aload_0
    //   30: monitorexit
    //   31: iconst_1
    //   32: ireturn
    //   33: aload_0
    //   34: monitorexit
    //   35: iconst_0
    //   36: ireturn
    //   37: astore_3
    //   38: aload_0
    //   39: monitorexit
    //   40: aload_3
    //   41: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	37	finally
    //   18	29	37	finally
  }
  
  public final void b(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield a : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast com/swmansion/gesturehandler/b
    //   13: astore_2
    //   14: aload_2
    //   15: ifnull -> 31
    //   18: aload_0
    //   19: aload_2
    //   20: invokespecial b : (Lcom/swmansion/gesturehandler/b;)V
    //   23: aload_0
    //   24: getfield a : Landroid/util/SparseArray;
    //   27: iload_1
    //   28: invokevirtual remove : (I)V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: astore_2
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_2
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	34	finally
    //   18	31	34	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */