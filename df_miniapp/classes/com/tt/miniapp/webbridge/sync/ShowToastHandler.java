package com.tt.miniapp.webbridge.sync;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowToastHandler extends WebEventHandler {
  protected long mDuration;
  
  protected String mIcon;
  
  protected String mTitle;
  
  public ShowToastHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      this.mDuration = jSONObject.optLong("duration", 1500L);
      this.mTitle = jSONObject.optString("title");
      this.mIcon = jSONObject.optString("icon");
      if (this.mDuration <= 0L)
        this.mDuration = 1500L; 
      if (TextUtils.isEmpty(this.mTitle)) {
        callbackFail("title不能为空");
        return CharacterUtils.empty();
      } 
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              Application application;
              MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
              if (miniappHostBase == null)
                application = AppbrandContext.getInst().getApplicationContext(); 
              if (application != null)
                ShowToastHandler.this.showToast((Context)application); 
              ShowToastHandler.this.callbackOk();
            }
          });
    } catch (JSONException jSONException) {
      AppBrandLogger.e("WebEventHandler", new Object[] { jSONException });
      callbackFail((Throwable)jSONException);
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "showToast";
  }
  
  protected void showToast(Context paramContext) {
    HostDependManager.getInst().showToast(paramContext, this.mArgs, this.mTitle, this.mDuration, this.mIcon);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ShowToastHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */