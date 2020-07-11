package com.facebook.react.bridge;

import com.facebook.jni.HybridData;

public class ProxyJavaScriptExecutor extends JavaScriptExecutor {
  private JavaJSExecutor mJavaJSExecutor;
  
  public ProxyJavaScriptExecutor(JavaJSExecutor paramJavaJSExecutor) {
    super(initHybrid(paramJavaJSExecutor));
    this.mJavaJSExecutor = paramJavaJSExecutor;
  }
  
  private static native HybridData initHybrid(JavaJSExecutor paramJavaJSExecutor);
  
  public void close() {
    JavaJSExecutor javaJSExecutor = this.mJavaJSExecutor;
    if (javaJSExecutor != null) {
      javaJSExecutor.close();
      this.mJavaJSExecutor = null;
    } 
  }
  
  public static class Factory implements JavaScriptExecutorFactory {
    private final JavaJSExecutor.Factory mJavaJSExecutorFactory;
    
    public Factory(JavaJSExecutor.Factory param1Factory) {
      this.mJavaJSExecutorFactory = param1Factory;
    }
    
    public JavaScriptExecutor create() throws Exception {
      return new ProxyJavaScriptExecutor(this.mJavaJSExecutorFactory.create());
    }
    
    public JavaScriptExecutor create(RNJavaScriptRuntime.SplitCommonType param1SplitCommonType) throws Exception {
      return new ProxyJavaScriptExecutor(this.mJavaJSExecutorFactory.create());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ProxyJavaScriptExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */