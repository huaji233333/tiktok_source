package com.tt.miniapp.net.httpdns;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetAddressUtil {
  public static InetAddress parseAddress(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    try {
      return InetAddress.getByName(paramString);
    } catch (UnknownHostException unknownHostException) {
      AppBrandLogger.e("tma_NetAddressUtil", new Object[] { unknownHostException.getMessage() });
      return null;
    } 
  }
  
  public static InetAddress parseAddress(String paramString1, String paramString2, int paramInt) {
    boolean bool = TextUtils.isEmpty(paramString1);
    InetAddress inetAddress = null;
    if (!bool) {
      if (TextUtils.isEmpty(paramString2))
        return null; 
      try {
        return InetAddress.getByAddress(paramString1, toByteArray(paramString2, paramInt));
      } catch (Exception exception) {
        return null;
      } 
    } 
    return inetAddress;
  }
  
  public static byte[] parseV4(String paramString) {
    byte[] arrayOfByte = new byte[4];
    int n = paramString.length();
    int k = 0;
    int m = 0;
    int j = 0;
    int i = 0;
    while (k < n) {
      int i1 = paramString.charAt(k);
      if (i1 >= 48 && i1 <= 57) {
        if (j == 3)
          return null; 
        if (j && !i)
          return null; 
        j++;
        i1 = i * 10 + i1 - 48;
        i = i1;
        if (i1 > 255)
          return null; 
      } else if (i1 == 46) {
        if (m == 3)
          return null; 
        if (j == 0)
          return null; 
        arrayOfByte[m] = (byte)i;
        m++;
        j = 0;
        i = 0;
      } else {
        return null;
      } 
      k++;
    } 
    if (m != 3)
      return null; 
    if (j == 0)
      return null; 
    arrayOfByte[m] = (byte)i;
    return arrayOfByte;
  }
  
  public static byte[] parseV6(String paramString) {
    // Byte code:
    //   0: bipush #16
    //   2: newarray byte
    //   4: astore #7
    //   6: iconst_m1
    //   7: istore #5
    //   9: aload_0
    //   10: ldc ':'
    //   12: iconst_m1
    //   13: invokevirtual split : (Ljava/lang/String;I)[Ljava/lang/String;
    //   16: astore_0
    //   17: aload_0
    //   18: arraylength
    //   19: iconst_1
    //   20: isub
    //   21: istore_1
    //   22: aload_0
    //   23: iconst_0
    //   24: aaload
    //   25: invokevirtual length : ()I
    //   28: ifne -> 53
    //   31: iload_1
    //   32: iconst_0
    //   33: iadd
    //   34: ifle -> 51
    //   37: aload_0
    //   38: iconst_1
    //   39: aaload
    //   40: invokevirtual length : ()I
    //   43: ifne -> 51
    //   46: iconst_1
    //   47: istore_2
    //   48: goto -> 55
    //   51: aconst_null
    //   52: areturn
    //   53: iconst_0
    //   54: istore_2
    //   55: iload_1
    //   56: istore_3
    //   57: aload_0
    //   58: iload_1
    //   59: aaload
    //   60: invokevirtual length : ()I
    //   63: ifne -> 92
    //   66: iload_1
    //   67: iload_2
    //   68: isub
    //   69: ifle -> 90
    //   72: aload_0
    //   73: iload_1
    //   74: iconst_1
    //   75: isub
    //   76: aaload
    //   77: invokevirtual length : ()I
    //   80: ifne -> 90
    //   83: iload_1
    //   84: iconst_1
    //   85: isub
    //   86: istore_3
    //   87: goto -> 92
    //   90: aconst_null
    //   91: areturn
    //   92: iload_3
    //   93: iload_2
    //   94: isub
    //   95: iconst_1
    //   96: iadd
    //   97: bipush #8
    //   99: if_icmple -> 104
    //   102: aconst_null
    //   103: areturn
    //   104: iconst_0
    //   105: istore_1
    //   106: iload_2
    //   107: istore #4
    //   109: iload #5
    //   111: istore_2
    //   112: iload_1
    //   113: istore #5
    //   115: iload #4
    //   117: iload_3
    //   118: if_icmpgt -> 316
    //   121: aload_0
    //   122: iload #4
    //   124: aaload
    //   125: invokevirtual length : ()I
    //   128: ifne -> 142
    //   131: iload_2
    //   132: iflt -> 137
    //   135: aconst_null
    //   136: areturn
    //   137: iload_1
    //   138: istore_2
    //   139: goto -> 305
    //   142: aload_0
    //   143: iload #4
    //   145: aaload
    //   146: bipush #46
    //   148: invokevirtual indexOf : (I)I
    //   151: iflt -> 214
    //   154: iload #4
    //   156: iload_3
    //   157: if_icmpge -> 162
    //   160: aconst_null
    //   161: areturn
    //   162: iload #4
    //   164: bipush #6
    //   166: if_icmple -> 171
    //   169: aconst_null
    //   170: areturn
    //   171: aload_0
    //   172: iload #4
    //   174: aaload
    //   175: iconst_1
    //   176: invokestatic toByteArray : (Ljava/lang/String;I)[B
    //   179: astore_0
    //   180: aload_0
    //   181: ifnonnull -> 186
    //   184: aconst_null
    //   185: areturn
    //   186: iconst_0
    //   187: istore_3
    //   188: iload_1
    //   189: istore #5
    //   191: iload_3
    //   192: iconst_4
    //   193: if_icmpge -> 316
    //   196: aload #7
    //   198: iload_1
    //   199: aload_0
    //   200: iload_3
    //   201: baload
    //   202: bastore
    //   203: iload_3
    //   204: iconst_1
    //   205: iadd
    //   206: istore_3
    //   207: iload_1
    //   208: iconst_1
    //   209: iadd
    //   210: istore_1
    //   211: goto -> 188
    //   214: iconst_0
    //   215: istore #5
    //   217: iload #5
    //   219: aload_0
    //   220: iload #4
    //   222: aaload
    //   223: invokevirtual length : ()I
    //   226: if_icmpge -> 248
    //   229: aload_0
    //   230: iload #4
    //   232: aaload
    //   233: iload #5
    //   235: invokevirtual charAt : (I)C
    //   238: bipush #16
    //   240: invokestatic digit : (CI)I
    //   243: ifge -> 377
    //   246: aconst_null
    //   247: areturn
    //   248: aload_0
    //   249: iload #4
    //   251: aaload
    //   252: bipush #16
    //   254: invokestatic parseInt : (Ljava/lang/String;I)I
    //   257: istore #5
    //   259: iload #5
    //   261: ldc 65535
    //   263: if_icmpgt -> 314
    //   266: iload #5
    //   268: ifge -> 273
    //   271: aconst_null
    //   272: areturn
    //   273: iload_1
    //   274: iconst_1
    //   275: iadd
    //   276: istore #6
    //   278: aload #7
    //   280: iload_1
    //   281: iload #5
    //   283: bipush #8
    //   285: iushr
    //   286: i2b
    //   287: bastore
    //   288: iload #6
    //   290: iconst_1
    //   291: iadd
    //   292: istore_1
    //   293: aload #7
    //   295: iload #6
    //   297: iload #5
    //   299: sipush #255
    //   302: iand
    //   303: i2b
    //   304: bastore
    //   305: iload #4
    //   307: iconst_1
    //   308: iadd
    //   309: istore #4
    //   311: goto -> 112
    //   314: aconst_null
    //   315: areturn
    //   316: iload #5
    //   318: bipush #16
    //   320: if_icmpge -> 329
    //   323: iload_2
    //   324: ifge -> 329
    //   327: aconst_null
    //   328: areturn
    //   329: iload_2
    //   330: iflt -> 371
    //   333: bipush #16
    //   335: iload #5
    //   337: isub
    //   338: iload_2
    //   339: iadd
    //   340: istore_1
    //   341: aload #7
    //   343: iload_2
    //   344: aload #7
    //   346: iload_1
    //   347: iload #5
    //   349: iload_2
    //   350: isub
    //   351: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   354: iload_2
    //   355: iload_1
    //   356: if_icmpge -> 371
    //   359: aload #7
    //   361: iload_2
    //   362: iconst_0
    //   363: bastore
    //   364: iload_2
    //   365: iconst_1
    //   366: iadd
    //   367: istore_2
    //   368: goto -> 354
    //   371: aload #7
    //   373: areturn
    //   374: astore_0
    //   375: aconst_null
    //   376: areturn
    //   377: iload #5
    //   379: iconst_1
    //   380: iadd
    //   381: istore #5
    //   383: goto -> 217
    // Exception table:
    //   from	to	target	type
    //   217	246	374	java/lang/NumberFormatException
    //   248	259	374	java/lang/NumberFormatException
  }
  
  public static byte[] toByteArray(String paramString, int paramInt) {
    if (paramInt == 1)
      return parseV4(paramString); 
    if (paramInt == 2)
      return parseV6(paramString); 
    throw new IllegalArgumentException("unknown address family");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\httpdns\NetAddressUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */