package com.ss.android.ugc.aweme.miniapp.g;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapp.settings.data.ABTestDAO;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.l.a;

public class k extends a {
  public AppBrandLogger.ILogger createLogger() {
    return new AppBrandLogger.ILogger(this) {
        public final void flush() {
          if (MiniAppService.inst().getBaseLibDepend() != null)
            MiniAppService.inst().getBaseLibDepend().a("f", "", "", null); 
        }
        
        public final void logD(String param1String1, String param1String2) {
          if (MiniAppService.inst().getBaseLibDepend() != null)
            MiniAppService.inst().getBaseLibDepend().a("d", param1String1, param1String2, null); 
        }
        
        public final void logE(String param1String1, String param1String2) {
          if (MiniAppService.inst().getBaseLibDepend() != null)
            MiniAppService.inst().getBaseLibDepend().a("e", param1String1, param1String2, null); 
        }
        
        public final void logE(String param1String1, String param1String2, Throwable param1Throwable) {
          if (MiniAppService.inst().getBaseLibDepend() != null)
            MiniAppService.inst().getBaseLibDepend().a("et", param1String1, param1String2, param1Throwable); 
        }
        
        public final void logI(String param1String1, String param1String2) {
          if (MiniAppService.inst().getBaseLibDepend() != null)
            MiniAppService.inst().getBaseLibDepend().a("i", param1String1, param1String2, null); 
        }
        
        public final void logW(String param1String1, String param1String2) {
          if (MiniAppService.inst().getBaseLibDepend() != null)
            MiniAppService.inst().getBaseLibDepend().a("w", param1String1, param1String2, null); 
        }
      };
  }
  
  public ABTestDAO.IUploadVids uploadVid() {
    return super.uploadVid();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */