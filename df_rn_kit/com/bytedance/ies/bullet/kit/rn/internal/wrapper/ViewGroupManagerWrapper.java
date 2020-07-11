package com.bytedance.ies.bullet.kit.rn.internal.wrapper;

import android.view.View;
import android.view.ViewGroup;
import com.bytedance.ies.bullet.kit.rn.core.BulletProp;
import com.bytedance.ies.bullet.kit.rn.core.f;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import d.f.b.g;
import d.f.b.l;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ViewGroupManagerWrapper extends ViewGroupManager<ViewGroup> {
  public static final a Companion = new a(null);
  
  public final Map<String, Method> methodMap;
  
  private final f<ViewGroup> real;
  
  public final Map<String, Class<?>> typeMap;
  
  public final Map<String, String> typeStringMap;
  
  private ViewGroupManagerWrapper(f<ViewGroup> paramf) {
    this.real = paramf;
    this.typeStringMap = new LinkedHashMap<String, String>();
    this.typeMap = new LinkedHashMap<String, Class<?>>();
    this.methodMap = new LinkedHashMap<String, Method>();
  }
  
  protected final void addEventEmitters(ThemedReactContext paramThemedReactContext, ViewGroup paramViewGroup) {
    l.b(paramThemedReactContext, "reactContext");
    l.b(paramViewGroup, "view");
    f.a(new a((ReactContext)paramThemedReactContext), (View)paramViewGroup);
  }
  
  protected final ViewGroup createViewInstance(ThemedReactContext paramThemedReactContext) {
    l.b(paramThemedReactContext, "reactContext");
    return (ViewGroup)this.real.a(new a((ReactContext)paramThemedReactContext));
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
  
  protected final void onAfterUpdateTransaction(ViewGroup paramViewGroup) {
    l.b(paramViewGroup, "view");
    f.b((View)paramViewGroup);
  }
  
  public final void onDropViewInstance(ViewGroup paramViewGroup) {
    l.b(paramViewGroup, "view");
    f.a((View)paramViewGroup);
  }
  
  public final void receiveCommand(ViewGroup paramViewGroup, int paramInt, ReadableArray paramReadableArray) {
    l.b(paramViewGroup, "root");
    View view = (View)paramViewGroup;
    if (paramReadableArray != null) {
      ArrayList arrayList = paramReadableArray.toArrayList();
    } else {
      paramViewGroup = null;
    } 
    f.a(view, paramInt, (List)paramViewGroup);
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
        f<ViewGroup> f1 = null;
        String str = null;
        if (bool) {
          f1 = this.real;
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
    
    public static ViewGroupManager<ViewGroup> a(f<ViewGroup> param1f) {
      l.b(param1f, "real");
      ViewGroupManagerWrapper viewGroupManagerWrapper = new ViewGroupManagerWrapper(param1f, null);
      try {
        Method[] arrayOfMethod = param1f.getClass().getDeclaredMethods();
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
                  Map<String, String> map1 = viewGroupManagerWrapper.typeStringMap;
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
                  Map<String, Class<?>> map = viewGroupManagerWrapper.typeMap;
                  String str2 = bulletProp.name();
                  Class<?> clazz1 = arrayOfClass[1];
                  l.a(clazz1, "it[1]");
                  map.put(str2, clazz1);
                  viewGroupManagerWrapper.methodMap.put(bulletProp.name(), method);
                } else {
                  StringBuilder stringBuilder = new StringBuilder("First param should be a view subclass to be updated: ");
                  stringBuilder.append(viewGroupManagerWrapper.getName());
                  stringBuilder.append("#");
                  stringBuilder.append(method.getName());
                  throw (Throwable)new RuntimeException(stringBuilder.toString());
                } 
              } else {
                StringBuilder stringBuilder = new StringBuilder("Wrong number of args for prop setter: ");
                stringBuilder.append(viewGroupManagerWrapper.getName());
                stringBuilder.append("#");
                stringBuilder.append(method.getName());
                throw (Throwable)new RuntimeException(stringBuilder.toString());
              } 
            } 
            i++;
            continue;
          } 
          return viewGroupManagerWrapper;
        } 
      } finally {}
      return viewGroupManagerWrapper;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\wrapper\ViewGroupManagerWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */