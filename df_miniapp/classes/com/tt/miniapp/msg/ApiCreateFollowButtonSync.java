package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.miniapp.component.nativeview.game.GameButton;
import com.tt.miniapp.component.nativeview.game.GameButtonHelper;
import com.tt.miniapp.component.nativeview.game.GameButtonManager;
import com.tt.miniapp.component.nativeview.game.GameButtonStyle;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.follow.FollowMethodImpl;
import com.tt.miniapp.follow.FollowResultCallback;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import org.json.JSONObject;

public class ApiCreateFollowButtonSync extends SyncMsgCtrl {
  public ApiCreateFollowButtonSync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    try {
      GameButton gameButton;
      String str1;
      JSONObject jSONObject3 = new JSONObject(this.mParams);
      String str2 = jSONObject3.getString("type");
      boolean bool = TextUtils.equals(str2, "text");
      if (!bool && !TextUtils.equals(str2, "image"))
        return ApiCallResult.a.b(getName()).d("error params.type").a().toString(); 
      JSONObject jSONObject2 = jSONObject3.getJSONObject("style");
      if (TextUtils.equals(str2, "image")) {
        StringBuilder stringBuilder = new StringBuilder();
        str1 = GameButtonHelper.filterImagePath(false, jSONObject3.getString("image"), stringBuilder);
        if (TextUtils.isEmpty(str1))
          return ApiCallResult.a.b(getName()).d(String.valueOf(stringBuilder)).a().toString(); 
        gameButton = GameButtonHelper.createImageButton((Context)miniappHostBase, GameButtonStyle.parse((Context)miniappHostBase, str1, jSONObject2));
      } else {
        gameButton = GameButtonHelper.createTextButton((Context)miniappHostBase, GameButtonStyle.parse((Context)miniappHostBase, str1.getString("text"), (JSONObject)gameButton));
      } 
      GameButtonManager gameButtonManager = GameButtonManager.get();
      if (gameButtonManager == null)
        return ApiCallResult.a.b(getName()).d("render activity not found").a().toString(); 
      final int viewId = gameButtonManager.addToParentView(gameButton);
      gameButton.setViewOnclickListener(new Runnable() {
            public void run() {
              ApiCreateFollowButtonSync.this.startFollowProcedure(activity, viewId);
              Event.builder("mp_btn_click").kv("btn_name", "follow_toutiaohao").flush();
            }
          });
      Event.builder("mp_btn_show").kv("btn_name", "follow_toutiaohao").flush();
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("buttonId", String.valueOf(i));
      return ApiCallResult.a.a(getName()).a(jSONObject1).a().toString();
    } catch (Exception exception) {
      return ApiCallResult.a.b(getName()).a(exception).a().toString();
    } 
  }
  
  public String getName() {
    return "createFollowButton";
  }
  
  public void startFollowProcedure(Activity paramActivity, int paramInt) {
    (new FollowMethodImpl(new FollowCallback(paramInt))).startFollow(paramActivity);
  }
  
  public static class FollowCallback implements FollowResultCallback {
    private String buttonId;
    
    public FollowCallback(int param1Int) {
      this.buttonId = String.valueOf(param1Int);
    }
    
    public void callBackResult(int param1Int, String param1String) {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("buttonId", this.buttonId);
        jSONObject.put("errCode", param1Int);
        jSONObject.put("errMsg", param1String);
        AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onClickFollowButton", jSONObject.toString());
        return;
      } catch (Exception exception) {
        AppBrandLogger.eWithThrowable("tma_ApiCreateFollowButtonSync", "error", exception);
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiCreateFollowButtonSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */