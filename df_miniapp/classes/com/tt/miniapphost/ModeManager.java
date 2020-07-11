package com.tt.miniapphost;

import java.util.HashMap;
import java.util.Map;

public class ModeManager {
  Map<String, NativeModule> modules = new HashMap<String, NativeModule>();
  
  private ModeManager() {}
  
  public static ModeManager getInst() {
    return Holder.holder;
  }
  
  public NativeModule get(String paramString) {
    return this.modules.get(paramString);
  }
  
  public Map<String, NativeModule> getModules() {
    return this.modules;
  }
  
  public void register(String paramString, NativeModule paramNativeModule) {
    this.modules.put(paramString, paramNativeModule);
  }
  
  static class Holder {
    static ModeManager holder = new ModeManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\ModeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */