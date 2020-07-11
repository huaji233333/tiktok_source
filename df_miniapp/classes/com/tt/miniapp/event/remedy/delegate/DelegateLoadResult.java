package com.tt.miniapp.event.remedy.delegate;

import android.text.TextUtils;
import com.tt.miniapp.event.remedy.AbsEventDelegate;
import com.tt.miniapp.event.remedy.EventEntity;
import com.tt.miniapp.event.remedy.InnerEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.EventHelper;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class DelegateLoadResult extends AbsEventDelegate {
  public DelegateLoadResult(InnerEventHandler paramInnerEventHandler) {
    super(paramInnerEventHandler);
  }
  
  public boolean handle(EventEntity paramEventEntity) {
    if (!paramEventEntity.innerHandled && "mp_load_start".equals(paramEventEntity.eventName)) {
      JSONObject jSONObject1 = null;
      try {
        JSONObject jSONObject = new JSONObject(paramEventEntity.eventData.toString());
        jSONObject1 = jSONObject;
      } catch (JSONException jSONException1) {
        AppBrandLogger.eWithThrowable("tma_DelegateLoadResult", "49411_build load_result json exp!", (Throwable)jSONException1);
      } 
      JSONObject jSONObject2 = jSONObject1;
      if (jSONObject1 == null) {
        jSONObject2 = new JSONObject();
        EventHelper.copyBasicCommonParams(paramEventEntity.eventData, jSONObject2);
      } 
      try {
        jSONObject2.put("result_type", "cancel").put("load_type", "").put("error_msg", "process killed").put("load_state", "host_process_unknown");
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("tma_DelegateLoadResult", "49411_put load_result json exp!", (Throwable)jSONException);
      } 
      add(keepKeyVal(new EventEntity("mp_load_result", jSONObject2, false)));
      return false;
    } 
    if ("mp_load_result".equals(((EventEntity)jSONException).eventName)) {
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
    if (TextUtils.isEmpty(paramEventEntity.eventData.optString("result_type", CharacterUtils.empty())))
      putKeyVal(paramEventEntity.eventData, "result_type", "cancel"); 
    if (!paramEventEntity.eventData.has("duration"))
      putKeyVal(paramEventEntity.eventData, "duration", Integer.valueOf(0)); 
    if (!paramEventEntity.eventData.has("total_duration"))
      putKeyVal(paramEventEntity.eventData, "total_duration", Integer.valueOf(0)); 
    if (!paramEventEntity.eventData.has("load_state"))
      putKeyVal(paramEventEntity.eventData, "load_state", "host_process_unknown"); 
    if (TextUtils.isEmpty(paramEventEntity.eventData.optString("error_msg", CharacterUtils.empty())))
      putKeyVal(paramEventEntity.eventData, "error_msg", "process killed"); 
    if (TextUtils.isEmpty(paramEventEntity.eventData.optString("load_image", CharacterUtils.empty())))
      putKeyVal(paramEventEntity.eventData, "load_image", "no_image"); 
    return super.keepKeyVal(paramEventEntity);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\delegate\DelegateLoadResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */