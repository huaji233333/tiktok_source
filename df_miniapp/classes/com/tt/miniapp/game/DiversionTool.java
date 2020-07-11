package com.tt.miniapp.game;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class DiversionTool {
  private String mGroupId = "";
  
  private DiversionTool() {}
  
  public static DiversionTool getIns() {
    return Holder.sInstance;
  }
  
  private void initBdpLog(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("initBdpLog: ");
    stringBuilder.append(paramString);
    AppBrandLogger.d("tma_DiversionTool", new Object[] { stringBuilder.toString() });
    if (TextUtils.isEmpty(paramString))
      return; 
    try {
      initValueFromJson(new JSONObject(paramString));
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_DiversionTool", new Object[] { jSONException });
      return;
    } 
  }
  
  private void initExtra(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("initExtra: ");
    stringBuilder.append(paramString);
    AppBrandLogger.d("tma_DiversionTool", new Object[] { stringBuilder.toString() });
    if (TextUtils.isEmpty(paramString))
      return; 
    try {
      initValueFromJson((new JSONObject(paramString)).optJSONObject("event_extra"));
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_DiversionTool", new Object[] { jSONException });
      return;
    } 
  }
  
  private void initValueFromJson(JSONObject paramJSONObject) {
    if (paramJSONObject == null)
      return; 
    this.mGroupId = paramJSONObject.optString("group_id");
  }
  
  public String getGroupId() {
    if (!TextUtils.isEmpty(this.mGroupId))
      return this.mGroupId; 
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity == null)
      return this.mGroupId; 
    initBdpLog(appInfoEntity.bdpLog);
    if (!TextUtils.isEmpty(this.mGroupId))
      return this.mGroupId; 
    initExtra(appInfoEntity.extra);
    return this.mGroupId;
  }
  
  static class Holder {
    public static final DiversionTool sInstance = new DiversionTool();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\game\DiversionTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */