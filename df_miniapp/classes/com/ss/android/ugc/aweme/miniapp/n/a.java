package com.ss.android.ugc.aweme.miniapp.n;

import android.content.Intent;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.option.n.b;

public final class a extends NativeModule {
  public boolean a;
  
  public b.d b;
  
  public a(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public final String getName() {
    return "scanCode";
  }
  
  public final String invoke(String paramString, NativeModule.NativeModuleCallback paramNativeModuleCallback) throws Exception {
    return null;
  }
  
  public final boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    boolean bool1 = this.a;
    boolean bool = false;
    if (!bool1)
      return false; 
    this.a = false;
    String str1 = MiniAppService.inst().getBaseLibDepend().a();
    String str2 = MiniAppService.inst().getBaseLibDepend().b();
    if (paramInt2 == -1) {
      if (paramIntent != null) {
        str1 = paramIntent.getStringExtra(str1);
        String str = paramIntent.getStringExtra(str2);
        b.d d1 = this.b;
        if (d1 != null)
          d1.onScanResult(str1, str); 
      } 
      bool = true;
    } 
    this.b = null;
    return bool;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\n\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */