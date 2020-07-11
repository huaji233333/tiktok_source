package com.tt.miniapp.settings.configs;

import android.content.Context;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;

public class SettingsConfigImp {
  public SettingsConfig create() {
    return (new SettingsConfig.Builder()).setContext((Context)AppbrandContext.getInst().getApplicationContext()).setRequestService(HostDependManager.getInst().createSettingsResponseService()).build();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\configs\SettingsConfigImp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */