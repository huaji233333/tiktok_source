package com.tt.option.s;

import android.app.Activity;
import android.content.Intent;
import com.tt.miniapp.impl.HostOptionNormalDependImpl;
import com.tt.miniapphost.entity.ChooseLocationResultEntity;
import com.tt.option.a;

public class a extends a<b> implements b {
  public boolean chooseLocationActivity(Activity paramActivity, int paramInt) {
    return inject() ? ((b)this.defaultOptionDepend).chooseLocationActivity(paramActivity, paramInt) : false;
  }
  
  public long getUseDuration() {
    return inject() ? ((b)this.defaultOptionDepend).getUseDuration() : -1L;
  }
  
  public ChooseLocationResultEntity handleChooseLocationResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return inject() ? ((b)this.defaultOptionDepend).handleChooseLocationResult(paramInt1, paramInt2, paramIntent) : null;
  }
  
  public boolean openLocation(Activity paramActivity, String paramString1, String paramString2, double paramDouble1, double paramDouble2, int paramInt, String paramString3) {
    return inject() ? ((b)this.defaultOptionDepend).openLocation(paramActivity, paramString1, paramString2, paramDouble1, paramDouble2, paramInt, paramString3) : false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\s\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */