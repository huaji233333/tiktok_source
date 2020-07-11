package com.facebook.react.modules.common;

import com.facebook.common.e.a;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.NativeModule;

public class ModuleDataCleaner {
  public static void cleanDataFromModules(CatalystInstance paramCatalystInstance) {
    for (NativeModule nativeModule : paramCatalystInstance.getNativeModules()) {
      if (nativeModule instanceof Cleanable) {
        StringBuilder stringBuilder = new StringBuilder("Cleaning data from ");
        stringBuilder.append(nativeModule.getName());
        a.a("ReactNative", stringBuilder.toString());
        ((Cleanable)nativeModule).clearSensitiveData();
      } 
    } 
  }
  
  public static interface Cleanable {
    void clearSensitiveData();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\common\ModuleDataCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */