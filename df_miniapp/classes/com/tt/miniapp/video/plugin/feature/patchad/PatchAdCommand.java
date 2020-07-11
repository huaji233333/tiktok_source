package com.tt.miniapp.video.plugin.feature.patchad;

import com.tt.miniapp.video.plugin.base.IVideoPluginCommand;

public class PatchAdCommand implements IVideoPluginCommand {
  public int code;
  
  public int command;
  
  public boolean isFullScreen;
  
  public boolean isPreRollAd;
  
  public String message;
  
  public PatchAdCommand(int paramInt, boolean paramBoolean) {
    this.command = paramInt;
    this.isPreRollAd = paramBoolean;
  }
  
  public int getCommand() {
    return this.command;
  }
  
  public Object getParams() {
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\patchad\PatchAdCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */