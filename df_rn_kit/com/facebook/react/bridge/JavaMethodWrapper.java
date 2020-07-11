package com.facebook.react.bridge;

import com.facebook.i.a.a;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaMethodWrapper implements NativeModule.NativeMethod {
  private static final ArgumentExtractor<ReadableNativeArray> ARGUMENT_EXTRACTOR_ARRAY;
  
  private static final ArgumentExtractor<Boolean> ARGUMENT_EXTRACTOR_BOOLEAN = new ArgumentExtractor<Boolean>() {
      public final Boolean extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
        return Boolean.valueOf(param1ReadableNativeArray.getBoolean(param1Int));
      }
    };
  
  public static final ArgumentExtractor<Callback> ARGUMENT_EXTRACTOR_CALLBACK;
  
  private static final ArgumentExtractor<Double> ARGUMENT_EXTRACTOR_DOUBLE = new ArgumentExtractor<Double>() {
      public final Double extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
        return Double.valueOf(param1ReadableNativeArray.getDouble(param1Int));
      }
    };
  
  private static final ArgumentExtractor<Dynamic> ARGUMENT_EXTRACTOR_DYNAMIC;
  
  private static final ArgumentExtractor<Float> ARGUMENT_EXTRACTOR_FLOAT = new ArgumentExtractor<Float>() {
      public final Float extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
        return Float.valueOf((float)param1ReadableNativeArray.getDouble(param1Int));
      }
    };
  
  private static final ArgumentExtractor<Integer> ARGUMENT_EXTRACTOR_INTEGER = new ArgumentExtractor<Integer>() {
      public final Integer extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
        return Integer.valueOf((int)param1ReadableNativeArray.getDouble(param1Int));
      }
    };
  
  private static final ArgumentExtractor<ReadableMap> ARGUMENT_EXTRACTOR_MAP;
  
  private static final ArgumentExtractor<Promise> ARGUMENT_EXTRACTOR_PROMISE;
  
  private static final ArgumentExtractor<String> ARGUMENT_EXTRACTOR_STRING = new ArgumentExtractor<String>() {
      public final String extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
        return param1ReadableNativeArray.getString(param1Int);
      }
    };
  
  private static final boolean DEBUG;
  
  private ArgumentExtractor[] mArgumentExtractors;
  
  private Object[] mArguments;
  
  private boolean mArgumentsProcessed;
  
  private int mJSArgumentsNeeded;
  
  private final Method mMethod;
  
  private final JavaModuleWrapper mModuleWrapper;
  
  private final int mParamLength;
  
  private final Class[] mParameterTypes;
  
  private String mSignature;
  
  private String mType = "async";
  
  static {
    ARGUMENT_EXTRACTOR_ARRAY = new ArgumentExtractor<ReadableNativeArray>() {
        public final ReadableNativeArray extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
          return param1ReadableNativeArray.getArray(param1Int);
        }
      };
    ARGUMENT_EXTRACTOR_DYNAMIC = new ArgumentExtractor<Dynamic>() {
        public final Dynamic extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
          return DynamicFromArray.create(param1ReadableNativeArray, param1Int);
        }
      };
    ARGUMENT_EXTRACTOR_MAP = new ArgumentExtractor<ReadableMap>() {
        public final ReadableMap extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
          return param1ReadableNativeArray.getMap(param1Int);
        }
      };
    ARGUMENT_EXTRACTOR_CALLBACK = new ArgumentExtractor<Callback>() {
        public final Callback extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
          return param1ReadableNativeArray.isNull(param1Int) ? null : new CallbackImpl(param1JSInstance, (int)param1ReadableNativeArray.getDouble(param1Int));
        }
      };
    ARGUMENT_EXTRACTOR_PROMISE = new ArgumentExtractor<Promise>() {
        public final Promise extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int) {
          return new PromiseImpl(JavaMethodWrapper.ARGUMENT_EXTRACTOR_CALLBACK.extractArgument(param1JSInstance, param1ReadableNativeArray, param1Int), JavaMethodWrapper.ARGUMENT_EXTRACTOR_CALLBACK.extractArgument(param1JSInstance, param1ReadableNativeArray, param1Int + 1));
        }
        
        public final int getJSArgumentsNeeded() {
          return 2;
        }
      };
    DEBUG = false;
  }
  
  public JavaMethodWrapper(JavaModuleWrapper paramJavaModuleWrapper, Method paramMethod, boolean paramBoolean) {
    this.mModuleWrapper = paramJavaModuleWrapper;
    this.mMethod = paramMethod;
    this.mMethod.setAccessible(true);
    this.mParameterTypes = this.mMethod.getParameterTypes();
    Class[] arrayOfClass = this.mParameterTypes;
    this.mParamLength = arrayOfClass.length;
    if (paramBoolean) {
      this.mType = "sync";
      return;
    } 
    int i = this.mParamLength;
    if (i > 0 && arrayOfClass[i - 1] == Promise.class)
      this.mType = "promise"; 
  }
  
  private ArgumentExtractor[] buildArgumentExtractors(Class[] paramArrayOfClass) {
    ArgumentExtractor[] arrayOfArgumentExtractor = new ArgumentExtractor[paramArrayOfClass.length];
    for (int i = 0; i < paramArrayOfClass.length; i += arrayOfArgumentExtractor[i].getJSArgumentsNeeded()) {
      Class<Boolean> clazz = paramArrayOfClass[i];
      if (clazz == Boolean.class || clazz == boolean.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_BOOLEAN;
      } else if (clazz == Integer.class || clazz == int.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_INTEGER;
      } else if (clazz == Double.class || clazz == double.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_DOUBLE;
      } else if (clazz == Float.class || clazz == float.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_FLOAT;
      } else if (clazz == String.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_STRING;
      } else if (clazz == Callback.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_CALLBACK;
      } else if (clazz == Promise.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_PROMISE;
        int j = paramArrayOfClass.length;
        boolean bool = true;
        if (i != j - 1)
          bool = false; 
        a.a(bool, "Promise must be used as last parameter only");
      } else if (clazz == ReadableMap.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_MAP;
      } else if (clazz == ReadableArray.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_ARRAY;
      } else if (clazz == Dynamic.class) {
        arrayOfArgumentExtractor[i] = ARGUMENT_EXTRACTOR_DYNAMIC;
      } else {
        StringBuilder stringBuilder = new StringBuilder("Got unknown argument class: ");
        stringBuilder.append(clazz.getSimpleName());
        throw new RuntimeException(stringBuilder.toString());
      } 
    } 
    return arrayOfArgumentExtractor;
  }
  
  private String buildSignature(Method paramMethod, Class[] paramArrayOfClass, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder(paramArrayOfClass.length + 2);
    if (paramBoolean) {
      stringBuilder.append(returnTypeToChar(paramMethod.getReturnType()));
      stringBuilder.append('.');
    } else {
      stringBuilder.append("v.");
    } 
    int i;
    for (i = 0; i < paramArrayOfClass.length; i++) {
      Class<Promise> clazz = paramArrayOfClass[i];
      if (clazz == Promise.class) {
        int j = paramArrayOfClass.length;
        paramBoolean = true;
        if (i != j - 1)
          paramBoolean = false; 
        a.a(paramBoolean, "Promise must be used as last parameter only");
      } 
      stringBuilder.append(paramTypeToChar(clazz));
    } 
    return stringBuilder.toString();
  }
  
  private int calculateJSArgumentsNeeded() {
    ArgumentExtractor[] arrayOfArgumentExtractor = (ArgumentExtractor[])a.b(this.mArgumentExtractors);
    int k = arrayOfArgumentExtractor.length;
    int i = 0;
    int j = 0;
    while (i < k) {
      j += arrayOfArgumentExtractor[i].getJSArgumentsNeeded();
      i++;
    } 
    return j;
  }
  
  private static char commonTypeToChar(Class<boolean> paramClass) {
    return (paramClass == boolean.class) ? 'z' : ((paramClass == Boolean.class) ? 'Z' : ((paramClass == int.class) ? 'i' : ((paramClass == Integer.class) ? 'I' : ((paramClass == double.class) ? 'd' : ((paramClass == Double.class) ? 'D' : ((paramClass == float.class) ? 'f' : ((paramClass == Float.class) ? 'F' : ((paramClass == String.class) ? 'S' : Character.MIN_VALUE))))))));
  }
  
  private String getAffectedRange(int paramInt1, int paramInt2) {
    if (paramInt2 > 1) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramInt1);
      stringBuilder1.append("-");
      stringBuilder1.append(paramInt1 + paramInt2 - 1);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt1);
    return stringBuilder.toString();
  }
  
  private static char paramTypeToChar(Class<Callback> paramClass) {
    char c = commonTypeToChar(paramClass);
    if (c != '\000')
      return c; 
    if (paramClass == Callback.class)
      return 'X'; 
    if (paramClass == Promise.class)
      return 'P'; 
    if (paramClass == ReadableMap.class)
      return 'M'; 
    if (paramClass == ReadableArray.class)
      return 'A'; 
    if (paramClass == Dynamic.class)
      return 'Y'; 
    StringBuilder stringBuilder = new StringBuilder("Got unknown param class: ");
    stringBuilder.append(paramClass.getSimpleName());
    throw new RuntimeException(stringBuilder.toString());
  }
  
  private void processArguments() {
    if (this.mArgumentsProcessed)
      return; 
    null = new StringBuilder();
    null.append(this.mModuleWrapper.getName());
    null.append(".");
    null.append(this.mMethod.getName());
    try {
      this.mArgumentsProcessed = true;
      this.mArgumentExtractors = buildArgumentExtractors(this.mParameterTypes);
      this.mSignature = buildSignature(this.mMethod, this.mParameterTypes, this.mType.equals("sync"));
      this.mArguments = new Object[this.mParameterTypes.length];
      this.mJSArgumentsNeeded = calculateJSArgumentsNeeded();
      return;
    } finally {}
  }
  
  private static char returnTypeToChar(Class<void> paramClass) {
    char c = commonTypeToChar(paramClass);
    if (c != '\000')
      return c; 
    if (paramClass == void.class)
      return 'v'; 
    if (paramClass == WritableMap.class)
      return 'M'; 
    if (paramClass == WritableArray.class)
      return 'A'; 
    StringBuilder stringBuilder = new StringBuilder("Got unknown return class: ");
    stringBuilder.append(paramClass.getSimpleName());
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public Method getMethod() {
    return this.mMethod;
  }
  
  public String getSignature() {
    if (!this.mArgumentsProcessed)
      processArguments(); 
    return (String)a.b(this.mSignature);
  }
  
  public String getType() {
    return this.mType;
  }
  
  public void invoke(JSInstance paramJSInstance, ReadableNativeArray paramReadableNativeArray) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mModuleWrapper.getName());
    stringBuilder.append(".");
    stringBuilder.append(this.mMethod.getName());
    String str = stringBuilder.toString();
    boolean bool = DEBUG;
    int i = 0;
    if (bool) {
      this.mModuleWrapper.getName();
      this.mMethod.getName();
    } 
    try {
      if (!this.mArgumentsProcessed)
        processArguments(); 
      if (this.mArguments != null && this.mArgumentExtractors != null) {
        StringBuilder stringBuilder2;
        int j = this.mJSArgumentsNeeded;
        int k = paramReadableNativeArray.size();
        if (j == k) {
          j = 0;
          try {
            while (i < this.mArgumentExtractors.length) {
              this.mArguments[i] = this.mArgumentExtractors[i].extractArgument(paramJSInstance, paramReadableNativeArray, j);
              k = this.mArgumentExtractors[i].getJSArgumentsNeeded();
              j += k;
              i++;
            } 
            try {
              this.mMethod.invoke(this.mModuleWrapper.getModule(), this.mArguments);
              return;
            } catch (IllegalArgumentException illegalArgumentException) {
              stringBuilder2 = new StringBuilder("Could not invoke ");
              stringBuilder2.append(str);
              throw new RuntimeException(stringBuilder2.toString(), illegalArgumentException);
            } catch (IllegalAccessException illegalAccessException) {
              stringBuilder2 = new StringBuilder("Could not invoke ");
              stringBuilder2.append(str);
              throw new RuntimeException(stringBuilder2.toString(), illegalAccessException);
            } catch (InvocationTargetException invocationTargetException) {
              if (invocationTargetException.getCause() instanceof RuntimeException)
                throw (RuntimeException)invocationTargetException.getCause(); 
              stringBuilder2 = new StringBuilder("Could not invoke ");
              stringBuilder2.append(str);
              throw new RuntimeException(stringBuilder2.toString(), invocationTargetException);
            } 
          } catch (UnexpectedNativeTypeException unexpectedNativeTypeException) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(unexpectedNativeTypeException.getMessage());
            stringBuilder2.append(" (constructing arguments for ");
            stringBuilder2.append(str);
            stringBuilder2.append(" at argument index ");
            stringBuilder2.append(getAffectedRange(j, this.mArgumentExtractors[i].getJSArgumentsNeeded()));
            stringBuilder2.append(")");
            throw new NativeArgumentsParseException(stringBuilder2.toString(), unexpectedNativeTypeException);
          } 
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append(" got ");
        stringBuilder1.append(stringBuilder2.size());
        stringBuilder1.append(" arguments, expected ");
        stringBuilder1.append(this.mJSArgumentsNeeded);
        throw new NativeArgumentsParseException(stringBuilder1.toString());
      } 
      throw new Error("processArguments failed");
    } finally {}
  }
  
  static abstract class ArgumentExtractor<T> {
    private ArgumentExtractor() {}
    
    public abstract T extractArgument(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray, int param1Int);
    
    public int getJSArgumentsNeeded() {
      return 1;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaMethodWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */