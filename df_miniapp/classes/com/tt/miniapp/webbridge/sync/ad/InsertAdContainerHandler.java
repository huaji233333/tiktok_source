package com.tt.miniapp.webbridge.sync.ad;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.ad.b;
import com.tt.option.ad.f;
import com.tt.option.e.k;

public class InsertAdContainerHandler extends BaseAdContainerHandler {
  public InsertAdContainerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    final f model = new f(this.mArgs);
    String str1 = f.b();
    String str2 = f.a;
    Event.Builder builder = Event.builder("mp_app_ad_create");
    if (str1 == null)
      str1 = ""; 
    builder = builder.kv("ad_type", str1);
    if (str2 != null) {
      str1 = str2;
    } else {
      str1 = "";
    } 
    builder.kv("ad_unit_id", str1).flush();
    if (!isSupportAppAd()) {
      b.a(f.b(), f.a, 1003, "feature is not supported in app");
      callbackAppUnSupportFeature(1003);
      return CharacterUtils.empty();
    } 
    final int viewId = ComponentIDCreator.create();
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            if (AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender() != null)
              AppBrandLogger.d("InsertAdContainerHandler", new Object[] { "insertAdContainer webviewId ", Integer.valueOf(AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender().getWebViewId()) }); 
            try {
              InsertAdContainerHandler.this.mRender.getNativeViewManager().addView(viewId, "ad", InsertAdContainerHandler.this.mArgs, (k)InsertAdContainerHandler.this);
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "InsertAdContainerHandler", exception.getStackTrace());
              StringBuilder stringBuilder = new StringBuilder("exception is ");
              stringBuilder.append(exception.getMessage());
              String str = stringBuilder.toString();
              b.a(model.b(), model.a, 1003, str);
              InsertAdContainerHandler.this.callbackFail(1003, str);
              return;
            } 
          }
        });
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "insertAdContainer";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ad\InsertAdContainerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */