package com.tt.miniapp.webbridge.sync.video;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

public class InsertVideoPlayerHandler extends WebEventHandler {
  public InsertVideoPlayerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      JSONObject jSONObject;
      if (this.mRender == null)
        return makeFailMsg("render is null"); 
      final Throwable[] throwable = new Throwable[1];
      final CountDownLatch latch = new CountDownLatch(1);
      final int viewId = ComponentIDCreator.create();
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              try {
                AppBrandLogger.d("tma_InsertVideoPlayerHandle", new Object[] { "insertVideo webviewId ", Integer.valueOf(AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender().getWebViewId()) });
                InsertVideoPlayerHandler.this.mRender.getNativeViewManager().addView(viewId, "video", InsertVideoPlayerHandler.this.mArgs, null);
              } catch (Exception exception) {
                AppBrandLogger.e("tma_InsertVideoPlayerHandle", new Object[] { exception });
                throwable[0] = exception;
              } 
              latch.countDown();
            }
          });
      countDownLatch.await();
      if (arrayOfThrowable[0] == null) {
        jSONObject = new JSONObject();
        jSONObject.put("videoPlayerId", i);
        return makeOkMsg(jSONObject);
      } 
      return makeFailMsg((Throwable)jSONObject[0]);
    } catch (Exception exception) {
      AppBrandLogger.e("tma_InsertVideoPlayerHandle", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "insertVideoPlayer";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\video\InsertVideoPlayerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */