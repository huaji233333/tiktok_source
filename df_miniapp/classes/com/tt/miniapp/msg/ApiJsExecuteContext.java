package com.tt.miniapp.msg;

import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsObject;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiJsExecuteContext {
  private JsObject mApiParams;
  
  private JsContext mJsContext;
  
  private List<SoftReference<JsObject>> mRetainObjects = new ArrayList<SoftReference<JsObject>>();
  
  public ApiJsExecuteContext(JsContext paramJsContext) {
    this.mJsContext = paramJsContext;
  }
  
  public ApiJsExecuteContext(JsObject paramJsObject) {
    this.mApiParams = paramJsObject;
    this.mJsContext = (JsContext)paramJsObject.getScopedContext();
  }
  
  public JsObject createArrayBuffer(int paramInt) {
    JsContext jsContext = this.mJsContext;
    return (jsContext == null) ? null : jsContext.createArrayBuffer(paramInt);
  }
  
  public JsObject createJsArray(int paramInt) {
    JsContext jsContext = this.mJsContext;
    return (jsContext == null) ? null : jsContext.createArray(paramInt);
  }
  
  public JsObject createObject() {
    JsContext jsContext = this.mJsContext;
    return (jsContext == null) ? null : jsContext.createObject();
  }
  
  public boolean getBoolean(String paramString) {
    return this.mApiParams.getBoolean(paramString);
  }
  
  public int getInt(String paramString) {
    return this.mApiParams.getInt(paramString);
  }
  
  public JsObject getObject(String paramString) {
    JsObject jsObject2 = null;
    JsObject jsObject1 = jsObject2;
    try {
      if (this.mApiParams != null) {
        jsObject1 = jsObject2;
        jsObject2 = this.mApiParams.getObject(paramString);
        jsObject1 = jsObject2;
        this.mRetainObjects.add(new SoftReference<JsObject>(jsObject2));
        return jsObject2;
      } 
      jsObject1 = jsObject2;
      throw new RuntimeException("mApiParams is null");
    } catch (Exception exception) {
      AppBrandLogger.e("ApiJsExecuteContext", new Object[] { "prop is not exist", exception, paramString });
      return jsObject1;
    } 
  }
  
  public <T> T getParamInJsArray(JsObject paramJsObject, int paramInt, Class<T> paramClass) {
    if (paramClass == JsObject.class)
      try {
        paramJsObject = paramJsObject.getObject(paramInt);
        this.mRetainObjects.add(new SoftReference<JsObject>(paramJsObject));
        return (T)paramJsObject;
      } catch (Exception exception) {
        AppBrandLogger.e("ApiJsExecuteContext", new Object[] { "prop is not exist", exception });
        return null;
      }  
    return (T)((paramClass == Integer.class) ? Integer.valueOf(exception.getInt(paramInt)) : ((paramClass == Boolean.class) ? Boolean.valueOf(exception.getBoolean(paramInt)) : ((paramClass == String.class) ? exception.getString(paramInt) : null)));
  }
  
  public <T> T getParamInJsObject(JsObject paramJsObject, String paramString, Class<T> paramClass) {
    if (paramClass == JsObject.class)
      try {
        paramJsObject = paramJsObject.getObject(paramString);
        this.mRetainObjects.add(new SoftReference<JsObject>(paramJsObject));
        return (T)paramJsObject;
      } catch (Exception exception) {
        AppBrandLogger.e("ApiJsExecuteContext", new Object[] { "prop is not exist", exception });
        return null;
      }  
    return (T)((paramClass == Integer.class) ? Integer.valueOf(exception.getInt(paramString)) : ((paramClass == Boolean.class) ? Boolean.valueOf(exception.getBoolean(paramString)) : ((paramClass == String.class) ? exception.getString(paramString) : null)));
  }
  
  public String getString(String paramString) {
    return this.mApiParams.getString(paramString);
  }
  
  public void release() {
    List<SoftReference<JsObject>> list = this.mRetainObjects;
    if (list != null && !list.isEmpty()) {
      Iterator<SoftReference<JsObject>> iterator = this.mRetainObjects.iterator();
      while (true) {
        if (iterator.hasNext()) {
          JsObject jsObject = ((SoftReference<JsObject>)iterator.next()).get();
          if (jsObject != null)
            try {
              jsObject.release();
            } catch (Exception exception) {
              AppBrandLogger.e("ApiJsExecuteContext", new Object[] { "object release failed" });
            }  
          continue;
        } 
        return;
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiJsExecuteContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */