package com.facebook.react.module.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ReactModule {
  boolean canOverrideExistingModule() default false;
  
  boolean hasConstants() default true;
  
  String name();
  
  boolean needsEagerInit() default false;
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\module\annotations\ReactModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */