package com.graphic.RNCanvas;

import com.facebook.common.e.a;
import java.lang.reflect.Method;
import java.util.HashMap;

public class CanvasMethodDelegate {
  private final String TAG = "CanvasMethodDelegate";
  
  private final HashMap<String, CanvasMethodWrapper> mMethods;
  
  private final Class mModuleClass;
  
  public CanvasMethodDelegate(Class paramClass) {
    this.mModuleClass = paramClass;
    this.mMethods = new HashMap<String, CanvasMethodWrapper>();
    findMethods();
  }
  
  private void findMethods() {
    Method[] arrayOfMethod = this.mModuleClass.getDeclaredMethods();
    int j = arrayOfMethod.length;
    for (int i = 0; i < j; i++) {
      CanvasMethodWrapper canvasMethodWrapper = new CanvasMethodWrapper(arrayOfMethod[i]);
      this.mMethods.put(canvasMethodWrapper.getName(), canvasMethodWrapper);
    } 
  }
  
  public void invoke(Object paramObject, String paramString, Object[] paramArrayOfObject) {
    CanvasMethodWrapper canvasMethodWrapper = this.mMethods.get(paramString);
    if (canvasMethodWrapper != null) {
      canvasMethodWrapper.invoke(paramObject, paramArrayOfObject);
      return;
    } 
    paramObject = new StringBuilder("Could not find method ");
    paramObject.append(paramString);
    a.b("CanvasMethodDelegate", paramObject.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasMethodDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */