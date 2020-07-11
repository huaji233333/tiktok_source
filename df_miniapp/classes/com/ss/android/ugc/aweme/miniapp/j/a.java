package com.ss.android.ugc.aweme.miniapp.j;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.f.j;
import com.ss.android.ugc.aweme.miniapp.utils.e;
import com.tt.miniapphost.hostmethod.HostMethodManager;
import com.tt.miniapphost.hostmethod.IHostMethod;
import com.tt.miniapphost.process.HostProcessBridge;
import org.json.JSONObject;

public class a implements IHostMethod {
  public static final String a = a.class.getSimpleName();
  
  private long b;
  
  private long a() {
    if (HostProcessBridge.getUserInfo() != null)
      try {
        this.b = Long.parseLong("userId");
      } catch (NumberFormatException numberFormatException) {} 
    return this.b;
  }
  
  public void call(Activity paramActivity, JSONObject paramJSONObject, HostMethodManager.ResponseCallBack paramResponseCallBack) throws Exception {
    e.a(paramActivity, j.a((Context)paramActivity), a(), paramResponseCallBack);
  }
  
  public String callSync(Activity paramActivity, JSONObject paramJSONObject) throws Exception {
    JSONObject jSONObject;
    a();
    String str = j.a((Context)paramActivity);
    if (TextUtils.isEmpty(str)) {
      jSONObject = new JSONObject();
      jSONObject.put("failed", "token is empty");
      return jSONObject.toString();
    } 
    e.a((Activity)jSONObject, str, a(), new HostMethodManager.ResponseCallBack(this) {
          public final void callResponse(String param1String) {}
        });
    return null;
  }
  
  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return false;
  }
  
  public boolean shouldHandleActivityResult(JSONObject paramJSONObject) {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\j\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */