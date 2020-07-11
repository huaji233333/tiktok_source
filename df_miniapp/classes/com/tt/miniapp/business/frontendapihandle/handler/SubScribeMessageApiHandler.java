package com.tt.miniapp.business.frontendapihandle.handler;

import android.util.ArrayMap;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.storage.async.Action;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.SubScribeMessageProcessor;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import d.f.b.l;
import java.util.Map;
import org.json.JSONArray;

public class SubScribeMessageApiHandler extends c {
  public SubScribeMessageProcessor subScribeMessageProcessor;
  
  public SubScribeMessageApiHandler(b paramb, a parama) {
    super(paramb, parama);
  }
  
  public void handleApi(final a apiInvokeInfo) {
    final Object templateIdArray = apiInvokeInfo.a("tmplIds");
    final String apiName = ((a)this).apiName;
    if (!(object instanceof JSONArray)) {
      triggerApiCallback(apiInvokeInfo, str, 1002, "invalid params", null);
      return;
    } 
    object = object;
    if (object.length() <= 0) {
      triggerApiCallback(apiInvokeInfo, str, 1002, "invalid params", null);
      return;
    } 
    if (!(object.opt(0) instanceof String)) {
      triggerApiCallback(apiInvokeInfo, str, 1002, "invalid params", null);
      return;
    } 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            SubScribeMessageApiHandler.this.subScribeMessageProcessor = new SubScribeMessageProcessor();
            SubScribeMessageApiHandler.this.subScribeMessageProcessor.requestSubScribeMessage(templateIdArray, new SubScribeMessageProcessor.SubscribeMessageCallback() {
                  public void onResult(int param2Int, String param2String, ArrayMap<String, String> param2ArrayMap) {
                    SubScribeMessageApiHandler.this.triggerApiCallback(apiInvokeInfo, apiName, param2Int, param2String, param2ArrayMap);
                  }
                });
          }
        }ThreadPools.defaults());
  }
  
  public void triggerApiCallback(a parama, String paramString1, int paramInt, String paramString2, ArrayMap<String, String> paramArrayMap) {
    ApiCallbackData.a a1;
    if (paramInt == 0) {
      l.b(paramString1, "apiName");
      a1 = new ApiCallbackData.a(paramString1, "ok", null);
    } else {
      String str = paramString2;
      if (paramString2 == null)
        str = ""; 
      a1 = ApiCallbackData.a.a((String)a1, str, 0);
      a1.a("errCode", Integer.valueOf(paramInt));
    } 
    if (paramArrayMap != null && paramArrayMap.size() > 0)
      for (Map.Entry entry : paramArrayMap.entrySet())
        a1.a((String)entry.getKey(), entry.getValue());  
    parama.a(a1.a());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\SubScribeMessageApiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */