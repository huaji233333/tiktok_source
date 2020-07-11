package com.facebook.react.module.annotations;

import com.facebook.react.bridge.NativeModule;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface ReactModuleList {
  Class<? extends NativeModule>[] nativeModules();
  
  Class<? extends NativeModule>[] viewManagers() default {};
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\module\annotations\ReactModuleList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */