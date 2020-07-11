package com.tt.miniapp.webbridge.sync.video;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

public class RemoveVideoPlayerHandler extends WebEventHandler {
  public RemoveVideoPlayerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      if (this.mRender == null)
        return makeFailMsg("render is null"); 
      final Throwable[] throwable = new Throwable[1];
      final CountDownLatch latch = new CountDownLatch(1);
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              try {
                AppBrandLogger.d("RemoveVideoPlayerHandler", new Object[0]);
                int i = (new JSONObject(RemoveVideoPlayerHandler.this.mArgs)).optInt("videoPlayerId");
                RemoveVideoPlayerHandler.this.mRender.getNativeViewManager().removeView(i, null);
              } catch (Exception exception) {
                AppBrandLogger.e("RemoveVideoPlayerHandler", new Object[] { exception });
                throwable[0] = exception;
              } 
              latch.countDown();
            }
          });
      countDownLatch.await();
      return (arrayOfThrowable[0] == null) ? makeOkMsg() : makeFailMsg(arrayOfThrowable[0]);
    } catch (Exception exception) {
      AppBrandLogger.e("RemoveVideoPlayerHandler", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "removeVideoPlayer";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\video\RemoveVideoPlayerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */