package com.tt.miniapp.business.aweme;

import android.app.Activity;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.c.a;
import com.bytedance.sandboxapp.protocol.service.c.b;
import com.bytedance.sandboxapp.protocol.service.c.c;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.service.suffixmeta.SuffixMetaEntity;
import com.tt.miniapp.service.suffixmeta.SuffixMetaServiceInterface;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.b.b;
import d.f.b.l;

public final class AwemeService implements a {
  private final MiniAppContext context;
  
  public AwemeService(MiniAppContext paramMiniAppContext) {
    this.context = paramMiniAppContext;
  }
  
  public final boolean canCheckFollowAwemeState() {
    return (SettingsDAO.getInt(getContext().getApplicationContext(), 0, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.CHECK_FOLLOW_AWEME_STATE }) == 1);
  }
  
  public final void checkFollowAwemeState(String paramString1, String paramString2, c paramc) {
    l.b(paramString1, "uid");
    l.b(paramString2, "secUid");
    l.b(paramc, "callback");
    HostProcessBridge.callAwemeHandler(1, paramString1, paramString2, paramc);
  }
  
  public final void getAwemeUidFromSuffixMeta(b paramb) {
    l.b(paramb, "callback");
    SuffixMetaServiceInterface suffixMetaServiceInterface = (SuffixMetaServiceInterface)getContext().getService(SuffixMetaServiceInterface.class);
    if (suffixMetaServiceInterface != null) {
      String str;
      SuffixMetaEntity suffixMetaEntity = suffixMetaServiceInterface.getOrNull(true);
      if (suffixMetaEntity != null) {
        str = suffixMetaEntity.awemeUserId;
        l.a(str, "suffixMetaEntity.awemeUserId");
        String str1 = suffixMetaEntity.awemeSecUserId;
        l.a(str1, "suffixMetaEntity.awemeSecUserId");
        paramb.a(str, str1);
        return;
      } 
      str.getRemoteImmediately(new AwemeService$getAwemeUidFromSuffixMeta$1(paramb));
      return;
    } 
    paramb.a("SuffixMetaServiceInterface is null");
  }
  
  public final MiniAppContext getContext() {
    return this.context;
  }
  
  public final boolean hasAwemeDepend() {
    return HostDependManager.getInst().hasAwemeDepend();
  }
  
  public final boolean hasLogin() {
    return (UserInfoManager.getHostClientUserInfo()).isLogin;
  }
  
  public final void onDestroy() {}
  
  public final void openAwemeUserProfile(Activity paramActivity, String paramString1, String paramString2, boolean paramBoolean, c paramc) {
    l.b(paramActivity, "activity");
    l.b(paramString1, "uid");
    l.b(paramString2, "secUid");
    l.b(paramc, "callback");
    HostDependManager.getInst().openAwemeUserProfile(paramActivity, paramString1, paramString2, paramBoolean, new AwemeService$openAwemeUserProfile$1(paramBoolean, paramString1, paramString2, paramc));
  }
  
  public static final class AwemeService$getAwemeUidFromSuffixMeta$1 implements SuffixMetaServiceInterface.SuffixMetaListener {
    AwemeService$getAwemeUidFromSuffixMeta$1(b param1b) {}
    
    public final void onError(String param1String) {
      b b1 = this.$callback;
      String str = param1String;
      if (param1String == null)
        str = "unknown"; 
      b1.a(str);
    }
    
    public final void onSuccess(SuffixMetaEntity param1SuffixMetaEntity) {
      // Byte code:
      //   0: aload_0
      //   1: getfield $callback : Lcom/bytedance/sandboxapp/protocol/service/c/b;
      //   4: astore #4
      //   6: aload_1
      //   7: ifnull -> 21
      //   10: aload_1
      //   11: getfield awemeUserId : Ljava/lang/String;
      //   14: astore_3
      //   15: aload_3
      //   16: astore_2
      //   17: aload_3
      //   18: ifnonnull -> 24
      //   21: ldc ''
      //   23: astore_2
      //   24: aload_1
      //   25: ifnull -> 39
      //   28: aload_1
      //   29: getfield awemeSecUserId : Ljava/lang/String;
      //   32: astore_3
      //   33: aload_3
      //   34: astore_1
      //   35: aload_3
      //   36: ifnonnull -> 42
      //   39: ldc ''
      //   41: astore_1
      //   42: aload #4
      //   44: aload_2
      //   45: aload_1
      //   46: invokeinterface a : (Ljava/lang/String;Ljava/lang/String;)V
      //   51: return
    }
  }
  
  public static final class AwemeService$openAwemeUserProfile$1 implements b {
    AwemeService$openAwemeUserProfile$1(boolean param1Boolean, String param1String1, String param1String2, c param1c) {}
    
    public final void onFailure(int param1Int, String param1String) {
      l.b(param1String, "msg");
      this.$callback.onFailure(param1Int, param1String);
    }
    
    public final void onSuccess() {
      if (this.$notifyFollowState) {
        HostProcessBridge.callAwemeHandler(2, this.$uid, this.$secUid, this.$callback);
        return;
      } 
      this.$callback.onFollowAwemeResult(null);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\aweme\AwemeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */