package com.tt.miniapp.msg.onUserCaptureScreen;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import org.json.JSONObject;

public class OnUserCaptureScreenManager implements TakeScreenshotManager.TakeScreenshotObserver {
  private TakeScreenshotManager mTakeScreenshotManager = new TakeScreenshotManager(this);
  
  private OnUserCaptureScreenManager() {}
  
  public static OnUserCaptureScreenManager getInstance() {
    return Holder.sInstance;
  }
  
  public void onUserCaptureScreen(String paramString, long paramLong) {
    boolean bool = AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground();
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder("onUserCaptureScreen: isBackground = ");
      stringBuilder.append(bool);
      AppBrandLogger.d("OnUserCaptureScreenManager", new Object[] { stringBuilder.toString() });
      return;
    } 
    JSONObject jSONObject = new JSONObject();
    AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("userCaptureScreenObserved", jSONObject.toString());
  }
  
  public void registerOnUserCaptureScreen() {
    this.mTakeScreenshotManager.registerTakeScreenshotObserver();
  }
  
  public void unregisterOnUserCaptureScreen() {
    this.mTakeScreenshotManager.unregisterTakeScreenshotObserver();
  }
  
  static final class Holder {
    static OnUserCaptureScreenManager sInstance = new OnUserCaptureScreenManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\onUserCaptureScreen\OnUserCaptureScreenManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */