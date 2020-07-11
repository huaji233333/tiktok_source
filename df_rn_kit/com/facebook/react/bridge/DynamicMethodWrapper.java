package com.facebook.react.bridge;

public class DynamicMethodWrapper implements NativeModule.NativeMethod {
  private final String methodName;
  
  private final JavaModuleWrapper module;
  
  public DynamicMethodWrapper(JavaModuleWrapper paramJavaModuleWrapper, String paramString) {
    this.methodName = paramString;
    this.module = paramJavaModuleWrapper;
  }
  
  public String getType() {
    return "async";
  }
  
  public void invoke(JSInstance paramJSInstance, ReadableNativeArray paramReadableNativeArray) {
    BaseJavaModule baseJavaModule = this.module.getModule();
    if (baseJavaModule instanceof IDynamicJavaMethodsFactory)
      ((NativeModule.NativeMethod)((IDynamicJavaMethodsFactory)baseJavaModule).getDynamicMethods().get(this.methodName)).invoke(paramJSInstance, paramReadableNativeArray); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\DynamicMethodWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */