package com.facebook.react.bridge;

import com.facebook.i.a.a;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSIModuleRegistry {
  private final Map<Class, JSIModule> mModules = (Map)new HashMap<Class<?>, JSIModule>();
  
  public <T extends JSIModule> T getModule(Class<T> paramClass) {
    return (T)a.b(this.mModules.get(paramClass));
  }
  
  public void registerModules(List<JSIModuleHolder> paramList) {
    for (JSIModuleHolder jSIModuleHolder : paramList)
      this.mModules.put(jSIModuleHolder.getJSIModuleClass(), jSIModuleHolder.getJSIModule()); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JSIModuleRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */