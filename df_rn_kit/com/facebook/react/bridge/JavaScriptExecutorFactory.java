package com.facebook.react.bridge;

public interface JavaScriptExecutorFactory {
  JavaScriptExecutor create() throws Exception;
  
  JavaScriptExecutor create(RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) throws Exception;
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaScriptExecutorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */