package com.facebook.react;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import java.util.List;

public abstract class ReactInstancePackage implements ReactPackage {
  public List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    throw new RuntimeException("ReactInstancePackage must be passed in the ReactInstanceManager.");
  }
  
  public abstract List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext, ReactInstanceManager paramReactInstanceManager);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactInstancePackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */