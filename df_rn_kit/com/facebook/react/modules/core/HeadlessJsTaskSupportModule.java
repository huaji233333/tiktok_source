package com.facebook.react.modules.core;

import com.facebook.common.e.a;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "HeadlessJsTaskSupport")
public class HeadlessJsTaskSupportModule extends ReactContextBaseJavaModule {
  public HeadlessJsTaskSupportModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public String getName() {
    return "HeadlessJsTaskSupport";
  }
  
  @ReactMethod
  public void notifyTaskFinished(int paramInt) {
    HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance((ReactContext)getReactApplicationContext());
    if (headlessJsTaskContext.isTaskRunning(paramInt)) {
      headlessJsTaskContext.finishTask(paramInt);
      return;
    } 
    a.b(HeadlessJsTaskSupportModule.class, "Tried to finish non-active task with id %d. Did it time out?", new Object[] { Integer.valueOf(paramInt) });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\HeadlessJsTaskSupportModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */