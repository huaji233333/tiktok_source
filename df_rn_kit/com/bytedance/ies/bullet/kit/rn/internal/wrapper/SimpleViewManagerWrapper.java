package com.bytedance.ies.bullet.kit.rn.internal.wrapper;

import android.view.View;
import com.bytedance.ies.bullet.kit.rn.core.BulletProp;
import com.bytedance.ies.bullet.kit.rn.core.e;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import d.f.b.g;
import d.f.b.l;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SimpleViewManagerWrapper extends SimpleViewManager<View> {
  public static final a Companion = new a(null);
  
  public final Map<String, Method> methodMap;
  
  private final e<View> real;
  
  public final Map<String, Class<?>> typeMap;
  
  public final Map<String, String> typeStringMap;
  
  private SimpleViewManagerWrapper(e<View> parame) {
    this.real = parame;
    this.typeStringMap = new LinkedHashMap<String, String>();
    this.typeMap = new LinkedHashMap<String, Class<?>>();
    this.methodMap = new LinkedHashMap<String, Method>();
  }
  
  public final void addEventEmitters(ThemedReactContext paramThemedReactContext, View paramView) {
    l.b(paramThemedReactContext, "reactContext");
    l.b(paramView, "view");
    e.a(new a((ReactContext)paramThemedReactContext), paramView);
  }
  
  public final View createViewInstance(ThemedReactContext paramThemedReactContext) {
    l.b(paramThemedReactContext, "reactContext");
    return this.real.a(new a((ReactContext)paramThemedReactContext));
  }
  
  public final Map<String, Integer> getCommandsMap() {
    return null;
  }
  
  public final String getName() {
    return this.real.a();
  }
  
  public final Map<String, String> getNativeProps() {
    Map<String, String> map = super.getNativeProps();
    map.putAll(this.typeStringMap);
    l.a(map, "super.getNativeProps().a…s $this\")\n        }\n    }");
    return map;
  }
  
  public final void onAfterUpdateTransaction(View paramView) {
    l.b(paramView, "view");
    e.b(paramView);
  }
  
  public final void onDropViewInstance(View paramView) {
    l.b(paramView, "view");
    e.a(paramView);
  }
  
  public final void receiveCommand(View paramView, int paramInt, ReadableArray paramReadableArray) {
    l.b(paramView, "root");
    if (paramReadableArray != null) {
      ArrayList arrayList = paramReadableArray.toArrayList();
    } else {
      paramReadableArray = null;
    } 
    e.a(paramView, paramInt, (List)paramReadableArray);
  }
  
  public final void setProperty(View paramView, String paramString, ReactStylesDiffMap paramReactStylesDiffMap) {
    l.b(paramView, "view");
    l.b(paramString, "name");
    l.b(paramReactStylesDiffMap, "props");
    Class<?> clazz = this.typeMap.get(paramString);
    Method method = this.methodMap.get(paramString);
    if (clazz != null && method != null)
      try {
        ArrayList arrayList;
        ReadableArray readableArray;
        method.setAccessible(true);
        if (int.class.isAssignableFrom(clazz))
          return; 
        if (double.class.isAssignableFrom(clazz))
          return; 
        if (float.class.isAssignableFrom(clazz))
          return; 
        if (boolean.class.isAssignableFrom(clazz))
          return; 
        if (String.class.isAssignableFrom(clazz))
          return; 
        boolean bool = List.class.isAssignableFrom(clazz);
        e<View> e1 = null;
        String str = null;
        if (bool) {
          e1 = this.real;
          readableArray = paramReactStylesDiffMap.getArray(paramString);
          paramString = str;
          if (readableArray != null)
            arrayList = readableArray.toArrayList(); 
          return;
        } 
        return;
      } finally {
        paramView = null;
      }  
  }
  
  public static final class a {
    private a() {}
    
    public static SimpleViewManager<View> a(e<View> param1e) {
      l.b(param1e, "real");
      SimpleViewManagerWrapper simpleViewManagerWrapper = new SimpleViewManagerWrapper(param1e, null);
      try {
        Method[] arrayOfMethod = param1e.getClass().getDeclaredMethods();
        l.a(arrayOfMethod, "real.javaClass.declaredMethods");
        int j = arrayOfMethod.length;
        int i = 0;
        while (true) {
          if (i < j) {
            Method method = arrayOfMethod[i];
            BulletProp bulletProp = method.<BulletProp>getAnnotation(BulletProp.class);
            if (bulletProp != null) {
              l.a(method, "method");
              Class[] arrayOfClass = method.getParameterTypes();
              int k = arrayOfClass.length;
              if (k == 2) {
                if (View.class.isAssignableFrom(arrayOfClass[0])) {
                  Map<String, String> map1 = simpleViewManagerWrapper.typeStringMap;
                  String str3 = bulletProp.name();
                  Class<?> clazz2 = arrayOfClass[1];
                  l.a(clazz2, "it[1]");
                  boolean bool = int.class.isAssignableFrom(clazz2);
                  String str1 = "number";
                  if (!bool && !double.class.isAssignableFrom(clazz2) && !float.class.isAssignableFrom(clazz2))
                    if (boolean.class.isAssignableFrom(clazz2)) {
                      str1 = "boolean";
                    } else if (String.class.isAssignableFrom(clazz2)) {
                      str1 = "String";
                    } else if (List.class.isAssignableFrom(clazz2)) {
                      str1 = "Array";
                    } else if (Map.class.isAssignableFrom(clazz2)) {
                      str1 = "Map";
                    } else {
                      str1 = "custom";
                    }  
                  map1.put(str3, str1);
                  Map<String, Class<?>> map = simpleViewManagerWrapper.typeMap;
                  String str2 = bulletProp.name();
                  Class<?> clazz1 = arrayOfClass[1];
                  l.a(clazz1, "it[1]");
                  map.put(str2, clazz1);
                  simpleViewManagerWrapper.methodMap.put(bulletProp.name(), method);
                } else {
                  StringBuilder stringBuilder = new StringBuilder("First param should be a view subclass to be updated: ");
                  stringBuilder.append(simpleViewManagerWrapper.getName());
                  stringBuilder.append("#");
                  stringBuilder.append(method.getName());
                  throw (Throwable)new RuntimeException(stringBuilder.toString());
                } 
              } else {
                StringBuilder stringBuilder = new StringBuilder("Wrong number of args for prop setter: ");
                stringBuilder.append(simpleViewManagerWrapper.getName());
                stringBuilder.append("#");
                stringBuilder.append(method.getName());
                throw (Throwable)new RuntimeException(stringBuilder.toString());
              } 
            } 
            i++;
            continue;
          } 
          return simpleViewManagerWrapper;
        } 
      } finally {}
      return simpleViewManagerWrapper;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\wrapper\SimpleViewManagerWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */