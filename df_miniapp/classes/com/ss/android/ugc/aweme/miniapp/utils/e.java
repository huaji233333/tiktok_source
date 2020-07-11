package com.ss.android.ugc.aweme.miniapp.utils;

import android.app.Activity;
import android.view.View;
import com.bytedance.apm.agent.v2.instrumentation.ClickAgent;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp.f;
import com.ss.android.ugc.aweme.miniapp_api.b.a.a;
import com.ss.android.ugc.aweme.miniapp_api.b.a.b;
import com.ss.android.ugc.aweme.miniapp_api.b.a.c;
import com.ss.android.ugc.aweme.miniapp_api.model.d;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.hostmethod.HostMethodManager;
import com.tt.miniapphost.process.HostProcessBridge;
import org.json.JSONException;
import org.json.JSONObject;

public final class e {
  public static void a(Activity paramActivity, String paramString, long paramLong, HostMethodManager.ResponseCallBack paramResponseCallBack) {
    JSONObject jSONObject = new JSONObject();
    MiniAppService.inst().getBaseLibDepend().a(0L, new a(paramString, paramLong, jSONObject, paramResponseCallBack) {
          public final Object a(c param1c) {
            try {
              return MiniAppService.inst().getBaseLibDepend().a(this.a, this.b);
            } catch (Exception exception) {
              try {
                e.a(this.c, 1, "get_relation_api_fail");
              } catch (JSONException jSONException) {
                AppBrandLogger.stacktrace(5, "TMA_MiniAppShareUtils", exception.getStackTrace());
              } 
              this.d.callResponse(this.c.toString());
              return null;
            } 
          }
        }new b(paramActivity, paramLong, jSONObject, paramResponseCallBack) {
          public final Object a(c param1c) {
            if (param1c.b != null) {
              d d = (d)param1c.b;
              if (d != null && d.d == 0 && !d.c && !param1c.a) {
                Activity activity = this.a;
                long l = this.b;
                MiniAppService.inst().getBaseLibDepend().a(activity, d, l, new View.OnClickListener() {
                      public final void onClick(View param1View) {
                        ClickAgent.onClick(param1View);
                        HostProcessBridge.logEvent("click_mp_follow_dialog", new JSONObject((f.a().a("final_status", "cancel")).a));
                      }
                    }new View.OnClickListener(d, l) {
                      public final void onClick(View param1View) {
                        ClickAgent.onClick(param1View);
                        HostProcessBridge.logEvent("click_mp_follow_dialog", new JSONObject((f.a().a("final_status", "confirm")).a));
                        long l1 = this.a.a;
                        long l2 = this.b;
                        MiniAppService.inst().getBaseLibDepend().a(0L, new a(l1, l2) {
                              public final Object a(c param1c) {
                                try {
                                  MiniAppService.inst().getBaseLibDepend().b(this.a, this.b);
                                } catch (Exception exception) {
                                  AppBrandLogger.stacktrace(5, "TMA_MiniAppShareUtils", exception.getStackTrace());
                                } 
                                return null;
                              }
                            }new b() {
                              public final Object a(c param1c) {
                                return null;
                              }
                            },  false);
                      }
                    });
                HostProcessBridge.logEvent("show_mp_follow_dialog", new JSONObject((f.a()).a));
              } else {
                try {
                  e.a(this.c, 1, "already_friends");
                } catch (JSONException jSONException) {
                  AppBrandLogger.stacktrace(5, "TMA_MiniAppShareUtils", jSONException.getStackTrace());
                } 
                this.d.callResponse(this.c.toString());
              } 
            } 
            return null;
          }
        }true);
  }
  
  public static void a(JSONObject paramJSONObject, int paramInt, String paramString) throws JSONException {
    paramJSONObject.put("status_msg", paramString);
    paramJSONObject.put("status_code", "failed");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniap\\utils\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */