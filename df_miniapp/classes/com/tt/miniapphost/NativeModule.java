package com.tt.miniapphost;

import android.app.Activity;
import android.content.Intent;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;

public abstract class NativeModule {
  private AppbrandContext appbrandContext;
  
  public NativeModule(AppbrandContext paramAppbrandContext) {
    this.appbrandContext = paramAppbrandContext;
  }
  
  public Activity getCurrentActivity() {
    return (Activity)this.appbrandContext.getCurrentActivity();
  }
  
  public abstract String getName();
  
  public g invoke(f paramf, NativeModuleCallback paramNativeModuleCallback) throws Exception {
    return null;
  }
  
  public abstract <T> String invoke(String paramString, NativeModuleCallback<T> paramNativeModuleCallback) throws Exception;
  
  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return false;
  }
  
  public void onStart() {}
  
  public void onStop() {}
  
  public static interface NativeModuleCallback<T> {
    void onNativeModuleCall(T param1T);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\NativeModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */