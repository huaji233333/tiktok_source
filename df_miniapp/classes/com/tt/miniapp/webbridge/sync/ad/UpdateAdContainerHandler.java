package com.tt.miniapp.webbridge.sync.ad;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.k;
import org.json.JSONObject;

public class UpdateAdContainerHandler extends BaseAdContainerHandler {
  public UpdateAdContainerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    if (!isSupportAppAd()) {
      callbackAppUnSupportFeature(1003);
      return CharacterUtils.empty();
    } 
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            AppBrandLogger.d("UpdateAdContainerHandler", new Object[] { "updateAdContainer" });
            try {
              int i = (new JSONObject(UpdateAdContainerHandler.this.mArgs)).optInt("viewId");
              if (UpdateAdContainerHandler.this.mRender.getNativeViewManager().hasView(i)) {
                UpdateAdContainerHandler.this.mRender.getNativeViewManager().updateView(i, UpdateAdContainerHandler.this.mArgs, (k)UpdateAdContainerHandler.this);
                return;
              } 
              UpdateAdContainerHandler.this.callbackFail(1003, "该adUnitId的Banner广告不存在");
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "UpdateAdContainerHandler", exception.getStackTrace());
              UpdateAdContainerHandler updateAdContainerHandler = UpdateAdContainerHandler.this;
              StringBuilder stringBuilder = new StringBuilder("exception is ");
              stringBuilder.append(exception.getMessage());
              updateAdContainerHandler.callbackFail(1003, stringBuilder.toString());
              return;
            } 
          }
        });
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "updateAdContainer";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ad\UpdateAdContainerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */