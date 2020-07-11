package com.tt.miniapphost.apm;

import android.content.Context;

public interface IApmService {
  void init(Context paramContext);
  
  void start(AppbrandApmStartConfig paramAppbrandApmStartConfig);
  
  void stop();
  
  void uploadALog(long paramLong1, long paramLong2, String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\apm\IApmService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */