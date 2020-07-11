package com.facebook.react.bridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public final class JavaScriptModuleRegistry {
  private final HashMap<Class<? extends JavaScriptModule>, JavaScriptModule> mModuleInstances = new HashMap<Class<? extends JavaScriptModule>, JavaScriptModule>();
  
  public final <T extends JavaScriptModule> T getJavaScriptModule(CatalystInstance paramCatalystInstance, Class<T> paramClass) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mModuleInstances : Ljava/util/HashMap;
    //   6: aload_2
    //   7: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: checkcast com/facebook/react/bridge/JavaScriptModule
    //   13: astore_3
    //   14: aload_3
    //   15: ifnull -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_3
    //   21: areturn
    //   22: aload_2
    //   23: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   26: astore_3
    //   27: new com/facebook/react/bridge/JavaScriptModuleRegistry$JavaScriptModuleInvocationHandler
    //   30: dup
    //   31: aload_1
    //   32: aload_2
    //   33: invokespecial <init> : (Lcom/facebook/react/bridge/CatalystInstance;Ljava/lang/Class;)V
    //   36: astore_1
    //   37: aload_3
    //   38: iconst_1
    //   39: anewarray java/lang/Class
    //   42: dup
    //   43: iconst_0
    //   44: aload_2
    //   45: aastore
    //   46: aload_1
    //   47: invokestatic newProxyInstance : (Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;
    //   50: checkcast com/facebook/react/bridge/JavaScriptModule
    //   53: astore_1
    //   54: aload_0
    //   55: getfield mModuleInstances : Ljava/util/HashMap;
    //   58: aload_2
    //   59: aload_1
    //   60: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   63: pop
    //   64: aload_0
    //   65: monitorexit
    //   66: aload_1
    //   67: areturn
    //   68: astore_1
    //   69: aload_0
    //   70: monitorexit
    //   71: aload_1
    //   72: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	68	finally
    //   22	64	68	finally
  }
  
  static class JavaScriptModuleInvocationHandler implements InvocationHandler {
    private final CatalystInstance mCatalystInstance;
    
    private final Class<? extends JavaScriptModule> mModuleInterface;
    
    private String mName;
    
    public JavaScriptModuleInvocationHandler(CatalystInstance param1CatalystInstance, Class<? extends JavaScriptModule> param1Class) {
      this.mCatalystInstance = param1CatalystInstance;
      this.mModuleInterface = param1Class;
    }
    
    private String getJSModuleName() {
      if (this.mName == null) {
        String str2 = this.mModuleInterface.getSimpleName();
        int i = str2.lastIndexOf('$');
        String str1 = str2;
        if (i != -1)
          str1 = str2.substring(i + 1); 
        this.mName = str1;
      } 
      return this.mName;
    }
    
    public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable {
      if (param1ArrayOfObject != null) {
        param1Object = Arguments.fromJavaArgs(param1ArrayOfObject);
      } else {
        param1Object = new WritableNativeArray();
      } 
      this.mCatalystInstance.callFunction(getJSModuleName(), param1Method.getName(), (NativeArray)param1Object);
      return null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaScriptModuleRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */