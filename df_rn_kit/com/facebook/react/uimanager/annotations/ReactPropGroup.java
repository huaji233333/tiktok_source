package com.facebook.react.uimanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReactPropGroup {
  String customType() default "__default_type__";
  
  double defaultDouble() default 0.0D;
  
  float defaultFloat() default 0.0F;
  
  int defaultInt() default 0;
  
  String[] names();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\annotations\ReactPropGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */