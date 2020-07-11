package com.tt.miniapp.video.plugin.base;

import android.content.Context;
import android.view.ViewGroup;
import java.util.ArrayList;

public interface IVideoPluginHost {
  void addPlugin(IVideoPlugin paramIVideoPlugin);
  
  void execCommand(int paramInt);
  
  void execCommand(IVideoPluginCommand paramIVideoPluginCommand);
  
  IVideoPlugin findPlugin(int paramInt);
  
  Context getContext();
  
  ViewGroup getPluginMainContainer();
  
  ArrayList<IVideoPlugin> getVideoPlugins();
  
  boolean notifyVideoPluginEvent(IVideoPluginEvent paramIVideoPluginEvent);
  
  void removePlugin(IVideoPlugin paramIVideoPlugin);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\base\IVideoPluginHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */