package com.tt.miniapp.webbridge;

import com.tt.frontendapiinterface.i;
import com.tt.miniapp.WebViewManager;
import com.tt.option.e.j;

public abstract class WebEventHandler extends j {
  public WebViewManager.IRender mRender;
  
  public WebEventHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super((i)paramIRender, paramString, paramInt);
    this.mRender = paramIRender;
  }
  
  public int getWebViewId() {
    WebViewManager.IRender iRender = this.mRender;
    return (iRender != null) ? iRender.getWebViewId() : 0;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\WebEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */