package com.ss.android.ugc.aweme.miniapp.j;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.hostmethod.HostMethodManager;
import com.tt.miniapphost.hostmethod.IHostMethod;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import org.json.JSONObject;

public class b implements IHostMethod {
  public static final String a = b.class.getSimpleName();
  
  private IpcCallback b;
  
  public void call(Activity paramActivity, JSONObject paramJSONObject, HostMethodManager.ResponseCallBack paramResponseCallBack) throws Exception {}
  
  public String callSync(Activity paramActivity, JSONObject paramJSONObject) {
    String str1 = paramJSONObject.optString("micro_app_id");
    String str2 = paramJSONObject.optString("hashTagName");
    int i = paramJSONObject.optInt("cursor");
    String str3 = paramJSONObject.optString("type");
    if (!TextUtils.equals("fetch_video", str3) && TextUtils.equals("open_video", str3))
      this.b = new IpcCallback(this, str2, str1, i, paramActivity) {
          public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            String str = param1CrossProcessDataEntity.getString("apiData");
            Intent intent = new Intent();
            intent.putExtra("micro_game_station_hashtag", this.a);
            intent.putExtra("micro_game_station_id", this.b);
            intent.putExtra("micro_game_station_cursor", this.c);
            intent.putExtra("micro_game_station_count", 10);
            intent.putExtra("id", str);
            intent.putExtra("video_from", "mp_page");
            MiniAppService.inst().getRouterDepend().a(this.d, intent);
          }
          
          public final void onIpcConnectError() {}
        }; 
    ProcessCallControlBridge.callHostProcessAsync("request_game_video", CrossProcessDataEntity.Builder.create().put("jsonData", paramJSONObject).build(), this.b);
    return null;
  }
  
  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return false;
  }
  
  public boolean shouldHandleActivityResult(JSONObject paramJSONObject) {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\j\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */