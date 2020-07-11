package com.tt.option.s;

import android.app.Activity;
import android.content.Intent;
import com.tt.miniapphost.entity.ChooseLocationResultEntity;

public interface b {
  boolean chooseLocationActivity(Activity paramActivity, int paramInt);
  
  long getUseDuration();
  
  ChooseLocationResultEntity handleChooseLocationResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  boolean openLocation(Activity paramActivity, String paramString1, String paramString2, double paramDouble1, double paramDouble2, int paramInt, String paramString3);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\s\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */