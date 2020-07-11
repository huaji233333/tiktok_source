package com.facebook.react.uimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReactProp {
  String customType() default "__default_type__";
  
  boolean defaultBoolean() default false;
  
  double defaultDouble() default 0.0D;
  
  float defaultFloat() default 0.0F;
  
  int defaultInt() default 0;
  
  String name();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\annotations\ReactProp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */