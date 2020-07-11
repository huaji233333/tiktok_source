package com.tt.miniapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class CommandUtil {
  public static List<String> executeShellCommand(String paramString) {
    // Byte code:
    //   0: aconst_null
    //   1: astore #5
    //   3: invokestatic getRuntime : ()Ljava/lang/Runtime;
    //   6: aload_0
    //   7: invokevirtual exec : (Ljava/lang/String;)Ljava/lang/Process;
    //   10: astore_0
    //   11: new java/io/DataOutputStream
    //   14: dup
    //   15: aload_0
    //   16: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   19: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   22: astore_3
    //   23: new java/io/BufferedReader
    //   26: dup
    //   27: new java/io/InputStreamReader
    //   30: dup
    //   31: aload_0
    //   32: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   35: invokespecial <init> : (Ljava/io/InputStream;)V
    //   38: invokespecial <init> : (Ljava/io/Reader;)V
    //   41: astore #6
    //   43: new java/io/BufferedReader
    //   46: dup
    //   47: new java/io/InputStreamReader
    //   50: dup
    //   51: aload_0
    //   52: invokevirtual getErrorStream : ()Ljava/io/InputStream;
    //   55: invokespecial <init> : (Ljava/io/InputStream;)V
    //   58: invokespecial <init> : (Ljava/io/Reader;)V
    //   61: astore #7
    //   63: aload_3
    //   64: ldc 'exit\\n'
    //   66: invokevirtual writeBytes : (Ljava/lang/String;)V
    //   69: aload_3
    //   70: invokevirtual flush : ()V
    //   73: aload #6
    //   75: invokestatic readOSMessage : (Ljava/io/BufferedReader;)Ljava/lang/String;
    //   78: astore #4
    //   80: aload #7
    //   82: invokestatic readOSMessage : (Ljava/io/BufferedReader;)Ljava/lang/String;
    //   85: astore #5
    //   87: aload_0
    //   88: invokevirtual waitFor : ()I
    //   91: istore_1
    //   92: new java/util/ArrayList
    //   95: dup
    //   96: invokespecial <init> : ()V
    //   99: astore_2
    //   100: aload_2
    //   101: iload_1
    //   102: invokestatic valueOf : (I)Ljava/lang/String;
    //   105: invokeinterface add : (Ljava/lang/Object;)Z
    //   110: pop
    //   111: aload_2
    //   112: aload #4
    //   114: invokeinterface add : (Ljava/lang/Object;)Z
    //   119: pop
    //   120: aload_2
    //   121: aload #5
    //   123: invokeinterface add : (Ljava/lang/Object;)Z
    //   128: pop
    //   129: aload_3
    //   130: invokevirtual close : ()V
    //   133: aload #6
    //   135: invokevirtual close : ()V
    //   138: aload #7
    //   140: invokevirtual close : ()V
    //   143: goto -> 146
    //   146: aload_2
    //   147: astore_3
    //   148: aload_0
    //   149: ifnull -> 379
    //   152: aload_0
    //   153: invokevirtual destroy : ()V
    //   156: aload_2
    //   157: areturn
    //   158: astore_2
    //   159: aload_3
    //   160: astore #5
    //   162: aload #6
    //   164: astore #4
    //   166: aload #7
    //   168: astore_3
    //   169: goto -> 273
    //   172: aconst_null
    //   173: astore_2
    //   174: aload #7
    //   176: astore #4
    //   178: goto -> 204
    //   181: astore_2
    //   182: aconst_null
    //   183: astore #7
    //   185: aload_3
    //   186: astore #5
    //   188: aload #6
    //   190: astore #4
    //   192: aload #7
    //   194: astore_3
    //   195: goto -> 273
    //   198: aconst_null
    //   199: astore #4
    //   201: aload #4
    //   203: astore_2
    //   204: aload #6
    //   206: astore #5
    //   208: aload_3
    //   209: astore #6
    //   211: aload #4
    //   213: astore_3
    //   214: goto -> 333
    //   217: astore_2
    //   218: aconst_null
    //   219: astore #6
    //   221: aload #6
    //   223: astore #4
    //   225: aload_3
    //   226: astore #5
    //   228: aload #6
    //   230: astore_3
    //   231: goto -> 273
    //   234: aconst_null
    //   235: astore_2
    //   236: aload_3
    //   237: astore #4
    //   239: aload_2
    //   240: astore_3
    //   241: goto -> 327
    //   244: astore_2
    //   245: aconst_null
    //   246: astore #4
    //   248: aload #4
    //   250: astore_3
    //   251: aload_3
    //   252: astore #5
    //   254: goto -> 273
    //   257: goto -> 322
    //   260: astore_2
    //   261: aconst_null
    //   262: astore #5
    //   264: aload #5
    //   266: astore #4
    //   268: aload #4
    //   270: astore_3
    //   271: aload_3
    //   272: astore_0
    //   273: aload #5
    //   275: ifnull -> 286
    //   278: aload #5
    //   280: invokevirtual close : ()V
    //   283: goto -> 286
    //   286: aload #4
    //   288: ifnull -> 299
    //   291: aload #4
    //   293: invokevirtual close : ()V
    //   296: goto -> 299
    //   299: aload_3
    //   300: ifnull -> 310
    //   303: aload_3
    //   304: invokevirtual close : ()V
    //   307: goto -> 310
    //   310: aload_0
    //   311: ifnull -> 318
    //   314: aload_0
    //   315: invokevirtual destroy : ()V
    //   318: aload_2
    //   319: athrow
    //   320: aconst_null
    //   321: astore_0
    //   322: aconst_null
    //   323: astore #4
    //   325: aconst_null
    //   326: astore_3
    //   327: aload_3
    //   328: astore_2
    //   329: aload #4
    //   331: astore #6
    //   333: aload #6
    //   335: ifnull -> 346
    //   338: aload #6
    //   340: invokevirtual close : ()V
    //   343: goto -> 346
    //   346: aload #5
    //   348: ifnull -> 359
    //   351: aload #5
    //   353: invokevirtual close : ()V
    //   356: goto -> 359
    //   359: aload_3
    //   360: ifnull -> 370
    //   363: aload_3
    //   364: invokevirtual close : ()V
    //   367: goto -> 370
    //   370: aload_2
    //   371: astore_3
    //   372: aload_0
    //   373: ifnull -> 379
    //   376: goto -> 152
    //   379: aload_3
    //   380: areturn
    //   381: astore_0
    //   382: goto -> 320
    //   385: astore_2
    //   386: goto -> 257
    //   389: astore_2
    //   390: goto -> 234
    //   393: astore_2
    //   394: goto -> 198
    //   397: astore_2
    //   398: goto -> 172
    //   401: astore #4
    //   403: aload #7
    //   405: astore #4
    //   407: goto -> 204
    //   410: astore_3
    //   411: goto -> 133
    //   414: astore_3
    //   415: goto -> 138
    //   418: astore_3
    //   419: goto -> 146
    //   422: astore #5
    //   424: goto -> 286
    //   427: astore #4
    //   429: goto -> 299
    //   432: astore_3
    //   433: goto -> 310
    //   436: astore #4
    //   438: goto -> 346
    //   441: astore #4
    //   443: goto -> 359
    //   446: astore_3
    //   447: goto -> 370
    // Exception table:
    //   from	to	target	type
    //   3	11	381	java/io/IOException
    //   3	11	381	java/lang/InterruptedException
    //   3	11	260	finally
    //   11	23	385	java/io/IOException
    //   11	23	385	java/lang/InterruptedException
    //   11	23	244	finally
    //   23	43	389	java/io/IOException
    //   23	43	389	java/lang/InterruptedException
    //   23	43	217	finally
    //   43	63	393	java/io/IOException
    //   43	63	393	java/lang/InterruptedException
    //   43	63	181	finally
    //   63	100	397	java/io/IOException
    //   63	100	397	java/lang/InterruptedException
    //   63	100	158	finally
    //   100	129	401	java/io/IOException
    //   100	129	401	java/lang/InterruptedException
    //   100	129	158	finally
    //   129	133	410	java/io/IOException
    //   133	138	414	java/io/IOException
    //   138	143	418	java/io/IOException
    //   278	283	422	java/io/IOException
    //   291	296	427	java/io/IOException
    //   303	307	432	java/io/IOException
    //   338	343	436	java/io/IOException
    //   351	356	441	java/io/IOException
    //   363	367	446	java/io/IOException
  }
  
  private static String readOSMessage(BufferedReader paramBufferedReader) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    while (true) {
      String str = paramBufferedReader.readLine();
      if (str != null) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder1 = new StringBuilder("lineString : ");
        stringBuilder1.append(str);
        printStream.println(stringBuilder1.toString());
        stringBuilder.append(str);
        stringBuilder.append("\n");
        continue;
      } 
      return stringBuilder.toString();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\CommandUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */