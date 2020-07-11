package com.tt.miniapp.jsbridge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Jscore {
  String jsfunctionname();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\Jscore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */