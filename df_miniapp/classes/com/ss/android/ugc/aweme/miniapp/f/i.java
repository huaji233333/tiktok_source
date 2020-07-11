package com.ss.android.ugc.aweme.miniapp.f;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.b.e;
import com.ss.android.ugc.aweme.miniapp_api.model.a.a;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import org.json.JSONObject;

public class i implements IAsyncHostDataHandler {
  public static final String a = i.class.getSimpleName();
  
  public void action(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    JSONObject jSONObject = paramCrossProcessDataEntity.getJSONObject("jsonData");
    String str1 = jSONObject.optString("micro_app_id");
    String str2 = jSONObject.optString("hashTagName");
    int j = jSONObject.optInt("cursor");
    String str3 = jSONObject.optString("type");
    e e = new e(this, paramAsyncIpcHandler) {
        public final void a(a param1a) {
          CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().put("apiData", param1a.getMessage()).build();
          this.a.callback(crossProcessDataEntity, true);
        }
      };
    MiniAppService.inst().getBaseLibDepend().a(str3, str1, str2, j, e);
  }
  
  public String getType() {
    return "request_game_video";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */