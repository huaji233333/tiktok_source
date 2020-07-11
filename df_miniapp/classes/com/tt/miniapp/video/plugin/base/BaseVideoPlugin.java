package com.tt.miniapp.video.plugin.base;

import android.content.Context;
import android.view.ViewGroup;

public class BaseVideoPlugin implements IVideoPlugin {
  private IVideoPluginHost mPluginHost;
  
  public Context getContext() {
    return (getHost() != null) ? getHost().getContext() : null;
  }
  
  public IVideoPluginHost getHost() {
    return this.mPluginHost;
  }
  
  public ViewGroup getPluginMainContainer() {
    IVideoPluginHost iVideoPluginHost = getHost();
    return (iVideoPluginHost != null) ? iVideoPluginHost.getPluginMainContainer() : null;
  }
  
  public int getPluginType() {
    return -1;
  }
  
  public boolean handleVideoEvent(IVideoPluginEvent paramIVideoPluginEvent) {
    return false;
  }
  
  public void onRegister(IVideoPluginHost paramIVideoPluginHost) {
    setHost(paramIVideoPluginHost);
  }
  
  public void onUnregister(IVideoPluginHost paramIVideoPluginHost) {
    setHost(null);
  }
  
  public void setHost(IVideoPluginHost paramIVideoPluginHost) {
    this.mPluginHost = paramIVideoPluginHost;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\base\BaseVideoPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */