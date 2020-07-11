package com.tt.miniapp.toast;

import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;

public class HideToastImpl extends NativeModule {
  public HideToastImpl(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public String getName() {
    return "hideToast";
  }
  
  public String invoke(String paramString, NativeModule.NativeModuleCallback paramNativeModuleCallback) throws Exception {
    HostDependManager.getInst().hideToast();
    if (paramNativeModuleCallback != null)
      paramNativeModuleCallback.onNativeModuleCall(null); 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\toast\HideToastImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */