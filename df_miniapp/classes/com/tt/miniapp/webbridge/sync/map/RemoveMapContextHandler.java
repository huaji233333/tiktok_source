package com.tt.miniapp.webbridge.sync.map;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.k;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoveMapContextHandler extends WebEventHandler {
  public RemoveMapContextHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            try {
              JSONObject jSONObject = new JSONObject(RemoveMapContextHandler.this.mArgs);
              int i = jSONObject.optInt("mapId", -1);
              if (i == -1) {
                RemoveMapContextHandler removeMapContextHandler = RemoveMapContextHandler.this;
                removeMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(removeMapContextHandler.getApiName(), "invalid map id", 103));
                return;
              } 
              if (RemoveMapContextHandler.this.mRender == null) {
                RemoveMapContextHandler removeMapContextHandler = RemoveMapContextHandler.this;
                removeMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(removeMapContextHandler.getApiName(), "render is null", 205));
                return;
              } 
              if (!RemoveMapContextHandler.this.mRender.getNativeViewManager().hasView(i)) {
                RemoveMapContextHandler removeMapContextHandler = RemoveMapContextHandler.this;
                removeMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(removeMapContextHandler.getApiName(), "invalid map id", 103));
                return;
              } 
              RemoveMapContextHandler.this.mRender.getNativeViewManager().removeView(i, (k)RemoveMapContextHandler.this);
              return;
            } catch (JSONException jSONException) {
              RemoveMapContextHandler removeMapContextHandler = RemoveMapContextHandler.this;
              removeMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(removeMapContextHandler.getApiName(), (Throwable)jSONException, 207));
              return;
            } 
          }
        });
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "removeMapContext";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\map\RemoveMapContextHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */