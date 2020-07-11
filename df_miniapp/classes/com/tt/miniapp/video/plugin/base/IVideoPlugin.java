package com.tt.miniapp.video.plugin.base;

public interface IVideoPlugin {
  IVideoPluginHost getHost();
  
  int getPluginType();
  
  boolean handleVideoEvent(IVideoPluginEvent paramIVideoPluginEvent);
  
  void onRegister(IVideoPluginHost paramIVideoPluginHost);
  
  void onUnregister(IVideoPluginHost paramIVideoPluginHost);
  
  void setHost(IVideoPluginHost paramIVideoPluginHost);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\base\IVideoPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */