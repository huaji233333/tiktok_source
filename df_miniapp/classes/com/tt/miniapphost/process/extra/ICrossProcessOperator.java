package com.tt.miniapphost.process.extra;

import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ICrossProcessOperator {
  void callbackOnOriginProcess(String paramString);
  
  boolean isSwitchProcessOperateAsync();
  
  void operateFinishOnGoalProcess(String paramString, AsyncIpcHandler paramAsyncIpcHandler);
  
  String operateProcessIdentify();
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface OperateProcessIdentify {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\extra\ICrossProcessOperator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */