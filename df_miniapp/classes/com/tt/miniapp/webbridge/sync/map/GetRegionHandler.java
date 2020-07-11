package com.tt.miniapp.webbridge.sync.map;

import android.view.View;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.map.Map;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.m.f;
import org.json.JSONObject;

public class GetRegionHandler extends WebEventHandler {
  public GetRegionHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    if (this.mRender == null)
      return makeFailMsg("render is null"); 
    try {
      int i = (new JSONObject(this.mArgs)).optInt("mapId");
      View view = this.mRender.getNativeViewManager().getView(i);
      if (!(view instanceof Map))
        return MapTempUtil.makeFailMsg(getApiName(), "invalid map id", 103); 
      f f = ((Map)view).getMapContext().d();
      JSONObject jSONObject1 = new JSONObject();
      JSONObject jSONObject2 = new JSONObject();
      jSONObject2.put("latitude", f.b.a);
      jSONObject2.put("longitude", f.b.b);
      jSONObject1.put("southwest", jSONObject2);
      jSONObject2 = new JSONObject();
      jSONObject2.put("latitude", f.a.a);
      return makeOkMsg(jSONObject1);
    } finally {
      Exception exception = null;
      AppBrandLogger.e("tma_GetRegionHandler", new Object[] { exception });
    } 
  }
  
  public String getApiName() {
    return "getRegion";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\map\GetRegionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */