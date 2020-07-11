package com.tt.miniapp.webbridge.sync.map;

import android.view.View;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class GetSkewHandler extends WebEventHandler {
  public GetSkewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    if (this.mRender == null)
      return makeFailMsg("render is null"); 
    try {
      int i = (new JSONObject(this.mArgs)).optInt("mapId");
      View view = this.mRender.getNativeViewManager().getView(i);
      if (!(view instanceof com.tt.miniapp.component.nativeview.map.Map))
        return MapTempUtil.makeFailMsg(getApiName(), "invalid map id", 103); 
      return makeOkMsg(jSONObject);
    } finally {
      Exception exception = null;
      AppBrandLogger.e("tma_GetSkewHandler", new Object[] { exception });
    } 
  }
  
  public String getApiName() {
    return "getSkew";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\map\GetSkewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */