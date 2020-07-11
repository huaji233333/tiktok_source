package com.tt.miniapp.webbridge.sync.map;

import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.k;

public class InsertMapContextHandler extends WebEventHandler {
  public InsertMapContextHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    AppBrandLogger.d("tma_InsertMapContextHandler", new Object[] { this.mArgs });
    final int mapViewId = ComponentIDCreator.create();
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            if (InsertMapContextHandler.this.mRender == null) {
              InsertMapContextHandler insertMapContextHandler = InsertMapContextHandler.this;
              insertMapContextHandler.invokeHandler(ApiCallResult.a.a(insertMapContextHandler.getApiName(), "render is null", 205).toString());
              return;
            } 
            InsertMapContextHandler.this.mRender.getNativeViewManager().addView(mapViewId, "map", InsertMapContextHandler.this.mArgs, (k)InsertMapContextHandler.this);
          }
        });
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "insertMapContext";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\map\InsertMapContextHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */