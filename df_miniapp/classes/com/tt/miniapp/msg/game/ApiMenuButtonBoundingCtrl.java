package com.tt.miniapp.msg.game;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.UIUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiMenuButtonBoundingCtrl extends SyncMsgCtrl {
  public ApiMenuButtonBoundingCtrl(String paramString) {
    super(paramString);
  }
  
  private String calcPosAndSize() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    Resources resources = application.getResources();
    int n = resources.getDimensionPixelSize(2097414151);
    int j = resources.getDimensionPixelSize(2097414149);
    int k = resources.getDimensionPixelSize(2097414152);
    int m = resources.getDimensionPixelSize(2097414150);
    if (AppbrandApplication.getInst().getAppInfo().isGame()) {
      i = UIUtils.getTitleBarHeight((Context)application, (AppbrandApplication.getInst().getAppInfo()).isLandScape);
    } else {
      i = UIUtils.getTitleBarHeight((Context)application, false);
    } 
    int i = i - n + (n - j) / 2;
    m = DevicesUtil.getScreenWidth((Context)application) - m;
    return makeMsg(UIUtils.px2dip((Context)application, k), UIUtils.px2dip((Context)application, j), UIUtils.px2dip((Context)application, i), UIUtils.px2dip((Context)application, m), UIUtils.px2dip((Context)application, (i + j)), UIUtils.px2dip((Context)application, (m - k)));
  }
  
  private String makeMsg(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("width", paramInt1);
      jSONObject.put("height", paramInt2);
      jSONObject.put("top", paramInt3);
      jSONObject.put("bottom", paramInt5);
      jSONObject.put("right", paramInt4);
      jSONObject.put("left", paramInt6);
      return makeOkMsg(jSONObject);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_ApiMenuButtonBoundingCtrl", new Object[] { jSONException });
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String act() {
    String str = calcPosAndSize();
    StringBuilder stringBuilder = new StringBuilder("result: ");
    stringBuilder.append(str);
    AppBrandLogger.i("tma_ApiMenuButtonBoundingCtrl", new Object[] { stringBuilder.toString() });
    return str;
  }
  
  public String getName() {
    return "getMenuButtonBoundingClientRect";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\game\ApiMenuButtonBoundingCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */