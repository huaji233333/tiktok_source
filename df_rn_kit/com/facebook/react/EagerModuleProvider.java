package com.facebook.react;

import com.facebook.react.bridge.NativeModule;
import javax.a.a;

public class EagerModuleProvider implements a<NativeModule> {
  private final NativeModule mModule;
  
  public EagerModuleProvider(NativeModule paramNativeModule) {
    this.mModule = paramNativeModule;
  }
  
  public NativeModule get() {
    return this.mModule;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\EagerModuleProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */