package com.tt.miniapp.dec;

import java.nio.ByteBuffer;

final class Transform {
  private static final byte[] PREFIX_SUFFIX;
  
  private static final int[] PREFIX_SUFFIX_HEADS;
  
  private static final int[] TRANSFORMS = new int[363];
  
  static {
    PREFIX_SUFFIX = new byte[217];
    PREFIX_SUFFIX_HEADS = new int[51];
    unpackTransforms(PREFIX_SUFFIX, PREFIX_SUFFIX_HEADS, TRANSFORMS, "# #s #, #e #.# the #.com/#Â # of # and # in # to #\"#\">#\n#]# for # a # that #. # with #'# from # by #. The # on # as # is #ing #\n\t#:#ed #(# at #ly #=\"# of the #. This #,# not #er #al #='#ful #ive #less #est #ize #ous #", "     !! ! ,  *!  &!  \" !  ) *   * -  ! # !  #!*!  +  ,$ !  -  %  .  / #   0  1 .  \"   2  3!*   4%  ! # /   5  6  7  8 0  1 &   $   9 +   :  ;  < '  !=  >  ?! 4  @ 4  2  &   A *# (   B  C& ) %  ) !*# *-% A +! *.  D! %'  & E *6  F  G% ! *A *%  H! D  I!+!  J!+   K +- *4! A  L!*4  M  N +6  O!*% +.! K *G  P +%(  ! G *D +D  Q +# *K!*G!+D!+# +G +A +4!+% +K!+4!*D!+K!*K");
  }
  
  static int transformDictionaryWord(byte[] paramArrayOfbyte, int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3, int paramInt4) {
    // Byte code:
    //   0: iload #5
    //   2: iconst_3
    //   3: imul
    //   4: istore #5
    //   6: getstatic com/tt/miniapp/dec/Transform.PREFIX_SUFFIX_HEADS : [I
    //   9: astore #11
    //   11: getstatic com/tt/miniapp/dec/Transform.TRANSFORMS : [I
    //   14: astore #12
    //   16: aload #11
    //   18: aload #12
    //   20: iload #5
    //   22: iaload
    //   23: iaload
    //   24: istore #6
    //   26: aload #12
    //   28: iload #5
    //   30: iconst_1
    //   31: iadd
    //   32: iaload
    //   33: istore #10
    //   35: aload #11
    //   37: aload #12
    //   39: iload #5
    //   41: iconst_2
    //   42: iadd
    //   43: iaload
    //   44: iaload
    //   45: istore #9
    //   47: iload_1
    //   48: istore #5
    //   50: getstatic com/tt/miniapp/dec/Transform.PREFIX_SUFFIX : [B
    //   53: astore #11
    //   55: aload #11
    //   57: iload #6
    //   59: baload
    //   60: ifeq -> 87
    //   63: aload_0
    //   64: iload #5
    //   66: aload #11
    //   68: iload #6
    //   70: baload
    //   71: bastore
    //   72: iload #5
    //   74: iconst_1
    //   75: iadd
    //   76: istore #5
    //   78: iload #6
    //   80: iconst_1
    //   81: iadd
    //   82: istore #6
    //   84: goto -> 50
    //   87: iconst_0
    //   88: istore #8
    //   90: iload #10
    //   92: bipush #12
    //   94: if_icmplt -> 107
    //   97: iload #10
    //   99: bipush #11
    //   101: isub
    //   102: istore #6
    //   104: goto -> 110
    //   107: iconst_0
    //   108: istore #6
    //   110: iload #6
    //   112: istore #7
    //   114: iload #6
    //   116: iload #4
    //   118: if_icmple -> 125
    //   121: iload #4
    //   123: istore #7
    //   125: iload #8
    //   127: istore #6
    //   129: iload #10
    //   131: bipush #9
    //   133: if_icmpgt -> 140
    //   136: iload #10
    //   138: istore #6
    //   140: iload #4
    //   142: iload #7
    //   144: isub
    //   145: iload #6
    //   147: isub
    //   148: istore #6
    //   150: iload_3
    //   151: iload #7
    //   153: iadd
    //   154: istore_3
    //   155: iload #6
    //   157: istore #4
    //   159: iload #4
    //   161: ifle -> 192
    //   164: aload_0
    //   165: iload #5
    //   167: aload_2
    //   168: iload_3
    //   169: invokevirtual get : (I)B
    //   172: bastore
    //   173: iload #4
    //   175: iconst_1
    //   176: isub
    //   177: istore #4
    //   179: iload #5
    //   181: iconst_1
    //   182: iadd
    //   183: istore #5
    //   185: iload_3
    //   186: iconst_1
    //   187: iadd
    //   188: istore_3
    //   189: goto -> 159
    //   192: iload #10
    //   194: bipush #11
    //   196: if_icmpeq -> 214
    //   199: iload #5
    //   201: istore #7
    //   203: iload #9
    //   205: istore #8
    //   207: iload #10
    //   209: bipush #10
    //   211: if_icmpne -> 379
    //   214: iload #5
    //   216: iload #6
    //   218: isub
    //   219: istore #7
    //   221: iload #7
    //   223: istore #4
    //   225: iload #6
    //   227: istore_3
    //   228: iload #10
    //   230: bipush #10
    //   232: if_icmpne -> 241
    //   235: iconst_1
    //   236: istore_3
    //   237: iload #7
    //   239: istore #4
    //   241: iload #5
    //   243: istore #7
    //   245: iload #9
    //   247: istore #8
    //   249: iload_3
    //   250: ifle -> 379
    //   253: aload_0
    //   254: iload #4
    //   256: baload
    //   257: sipush #255
    //   260: iand
    //   261: istore #6
    //   263: iload #6
    //   265: sipush #192
    //   268: if_icmpge -> 310
    //   271: iload #6
    //   273: bipush #97
    //   275: if_icmplt -> 297
    //   278: iload #6
    //   280: bipush #122
    //   282: if_icmpgt -> 297
    //   285: aload_0
    //   286: iload #4
    //   288: aload_0
    //   289: iload #4
    //   291: baload
    //   292: bipush #32
    //   294: ixor
    //   295: i2b
    //   296: bastore
    //   297: iload #4
    //   299: iconst_1
    //   300: iadd
    //   301: istore #4
    //   303: iload_3
    //   304: iconst_1
    //   305: isub
    //   306: istore_3
    //   307: goto -> 241
    //   310: iload #6
    //   312: sipush #224
    //   315: if_icmpge -> 349
    //   318: iload #4
    //   320: iconst_1
    //   321: iadd
    //   322: istore #6
    //   324: aload_0
    //   325: iload #6
    //   327: aload_0
    //   328: iload #6
    //   330: baload
    //   331: bipush #32
    //   333: ixor
    //   334: i2b
    //   335: bastore
    //   336: iload #4
    //   338: iconst_2
    //   339: iadd
    //   340: istore #4
    //   342: iload_3
    //   343: iconst_2
    //   344: isub
    //   345: istore_3
    //   346: goto -> 241
    //   349: iload #4
    //   351: iconst_2
    //   352: iadd
    //   353: istore #6
    //   355: aload_0
    //   356: iload #6
    //   358: aload_0
    //   359: iload #6
    //   361: baload
    //   362: iconst_5
    //   363: ixor
    //   364: i2b
    //   365: bastore
    //   366: iload #4
    //   368: iconst_3
    //   369: iadd
    //   370: istore #4
    //   372: iload_3
    //   373: iconst_3
    //   374: isub
    //   375: istore_3
    //   376: goto -> 241
    //   379: getstatic com/tt/miniapp/dec/Transform.PREFIX_SUFFIX : [B
    //   382: astore_2
    //   383: aload_2
    //   384: iload #8
    //   386: baload
    //   387: ifeq -> 413
    //   390: aload_0
    //   391: iload #7
    //   393: aload_2
    //   394: iload #8
    //   396: baload
    //   397: bastore
    //   398: iload #7
    //   400: iconst_1
    //   401: iadd
    //   402: istore #7
    //   404: iload #8
    //   406: iconst_1
    //   407: iadd
    //   408: istore #8
    //   410: goto -> 379
    //   413: iload #7
    //   415: iload_1
    //   416: isub
    //   417: ireturn
  }
  
  private static void unpackTransforms(byte[] paramArrayOfbyte, int[] paramArrayOfint1, int[] paramArrayOfint2, String paramString1, String paramString2) {
    int k;
    int m = paramString1.length();
    byte b = 0;
    int i = 0;
    int j = 1;
    while (true) {
      k = b;
      if (i < m) {
        char c = paramString1.charAt(i);
        paramArrayOfbyte[i] = (byte)c;
        k = j;
        if (c == '#') {
          paramArrayOfint1[j] = i + 1;
          paramArrayOfbyte[i] = 0;
          k = j + 1;
        } 
        i++;
        j = k;
        continue;
      } 
      break;
    } 
    while (k < 363) {
      paramArrayOfint2[k] = paramString2.charAt(k) - 32;
      k++;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\Transform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */