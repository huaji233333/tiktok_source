package com.facebook.react.modules.debug;

import com.facebook.i.a.a;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = "SourceCode")
public class SourceCodeModule extends BaseJavaModule {
  private final ReactContext mReactContext;
  
  public SourceCodeModule(ReactContext paramReactContext) {
    this.mReactContext = paramReactContext;
  }
  
  public Map<String, Object> getConstants() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("scriptURL", a.a(this.mReactContext.getCatalystInstance().getSourceURL(), "No source URL loaded, have you initialised the instance?"));
    return (Map)hashMap;
  }
  
  public String getName() {
    return "SourceCode";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\debug\SourceCodeModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */