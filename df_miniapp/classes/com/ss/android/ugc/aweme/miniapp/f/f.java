package com.ss.android.ugc.aweme.miniapp.f;

import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.a.g;
import com.ss.android.ugc.aweme.miniapp_api.model.c;
import com.ss.android.ugc.aweme.miniapp_api.model.j;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

public final class f implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    c c = MiniAppService.inst().getBaseLibDepend().i();
    j j = MiniAppService.inst().getBaseLibDepend().h();
    String str1 = c.b;
    boolean bool = c.a;
    String str2 = Locale.getDefault().getLanguage();
    String str3 = Locale.getDefault().getCountry();
    String str4 = MiniAppService.inst().getBaseLibDepend().e();
    String str5 = MiniAppService.inst().getBaseLibDepend().f();
    if (TextUtils.isEmpty(str4)) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("cookies", str5);
      g g = MiniAppService.inst().getMonitorDepend();
      if (g != null)
        g.a("mini_app_session", new JSONObject(hashMap)); 
    } 
    return CrossProcessDataEntity.Builder.create().put("avatarUrl", str1).put("nickName", j.a).put("gender", j.d).put("language", str2).put("country", str3).put("sec_uid", j.c).put("isLogin", Boolean.valueOf(bool)).put("userId", j.b).put("sessionId", str4).build();
  }
  
  public final String getType() {
    return "getUserInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */