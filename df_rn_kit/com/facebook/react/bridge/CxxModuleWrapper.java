package com.facebook.react.bridge;

import com.facebook.jni.HybridData;

public class CxxModuleWrapper extends CxxModuleWrapperBase {
  protected CxxModuleWrapper(HybridData paramHybridData) {
    super(paramHybridData);
  }
  
  public static String getAbsolutePath(String paramString) {
    return paramString;
  }
  
  public static CxxModuleWrapper makeDso(String paramString1, String paramString2) {
    _lancet.com_ss_android_ugc_aweme_lancet_launch_LoadSoLancet_loadLibrary(paramString1);
    return makeDsoNative(getAbsolutePath(paramString1), paramString2);
  }
  
  private static native CxxModuleWrapper makeDsoNative(String paramString1, String paramString2);
  
  class CxxModuleWrapper {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\CxxModuleWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */