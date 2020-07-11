package com.tt.miniapp.video.plugin.base;

import android.content.Context;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

public class BaseVideoPluginHost implements IVideoPluginHost {
  private ArrayList<IVideoPlugin> mPlugins;
  
  public void addPlugin(IVideoPlugin paramIVideoPlugin) {
    if (this.mPlugins == null)
      this.mPlugins = new ArrayList<IVideoPlugin>(); 
    if (paramIVideoPlugin != null) {
      if (!this.mPlugins.contains(paramIVideoPlugin))
        this.mPlugins.add(paramIVideoPlugin); 
      paramIVideoPlugin.onRegister(this);
    } 
  }
  
  public void execCommand(int paramInt) {}
  
  public void execCommand(IVideoPluginCommand paramIVideoPluginCommand) {}
  
  public IVideoPlugin findPlugin(int paramInt) {
    ArrayList<IVideoPlugin> arrayList = this.mPlugins;
    if (arrayList == null)
      return null; 
    for (IVideoPlugin iVideoPlugin : arrayList) {
      if (iVideoPlugin != null && iVideoPlugin.getPluginType() == paramInt)
        return iVideoPlugin; 
    } 
    return null;
  }
  
  public Context getContext() {
    return null;
  }
  
  public ViewGroup getPluginMainContainer() {
    return null;
  }
  
  public ArrayList<IVideoPlugin> getVideoPlugins() {
    return this.mPlugins;
  }
  
  public boolean notifyVideoPluginEvent(IVideoPluginEvent paramIVideoPluginEvent) {
    boolean bool2 = false;
    boolean bool1 = false;
    if (paramIVideoPluginEvent == null)
      return false; 
    ArrayList<IVideoPlugin> arrayList = this.mPlugins;
    if (arrayList != null) {
      Iterator<IVideoPlugin> iterator = arrayList.iterator();
      while (true) {
        bool2 = bool1;
        if (iterator.hasNext()) {
          if (((IVideoPlugin)iterator.next()).handleVideoEvent(paramIVideoPluginEvent))
            bool1 = true; 
          continue;
        } 
        break;
      } 
    } 
    return bool2;
  }
  
  public void removePlugin(IVideoPlugin paramIVideoPlugin) {
    if (paramIVideoPlugin != null) {
      ArrayList<IVideoPlugin> arrayList = this.mPlugins;
      if (arrayList != null)
        arrayList.remove(paramIVideoPlugin); 
      paramIVideoPlugin.onUnregister(this);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\base\BaseVideoPluginHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */