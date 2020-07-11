package com.tt.option.l;

import com.tt.miniapp.settings.data.ABTestDAO;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.a;

public class a extends a<b> implements b {
  public AppBrandLogger.ILogger createLogger() {
    return new AppBrandLogger.ILogger(this) {
        public final void flush() {}
        
        public final void logD(String param1String1, String param1String2) {}
        
        public final void logE(String param1String1, String param1String2) {}
        
        public final void logE(String param1String1, String param1String2, Throwable param1Throwable) {}
        
        public final void logI(String param1String1, String param1String2) {}
        
        public final void logW(String param1String1, String param1String2) {}
      };
  }
  
  public ABTestDAO.IUploadVids uploadVid() {
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\l\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */