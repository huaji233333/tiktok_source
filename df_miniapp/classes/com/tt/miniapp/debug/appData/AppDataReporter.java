package com.tt.miniapp.debug.appData;

import android.text.TextUtils;
import android.util.SparseArray;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.util.DebugUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class AppDataReporter {
  private SparseArray<AppData> appDatas;
  
  public void addAppData(AppData paramAppData) {
    if (this.appDatas == null)
      this.appDatas = new SparseArray(); 
    AppBrandLogger.d("AppDataReporter", new Object[] { "data: ", paramAppData.data, "webviewId: ", Integer.valueOf(paramAppData.webviewId) });
    if (paramAppData.webviewId > 0) {
      this.appDatas.put(paramAppData.webviewId, paramAppData);
      return;
    } 
    DebugUtil.outputError("AppDataReporter", new Object[] { "webviewId异常，AppData更新异常" });
  }
  
  public String geneResult(AppData paramAppData) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("params", AppData.toJson(paramAppData));
      jSONObject.put("method", "AppData");
    } catch (JSONException jSONException) {}
    return jSONObject.toString();
  }
  
  public AppData getAppData(int paramInt) {
    SparseArray<AppData> sparseArray = this.appDatas;
    return (sparseArray != null) ? (AppData)sparseArray.get(paramInt) : null;
  }
  
  public AppData parseAppData(String paramString, int paramInt) {
    try {
      if (!TextUtils.isEmpty(paramString)) {
        JSONObject jSONObject = (new JSONObject(paramString)).getJSONObject("data");
        if (TextUtils.equals(jSONObject.optString("name"), "appDataChange")) {
          JSONObject jSONObject1 = jSONObject.getJSONObject("args");
          AppData appData = new AppData();
          appData.webviewId = paramInt;
          appData.data = jSONObject1.optString("data");
          jSONObject1 = jSONObject1.optJSONObject("ext");
          if (jSONObject1 != null) {
            appData.route = jSONObject1.optString("route");
            return appData;
          } 
          appData.route = AppbrandApplication.getInst().getCurrentPagePath();
          return appData;
        } 
      } 
    } catch (JSONException jSONException) {}
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\appData\AppDataReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */