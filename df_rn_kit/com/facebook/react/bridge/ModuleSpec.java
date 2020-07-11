package com.facebook.react.bridge;

import java.lang.reflect.Constructor;
import javax.a.a;

public class ModuleSpec {
  public static final Class[] CONTEXT_SIGNATURE;
  
  public static final Class[] EMPTY_SIGNATURE = new Class[0];
  
  private final a<? extends NativeModule> mProvider;
  
  private final Class<? extends NativeModule> mType;
  
  static {
    CONTEXT_SIGNATURE = new Class[] { ReactApplicationContext.class };
  }
  
  private ModuleSpec(Class<? extends NativeModule> paramClass, a<? extends NativeModule> parama) {
    this.mType = paramClass;
    this.mProvider = parama;
  }
  
  public static ModuleSpec nativeModuleSpec(Class<? extends NativeModule> paramClass, a<? extends NativeModule> parama) {
    return new ModuleSpec(paramClass, parama);
  }
  
  public static ModuleSpec simple(final Class<? extends NativeModule> type) {
    return new ModuleSpec(type, new ConstructorProvider(type, EMPTY_SIGNATURE) {
          public final NativeModule get() {
            try {
              return getConstructor(type, ModuleSpec.EMPTY_SIGNATURE).newInstance(new Object[0]);
            } catch (Exception exception) {
              StringBuilder stringBuilder = new StringBuilder("ModuleSpec with class: ");
              stringBuilder.append(type.getName());
              throw new RuntimeException(stringBuilder.toString(), exception);
            } 
          }
        });
  }
  
  public static ModuleSpec simple(final Class<? extends NativeModule> type, final ReactApplicationContext context) {
    return new ModuleSpec(type, new ConstructorProvider(type, CONTEXT_SIGNATURE) {
          public final NativeModule get() {
            try {
              return getConstructor(type, ModuleSpec.CONTEXT_SIGNATURE).newInstance(new Object[] { this.val$context });
            } catch (Exception exception) {
              StringBuilder stringBuilder = new StringBuilder("ModuleSpec with class: ");
              stringBuilder.append(type.getName());
              throw new RuntimeException(stringBuilder.toString(), exception);
            } 
          }
        });
  }
  
  public static ModuleSpec viewManagerSpec(a<? extends NativeModule> parama) {
    return new ModuleSpec(null, parama);
  }
  
  public a<? extends NativeModule> getProvider() {
    return this.mProvider;
  }
  
  public Class<? extends NativeModule> getType() {
    return this.mType;
  }
  
  static abstract class ConstructorProvider implements a<NativeModule> {
    protected Constructor<? extends NativeModule> mConstructor;
    
    public ConstructorProvider(Class<? extends NativeModule> param1Class, Class[] param1ArrayOfClass) {}
    
    protected Constructor<? extends NativeModule> getConstructor(Class<? extends NativeModule> param1Class, Class[] param1ArrayOfClass) throws NoSuchMethodException {
      Constructor<? extends NativeModule> constructor = this.mConstructor;
      return (constructor != null) ? constructor : param1Class.getConstructor(param1ArrayOfClass);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ModuleSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */