package com.ss.android.ugc.aweme.miniapp.g;

import android.app.Activity;
import android.content.Intent;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp.d.a;
import com.ss.android.ugc.aweme.miniapp.n.a;
import com.ss.android.ugc.aweme.miniapp_api.a.l;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.ModeManager;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.ScanResultEntity;
import com.tt.option.n.a;
import com.tt.option.n.b;
import java.util.ArrayList;
import java.util.List;

public class m extends a {
  public static void a(Activity paramActivity, int paramInt1, int paramInt2) {
    MiniAppService.inst().getRouterDepend().a(paramActivity, paramInt1, paramInt2);
  }
  
  public void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, b.b paramb, b.a parama) {
    if (paramBoolean1 && paramBoolean2) {
      String str1 = paramActivity.getResources().getString(2097742067);
      String str2 = paramActivity.getResources().getString(2097742065);
      String[] arrayOfString = new String[2];
      arrayOfString[0] = str1;
      arrayOfString[1] = str2;
      (new a(AppbrandContext.getInst())).a(paramActivity, arrayOfString, new NativeModule.NativeModuleCallback<String>(this, arrayOfString, str1, paramActivity, str2, paramInt) {
          
          });
    } else if (paramBoolean2) {
      a(paramActivity, 1, 1);
    } else if (paramBoolean1) {
      a(paramActivity, 2, paramInt);
    } 
    parama.setActivityResultHandler(new IActivityResultHandler(this, paramb) {
          public final boolean autoClearAfterActivityResult() {
            return true;
          }
          
          public final boolean handleActivityResult(int param1Int1, int param1Int2, Intent param1Intent) {
            if (param1Intent != null) {
              ArrayList arrayList = param1Intent.getParcelableArrayListExtra("key_media_list");
            } else {
              param1Intent = null;
            } 
            this.a.onSuccess((List)param1Intent);
            return true;
          }
        });
  }
  
  public ScanResultEntity handleActivityScanResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return super.handleActivityScanResult(paramInt1, paramInt2, paramIntent);
  }
  
  public boolean scanCode(Activity paramActivity, b.d paramd) {
    NativeModule nativeModule = ModeManager.getInst().get("scanCode");
    if (nativeModule instanceof a) {
      a a1 = (a)nativeModule;
      a1.a = true;
      l l = MiniAppService.inst().getRouterDepend();
      if (l != null)
        l.a(a1.getCurrentActivity(), true); 
      a1.b = paramd;
    } 
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */