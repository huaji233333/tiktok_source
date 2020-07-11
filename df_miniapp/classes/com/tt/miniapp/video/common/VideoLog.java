package com.tt.miniapp.video.common;

import com.tt.miniapphost.AppBrandLogger;

public class VideoLog {
  private VideoLog() {}
  
  public static VideoLog getInstance() {
    return LazyHolder.INSTANCE;
  }
  
  public void writeVideoLog(String paramString, boolean paramBoolean) {
    AppBrandLogger.d("tma_VideoLog", new Object[] { paramString });
  }
  
  static class LazyHolder {
    static VideoLog INSTANCE = new VideoLog();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\common\VideoLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */