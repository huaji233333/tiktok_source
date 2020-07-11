package com.bytedance.ies.bullet.kit.rn.internal.wrapper;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CallbackImpl;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.DynamicFromArray;
import com.facebook.react.bridge.JSInstance;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

final class b implements NativeModule.NativeMethod {
  public static final a<Callback> a;
  
  private static final a<Boolean> b = new a<Boolean>() {
    
    };
  
  private static final a<Double> c = new a<Double>() {
    
    };
  
  private static final a<Float> d = new a<Float>() {
    
    };
  
  private static final a<Integer> e = new a<Integer>() {
    
    };
  
  private static final a<String> f = new a<String>() {
    
    };
  
  private static final a<ReadableNativeArray> g = new a<ReadableNativeArray>() {
    
    };
  
  private static final a<Dynamic> h = new a<Dynamic>() {
    
    };
  
  private static final a<ReadableMap> i = new a<ReadableMap>() {
    
    };
  
  private static final a<Promise> j;
  
  private final String k;
  
  private final com.bytedance.ies.bullet.kit.rn.core.b l;
  
  private final Method m;
  
  private final Class[] n;
  
  private boolean o;
  
  private a[] p;
  
  private Object[] q;
  
  private int r;
  
  static {
    a = new a<Callback>() {
      
      };
    j = new a<Promise>() {
        public final int a() {
          return 2;
        }
      };
  }
  
  public b(com.bytedance.ies.bullet.kit.rn.core.b paramb, Method paramMethod, String paramString) {
    this.k = paramString;
    this.l = paramb;
    this.m = paramMethod;
    this.m.setAccessible(true);
    Class[] arrayOfClass = this.m.getParameterTypes();
    this.n = new Class[arrayOfClass.length];
    int i;
    for (i = 0; i < arrayOfClass.length; i++) {
      if (List.class.isAssignableFrom(arrayOfClass[i])) {
        this.n[i] = ReadableArray.class;
      } else if (Map.class.isAssignableFrom(arrayOfClass[i])) {
        this.n[i] = ReadableMap.class;
      } else if (com.bytedance.ies.bullet.kit.rn.core.a.class.isAssignableFrom(arrayOfClass[i])) {
        this.n[i] = Callback.class;
      } else {
        this.n[i] = arrayOfClass[i];
      } 
    } 
  }
  
  private int a() {
    a[] arrayOfA = this.p;
    int i = 0;
    if (arrayOfA == null)
      return 0; 
    int k = arrayOfA.length;
    int j = 0;
    while (i < k) {
      j += arrayOfA[i].a();
      i++;
    } 
    return j;
  }
  
  public final String getType() {
    return "async";
  }
  
  public final void invoke(JSInstance paramJSInstance, ReadableNativeArray paramReadableNativeArray) {
    // Byte code:
    //   0: aload_0
    //   1: getfield o : Z
    //   4: istore #7
    //   6: iconst_0
    //   7: istore #6
    //   9: iload #7
    //   11: ifne -> 366
    //   14: aload_0
    //   15: getfield o : Z
    //   18: istore #7
    //   20: iload #7
    //   22: ifne -> 366
    //   25: aload_0
    //   26: iconst_1
    //   27: putfield o : Z
    //   30: aload_0
    //   31: getfield n : [Ljava/lang/Class;
    //   34: astore #9
    //   36: aload #9
    //   38: arraylength
    //   39: anewarray com/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a
    //   42: astore #10
    //   44: iconst_0
    //   45: istore_3
    //   46: iload_3
    //   47: aload #9
    //   49: arraylength
    //   50: if_icmpge -> 340
    //   53: aload #9
    //   55: iload_3
    //   56: aaload
    //   57: astore #8
    //   59: aload #8
    //   61: ldc java/lang/Boolean
    //   63: if_acmpeq -> 320
    //   66: aload #8
    //   68: getstatic java/lang/Boolean.TYPE : Ljava/lang/Class;
    //   71: if_acmpne -> 77
    //   74: goto -> 320
    //   77: aload #8
    //   79: ldc java/lang/Integer
    //   81: if_acmpeq -> 310
    //   84: aload #8
    //   86: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
    //   89: if_acmpne -> 95
    //   92: goto -> 310
    //   95: aload #8
    //   97: ldc java/lang/Double
    //   99: if_acmpeq -> 300
    //   102: aload #8
    //   104: getstatic java/lang/Double.TYPE : Ljava/lang/Class;
    //   107: if_acmpne -> 113
    //   110: goto -> 300
    //   113: aload #8
    //   115: ldc java/lang/Float
    //   117: if_acmpeq -> 290
    //   120: aload #8
    //   122: getstatic java/lang/Float.TYPE : Ljava/lang/Class;
    //   125: if_acmpne -> 131
    //   128: goto -> 290
    //   131: aload #8
    //   133: ldc java/lang/String
    //   135: if_acmpne -> 148
    //   138: aload #10
    //   140: iload_3
    //   141: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.f : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   144: aastore
    //   145: goto -> 327
    //   148: aload #8
    //   150: ldc com/facebook/react/bridge/Callback
    //   152: if_acmpne -> 165
    //   155: aload #10
    //   157: iload_3
    //   158: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.a : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   161: aastore
    //   162: goto -> 327
    //   165: aload #8
    //   167: ldc com/facebook/react/bridge/Promise
    //   169: if_acmpne -> 204
    //   172: aload #10
    //   174: iload_3
    //   175: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.j : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   178: aastore
    //   179: iload_3
    //   180: aload #9
    //   182: arraylength
    //   183: iconst_1
    //   184: isub
    //   185: if_icmpne -> 856
    //   188: iconst_1
    //   189: istore #7
    //   191: goto -> 194
    //   194: iload #7
    //   196: ldc 'Promise must be used as last parameter only'
    //   198: invokestatic a : (ZLjava/lang/String;)V
    //   201: goto -> 327
    //   204: aload #8
    //   206: ldc com/facebook/react/bridge/ReadableMap
    //   208: if_acmpne -> 221
    //   211: aload #10
    //   213: iload_3
    //   214: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.i : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   217: aastore
    //   218: goto -> 327
    //   221: aload #8
    //   223: ldc com/facebook/react/bridge/ReadableArray
    //   225: if_acmpne -> 238
    //   228: aload #10
    //   230: iload_3
    //   231: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.g : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   234: aastore
    //   235: goto -> 327
    //   238: aload #8
    //   240: ldc com/facebook/react/bridge/Dynamic
    //   242: if_acmpne -> 255
    //   245: aload #10
    //   247: iload_3
    //   248: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.h : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   251: aastore
    //   252: goto -> 327
    //   255: new java/lang/StringBuilder
    //   258: dup
    //   259: ldc 'Got unknown argument class: '
    //   261: invokespecial <init> : (Ljava/lang/String;)V
    //   264: astore #9
    //   266: aload #9
    //   268: aload #8
    //   270: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   273: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: new java/lang/RuntimeException
    //   280: dup
    //   281: aload #9
    //   283: invokevirtual toString : ()Ljava/lang/String;
    //   286: invokespecial <init> : (Ljava/lang/String;)V
    //   289: athrow
    //   290: aload #10
    //   292: iload_3
    //   293: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.d : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   296: aastore
    //   297: goto -> 327
    //   300: aload #10
    //   302: iload_3
    //   303: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.c : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   306: aastore
    //   307: goto -> 327
    //   310: aload #10
    //   312: iload_3
    //   313: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.e : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   316: aastore
    //   317: goto -> 327
    //   320: aload #10
    //   322: iload_3
    //   323: getstatic com/bytedance/ies/bullet/kit/rn/internal/wrapper/b.b : Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   326: aastore
    //   327: iload_3
    //   328: aload #10
    //   330: iload_3
    //   331: aaload
    //   332: invokevirtual a : ()I
    //   335: iadd
    //   336: istore_3
    //   337: goto -> 46
    //   340: aload_0
    //   341: aload #10
    //   343: putfield p : [Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   346: aload_0
    //   347: aload_0
    //   348: getfield n : [Ljava/lang/Class;
    //   351: arraylength
    //   352: anewarray java/lang/Object
    //   355: putfield q : [Ljava/lang/Object;
    //   358: aload_0
    //   359: aload_0
    //   360: invokespecial a : ()I
    //   363: putfield r : I
    //   366: aload_0
    //   367: getfield q : [Ljava/lang/Object;
    //   370: ifnull -> 838
    //   373: aload_0
    //   374: getfield p : [Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   377: ifnull -> 838
    //   380: aload_0
    //   381: getfield r : I
    //   384: istore_3
    //   385: aload_2
    //   386: invokevirtual size : ()I
    //   389: istore #4
    //   391: iload_3
    //   392: iload #4
    //   394: if_icmpne -> 775
    //   397: iconst_0
    //   398: istore #4
    //   400: iconst_0
    //   401: istore_3
    //   402: iload #6
    //   404: istore #5
    //   406: iload #4
    //   408: aload_0
    //   409: getfield p : [Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   412: arraylength
    //   413: if_icmpge -> 462
    //   416: aload_0
    //   417: getfield q : [Ljava/lang/Object;
    //   420: iload #4
    //   422: aload_0
    //   423: getfield p : [Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   426: iload #4
    //   428: aaload
    //   429: aload_1
    //   430: aload_2
    //   431: iload_3
    //   432: invokevirtual a : (Lcom/facebook/react/bridge/JSInstance;Lcom/facebook/react/bridge/ReadableNativeArray;I)Ljava/lang/Object;
    //   435: aastore
    //   436: aload_0
    //   437: getfield p : [Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   440: iload #4
    //   442: aaload
    //   443: invokevirtual a : ()I
    //   446: istore #5
    //   448: iload_3
    //   449: iload #5
    //   451: iadd
    //   452: istore_3
    //   453: iload #4
    //   455: iconst_1
    //   456: iadd
    //   457: istore #4
    //   459: goto -> 402
    //   462: iload #5
    //   464: aload_0
    //   465: getfield n : [Ljava/lang/Class;
    //   468: arraylength
    //   469: if_icmpge -> 564
    //   472: aload_0
    //   473: getfield q : [Ljava/lang/Object;
    //   476: iload #5
    //   478: aaload
    //   479: astore_1
    //   480: aload_1
    //   481: instanceof com/facebook/react/bridge/ReadableArray
    //   484: ifeq -> 506
    //   487: aload_0
    //   488: getfield q : [Ljava/lang/Object;
    //   491: iload #5
    //   493: aload_1
    //   494: checkcast com/facebook/react/bridge/ReadableArray
    //   497: invokeinterface toArrayList : ()Ljava/util/ArrayList;
    //   502: aastore
    //   503: goto -> 555
    //   506: aload_1
    //   507: instanceof com/facebook/react/bridge/ReadableMap
    //   510: ifeq -> 532
    //   513: aload_0
    //   514: getfield q : [Ljava/lang/Object;
    //   517: iload #5
    //   519: aload_1
    //   520: checkcast com/facebook/react/bridge/ReadableMap
    //   523: invokeinterface toHashMap : ()Ljava/util/HashMap;
    //   528: aastore
    //   529: goto -> 555
    //   532: aload_1
    //   533: instanceof com/facebook/react/bridge/Callback
    //   536: ifeq -> 555
    //   539: aload_0
    //   540: getfield q : [Ljava/lang/Object;
    //   543: iload #5
    //   545: new com/bytedance/ies/bullet/kit/rn/internal/wrapper/b$3
    //   548: dup
    //   549: aload_0
    //   550: aload_1
    //   551: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b;Ljava/lang/Object;)V
    //   554: aastore
    //   555: iload #5
    //   557: iconst_1
    //   558: iadd
    //   559: istore #5
    //   561: goto -> 462
    //   564: aload_0
    //   565: getfield m : Ljava/lang/reflect/Method;
    //   568: aload_0
    //   569: getfield l : Lcom/bytedance/ies/bullet/kit/rn/core/b;
    //   572: aload_0
    //   573: getfield q : [Ljava/lang/Object;
    //   576: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   579: pop
    //   580: return
    //   581: astore_1
    //   582: goto -> 590
    //   585: astore_1
    //   586: goto -> 590
    //   589: astore_1
    //   590: new java/lang/StringBuilder
    //   593: dup
    //   594: ldc 'Could not invoke '
    //   596: invokespecial <init> : (Ljava/lang/String;)V
    //   599: astore_2
    //   600: aload_2
    //   601: aload_0
    //   602: getfield k : Ljava/lang/String;
    //   605: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   608: pop
    //   609: new java/lang/RuntimeException
    //   612: dup
    //   613: aload_2
    //   614: invokevirtual toString : ()Ljava/lang/String;
    //   617: aload_1
    //   618: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   621: athrow
    //   622: astore_2
    //   623: new java/lang/StringBuilder
    //   626: dup
    //   627: invokespecial <init> : ()V
    //   630: astore #8
    //   632: aload #8
    //   634: aload_2
    //   635: invokevirtual getMessage : ()Ljava/lang/String;
    //   638: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   641: pop
    //   642: aload #8
    //   644: ldc ' (constructing arguments for '
    //   646: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   649: pop
    //   650: aload #8
    //   652: aload_0
    //   653: getfield k : Ljava/lang/String;
    //   656: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   659: pop
    //   660: aload #8
    //   662: ldc ' at argument index '
    //   664: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   667: pop
    //   668: aload_0
    //   669: getfield p : [Lcom/bytedance/ies/bullet/kit/rn/internal/wrapper/b$a;
    //   672: iload #4
    //   674: aaload
    //   675: invokevirtual a : ()I
    //   678: istore #4
    //   680: iload #4
    //   682: iconst_1
    //   683: if_icmple -> 726
    //   686: new java/lang/StringBuilder
    //   689: dup
    //   690: invokespecial <init> : ()V
    //   693: astore_1
    //   694: aload_1
    //   695: iload_3
    //   696: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   699: pop
    //   700: aload_1
    //   701: ldc '-'
    //   703: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   706: pop
    //   707: aload_1
    //   708: iload_3
    //   709: iload #4
    //   711: iadd
    //   712: iconst_1
    //   713: isub
    //   714: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   717: pop
    //   718: aload_1
    //   719: invokevirtual toString : ()Ljava/lang/String;
    //   722: astore_1
    //   723: goto -> 745
    //   726: new java/lang/StringBuilder
    //   729: dup
    //   730: invokespecial <init> : ()V
    //   733: astore_1
    //   734: aload_1
    //   735: iload_3
    //   736: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   739: pop
    //   740: aload_1
    //   741: invokevirtual toString : ()Ljava/lang/String;
    //   744: astore_1
    //   745: aload #8
    //   747: aload_1
    //   748: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   751: pop
    //   752: aload #8
    //   754: ldc_w ')'
    //   757: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   760: pop
    //   761: new com/facebook/react/bridge/NativeArgumentsParseException
    //   764: dup
    //   765: aload #8
    //   767: invokevirtual toString : ()Ljava/lang/String;
    //   770: aload_2
    //   771: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   774: athrow
    //   775: new java/lang/StringBuilder
    //   778: dup
    //   779: invokespecial <init> : ()V
    //   782: astore_1
    //   783: aload_1
    //   784: aload_0
    //   785: getfield k : Ljava/lang/String;
    //   788: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   791: pop
    //   792: aload_1
    //   793: ldc_w ' got '
    //   796: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   799: pop
    //   800: aload_1
    //   801: aload_2
    //   802: invokevirtual size : ()I
    //   805: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   808: pop
    //   809: aload_1
    //   810: ldc_w ' arguments, expected '
    //   813: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   816: pop
    //   817: aload_1
    //   818: aload_0
    //   819: getfield r : I
    //   822: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   825: pop
    //   826: new com/facebook/react/bridge/NativeArgumentsParseException
    //   829: dup
    //   830: aload_1
    //   831: invokevirtual toString : ()Ljava/lang/String;
    //   834: invokespecial <init> : (Ljava/lang/String;)V
    //   837: athrow
    //   838: new java/lang/Error
    //   841: dup
    //   842: ldc_w 'processArguments failed'
    //   845: invokespecial <init> : (Ljava/lang/String;)V
    //   848: athrow
    //   849: astore_1
    //   850: return
    //   851: astore #8
    //   853: goto -> 366
    //   856: iconst_0
    //   857: istore #7
    //   859: goto -> 194
    // Exception table:
    //   from	to	target	type
    //   0	6	849	finally
    //   14	20	849	finally
    //   25	44	851	finally
    //   46	53	851	finally
    //   66	74	851	finally
    //   84	92	851	finally
    //   102	110	851	finally
    //   120	128	851	finally
    //   138	145	851	finally
    //   155	162	851	finally
    //   172	188	851	finally
    //   194	201	851	finally
    //   211	218	851	finally
    //   228	235	851	finally
    //   245	252	851	finally
    //   255	290	851	finally
    //   290	297	851	finally
    //   300	307	851	finally
    //   310	317	851	finally
    //   320	327	851	finally
    //   327	337	851	finally
    //   340	366	851	finally
    //   366	391	849	finally
    //   406	448	622	com/facebook/react/bridge/UnexpectedNativeTypeException
    //   406	448	849	finally
    //   462	503	849	finally
    //   506	529	849	finally
    //   532	555	849	finally
    //   564	580	589	java/lang/IllegalArgumentException
    //   564	580	585	java/lang/IllegalAccessException
    //   564	580	581	java/lang/reflect/InvocationTargetException
    //   564	580	849	finally
    //   590	622	849	finally
    //   623	680	849	finally
    //   686	723	849	finally
    //   726	745	849	finally
    //   745	775	849	finally
    //   775	838	849	finally
    //   838	849	849	finally
  }
  
  static abstract class a<T> {
    private a() {}
    
    public int a() {
      return 1;
    }
    
    public abstract T a(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\wrapper\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */