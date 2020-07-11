package com.tt.miniapphost.render.internal;

import d.f.b.l;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class InterfaceConverter {
  public static final InterfaceConverter INSTANCE = new InterfaceConverter();
  
  private static final Map<Class<?>, Class<?>> sInterfaceMap;
  
  private static final Map<Class<?>, Map<Method, Method>> sInterfaceMapCache = new HashMap<Class<?>, Map<Method, Method>>();
  
  static {
    sInterfaceMap = new HashMap<Class<?>, Class<?>>();
  }
  
  public final void checkAndCacheInterface(Class<?> paramClass1, Class<?> paramClass2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc 'fromInterface'
    //   5: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload_2
    //   9: ldc 'toInterface'
    //   11: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   14: getstatic com/tt/miniapphost/render/internal/InterfaceConverter.sInterfaceMapCache : Ljava/util/Map;
    //   17: aload_1
    //   18: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   23: istore #6
    //   25: iload #6
    //   27: ifeq -> 33
    //   30: aload_0
    //   31: monitorexit
    //   32: return
    //   33: aload_1
    //   34: invokevirtual isInterface : ()Z
    //   37: ifeq -> 363
    //   40: aload_2
    //   41: invokevirtual isInterface : ()Z
    //   44: ifeq -> 326
    //   47: getstatic com/tt/miniapphost/render/internal/InterfaceConverter.sInterfaceMap : Ljava/util/Map;
    //   50: aload_1
    //   51: aload_2
    //   52: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   57: pop
    //   58: new java/util/HashMap
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: checkcast java/util/Map
    //   68: astore #7
    //   70: aload_1
    //   71: invokevirtual getMethods : ()[Ljava/lang/reflect/Method;
    //   74: astore #8
    //   76: aload #8
    //   78: arraylength
    //   79: istore #5
    //   81: iconst_0
    //   82: istore #4
    //   84: iconst_0
    //   85: istore_3
    //   86: iload_3
    //   87: iload #5
    //   89: if_icmpge -> 183
    //   92: aload #8
    //   94: iload_3
    //   95: aaload
    //   96: astore #9
    //   98: aload #9
    //   100: ldc com/tt/miniapphost/render/internal/InterfaceConverter$ConvertToMethod
    //   102: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
    //   105: checkcast com/tt/miniapphost/render/internal/InterfaceConverter$ConvertToMethod
    //   108: astore #10
    //   110: aload #10
    //   112: ifnull -> 176
    //   115: aload #10
    //   117: invokeinterface value : ()Ljava/lang/String;
    //   122: astore #10
    //   124: aload #9
    //   126: ldc 'fromMethod'
    //   128: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   131: aload #9
    //   133: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   136: astore #11
    //   138: aload_2
    //   139: aload #10
    //   141: aload #11
    //   143: aload #11
    //   145: arraylength
    //   146: invokestatic copyOf : ([Ljava/lang/Object;I)[Ljava/lang/Object;
    //   149: checkcast [Ljava/lang/Class;
    //   152: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   155: astore #10
    //   157: aload #10
    //   159: ldc 'toMethod'
    //   161: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   164: aload #7
    //   166: aload #10
    //   168: aload #9
    //   170: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   175: pop
    //   176: iload_3
    //   177: iconst_1
    //   178: iadd
    //   179: istore_3
    //   180: goto -> 86
    //   183: aload_2
    //   184: invokevirtual getMethods : ()[Ljava/lang/reflect/Method;
    //   187: astore #8
    //   189: aload #8
    //   191: arraylength
    //   192: aload #7
    //   194: invokeinterface size : ()I
    //   199: if_icmple -> 311
    //   202: new java/util/ArrayList
    //   205: dup
    //   206: invokespecial <init> : ()V
    //   209: checkcast java/util/List
    //   212: astore_2
    //   213: aload #8
    //   215: arraylength
    //   216: istore #5
    //   218: iload #4
    //   220: istore_3
    //   221: iload_3
    //   222: iload #5
    //   224: if_icmpge -> 264
    //   227: aload #8
    //   229: iload_3
    //   230: aaload
    //   231: astore #9
    //   233: aload #7
    //   235: aload #9
    //   237: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   242: ifne -> 416
    //   245: aload #9
    //   247: ldc 'method'
    //   249: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   252: aload_2
    //   253: aload #9
    //   255: invokeinterface add : (Ljava/lang/Object;)Z
    //   260: pop
    //   261: goto -> 416
    //   264: new java/lang/StringBuilder
    //   267: dup
    //   268: invokespecial <init> : ()V
    //   271: astore #7
    //   273: aload #7
    //   275: aload_2
    //   276: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   279: pop
    //   280: aload #7
    //   282: ldc ', interface: '
    //   284: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload #7
    //   290: aload_1
    //   291: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   294: pop
    //   295: new java/lang/NoSuchMethodException
    //   298: dup
    //   299: aload #7
    //   301: invokevirtual toString : ()Ljava/lang/String;
    //   304: invokespecial <init> : (Ljava/lang/String;)V
    //   307: checkcast java/lang/Throwable
    //   310: athrow
    //   311: getstatic com/tt/miniapphost/render/internal/InterfaceConverter.sInterfaceMapCache : Ljava/util/Map;
    //   314: aload_2
    //   315: aload #7
    //   317: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   322: pop
    //   323: aload_0
    //   324: monitorexit
    //   325: return
    //   326: new java/lang/StringBuilder
    //   329: dup
    //   330: ldc '2nd param must be interface: '
    //   332: invokespecial <init> : (Ljava/lang/String;)V
    //   335: astore_1
    //   336: aload_1
    //   337: aload_2
    //   338: invokevirtual getName : ()Ljava/lang/String;
    //   341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   344: pop
    //   345: new java/lang/IllegalArgumentException
    //   348: dup
    //   349: aload_1
    //   350: invokevirtual toString : ()Ljava/lang/String;
    //   353: invokevirtual toString : ()Ljava/lang/String;
    //   356: invokespecial <init> : (Ljava/lang/String;)V
    //   359: checkcast java/lang/Throwable
    //   362: athrow
    //   363: new java/lang/StringBuilder
    //   366: dup
    //   367: ldc '1st param must be interface: '
    //   369: invokespecial <init> : (Ljava/lang/String;)V
    //   372: astore_2
    //   373: aload_2
    //   374: aload_1
    //   375: invokevirtual getName : ()Ljava/lang/String;
    //   378: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   381: pop
    //   382: new java/lang/IllegalArgumentException
    //   385: dup
    //   386: aload_2
    //   387: invokevirtual toString : ()Ljava/lang/String;
    //   390: invokevirtual toString : ()Ljava/lang/String;
    //   393: invokespecial <init> : (Ljava/lang/String;)V
    //   396: checkcast java/lang/Throwable
    //   399: athrow
    //   400: astore_1
    //   401: aload_0
    //   402: monitorexit
    //   403: goto -> 408
    //   406: aload_1
    //   407: athrow
    //   408: goto -> 406
    //   411: astore #9
    //   413: goto -> 176
    //   416: iload_3
    //   417: iconst_1
    //   418: iadd
    //   419: istore_3
    //   420: goto -> 221
    // Exception table:
    //   from	to	target	type
    //   2	25	400	finally
    //   33	81	400	finally
    //   98	110	400	finally
    //   115	176	411	java/lang/NoSuchMethodException
    //   115	176	400	finally
    //   183	218	400	finally
    //   233	261	400	finally
    //   264	311	400	finally
    //   311	323	400	finally
    //   326	363	400	finally
    //   363	400	400	finally
  }
  
  public final <T> Object convertInterface(T paramT, Class<? super T> paramClass) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: ldc 'fromInterface'
    //   5: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: getstatic com/tt/miniapphost/render/internal/InterfaceConverter.sInterfaceMap : Ljava/util/Map;
    //   11: aload_2
    //   12: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   17: checkcast java/lang/Class
    //   20: astore_3
    //   21: aload_3
    //   22: ifnull -> 82
    //   25: getstatic com/tt/miniapphost/render/internal/InterfaceConverter.sInterfaceMapCache : Ljava/util/Map;
    //   28: aload_3
    //   29: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   34: checkcast java/util/Map
    //   37: astore #4
    //   39: aload_3
    //   40: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   43: astore_2
    //   44: new com/tt/miniapphost/render/internal/InterfaceConverter$convertInterface$1
    //   47: dup
    //   48: aload #4
    //   50: aload_1
    //   51: invokespecial <init> : (Ljava/util/Map;Ljava/lang/Object;)V
    //   54: checkcast java/lang/reflect/InvocationHandler
    //   57: astore_1
    //   58: aload_2
    //   59: iconst_1
    //   60: anewarray java/lang/Class
    //   63: dup
    //   64: iconst_0
    //   65: aload_3
    //   66: aastore
    //   67: aload_1
    //   68: invokestatic newProxyInstance : (Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;
    //   71: astore_1
    //   72: aload_1
    //   73: ldc 'Proxy.newProxyInstance(t…oke(obj, *args)\\n        }'
    //   75: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   78: aload_0
    //   79: monitorexit
    //   80: aload_1
    //   81: areturn
    //   82: new java/lang/StringBuilder
    //   85: dup
    //   86: ldc 'checkAndCacheInterface is not called for: '
    //   88: invokespecial <init> : (Ljava/lang/String;)V
    //   91: astore_1
    //   92: aload_1
    //   93: aload_2
    //   94: invokevirtual getName : ()Ljava/lang/String;
    //   97: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: new java/lang/IllegalStateException
    //   104: dup
    //   105: aload_1
    //   106: invokevirtual toString : ()Ljava/lang/String;
    //   109: invokespecial <init> : (Ljava/lang/String;)V
    //   112: checkcast java/lang/Throwable
    //   115: athrow
    //   116: astore_1
    //   117: aload_0
    //   118: monitorexit
    //   119: aload_1
    //   120: athrow
    // Exception table:
    //   from	to	target	type
    //   2	21	116	finally
    //   25	78	116	finally
    //   82	116	116	finally
  }
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD})
  public static @interface ConvertToMethod {
    String value();
  }
  
  static final class InterfaceConverter$convertInterface$1 implements InvocationHandler {
    InterfaceConverter$convertInterface$1(Map param1Map, Object param1Object) {}
    
    public final Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) {
      param1Object = this.$convertMethodMap;
      if (param1Object == null)
        l.a(); 
      param1Object = param1Object.get(param1Method);
      return (param1Object != null) ? param1Object.invoke(this.$obj, Arrays.copyOf(param1ArrayOfObject, param1ArrayOfObject.length)) : null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\render\internal\InterfaceConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */