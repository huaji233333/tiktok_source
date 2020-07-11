package com.tt.miniapp.webbridge.sync.video;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

public class UpdateVideoPlayerHandler extends WebEventHandler {
  public UpdateVideoPlayerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
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
                int i = (new JSONObject(UpdateVideoPlayerHandler.this.mArgs)).optInt("videoPlayerId");
                UpdateVideoPlayerHandler.this.mRender.getNativeViewManager().updateView(i, UpdateVideoPlayerHandler.this.mArgs, null);
              } catch (Exception exception) {
                AppBrandLogger.e("UpdateVideoPlayerHandler", new Object[] { exception });
                throwable[0] = exception;
              } 
              latch.countDown();
            }
          });
      countDownLatch.await();
      return (arrayOfThrowable[0] == null) ? makeOkMsg() : makeFailMsg(arrayOfThrowable[0]);
    } catch (Exception exception) {
      AppBrandLogger.e("UpdateVideoPlayerHandler", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "updateVideoPlayer";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\video\UpdateVideoPlayerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */