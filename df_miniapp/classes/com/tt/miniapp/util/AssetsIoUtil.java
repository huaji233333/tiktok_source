package com.tt.miniapp.util;

import android.content.Context;
import com.tt.miniapphost.AppBrandLogger;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AssetsIoUtil {
  public static String getFromAssets(Context paramContext, String paramString) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramContext.getResources().getAssets().open(paramString)));
      String str = "";
      while (true) {
        String str1 = bufferedReader.readLine();
        if (str1 != null) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str);
          stringBuilder.append(str1);
          str = stringBuilder.toString();
          continue;
        } 
        return str;
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "AssetsIoUtil", exception.getStackTrace());
      return "";
    } 
  }
  
  public static byte[] getFromAssetsByte(Context paramContext, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   4: invokevirtual getAssets : ()Landroid/content/res/AssetManager;
    //   7: aload_1
    //   8: invokevirtual open : (Ljava/lang/String;)Ljava/io/InputStream;
    //   11: astore_1
    //   12: aload_1
    //   13: astore_0
    //   14: aload_1
    //   15: invokevirtual available : ()I
    //   18: newarray byte
    //   20: astore_2
    //   21: aload_1
    //   22: astore_0
    //   23: aload_1
    //   24: aload_2
    //   25: invokevirtual read : ([B)I
    //   28: pop
    //   29: aload_1
    //   30: ifnull -> 51
    //   33: aload_1
    //   34: invokevirtual close : ()V
    //   37: aload_2
    //   38: areturn
    //   39: astore_0
    //   40: bipush #6
    //   42: ldc 'AssetsIoUtil'
    //   44: aload_0
    //   45: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   48: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   51: aload_2
    //   52: areturn
    //   53: astore_2
    //   54: goto -> 66
    //   57: astore_1
    //   58: aconst_null
    //   59: astore_0
    //   60: goto -> 104
    //   63: astore_2
    //   64: aconst_null
    //   65: astore_1
    //   66: aload_1
    //   67: astore_0
    //   68: bipush #6
    //   70: ldc 'AssetsIoUtil'
    //   72: aload_2
    //   73: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   76: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   79: aload_1
    //   80: ifnull -> 101
    //   83: aload_1
    //   84: invokevirtual close : ()V
    //   87: aconst_null
    //   88: areturn
    //   89: astore_0
    //   90: bipush #6
    //   92: ldc 'AssetsIoUtil'
    //   94: aload_0
    //   95: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   98: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   101: aconst_null
    //   102: areturn
    //   103: astore_1
    //   104: aload_0
    //   105: ifnull -> 127
    //   108: aload_0
    //   109: invokevirtual close : ()V
    //   112: goto -> 127
    //   115: astore_0
    //   116: bipush #6
    //   118: ldc 'AssetsIoUtil'
    //   120: aload_0
    //   121: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   124: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   127: aload_1
    //   128: athrow
    // Exception table:
    //   from	to	target	type
    //   0	12	63	java/lang/Exception
    //   0	12	57	finally
    //   14	21	53	java/lang/Exception
    //   14	21	103	finally
    //   23	29	53	java/lang/Exception
    //   23	29	103	finally
    //   33	37	39	java/lang/Exception
    //   68	79	103	finally
    //   83	87	89	java/lang/Exception
    //   108	112	115	java/lang/Exception
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\AssetsIoUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */