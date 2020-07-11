package com.tt.miniapphost.process.annotation.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface TransferData {
  String[] callKeys();
  
  String[] callbackKeys() default {};
  
  String[] resultKeys() default {};
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\annotation\data\TransferData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */