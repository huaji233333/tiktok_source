package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import com.tt.miniapp.database.usagerecord.UsageRecordInfo;
import com.tt.miniapp.manager.DbManager;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetUsageRecordCtrl extends SyncMsgCtrl {
  private String functionName;
  
  public ApiGetUsageRecordCtrl(String paramString1, String paramString2) {
    super(paramString2);
    this.functionName = paramString1;
  }
  
  private static JSONObject getMiniappUsageRecordList(List<String> paramList) {
    if (paramList == null || paramList.isEmpty()) {
      AppBrandLogger.e("SyncMsgCtrl", new Object[] { "getMiniappUsageRecordList appidList is empty" });
      return null;
    } 
    paramList = DbManager.getInstance().getUsageRecordDao().getAvailUsageRecordInfoList(paramList);
    JSONObject jSONObject = new JSONObject();
    JSONArray jSONArray = new JSONArray();
    try {
      for (UsageRecordInfo usageRecordInfo : paramList) {
        String str1;
        JSONObject jSONObject1 = new JSONObject();
        jSONObject1.put("appID", usageRecordInfo.appID);
        jSONObject1.put("startTime", usageRecordInfo.startTime);
        jSONObject1.put("duration", usageRecordInfo.duration);
        boolean bool = TextUtils.isEmpty(usageRecordInfo.scene);
        String str2 = "0";
        if (bool) {
          str1 = "0";
        } else {
          str1 = usageRecordInfo.scene;
        } 
        jSONObject1.put("scene", str1);
        if (TextUtils.isEmpty(usageRecordInfo.subScene)) {
          str1 = str2;
        } else {
          str1 = usageRecordInfo.subScene;
        } 
        jSONObject1.put("subScene", str1);
        jSONArray.put(jSONObject1);
      } 
      jSONObject.put("records", jSONArray);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SyncMsgCtrl", new Object[] { jSONException });
    } 
    AppBrandLogger.d("SyncMsgCtrl", new Object[] { "result = ", jSONObject.toString() });
    return jSONObject;
  }
  
  public String act() {
    try {
      int i = (new JSONObject(this.mParams)).getInt("type");
      List<String> list = new ArrayList();
      list.add((AppbrandApplication.getInst().getAppInfo()).appId);
      if (i == 1)
        list = NetUtil.getCanJumpOtherMiniappList(); 
      JSONObject jSONObject = getMiniappUsageRecordList(list);
      return (jSONObject != null) ? makeOkMsg((new JSONObject()).put("data", jSONObject)) : makeFailMsg("Miniapp Usage Record List is null");
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SyncMsgCtrl", new Object[] { jSONException });
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String getName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiGetUsageRecordCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */