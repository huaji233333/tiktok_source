package com.tt.miniapphost.process.data;

public class CrossProcessInformation {
  public static class CallerProcess {
    private final int mCallerProcessCallbackId;
    
    private final String mCallerProcessIdentify;
    
    public CallerProcess(String param1String, int param1Int) {
      this.mCallerProcessIdentify = param1String;
      this.mCallerProcessCallbackId = param1Int;
    }
    
    public int getCallerProcessCallbackId() {
      return this.mCallerProcessCallbackId;
    }
    
    public String getCallerProcessIdentify() {
      return this.mCallerProcessIdentify;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\data\CrossProcessInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */