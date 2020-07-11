package com.ss.android.ugc.aweme.miniapp.f;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.b.b;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;

public class g implements IAsyncHostDataHandler {
  public static boolean a;
  
  public static final String b = g.class.getSimpleName();
  
  private static b c;
  
  public void action(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    byte b1;
    CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().build();
    String str = paramCrossProcessDataEntity.getString("live_stream_action");
    switch (str.hashCode()) {
      default:
        b1 = -1;
        break;
      case 1935018183:
        if (str.equals("unregister_live_stream_end_listener")) {
          b1 = 3;
          break;
        } 
      case 782245952:
        if (str.equals("register_live_stream_end_listener")) {
          b1 = 2;
          break;
        } 
      case 23018858:
        if (str.equals("pause_live_stream")) {
          b1 = 0;
          break;
        } 
      case -1541913311:
        if (str.equals("resume_live_stream")) {
          b1 = 1;
          break;
        } 
    } 
    if (b1 != 0) {
      if (b1 != 1) {
        if (b1 != 2) {
          if (b1 != 3)
            return; 
          MiniAppService.inst().getBaseLibDepend();
          paramAsyncIpcHandler.callback(crossProcessDataEntity);
          a = false;
          return;
        } 
        c = new b(this, paramAsyncIpcHandler, crossProcessDataEntity) {
          
          };
        MiniAppService.inst().getBaseLibDepend();
        a = true;
        return;
      } 
      MiniAppService.inst().getBaseLibDepend();
      paramAsyncIpcHandler.callback(crossProcessDataEntity);
      return;
    } 
    MiniAppService.inst().getBaseLibDepend();
    paramAsyncIpcHandler.callback(crossProcessDataEntity);
  }
  
  public String getType() {
    return "live_window_event";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */