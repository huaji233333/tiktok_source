package com.bytedance.sandboxapp.b;

import android.app.Activity;
import android.content.Context;

public interface a {
  Context getApplicationContext();
  
  Activity getCurrentActivity();
  
  <T extends b> T getService(Class<T> paramClass);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */