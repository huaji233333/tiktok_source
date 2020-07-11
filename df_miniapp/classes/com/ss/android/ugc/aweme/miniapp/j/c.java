package com.ss.android.ugc.aweme.miniapp.j;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.b.a.a;
import com.ss.android.ugc.aweme.miniapp_api.b.a.b;
import com.ss.android.ugc.aweme.miniapp_api.model.a;
import com.tt.miniapphost.hostmethod.HostMethodManager;
import com.tt.miniapphost.hostmethod.IHostMethod;
import org.json.JSONObject;

public final class c implements IHostMethod {
  public final void call(Activity paramActivity, JSONObject paramJSONObject, HostMethodManager.ResponseCallBack paramResponseCallBack) {
    String str = paramJSONObject.optString("alias_id");
    MiniAppService.inst().getBaseLibDepend().a(0L, new a(this, str) {
          public final Object a(com.ss.android.ugc.aweme.miniapp_api.b.a.c param1c) {
            try {
              return MiniAppService.inst().getBaseLibDepend().b(this.a);
            } catch (Exception exception) {
              return null;
            } 
          }
        }new b(this, paramResponseCallBack, paramActivity) {
          public final Object a(com.ss.android.ugc.aweme.miniapp_api.b.a.c param1c) {
            if (param1c.a || param1c.b == null) {
              this.a.callResponse("{\"errMsg\":\"fail Network not available\"}");
              return null;
            } 
            String str = ((a)param1c.b).c.a;
            if (TextUtils.isEmpty(str)) {
              this.a.callResponse("{\"errMsg\":\"fail Video not exist\"}");
            } else {
              if (this.b != null) {
                Intent intent = new Intent();
                intent.putExtra("id", str);
                MiniAppService.inst().getRouterDepend().a(this.b, intent);
              } 
              this.a.callResponse("{\"errMsg\":\"ok\"}");
            } 
            return null;
          }
        }true);
  }
  
  public final String callSync(Activity paramActivity, JSONObject paramJSONObject) {
    return "";
  }
  
  public final boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return false;
  }
  
  public final boolean shouldHandleActivityResult(JSONObject paramJSONObject) {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\j\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */