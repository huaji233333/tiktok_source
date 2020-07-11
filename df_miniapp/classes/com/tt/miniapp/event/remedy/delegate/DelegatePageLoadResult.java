package com.tt.miniapp.event.remedy.delegate;

import android.text.TextUtils;
import com.tt.miniapp.event.remedy.AbsEventDelegate;
import com.tt.miniapp.event.remedy.EventEntity;
import com.tt.miniapp.event.remedy.InnerEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.EventHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class DelegatePageLoadResult extends AbsEventDelegate {
  public DelegatePageLoadResult(InnerEventHandler paramInnerEventHandler) {
    super(paramInnerEventHandler);
  }
  
  public boolean handle(EventEntity paramEventEntity) {
    if (!paramEventEntity.innerHandled && "mp_page_load_start".equals(paramEventEntity.eventName)) {
      JSONObject jSONObject1 = null;
      try {
        JSONObject jSONObject = new JSONObject(paramEventEntity.eventData.toString());
        jSONObject1 = jSONObject;
      } catch (JSONException jSONException1) {
        AppBrandLogger.eWithThrowable("DelegatePageLoadResult", "build page_load_result json exp!", (Throwable)jSONException1);
      } 
      JSONObject jSONObject2 = jSONObject1;
      if (jSONObject1 == null)
        jSONObject2 = new JSONObject(); 
      EventHelper.copyBasicCommonParams(paramEventEntity.eventData, jSONObject2);
      try {
        jSONObject2.put("page_path", paramEventEntity.eventData.optString("page_path", "")).put("result_type", "cancel").put("duration", 0).put("error_msg", "process killed");
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("DelegatePageLoadResult", "49411_put load_result json exp!", (Throwable)jSONException);
      } 
      add(keepKeyVal(new EventEntity("mp_page_load_result", jSONObject2, false)));
      return false;
    } 
    if ("mp_page_load_result".equals(((EventEntity)jSONException).eventName)) {
      if (((EventEntity)jSONException).innerHandled) {
        update(keepKeyVal((EventEntity)jSONException));
        return true;
      } 
      if (!remove((EventEntity)jSONException))
        return true; 
    } 
    return false;
  }
  
  public EventEntity keepKeyVal(EventEntity paramEventEntity) {
    putKeyVal(paramEventEntity.eventData, "result_type", "cancel");
    if (!paramEventEntity.eventData.has("duration"))
      putKeyVal(paramEventEntity.eventData, "duration", Integer.valueOf(0)); 
    if (TextUtils.isEmpty(paramEventEntity.eventData.optString("error_msg", "")))
      putKeyVal(paramEventEntity.eventData, "error_msg", "process killed"); 
    return super.keepKeyVal(paramEventEntity);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\delegate\DelegatePageLoadResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */