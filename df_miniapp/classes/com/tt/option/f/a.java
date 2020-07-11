package com.tt.option.f;

import android.app.Activity;
import com.tt.option.a;
import java.util.HashMap;

public final class a extends a<c> implements c {
  public final void startFaceLive(Activity paramActivity, HashMap<String, String> paramHashMap, b paramb) {
    if (inject()) {
      ((c)this.defaultOptionDepend).startFaceLive(paramActivity, paramHashMap, paramb);
      return;
    } 
    if (paramb != null)
      paramb.onResult(-1, "feature not support now"); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\f\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */