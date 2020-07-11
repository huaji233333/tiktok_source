package com.tt.miniapp.webbridge.sync.ad;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

public class RemoveAdContainerHandler extends BaseAdContainerHandler {
  public RemoveAdContainerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    if (!isSupportAppAd()) {
      callbackAppUnSupportFeature(1003);
      return CharacterUtils.empty();
    } 
    final CountDownLatch latch = new CountDownLatch(1);
    try {
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              AppBrandLogger.d("RemoveAdContainerHandler", new Object[] { "removeAdContainer" });
              try {
                int i = (new JSONObject(RemoveAdContainerHandler.this.mArgs)).optInt("viewId");
                RemoveAdContainerHandler.this.mRender.getNativeViewManager().removeView(i, null);
                RemoveAdContainerHandler.this.callbackOk();
              } catch (Exception exception) {
                AppBrandLogger.stacktrace(6, "RemoveAdContainerHandler", exception.getStackTrace());
                RemoveAdContainerHandler removeAdContainerHandler = RemoveAdContainerHandler.this;
                StringBuilder stringBuilder = new StringBuilder("exception is ");
                stringBuilder.append(exception.getMessage());
                removeAdContainerHandler.callbackFail(1003, stringBuilder.toString());
              } 
              latch.countDown();
            }
          });
      countDownLatch.await();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "RemoveAdContainerHandler", exception.getStackTrace());
      StringBuilder stringBuilder = new StringBuilder("exception is ");
      stringBuilder.append(exception.getMessage());
      callbackFail(1003, stringBuilder.toString());
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "removeAdContainer";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ad\RemoveAdContainerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */