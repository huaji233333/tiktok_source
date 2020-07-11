package com.tt.miniapp.settings.configs;

import android.content.Context;
import com.tt.miniapp.settings.net.RequestService;

public class SettingsConfig {
  private Context context;
  
  private RequestService requestService;
  
  public SettingsConfig(Context paramContext, RequestService paramRequestService) {
    this.context = paramContext;
    this.requestService = paramRequestService;
  }
  
  public Context getContext() {
    return this.context;
  }
  
  public RequestService getRequestService() {
    return this.requestService;
  }
  
  public void setContext(Context paramContext) {
    this.context = paramContext;
  }
  
  public void setRequestService(RequestService paramRequestService) {
    this.requestService = paramRequestService;
  }
  
  public static class Builder {
    private Context context;
    
    private RequestService requestService;
    
    public SettingsConfig build() {
      Context context = this.context;
      if (context != null) {
        RequestService requestService = this.requestService;
        if (requestService != null)
          return new SettingsConfig(context, requestService); 
        throw new IllegalArgumentException("requestService 不能为空");
      } 
      throw new IllegalArgumentException("context 不能为空");
    }
    
    public Builder setContext(Context param1Context) {
      this.context = param1Context;
      return this;
    }
    
    public Builder setRequestService(RequestService param1RequestService) {
      this.requestService = param1RequestService;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\configs\SettingsConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */