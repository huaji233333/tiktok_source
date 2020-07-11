package com.tt.miniapp.webbridge.sync.liveplayer;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import d.f.b.l;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

public final class RemoveLivePlayerHandler extends WebEventHandler {
  public final String TAG = "RemoveLivePlayerHandler";
  
  public RemoveLivePlayerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public final String act() {
    try {
      if (this.mRender == null) {
        str = makeFailMsg("render is null");
        l.a(str, "makeFailMsg(ApiCallConst…ExtraInfo.RENDER_IS_NULL)");
        return str;
      } 
      HostDependManager hostDependManager = HostDependManager.getInst();
      l.a(hostDependManager, "HostDependManager.getInst()");
      if (!hostDependManager.isSupportNativeLivePlayer()) {
        str = makeFailMsg("feature is not supported in app");
        l.a(str, "makeFailMsg(ApiCallConst…ATURE_NOT_SUPPORT_IN_APP)");
        return str;
      } 
      Throwable[] arrayOfThrowable = new Throwable[1];
      CountDownLatch countDownLatch = new CountDownLatch(1);
      AppbrandContext.mainHandler.post(new RemoveLivePlayerHandler$act$1(arrayOfThrowable, countDownLatch));
      countDownLatch.await();
      if (arrayOfThrowable[0] == null) {
        str = makeOkMsg();
        l.a(str, "makeOkMsg()");
        return str;
      } 
      String str = makeFailMsg((Throwable)str[0]);
      l.a(str, "makeFailMsg(throwable[0])");
      return str;
    } catch (Exception exception) {
      AppBrandLogger.e(this.TAG, new Object[] { exception });
      String str = makeFailMsg(exception);
      l.a(str, "makeFailMsg(e)");
      return str;
    } 
  }
  
  public final String getApiName() {
    return "removeLivePlayer";
  }
  
  static final class RemoveLivePlayerHandler$act$1 implements Runnable {
    RemoveLivePlayerHandler$act$1(Throwable[] param1ArrayOfThrowable, CountDownLatch param1CountDownLatch) {}
    
    public final void run() {
      try {
        AppBrandLogger.d("RemoveVideoPlayerHandler", new Object[0]);
        int i = (new JSONObject(RemoveLivePlayerHandler.this.mArgs)).optInt("livePlayerId");
        WebViewManager.IRender iRender = RemoveLivePlayerHandler.this.mRender;
        l.a(iRender, "mRender");
        iRender.getNativeViewManager().removeView(i, null);
      } catch (Exception exception) {
        AppBrandLogger.e(RemoveLivePlayerHandler.this.TAG, new Object[] { exception });
        this.$throwable[0] = exception;
      } 
      this.$latch.countDown();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\liveplayer\RemoveLivePlayerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */