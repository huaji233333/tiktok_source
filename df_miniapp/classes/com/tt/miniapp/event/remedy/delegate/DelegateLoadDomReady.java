package com.tt.miniapp.event.remedy.delegate;

import com.tt.miniapp.event.remedy.AbsEventDelegate;
import com.tt.miniapp.event.remedy.EventEntity;
import com.tt.miniapp.event.remedy.InnerEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.EventHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class DelegateLoadDomReady extends AbsEventDelegate {
  public DelegateLoadDomReady(InnerEventHandler paramInnerEventHandler) {
    super(paramInnerEventHandler);
  }
  
  private boolean needPrepareData(EventEntity paramEventEntity) {
    return paramEventEntity.innerHandled ? false : (!"mp_load_start".equals(paramEventEntity.eventName) ? false : (!!"micro_app".equals(paramEventEntity.eventData.optString("_param_for_special"))));
  }
  
  public boolean handle(EventEntity paramEventEntity) {
    if (needPrepareData(paramEventEntity)) {
      JSONObject jSONObject = new JSONObject();
      EventHelper.copyBasicCommonParams(paramEventEntity.eventData, jSONObject);
      try {
        jSONObject.put("total_duration", 0).put("result_type", "cancel").put("error_msg", "process killed");
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("DelegateLoadDomReady", "mp_load_dom_ready json exp!", (Throwable)jSONException);
      } 
      add(keepKeyVal(new EventEntity("mp_load_domready", jSONObject, false)));
      return false;
    } 
    if ("mp_load_domready".equals(((EventEntity)jSONException).eventName)) {
      if (((EventEntity)jSONException).innerHandled) {
        update(keepKeyVal((EventEntity)jSONException));
        return true;
      } 
      if (!remove((EventEntity)jSONException))
        return true; 
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\delegate\DelegateLoadDomReady.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */