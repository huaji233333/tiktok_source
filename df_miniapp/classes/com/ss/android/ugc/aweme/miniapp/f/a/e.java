package com.ss.android.ugc.aweme.miniapp.f.a;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import org.json.JSONObject;

public final class e implements IAsyncHostDataHandler {
  public final void action(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    if (paramCrossProcessDataEntity == null) {
      paramAsyncIpcHandler.callback(null);
      return;
    } 
    try {
      JSONObject jSONObject = new JSONObject(paramCrossProcessDataEntity.getString("apiData"));
      String str = jSONObject.optString("action");
      jSONObject.getString("userId");
      paramCrossProcessDataEntity.getString("ttId");
      if (!TextUtils.equals(str, "follow")) {
        TextUtils.equals(str, "unfollow");
        return;
      } 
    } catch (Exception exception) {
      try {
        AppBrandLogger.e("UserRelationHandler", new Object[] { "", exception });
        return;
      } catch (Exception exception1) {
        AppBrandLogger.e("UserRelationHandler", new Object[] { "", exception1 });
        paramAsyncIpcHandler.callback(null);
      } 
    } 
  }
  
  public final String getType() {
    return "handleUserRelation";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\a\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */