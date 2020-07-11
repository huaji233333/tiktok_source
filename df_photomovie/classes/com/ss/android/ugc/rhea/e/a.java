package com.ss.android.ugc.rhea.e;

import android.content.Context;
import java.io.File;
import java.text.SimpleDateFormat;

public final class a {
  public static File a;
  
  public static final SimpleDateFormat b;
  
  public static final a c = new a();
  
  static {
    b = new SimpleDateFormat("HH_mm_ss_yyyy_MM_dd");
  }
  
  public final void a(String paramString, Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc 'data'
    //   5: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload_2
    //   9: ifnonnull -> 15
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: getstatic com/ss/android/ugc/rhea/e/a.a : Ljava/io/File;
    //   18: astore_2
    //   19: iconst_1
    //   20: istore #5
    //   22: aload_2
    //   23: ifnonnull -> 29
    //   26: goto -> 62
    //   29: aload_2
    //   30: invokevirtual length : ()J
    //   33: lstore #6
    //   35: lload #6
    //   37: l2d
    //   38: dstore_3
    //   39: dload_3
    //   40: invokestatic isNaN : (D)Z
    //   43: pop
    //   44: dload_3
    //   45: ldc2_w 1048576.0
    //   48: ddiv
    //   49: ldc2_w 3.0
    //   52: dcmpl
    //   53: iflt -> 59
    //   56: goto -> 62
    //   59: iconst_0
    //   60: istore #5
    //   62: iload #5
    //   64: ifeq -> 70
    //   67: aload_0
    //   68: monitorexit
    //   69: return
    //   70: getstatic com/ss/android/ugc/rhea/e/a.a : Ljava/io/File;
    //   73: astore_2
    //   74: aload_2
    //   75: ifnull -> 123
    //   78: getstatic d/m/d.a : Ljava/nio/charset/Charset;
    //   81: astore #8
    //   83: aload_2
    //   84: ldc '$this$appendText'
    //   86: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   89: aload_1
    //   90: ldc 'text'
    //   92: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   95: aload #8
    //   97: ldc 'charset'
    //   99: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   102: aload_1
    //   103: aload #8
    //   105: invokevirtual getBytes : (Ljava/nio/charset/Charset;)[B
    //   108: astore_1
    //   109: aload_1
    //   110: ldc '(this as java.lang.String).getBytes(charset)'
    //   112: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   115: aload_2
    //   116: aload_1
    //   117: invokestatic a : (Ljava/io/File;[B)V
    //   120: aload_0
    //   121: monitorexit
    //   122: return
    //   123: aload_0
    //   124: monitorexit
    //   125: return
    //   126: astore_1
    //   127: aload_0
    //   128: monitorexit
    //   129: aload_1
    //   130: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	126	finally
    //   15	19	126	finally
    //   29	35	126	finally
    //   70	74	126	finally
    //   78	120	126	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_photomovie\classes.jar!\com\ss\androi\\ugc\rhea\e\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */