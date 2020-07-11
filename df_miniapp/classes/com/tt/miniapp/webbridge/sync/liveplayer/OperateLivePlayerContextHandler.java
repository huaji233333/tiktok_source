package com.tt.miniapp.webbridge.sync.liveplayer;

import android.text.TextUtils;
import android.view.View;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.liveplayer.LivePlayer;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.CharacterUtils;
import d.f.b.l;
import d.f.b.w;
import org.json.JSONObject;

public final class OperateLivePlayerContextHandler extends WebEventHandler {
  private final String TAG = "OperateLivePlayerContextHandler";
  
  public OperateLivePlayerContextHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public final String act() {
    try {
      String str1;
      JSONObject jSONObject = new JSONObject(this.mArgs);
      int i = jSONObject.optInt("livePlayerId");
      String str2 = jSONObject.optString("type");
      if (this.mRender == null) {
        callbackFail("render is null");
        str1 = CharacterUtils.empty();
        l.a(str1, "CharacterUtils.empty()");
        return str1;
      } 
      HostDependManager hostDependManager = HostDependManager.getInst();
      l.a(hostDependManager, "HostDependManager.getInst()");
      if (!hostDependManager.isSupportNativeLivePlayer()) {
        str1 = makeFailMsg("feature is not supported in app");
        l.a(str1, "makeFailMsg(ApiCallConst…ATURE_NOT_SUPPORT_IN_APP)");
        return str1;
      } 
      w.e e = new w.e();
      e.element = null;
      WebViewManager.IRender iRender = this.mRender;
      l.a(iRender, "mRender");
      View view = iRender.getNativeViewManager().getView(i);
      if (view instanceof LivePlayer)
        e.element = view; 
      if ((LivePlayer)e.element == null) {
        StringBuilder stringBuilder = new StringBuilder("LivePlayer not found: ");
        stringBuilder.append(i);
        str1 = stringBuilder.toString();
        AppBrandLogger.e(this.TAG, new Object[] { str1 });
        callbackFail(str1);
        str1 = CharacterUtils.empty();
        l.a(str1, "CharacterUtils.empty()");
        return str1;
      } 
      AppbrandContext.mainHandler.post(new OperateLivePlayerContextHandler$act$1(str2, (JSONObject)str1, e));
    } catch (Exception exception) {
      AppBrandLogger.e(this.TAG, new Object[] { exception });
      callbackFail(exception);
    } 
    String str = CharacterUtils.empty();
    l.a(str, "CharacterUtils.empty()");
    return str;
  }
  
  public final String getApiName() {
    return "operateLivePlayerContext";
  }
  
  static final class OperateLivePlayerContextHandler$act$1 implements Runnable {
    OperateLivePlayerContextHandler$act$1(String param1String, JSONObject param1JSONObject, w.e param1e) {}
    
    public final void run() {
      Object object;
      if (TextUtils.equals(this.$operationType, "requestFullScreen")) {
        object = this.$jsonObject;
      } else {
        object = null;
      } 
      LivePlayer livePlayer = (LivePlayer)this.$component.element;
      String str = this.$operationType;
      l.a(str, "operationType");
      if (livePlayer.operateLivePlayer(str, object)) {
        OperateLivePlayerContextHandler.this.callbackOk();
        return;
      } 
      OperateLivePlayerContextHandler.this.callbackFail(a.b("type"));
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\liveplayer\OperateLivePlayerContextHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */