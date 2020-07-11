package com.facebook.react.bridge;

public interface JavaJSExecutor {
  void close();
  
  String executeJSCall(String paramString1, String paramString2) throws ProxyExecutorException;
  
  void loadApplicationScript(String paramString) throws ProxyExecutorException;
  
  void setGlobalVariable(String paramString1, String paramString2);
  
  public static interface Factory {
    JavaJSExecutor create() throws Exception;
  }
  
  public static class ProxyExecutorException extends Exception {
    public ProxyExecutorException(Throwable param1Throwable) {
      super(param1Throwable);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaJSExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */