package com.he.loader;

public class Library {
  private static Loader _loader;
  
  public static String getSoPluginDir() {
    Loader loader = _loader;
    return (loader == null) ? null : loader.getSoPluginDir();
  }
  
  public static void load(String paramString) throws Exception {
    Loader loader = _loader;
    if (loader != null) {
      loader.load(paramString);
      return;
    } 
    _lancet.com_ss_android_ugc_aweme_lancet_launch_LoadSoLancet_loadLibrary(paramString);
  }
  
  public static void setLoader(Loader paramLoader) {
    _loader = paramLoader;
  }
  
  public static interface Loader {
    String getSoPluginDir();
    
    void load(String param1String) throws Exception;
  }
  
  class Library {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\loader\Library.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */