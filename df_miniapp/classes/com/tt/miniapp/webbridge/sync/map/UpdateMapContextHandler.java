package com.tt.miniapp.webbridge.sync.map;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.k;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateMapContextHandler extends WebEventHandler {
  public UpdateMapContextHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            try {
              UpdateMapContextHandler updateMapContextHandler;
              JSONObject jSONObject = new JSONObject(UpdateMapContextHandler.this.mArgs);
              int i = jSONObject.optInt("mapId", -1);
              if (i == -1) {
                updateMapContextHandler = UpdateMapContextHandler.this;
                updateMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(updateMapContextHandler.getApiName(), "invalid map id", 103));
                return;
              } 
              if (UpdateMapContextHandler.this.mRender == null) {
                updateMapContextHandler = UpdateMapContextHandler.this;
                updateMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(updateMapContextHandler.getApiName(), "render is null", 205));
                return;
              } 
              NativeViewManager nativeViewManager = UpdateMapContextHandler.this.mRender.getNativeViewManager();
              if (!nativeViewManager.hasView(i)) {
                updateMapContextHandler = UpdateMapContextHandler.this;
                updateMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(updateMapContextHandler.getApiName(), "invalid map id", 103));
                return;
              } 
              updateMapContextHandler.updateView(i, UpdateMapContextHandler.this.mArgs, (k)UpdateMapContextHandler.this);
              return;
            } catch (JSONException jSONException) {
              UpdateMapContextHandler updateMapContextHandler = UpdateMapContextHandler.this;
              updateMapContextHandler.invokeHandler(MapTempUtil.makeFailMsg(updateMapContextHandler.getApiName(), (Throwable)jSONException, 207));
              return;
            } 
          }
        });
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "updateMapContext";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\map\UpdateMapContextHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */