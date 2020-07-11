package com.tt.miniapp.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tt.miniapp.map.AppbrandMapActivity;
import com.tt.miniapphost.entity.ChooseLocationResultEntity;
import com.tt.option.s.b;

public class HostOptionNormalDependImpl implements b {
  public boolean chooseLocationActivity(Activity paramActivity, int paramInt) {
    return false;
  }
  
  public long getUseDuration() {
    return -1L;
  }
  
  public ChooseLocationResultEntity handleChooseLocationResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return null;
  }
  
  public boolean openLocation(Activity paramActivity, String paramString1, String paramString2, double paramDouble1, double paramDouble2, int paramInt, String paramString3) {
    Intent intent = new Intent((Context)paramActivity, AppbrandMapActivity.class);
    intent.putExtra("name", paramString1);
    intent.putExtra("address", paramString2);
    intent.putExtra("latitude", paramDouble1);
    intent.putExtra("longitude", paramDouble2);
    intent.putExtra("scale", paramInt);
    paramActivity.startActivity(intent);
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionNormalDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */