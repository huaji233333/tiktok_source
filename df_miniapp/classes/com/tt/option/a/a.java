package com.tt.option.a;

import android.content.Context;
import com.tt.miniapphost.apm.AppbrandApmStartConfig;
import com.tt.miniapphost.apm.IApmService;
import com.tt.option.a;

public final class a extends a<b> implements b {
  public final IApmService createApmService() {
    return new IApmService(this) {
        public final void init(Context param1Context) {}
        
        public final void start(AppbrandApmStartConfig param1AppbrandApmStartConfig) {}
        
        public final void stop() {}
        
        public final void uploadALog(long param1Long1, long param1Long2, String param1String) {}
      };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */