package com.bytedance.ies.bullet.kit.rn.internal.wrapper;

import com.bytedance.ies.bullet.kit.rn.core.BulletMethod;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.IDynamicJavaMethodsFactory;
import com.facebook.react.bridge.NativeModule;
import d.f;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class NativeModuleWrapper extends BaseJavaModule implements IDynamicJavaMethodsFactory {
  public static final a Companion = new a(null);
  
  public static List<String> DYNAMIC_METHODS = new ArrayList<String>();
  
  public static Map<String, Method> DYNAMIC_METHODS_MAP = new LinkedHashMap<String, Method>();
  
  private final f dynamicMethodsTmp$delegate;
  
  public final com.bytedance.ies.bullet.kit.rn.core.b real;
  
  private NativeModuleWrapper(com.bytedance.ies.bullet.kit.rn.core.b paramb) {
    this.real = paramb;
    this.dynamicMethodsTmp$delegate = g.a(new b(this));
  }
  
  private final Map<String, NativeModule.NativeMethod> getDynamicMethodsTmp() {
    return (Map<String, NativeModule.NativeMethod>)this.dynamicMethodsTmp$delegate.getValue();
  }
  
  public final Map<String, Object> getConstants() {
    return this.real.getConstants();
  }
  
  public final Map<String, NativeModule.NativeMethod> getDynamicMethods() {
    return getDynamicMethodsTmp();
  }
  
  public final String getName() {
    return this.real.getName();
  }
  
  public final boolean hasConstants() {
    return this.real.hasConstants();
  }
  
  public final void initialize() {
    this.real.initialize();
  }
  
  public final void onCatalystInstanceDestroy() {
    this.real.onCatalystInstanceDestroy();
  }
  
  public static final class a {
    private a() {}
    
    public static NativeModule a(com.bytedance.ies.bullet.kit.rn.core.b param1b) {
      l.b(param1b, "real");
      Method[] arrayOfMethod = param1b.getClass().getDeclaredMethods();
      l.a(arrayOfMethod, "real.javaClass.declaredMethods");
      int j = arrayOfMethod.length;
      for (int i = 0; i < j; i++) {
        Method method = arrayOfMethod[i];
        if ((BulletMethod)method.<BulletMethod>getAnnotation(BulletMethod.class) != null) {
          List<String> list = NativeModuleWrapper.DYNAMIC_METHODS;
          l.a(method, "method");
          if (!list.contains(method.getName())) {
            list = NativeModuleWrapper.DYNAMIC_METHODS;
            String str = method.getName();
            l.a(str, "method.name");
            list.add(str);
            Map<String, Method> map = a();
            str = method.getName();
            l.a(str, "method.name");
            map.put(str, method);
          } 
        } 
      } 
      return (NativeModule)new NativeModuleWrapper(param1b, null);
    }
    
    public static Map<String, Method> a() {
      return NativeModuleWrapper.DYNAMIC_METHODS_MAP;
    }
  }
  
  static final class b extends m implements d.f.a.a<Map<String, NativeModule.NativeMethod>> {
    b(NativeModuleWrapper param1NativeModuleWrapper) {
      super(0);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\wrapper\NativeModuleWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */