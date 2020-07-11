package com.graphic.RNCanvas;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class CanvasMethodWrapper {
  private static final ArgumentExtractor<Float> ARGUMENT_EXTRACTOR_FLOAT = new ArgumentExtractor<Float>() {
      public final Float extractArgument(Object[] param1ArrayOfObject, int param1Int) {
        return (param1ArrayOfObject[param1Int] == null) ? Float.valueOf(0.0F) : new Float(((Double)param1ArrayOfObject[param1Int]).doubleValue());
      }
    };
  
  private static final ArgumentExtractor<float[]> ARGUMENT_EXTRACTOR_FLOAT_LIST = new ArgumentExtractor<float[]>() {
      public final float[] extractArgument(Object[] param1ArrayOfObject, int param1Int) {
        ArrayList arrayList = (ArrayList)param1ArrayOfObject[param1Int];
        float[] arrayOfFloat = new float[arrayList.size()];
        for (param1Int = 0; param1Int < arrayOfFloat.length; param1Int++)
          arrayOfFloat[param1Int] = ((Double)arrayList.get(param1Int)).floatValue(); 
        return arrayOfFloat;
      }
    };
  
  private static final ArgumentExtractor<HashMap> ARGUMENT_EXTRACTOR_HASHMAP;
  
  private static final ArgumentExtractor<Integer> ARGUMENT_EXTRACTOR_INTEGER = new ArgumentExtractor<Integer>() {
      public final Integer extractArgument(Object[] param1ArrayOfObject, int param1Int) {
        return new Integer(((Double)param1ArrayOfObject[param1Int]).intValue());
      }
    };
  
  private static final ArgumentExtractor<int[]> ARGUMENT_EXTRACTOR_INTEGER_LIST = new ArgumentExtractor<int[]>() {
      public final int[] extractArgument(Object[] param1ArrayOfObject, int param1Int) {
        ArrayList arrayList = (ArrayList)param1ArrayOfObject[param1Int];
        int[] arrayOfInt = new int[arrayList.size()];
        for (param1Int = 0; param1Int < arrayOfInt.length; param1Int++)
          arrayOfInt[param1Int] = ((Double)arrayList.get(param1Int)).intValue(); 
        return arrayOfInt;
      }
    };
  
  private ArgumentExtractor[] mArgumentExtractors;
  
  private final Method mMethod;
  
  private String mMethodName;
  
  private final Class[] mParameterTypes;
  
  static {
    ARGUMENT_EXTRACTOR_HASHMAP = new ArgumentExtractor<HashMap>() {
        public final HashMap extractArgument(Object[] param1ArrayOfObject, int param1Int) {
          return (HashMap)param1ArrayOfObject[param1Int];
        }
      };
  }
  
  public CanvasMethodWrapper(Method paramMethod) {
    this.mMethod = paramMethod;
    this.mMethod.setAccessible(true);
    this.mParameterTypes = this.mMethod.getParameterTypes();
    processArguments();
  }
  
  private ArgumentExtractor[] buildArgumentExtractors(Class[] paramArrayOfClass) {
    ArgumentExtractor[] arrayOfArgumentExtractor = new ArgumentExtractor[paramArrayOfClass.length];
    for (int i = 0; i < paramArrayOfClass.length; i++) {
      Class<Float> clazz = paramArrayOfClass[i];
      if (clazz == Float.class || clazz == float.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_FLOAT;
      } else if (clazz == float[].class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_FLOAT_LIST;
      } else if (clazz == int.class || clazz == Integer.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_INTEGER;
      } else if (clazz == int[].class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_INTEGER_LIST;
      } else if (clazz == HashMap.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_HASHMAP;
      } else {
        arrayOfArgumentExtractor[i] = new ArgumentExtractor();
      } 
    } 
    return arrayOfArgumentExtractor;
  }
  
  private String buildName(Class[] paramArrayOfClass) {
    String str = this.mMethod.getName();
    for (int i = 0; i < paramArrayOfClass.length; i++) {
      String str2 = str;
      if (i == 0) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append(":");
        str2 = stringBuilder1.toString();
      } 
      Class clazz = paramArrayOfClass[i];
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str2);
      stringBuilder.append(paramTypeToString(clazz));
      str2 = stringBuilder.toString();
      String str1 = str2;
      if (i < paramArrayOfClass.length - 1) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str2);
        stringBuilder1.append(":");
        str = stringBuilder1.toString();
      } 
    } 
    return str;
  }
  
  private static String paramTypeToString(Class<boolean> paramClass) {
    return (paramClass == boolean.class) ? "boolean" : ((paramClass == Boolean.class) ? "Boolean" : ((paramClass == int.class) ? "int" : ((paramClass == int[].class) ? "int[]" : ((paramClass == Integer.class) ? "Integer" : ((paramClass == double.class) ? "double" : ((paramClass == Double.class) ? "Double" : ((paramClass == float.class) ? "float" : ((paramClass == float[].class) ? "float[]" : ((paramClass == Float.class) ? "Float" : ((paramClass == String.class) ? "String" : ((paramClass == HashMap.class) ? "HashMap" : "other")))))))))));
  }
  
  private void processArguments() {
    this.mMethodName = buildName(this.mParameterTypes);
    this.mArgumentExtractors = buildArgumentExtractors(this.mParameterTypes);
  }
  
  public String getName() {
    return this.mMethodName;
  }
  
  public void invoke(Object paramObject, Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_2
    //   1: arraylength
    //   2: anewarray java/lang/Object
    //   5: astore #4
    //   7: aload_0
    //   8: getfield mArgumentExtractors : [Lcom/graphic/RNCanvas/CanvasMethodWrapper$ArgumentExtractor;
    //   11: ifnull -> 160
    //   14: iconst_0
    //   15: istore_3
    //   16: aload_0
    //   17: getfield mArgumentExtractors : [Lcom/graphic/RNCanvas/CanvasMethodWrapper$ArgumentExtractor;
    //   20: astore #5
    //   22: iload_3
    //   23: aload #5
    //   25: arraylength
    //   26: if_icmpge -> 49
    //   29: aload #4
    //   31: iload_3
    //   32: aload #5
    //   34: iload_3
    //   35: aaload
    //   36: aload_2
    //   37: iload_3
    //   38: invokevirtual extractArgument : ([Ljava/lang/Object;I)Ljava/lang/Object;
    //   41: aastore
    //   42: iload_3
    //   43: iconst_1
    //   44: iadd
    //   45: istore_3
    //   46: goto -> 16
    //   49: aload_0
    //   50: getfield mMethod : Ljava/lang/reflect/Method;
    //   53: aload_1
    //   54: aload #4
    //   56: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   59: pop
    //   60: return
    //   61: astore_1
    //   62: new java/lang/StringBuilder
    //   65: dup
    //   66: ldc 'Could not invoke '
    //   68: invokespecial <init> : (Ljava/lang/String;)V
    //   71: astore_2
    //   72: aload_2
    //   73: aload_0
    //   74: getfield mMethodName : Ljava/lang/String;
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: new java/lang/RuntimeException
    //   84: dup
    //   85: aload_2
    //   86: invokevirtual toString : ()Ljava/lang/String;
    //   89: aload_1
    //   90: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   93: athrow
    //   94: astore_1
    //   95: new java/lang/StringBuilder
    //   98: dup
    //   99: ldc 'Could not invoke '
    //   101: invokespecial <init> : (Ljava/lang/String;)V
    //   104: astore_2
    //   105: aload_2
    //   106: aload_0
    //   107: getfield mMethodName : Ljava/lang/String;
    //   110: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: pop
    //   114: new java/lang/RuntimeException
    //   117: dup
    //   118: aload_2
    //   119: invokevirtual toString : ()Ljava/lang/String;
    //   122: aload_1
    //   123: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   126: athrow
    //   127: astore_1
    //   128: new java/lang/StringBuilder
    //   131: dup
    //   132: ldc 'Could not invoke '
    //   134: invokespecial <init> : (Ljava/lang/String;)V
    //   137: astore_2
    //   138: aload_2
    //   139: aload_0
    //   140: getfield mMethodName : Ljava/lang/String;
    //   143: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: pop
    //   147: new java/lang/RuntimeException
    //   150: dup
    //   151: aload_2
    //   152: invokevirtual toString : ()Ljava/lang/String;
    //   155: aload_1
    //   156: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   159: athrow
    //   160: new java/lang/Error
    //   163: dup
    //   164: ldc 'processArguments failed'
    //   166: invokespecial <init> : (Ljava/lang/String;)V
    //   169: astore_1
    //   170: goto -> 175
    //   173: aload_1
    //   174: athrow
    //   175: goto -> 173
    // Exception table:
    //   from	to	target	type
    //   49	60	127	java/lang/IllegalArgumentException
    //   49	60	94	java/lang/IllegalAccessException
    //   49	60	61	java/lang/reflect/InvocationTargetException
  }
  
  static class ArgumentExtractor<T> {
    private ArgumentExtractor() {}
    
    public T extractArgument(Object[] param1ArrayOfObject, int param1Int) {
      return (T)param1ArrayOfObject[param1Int];
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasMethodWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */